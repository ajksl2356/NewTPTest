package com.lym2024.newtptest.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.lym2024.newtptest.G
import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.UserAccount
import com.lym2024.newtptest.databinding.ActivityLoginBinding
import com.lym2024.newtptest.network.RetrofitApiService
import com.lym2024.newtptest.network.RetrofitHelper
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvGo.setOnClickListener { startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.tvSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }
        binding.layoutEmailLogin.setOnClickListener { startActivity(Intent(this, EmailLoginActivity::class.java))
        }
        binding.btnLoginKakao.setOnClickListener { clickKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickNaver() }
    }
    private fun clickKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 로그인 취소", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "카카오 로그인 성공!!", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        val id: String = user.id.toString()
                        val nickname: String = user.kakaoAccount?.profile?.nickname ?: ""

                        Toast.makeText(this, "$id\n$nickname", Toast.LENGTH_SHORT).show()
                        G.userAccount = UserAccount(id, nickname)

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
    private fun clickGoogle() {
        val signInOptions: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val intent: Intent = GoogleSignIn.getClient(this, signInOptions).signInIntent
        resultLauncher.launch(intent)
    }
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val intent: Intent? = it.data
        if (it.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val id: String = account.id.toString()
            val email: String = account.email ?: ""
            Toast.makeText(this, "$id\n$email", Toast.LENGTH_SHORT).show()
            G.userAccount = UserAccount(id, email)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "로그인 취소", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clickNaver() {
        NaverIdLoginSDK.initialize(this, "5ctHj5RXe5j7BVwlHBDk", "tUTH849W4L", "예술공작소")
        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                TODO("Not yet implemented")
                Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
            }
            override fun onSuccess() {
                Toast.makeText(this@LoginActivity, "로그인성공", Toast.LENGTH_SHORT).show()
                val accessToken: String? = NaverIdLoginSDK.getAccessToken()
                val retrofit = RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
                val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
                val call = retrofitApiService.getNidUserInfo("Bearer ${accessToken}")
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val s = response.body()
                        AlertDialog.Builder(this@LoginActivity).setMessage(s).create().show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }
}

