package com.lym2024.newtptest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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
    }

    private fun clickSave() {
        val newPassword: String = binding.inputLayoutPassword.editText!!.text.toString()
        val userEmail: String = binding.inputLayoutEmail.editText!!.text.toString()
        // 사용자가 입력한 이메일로 비밀번호 업데이트
        val userCollectionRef = FirebaseFirestore.getInstance().collection("emailUsers")

// 해당 이메일 주소를 가진 사용자 문서를 찾음
        userCollectionRef.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // 문서가 존재하는 경우 비밀번호 업데이트
                    val userDocRef = documents.documents[0].reference
                    userDocRef.update("password", newPassword)
                        .addOnSuccessListener {
                            Toast.makeText(this, "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e -> Toast.makeText(this, "비밀번호 변경에 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "해당 이메일 주소의 사용자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e -> Toast.makeText(this, "문서를 가져오는 도중에 오류가 발생했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

//    private fun clickSave() {
//        val newPassword: String = binding.inputLayoutPassword.editText!!.text.toString()
//        val userEmail: String = binding.inputLayoutEmail.editText!!.text.toString()
//        val data:MutableMap<String,Any> = mutableMapOf()
//
//        data["email"] = userEmail
//        data["password"] = newPassword
//
//        val userRef: CollectionReference = Firebase.firestore.collection("emailUsers")
//        val user: data = data(userEmail, newPassword)
//        userRef.add(user).addOnSuccessListener {
//            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show() }
//
//        binding.btnSave.setOnClickListener {
//            startActivity(Intent(this, EmailLoginActivity::class.java))
//        }
//        finish()
//    }

