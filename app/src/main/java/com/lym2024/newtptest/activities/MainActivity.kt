package com.lym2024.newtptest.activities


import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import com.lym2024.newtptest.R
import com.lym2024.newtptest.data.Data
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.data.PlaceMeta
import com.lym2024.newtptest.data.Search
import com.lym2024.newtptest.data.detail
import com.lym2024.newtptest.data.start
import com.lym2024.newtptest.databinding.ActivityMainBinding
import com.lym2024.newtptest.fragments.PlaceFavorFragment
import com.lym2024.newtptest.fragments.PlaceListFragment
import com.lym2024.newtptest.fragments.PlaceMapFragment
import com.lym2024.newtptest.network.RetrofitApiService
import com.lym2024.newtptest.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//abaad2a1-e5ac-417d-989d-b5a25ad7bbb3   서비스키

class MainActivity : AppCompatActivity() {
    var searchQuery: String? = null
    var myLocation: Location? = null
    var datainfo: Data? = null
    var search: Search? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) } // [ Google Fused Location API 사용  : play-services-location ]
    val locationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, PlaceListFragment())
            .commit() // bnv의 선택에 따라 Fragment를 동적으로 교체.
        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_bnv_upload -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, PlaceListFragment()).commit()

                R.id.menu_bnv_map -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, PlaceMapFragment()).commit()

                R.id.menu_bnv_girock -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, PlaceFavorFragment()).commit()
            }
            true
        }   //레트로핏을 이용한 REST API 작업 수행 - GET 방식
        val retrofit =
            RetrofitHelper.getRetrofitInstance("http://api.kcisa.kr/openapi/")

        val retrofitApiService = retrofit.create(RetrofitApiService::class.java)

        val call = retrofitApiService.getDatainfo("abaad2a1-e5ac-417d-989d-b5a25ad7bbb3", "10", "1")
        call.enqueue(object : Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                datainfo = response.body()

                val num : start ?= datainfo?.num
                val finsh : List<detail> ?= datainfo?.finsh
                AlertDialog.Builder(this@MainActivity).setMessage("${num?.total_count}\n${finsh?.get(0)?.title}").create().show()
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}

