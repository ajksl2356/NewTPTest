package com.lym2024.newtptest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.data
import com.lym2024.newtptest.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    val binding by lazy { ActivityPasswordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { clickSave() }
        finish()
    }
    private fun clickSave() {
        val newPassword: String = binding.inputLayoutPassword.editText!!.text.toString()
        val userEmail: String = binding.inputLayoutEmail.editText!!.text.toString()
        val data:MutableMap<String,Any> = mutableMapOf()

        data["email"] = userEmail
        data["password"] = newPassword

        val userRef: CollectionReference = Firebase.firestore.collection("emailUsers")
        val user: data = data(userEmail, newPassword)
        userRef.document().set(user).addOnSuccessListener {
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show() }

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this, EmailLoginActivity::class.java))
        }
        finish()
    }
}