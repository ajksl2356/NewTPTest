package com.lym2024.newtptest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.lym2024.newtptest.G
import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.UserAccount
import com.lym2024.newtptest.databinding.ActivityLoginBinding
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

        binding.tvGo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.tvSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }

        binding.layoutEmailLogin.setOnClickListener { startActivity(Intent(this, EmailLoginActivity::class.java)) }

        binding.btnLoginKakao.setOnClickListener { clickKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickNaver() }
    }
    private fun clickKakao(){

        val callback : (OAuthToken?, Throwable?)->Unit = { token, error ->
            if (error != null ){
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "카카오 로그인 성공!!", Toast.LENGTH_SHORT).show()


                UserApiClient.instance.me { user, error ->
                    if (user != null){
                        val id : String = user.id.toString()
                        val nickname : String = user.kakaoAccount?.profile?.nickname ?: ""

                        Toast.makeText(this, "$id\n$nickname", Toast.LENGTH_SHORT).show()
                        G.userAccount = UserAccount(id, nickname)

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk( this, callback = callback )
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this, callback =  callback)
        }
    }
private fun clickGoogle(){
    //Toast.makeText(this, "구글 로그인", Toast.LENGTH_SHORT).show()

    // 로그인 옵션객체 생성 -  Builder - 이메일 요청..
    val signInOptions : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

    // 구글 로그인을 하는 화면 액티비티를 실행하는 Intent 객체로 로그인 구현
    val intent : Intent = GoogleSignIn.getClient(this, signInOptions).signInIntent
    resultLauncher.launch(intent)
}

// 구글 로그인 화면 결과를 받아주는 대행사 등록
val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    // 로그인 결과를 가져온 인텐트 소환
    val intent : Intent? = it.data
    // 인텐트로 부터 구성 계정 정보를 가저오는 작업자 객체를 소환..
    val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

    // 작업자로부터 계정 받기
    val account : GoogleSignInAccount = task.result
    val id : String = account.id.toString()
    val email : String = account.email ?: ""

    Toast.makeText(this, "$id\n$email", Toast.LENGTH_SHORT).show()
    G.userAccount = UserAccount(id, email)

    // main 화면으로 이동
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}
//
private fun clickNaver(){
//
//    // 네아로 SDK 초기화
//    NaverIdLoginSDK.initialize(this, "Rc9jB4t9RC9cdVJ4Y_8n","bXGqaINhRQ","검색")
//
//    //로그인 요청
//    NaverIdLoginSDK.authenticate(this, object  : OAuthLoginCallback {
//        override fun onError(errorCode: Int, message: String) {
//            TODO("Not yet implemented")
//            Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onFailure(httpStatus: Int, message: String) {
//            Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onSuccess() {
//            Toast.makeText(this@LoginActivity, "로그인성공", Toast.LENGTH_SHORT).show()
//
//            // 사용자 정보를 받아오기.. -- REST API 로 받아야 함.
//            // 로그인에 성공하면. REST API로 요청할 수 있는 토큰(token)을 발급받음.
//            val accessToken : String? = NaverIdLoginSDK.getAccessToken()
//
//            // Retrofit 작업을 통해 사용자 정보 가져오기
//            val retrofit = RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
//            val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
//            val call = retrofitApiService.getNidUserInfo("Bearer ${accessToken}")
//            call.enqueue(object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    val s = response.body()
//                    AlertDialog.Builder(this@LoginActivity).setMessage(s).create().show()
//
//                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
//                    finish()
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
//                }
//
//            })
        }
//    })
}

