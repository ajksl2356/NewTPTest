package com.lym2024.newtptest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.android.gms.tasks.OnFailureListener
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
        binding.btnLogin.setOnClickListener { clickLogin() }
        binding.btnChange.setOnClickListener { clickChange() }
    }

    private fun clickLogin() {
        val email = binding.inputLayoutEmail.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()
        val userRef: CollectionReference = Firebase.firestore.collection("emailUsers")

        if (email.isNotEmpty() && password.isNotEmpty()) {
            userRef.whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "이메일 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(exception: Exception) {
                        Toast.makeText(this@EmailLoginActivity, "로그인 중 오류가 발생했습니다: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clickChange(){
        startActivity(Intent(this,PasswordActivity::class.java))
    }
}