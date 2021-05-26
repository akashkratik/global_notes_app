package com.example.globalnotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View

import android.widget.EditText
import android.widget.Toast
import com.example.globalnotesapp.daos.UserDao
import com.example.globalnotesapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etDob: EditText
    private lateinit var etAge: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private val TAG = "Login"
    private var flag = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etDob = findViewById(R.id.et_dob)
        etAge = findViewById(R.id.et_age)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            updateUI(currentUser)
        }
    }

    fun initiateLogin(view: View) {

        when {
            TextUtils.isEmpty(etName.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(etEmail.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(etDob.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter DoB", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(etAge.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter Age", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(etPassword.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(etConfirmPassword.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }
            !etPassword.text.toString().equals(etConfirmPassword.text.toString()) -> {
                Toast.makeText(this, "Password not confirmed", Toast.LENGTH_SHORT).show()
            }else -> {
            val email = etEmail.text.toString().trim(){it <= ' '}
            val password = etPassword.text.toString().trim(){it <= ' '}
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null){
            if(flag==0){
                val user = User(firebaseUser.uid, etName.text.toString(), etAge.text.toString(), etEmail.text.toString())
                val userDao = UserDao()
                userDao.addUser(user)
                flag = 1
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}