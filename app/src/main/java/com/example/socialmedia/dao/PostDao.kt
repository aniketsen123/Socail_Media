package com.example.socialmedia.dao

import com.example.socialmedia.Post
import com.example.socialmedia.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db=FirebaseFirestore.getInstance()
    val postCollections=db.collection("post")
    val  auth=Firebase.auth
fun addPost(text:String)
{
    //curretn user id
    val currentuser=auth.currentUser!!.uid
    GlobalScope.launch (Dispatchers.IO){
        val userDao = Dao()
        val user = userDao.getUserbyid(currentuser).await().toObject(User::class.java)!!
        val currentime=System.currentTimeMillis()
        val post=Post(text,user,currentime)
        postCollections.document().set(post)

    }
}
    fun getPostById(postId: String):Task<DocumentSnapshot>
    {
        return postCollections.document(postId).get()
    }
    fun updatelikes(postId:String)
    {
        val currentuser=auth.currentUser!!.uid
        GlobalScope.launch {
            val post = getPostById(postId).await().toObject(Post::class.java)
            val isLiked=post!!.like.contains((currentuser))
            if(isLiked)
            {
                post.like.remove(currentuser)
            }
            else
            {
                post.like.add(currentuser)
            }
            postCollections.document(postId).set(post)
        }
    }
}