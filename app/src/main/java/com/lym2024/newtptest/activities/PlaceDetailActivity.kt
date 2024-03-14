package com.lym2024.newtptest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lym2024.newtptest.R
import com.lym2024.newtptest.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlaceDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
    }
}