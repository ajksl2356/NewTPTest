package com.lym2024.newtptest.activities

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlaceDetailBinding.inflate(layoutInflater) }
    private lateinit var place : Place
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
        db2.execSQL("CREATE TABLE IF NOT EXISTS favor(id TEXT PRIMARY KEY, place_name TEXT, category_name TEXT, phone TEXT, address_name TEXT, road_address_name TEXT, x TEXT, y TEXT, place_url TEXT, distance TEXT )")

    }
}