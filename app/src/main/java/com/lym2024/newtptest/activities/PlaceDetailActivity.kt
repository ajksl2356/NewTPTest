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
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlaceDetailBinding.inflate(layoutInflater) }
    private lateinit var place : Place
    private var isFavorite = false
    private lateinit var db2 : SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val s : String? = intent.getStringExtra("place")
        s?.also {
            place = Gson().fromJson(it, Place::class.java)
            binding.wv2.webViewClient = WebViewClient()
            binding.wv2.webChromeClient = WebChromeClient()
            binding.wv2.settings.javaScriptEnabled = true //JS동작을 막아놓아서
            binding.wv2.loadUrl(place.place_url)
        }
        db2 = openOrCreateDatabase("place", MODE_PRIVATE,null)
        db2.execSQL("CREATE TABLE IF NOT EXISTS good(id TEXT PRIMARY KEY, place_name TEXT, category_name TEXT, phone TEXT, address_name TEXT, road_address_name TEXT, x TEXT, y TEXT, place_url TEXT, distance TEXT )")
        isFavorite = checkFavorite()
        if (isFavorite) binding.fabFavor.setImageResource(R.drawable.baseline_favorite_24)
        else binding.fabFavor.setImageResource(R.drawable.baseline_favorite_border_24)
        // 찜 버튼 클릭 처리
        binding.fabFavor.setOnClickListener {
            if (isFavorite){
                // 찜 DB의 데이터를 삭제
                place.apply {
                    db2.execSQL("DELETE FROM good WHERE id = ?", arrayOf(id))
                }
                Toast.makeText(this, "찜 목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
            }else{
                // 찜 DB에 데이터를 저장
                place.apply {
                    place.apply {
                        db2.execSQL("INSERT INTO good VALUES('$id','$place_name','$category_name','$phone','$address_name','$road_address_name','$x','$y','$place_url','$distance')")
                    }
                }
                Toast.makeText(this, "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
            isFavorite =! isFavorite
            if (isFavorite) binding.fabFavor.setImageResource(R.drawable.baseline_favorite_24)
            else binding.fabFavor.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }
    private fun checkFavorite() : Boolean {
        place.apply {
            val cursor : Cursor = db2.rawQuery("SELECT * FROM good WHERE id =?", arrayOf(id))
            if(cursor.count>0)return true
        }
        return false
    }

    override fun onBackPressed() {
        if (binding.wv2.canGoBack()) binding.wv2.goBack()
        super.onBackPressed()
    }


}