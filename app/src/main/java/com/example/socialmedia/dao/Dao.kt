package com.example.socialmedia.dao

import com.example.socialmedia.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Dao {
    val db = FirebaseFirestore.getInstance()
    val userCollection = db.collection("users")
    fun adduser(user: User) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.uid).set(it)
            }
        }
    }
    fun getUserbyid(uId:String): Task<DocumentSnapshot> {
        return userCollection.document(uId).get()
    }
}