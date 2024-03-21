package com.lym2024.newtptest.activities

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.gson.Gson
import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.Title
import com.lym2024.newtptest.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlaceDetailBinding.inflate(layoutInflater) }
    private var isFavorite = false
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
        db.execSQL("CREATE TABLE IF NOT EXISTS fav (title TEXT PRIMARY KEY, type TEXT, period TEXT, eventPeriod TEXT, eventSite TEXT, charge TEXT, contactPoint TEXT, url TEXT, imageObject TEXT, description TEXT, viewCount TEXT )") //이거를 갯수 맞추어야 하는지 한번 확인해봐야함 - 페이버릿 프레그먼트랑 같이 확인해야함
        isFavorite = checkFavorite()
        if (isFavorite) binding.fabFavor.setImageResource(R.drawable.baseline_favorite_24)
        else binding.fabFavor.setImageResource(R.drawable.baseline_favorite_border_24)
        binding.fabFavor.setOnClickListener {
            if (isFavorite){
                title.apply {
                    db.execSQL("DELETE FROM fav WHERE title = ?", arrayOf(title))
                }
                Toast.makeText(this, "찜 목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
            }else{
                // 찜 DB에 데이터를 저장
                    title.apply {
                        db.execSQL("INSERT INTO fav VALUES('$title','$type','$period','$eventPeriod','$eventSite','$charge','$contactPoint','$url','$imageObject','$description','$viewCount')") //이것도 개수 맞추어야 하는지 한번 확인해봐야함 - 페이버릿 프레그먼트랑 같이 확인해야함
                    }
                Toast.makeText(this, "찜 목록에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
            }
            isFavorite =! isFavorite
            if (isFavorite) binding.fabFavor.setImageResource(R.drawable.baseline_favorite_24)
            else binding.fabFavor.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }// onCreate....
    private fun checkFavorite() : Boolean {
        title.apply {
            val cursor : Cursor =db.rawQuery("SELECT * FROM fav WHERE title =?", arrayOf(title) )
            if(cursor.count>0)return true
        }
        return false
    }
    override fun onBackPressed() {
        if (binding.wv.canGoBack())binding.wv.goBack()
        super.onBackPressed()
    }

}