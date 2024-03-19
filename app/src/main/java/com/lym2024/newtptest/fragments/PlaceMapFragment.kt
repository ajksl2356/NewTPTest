package com.lym2024.newtptest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.lym2024.newtptest.R
import com.lym2024.newtptest.activities.MainActivity
import com.lym2024.newtptest.databinding.FragmentPlaceMapBinding

class PlaceMapFragment : Fragment() {

    private val binding : FragmentPlaceMapBinding by lazy { FragmentPlaceMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.start(mapReadyCallback)
    }
    val mapReadyCallback : KakaoMapReadyCallback = object : KakaoMapReadyCallback(){
        override fun onMapReady(kakaoMap: KakaoMap) {
            val latitude : Double = (activity as MainActivity).myLocation?.latitude ?:37.5664
            val logitude : Double = (activity as MainActivity).myLocation?.longitude ?:126.9778
            val myPos : LatLng = LatLng.from(latitude, logitude)
            val cameraUpdate : CameraUpdate = CameraUpdateFactory.newCenterPosition(myPos,16)
            kakaoMap.moveCamera(cameraUpdate)
            val labelOptions : LabelOptions = LabelOptions.from(myPos).setStyles(R.drawable.ic_mypin)
            val labelLayer : LabelLayer? = kakaoMap.labelManager!!.layer
        }


    }

}