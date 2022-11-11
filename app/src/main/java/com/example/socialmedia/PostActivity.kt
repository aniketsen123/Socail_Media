package com.example.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialmedia.dao.PostDao
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        button.setOnClickListener{
            postDao=PostDao()
           val input= enter.text.toString().trim()
            if(input.isNotEmpty())
            {
              postDao.addPost(input)
                finish()
            }
        }

    }
}