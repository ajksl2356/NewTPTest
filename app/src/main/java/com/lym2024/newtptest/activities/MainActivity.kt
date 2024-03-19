package com.lym2024.newtptest.activities
import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
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
import com.lym2024.newtptest.data.AA
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.data.PlaceMeta
import com.lym2024.newtptest.data.QSD
import com.lym2024.newtptest.data.ResultCode
import com.lym2024.newtptest.data.Search
import com.lym2024.newtptest.data.Title
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
    var searchQuery: String = "서울"
    var myLocation: Location? = null
    var aa: AA? = null
    var search: Search? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val locationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, PlaceListFragment())
            .commit()
        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_bnv_upload -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceListFragment()).commit()
                R.id.menu_bnv_map -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceMapFragment()).commit()
                R.id.menu_bnv_girock -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceFavorFragment()).commit()
            }
            true
        }
        binding.bnv.background = null
        pasing()
        val permissionState : Int = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionState == PackageManager.PERMISSION_DENIED){
            permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            requestMyLocation()
        }
        binding.toolbar.setNavigationOnClickListener { requestMyLocation() }

    }// on Created method..
    val permissionResultLauncher : ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()){
    if (it) requestMyLocation()
        else Toast.makeText(this,"내 위치정보를 제공하지 않아 검색 사용이 제한", Toast.LENGTH_SHORT).show()
    }



    private fun requestMyLocation(){
        val request : LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,3000).build()
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        locationProviderClient.requestLocationUpdates(request,locationCallback,Looper.getMainLooper())
    }
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            myLocation = p0.lastLocation
            locationProviderClient.removeLocationUpdates(this)
            searchPlaces()
        }
    }
    private fun pasing(){
        val retrofit = RetrofitHelper.getRetrofitInstance("http://api.kcisa.kr/openapi/")
        val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
        val call = retrofitApiService.getDatainfo("abaad2a1-e5ac-417d-989d-b5a25ad7bbb3", "10", "1")
        call.enqueue(object :Callback<AA>{
            override fun onResponse(call: Call<AA>, response: Response<AA>) {
                aa = response.body()
                val header : ResultCode? = aa?.response?.header
                val body : List<Title> ?= aa?.response?.body?.items?.item
//                AlertDialog.Builder(this@MainActivity).setMessage("$aa").create().show()
            }

            override fun onFailure(call: Call<AA>, t: Throwable) {
                Toast.makeText(this@MainActivity, "asdasdasd", Toast.LENGTH_SHORT).show()
            }

        })
    }
    // 카카오 로컬 검색 API를 활용하여 키워드로 장소를 검색하는 기능 메소드
    private fun searchPlaces() {
//        Toast.makeText(this, "$searchQuery\\n${myLocation?.latitude},${myLocation?.longitude}", Toast.LENGTH_SHORT).show()
        val retrofit = RetrofitHelper.getRetrofitInstance("https://dapi.kakao.com")
        val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
        val call = retrofitApiService.searchPlace(searchQuery, myLocation?.longitude.toString(),myLocation?.latitude.toString() )
        call.enqueue(object : Callback<Search>{
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                search = response.body()

                val meta: PlaceMeta? = search?.meta
                val document: List<Place>? = search?.documents
                binding.bnv.selectedItemId = R.id.menu_bnv_upload
            }
            override fun onFailure(call: Call<Search>, t: Throwable) {
                Toast.makeText(this@MainActivity, "서버 오류가 있습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}// main Activity.....
