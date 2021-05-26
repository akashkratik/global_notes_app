package com.example.globalnotesapp.daos

import android.widget.Toast
import com.example.globalnotesapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    val userCollections = db.collection("users")


    fun addUser(user: User?){
        user?.let{
            GlobalScope.launch(Dispatchers.IO){
                userCollections.document(user.uid).set(it)
            }
        }

    }
    fun getUserById(uid: String): Task<DocumentSnapshot>{
        return userCollections.document(uid).get()
    }
    fun addNote(note: String, uid: String){
        GlobalScope.launch {
            val user = getUserById(uid).await().toObject(User::class.java)!!
            user.posts.add(note)
            userCollections.document(uid).set(user)
        }
    }

    fun getNotes(uid: String): ArrayList<String>{
        var user: User
        var posts: ArrayList<String> = ArrayList()
        GlobalScope.launch {
            val response = getUserById(uid)
            user = response.await().toObject(User::class.java)!!
            posts = user.posts
        }
        return posts
    }

}