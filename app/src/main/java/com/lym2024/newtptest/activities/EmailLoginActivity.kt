package com.lym2024.newtptest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lym2024.newtptest.R
import com.lym2024.newtptest.databinding.ActivityEmailLoginBinding

class EmailLoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnSignin.setOnClickListener { clickSignup() }
        binding.btnChange.setOnClickListener { clickChange() }
    }
    private fun clickSignup(){
        val email = binding.inputLayoutEmail.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()
        val userRef : CollectionReference = Firebase.firestore.collection("emailUser")
    }
    private fun clickChange(){
        binding.btnChange.setOnClickListener {
            startActivity(Intent(this@EmailLoginActivity, PasswordActivity::class.java))
            finish()
        }
    }
}