package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.concurrent.futures.CallbackToFutureAdapter
import com.example.weatherapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class signUp : AppCompatActivity() {
   // private lateinit var binding: ActivitySignUpBinding // Declare binding variable
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var database: DatabaseReference
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private fun createAccount(email: String, password: String) {
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
        if(task.isSuccessful){
            Toast.makeText(this,"Account is Created Successfully",Toast.LENGTH_SHORT).show()
            saveUserData()
            val intent=Intent(this,logIn::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this,"Account Creation Failed, please check the details or try again",Toast.LENGTH_SHORT).show()
            Log.d("Account","Create Account Failed",task.exception)
        }
    }
}

    private fun saveUserData() {
        userName = binding.userName.text.toString().trim()
        email = binding.EmailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName,email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // SAVE USER DATA INTO FIREBASE
        database.child("user").child(userId).setValue(user)

}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialize Firebase auth
        auth = Firebase.auth
        // Initialize Firebase database
        database = Firebase.database.reference

        // get text form Edit Text
        binding.createAccountButton.setOnClickListener {
            val email = binding.EmailAddress.text.toString()
            val password = binding.password.text.toString()
            val userName = binding.userName.text.toString()
            if (email.isBlank() || password.isBlank() || userName.isBlank()) {
                Toast.makeText(this, "Please Fill the Details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }


        }
        binding.loginBTN.setOnClickListener {
            val i = Intent(this, logIn::class.java)
            startActivity(i)
        }
    }
}
