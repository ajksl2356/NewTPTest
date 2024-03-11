package com.lym2024.newtptest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lym2024.newtptest.R
import com.lym2024.newtptest.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnSignup.setOnClickListener { clickSignUp() }
    }
    private fun clickSignUp(){

        var email = binding.inputLayoutEmail.editText!!.text.toString()
        var password = binding.inputLayoutPassword.editText!!.text.toString()
        var passwordConfirm = binding.inputLayoutPasswordConfirm.editText!!.text.toString()

        if (password != passwordConfirm){
            AlertDialog.Builder(this).setMessage("패스워드 확인에 문제가 있습니다. 다시 확인하여 입력해주시기 바랍니다.").create().show()
            binding.inputLayoutPasswordConfirm.editText!!.selectAll()
            return
        }
        val userRef : CollectionReference = Firebase.firestore.collection("emailUsers")

        userRef.whereEqualTo("email", email).get().addOnSuccessListener {
            if (it.documents.size>0){
                AlertDialog.Builder(this).setMessage("중복된 이메일이 있습니당. 다시 확인하여 입력해 주시기 바랍니다.").create().show()
                binding.inputLayoutEmail.editText!!.requestFocus()
                binding.inputLayoutEmail.editText!!.selectAll()
            }else{
                val user : MutableMap<String, String> = mutableMapOf()
                user["email"] = email
                user["password"] = password
                userRef.document().set(user).addOnSuccessListener {
                    AlertDialog.Builder(this)
                        .setMessage("축하합니다.\n회원가입이 완료 되었습니다.")
                        .setPositiveButton("확인", { p0,p1 -> finish() })
                        .create().show()
                }// -------------------------------------------------------------------

            }
        }
    }
}