package com.example.globalnotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalnotesapp.daos.UserDao
import com.example.globalnotesapp.models.User
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class NotesActivity : AppCompatActivity() {
    private lateinit var uid: String
    private lateinit var userDao: UserDao
    private lateinit var adapter: NotesAdapter
    private lateinit var rvNotes: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    companion object{
        lateinit var notesList: ArrayList<String>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        uid = intent.getStringExtra("userId")!!
        userDao = UserDao()
        notesList = ArrayList()
        rvNotes = findViewById(R.id.rv_note_list)
        var user:User
        adapter = NotesAdapter()
        rvNotes.adapter = adapter
        userDao.userCollections.document(uid).get()
                .addOnSuccessListener { DocumentSnapshot ->
                    user = DocumentSnapshot.toObject(User::class.java)!!
                    for(post in user.posts){
                        notesList.add(post)
                    }
                    adapter.updateNotesList(notesList)
                }

        Log.d("posts", "in ${notesList.size}")
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    fun onBackBtnPressed(view: View) {
        finish()
    }

}