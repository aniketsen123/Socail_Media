package com.example.socialmedia


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmedia.dao.PostDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IpostAdapter {
    private lateinit var adapter: RecycleAdapter
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab=findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
 val intent=Intent(this,PostActivity::class.java)
            startActivity(intent)
        }
        setuprecycleview()
    }

    private fun setuprecycleview() {
        postDao= PostDao()
        val postcollection=postDao.postCollections
        val query=postcollection.orderBy("createdAt", Query.Direction.DESCENDING)
        val recycleviewoption=FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
         adapter= RecycleAdapter(recycleviewoption,this)
        recycleview.adapter=adapter
        recycleview.layoutManager=LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onItemClicked(postId: String) {
        postDao.updatelikes(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.iv_refresh->out()
        }
        return super.onOptionsItemSelected(item)
    }
    fun sigout()
    {
Firebase.auth.signOut()
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build()
        val googlesignout=GoogleSignIn.getClient(this,gso)
        googlesignout.signOut()
        val intent=Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun out()
    {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("SIGN OUT")
        builder.setPositiveButton("YES"){
            dialogiterface,_->
                     sigout()
        }
        builder.setNegativeButton("NO"){
            dialoginterface,_->
            dialoginterface.dismiss()
        }
        val alertDialog:AlertDialog=builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }
}
