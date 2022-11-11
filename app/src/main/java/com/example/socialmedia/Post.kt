package com.example.socialmedia

import com.example.socialmedia.model.User

data class Post (
 val text:String="",
 val user:User=User(),
 val createdAt:Long=0L,
 val like:ArrayList<String> = ArrayList())