package com.lym2024.newtptest.activities


import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.location.LocationServices

import com.lym2024.newtptest.R
import com.lym2024.newtptest.databinding.ActivityMainBinding
import com.lym2024.newtptest.fragments.PlaceFavorFragment
import com.lym2024.newtptest.fragments.PlaceListFragment
import com.lym2024.newtptest.fragments.PlaceMapFragment


class MainActivity : AppCompatActivity() {


    var myLocation : Location?= null

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)} // [ Google Fused Location API 사용  : play-services-location ]

    val locationProviderClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, PlaceListFragment()).commit() // bnv의 선택에 따라 Fragment를 동적으로 교체.
        binding.bnv.setOnItemSelectedListener{

            when(it.itemId){
                R.id.menu_bnv_upload-> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceListFragment()).commit()
                R.id.menu_bnv_map -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceMapFragment()).commit()
                R.id.menu_bnv_girock-> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceFavorFragment()).commit()

            }
            true // OnItemSelectedListener의 추상메소드는 리턴값을 가지고 있음. SAM변환을 하면 return키워드를 사용하면 안됨.
        }



    }
}