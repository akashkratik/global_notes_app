package com.example.globalnotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.globalnotesapp.daos.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddNoteActivity : AppCompatActivity() {
    private lateinit var noteText: EditText
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        noteText = findViewById(R.id.et_note_text)
        userDao = UserDao()
    }

    fun createNoteAndAddToDB(view: View) {
        when{
            TextUtils.isEmpty(noteText.text.toString().trim(){it <= ' '}) -> {
                Toast.makeText(this, "Your note cannot be empty.", Toast.LENGTH_SHORT).show()
            }else->{
                val auth = Firebase.auth
                val currentUserId = auth.currentUser!!.uid
                userDao.addNote(noteText.text.toString(), currentUserId)
                finish()
            }
        }
    }
}