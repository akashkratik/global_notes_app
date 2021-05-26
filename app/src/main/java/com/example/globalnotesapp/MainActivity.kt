package com.example.globalnotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalnotesapp.daos.UserDao
import com.example.globalnotesapp.models.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity(), IUserAdapter {

    private lateinit var rvUsers: RecyclerView
    private lateinit var adapter: UsersAdapter
    private lateinit var userDao: UserDao
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDao = UserDao()
        rvUsers = findViewById(R.id.rv_users)
        tvUserName = findViewById(R.id.tv_user_name)
        tvUserEmail = findViewById(R.id.tv_user_email)
        setUpCurrentUser()
        setUpRecyclerView()
    }

    private fun setUpCurrentUser() {
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        var user: User
        userDao.userCollections.document(currentUserId).get()
            .addOnSuccessListener { DocumentSnapshot ->
                user = DocumentSnapshot.toObject(User::class.java)!!
                tvUserName.text = user.name
                tvUserEmail.text = user.email
            }
    }

    private fun setUpRecyclerView() {
        val userCollection = userDao.userCollections
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<User>().setQuery(
            userCollection,
            User::class.java
        ).build()
        adapter = UsersAdapter(recyclerViewOption, this)
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)
    }

    fun showAllNotes(view: View) {
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("userId", currentUserId)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    fun addNote(view: View) {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onCardClicked(uid: String) {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("userId", uid)
        startActivity(intent)
    }
}