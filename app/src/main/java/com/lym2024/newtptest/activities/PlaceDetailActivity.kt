package com.lym2024.newtptest.activities

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.Title
import com.lym2024.newtptest.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlaceDetailBinding.inflate(layoutInflater) }
    private lateinit var title :Title
    private lateinit var db : SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val s : String? = intent.getStringExtra("url")
        s?.also {

            title = Gson().fromJson(it, Title::class.java)
            binding.wv.webViewClient = WebViewClient() // 현재 웹뷰안에서 웹문서를 열리도록..
            binding.wv.webChromeClient = WebChromeClient() // 웹문서안에서 다이얼로그나 팝업같은 것들이 발동하도록..
            binding.wv.settings.javaScriptEnabled = true // 웹뷰는 기본적으로 보안문제로 JS 동작을 막아놓았기에.. 허용 하도록..
            binding.wv.loadUrl(title.url)
        }
        db = openOrCreateDatabase("url", MODE_PRIVATE, null)

        // "favor"라는 이름의 표(테이블) 만들기 - SQL 쿼리문을 사용하기.. CRUD 작업수행
        db.execSQL("CREATE TABLE IF NOT EXISTS favor(id TEXT PRIMARY KEY, title TEXT, type TEXT, contactPoint TEXT, url TEXT )")
    }
}