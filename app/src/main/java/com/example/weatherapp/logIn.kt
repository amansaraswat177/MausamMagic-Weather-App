package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherapp.databinding.ActivityLogInBinding
import com.example.weatherapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class logIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginbtn.setOnClickListener {


            if (binding.emailAddress.text.isEmpty() or binding.emailAddress.text.isEmpty()) {
                Toast.makeText(this, "Please fill the foollowing details", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Firebase.auth.signInWithEmailAndPassword(
                    binding.emailAddress.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Your details is incorrect", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        binding.donthavebtn.setOnClickListener {
          val intent =   Intent(this,signUp::class.java)
            startActivity(intent)
            finish()
        }

    }
}