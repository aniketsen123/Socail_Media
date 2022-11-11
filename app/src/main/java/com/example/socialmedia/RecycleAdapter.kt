package com.example.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecycleAdapter(options: FirestoreRecyclerOptions<Post>,val listerner:IpostAdapter) :FirestoreRecyclerAdapter<Post,RecycleAdapter.postviewHolder>(
    options
)  {
    class postviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postviewHolder {
         val holder=postviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itempost,parent,false))
            holder.likeButton.setOnClickListener{
                   listerner.onItemClicked(snapshots.getSnapshot(holder.adapterPosition).id)
            }
        return holder
    }

    override fun onBindViewHolder(holder: postviewHolder, position: Int, model: Post) {
        holder.postText.text=model.text
        holder.userText.text=model.user.name
        Glide.with(holder.userImage.context).load(model.user.img).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.like.size.toString()
        holder.createdAt.text=utils.getTimeAgo(model.createdAt)

        val auth=Firebase.auth
        val current=auth.currentUser!!.uid
        val isLiked=model.like.contains(current)
        if(isLiked)
        {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_baseline_favorite_24))
        }
        else
        {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,
                R.drawable.ic_baseline_favorite_border_24
            ))
        }
    }
}
interface IpostAdapter
{
    fun onItemClicked(postId:String)
}