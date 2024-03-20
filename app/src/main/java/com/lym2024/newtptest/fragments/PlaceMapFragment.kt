package com.lym2024.newtptest.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.mapwidget.InfoWindowOptions
import com.kakao.vectormap.mapwidget.component.GuiLayout
import com.kakao.vectormap.mapwidget.component.GuiText
import com.kakao.vectormap.mapwidget.component.Orientation
import com.lym2024.newtptest.R
import com.lym2024.newtptest.activities.MainActivity
import com.lym2024.newtptest.activities.PlaceDetailActivity
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.databinding.FragmentPlaceMapBinding
import org.json.JSONObject

class PlaceMapFragment : Fragment() {

    private val binding : FragmentPlaceMapBinding by lazy { FragmentPlaceMapBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            val labelLayer : LabelLayer = kakaoMap.labelManager!!.layer!!
            labelLayer.addLabel(labelOptions)

            val placeList : List<Place> ?= (activity as MainActivity).search?.documents
            placeList?.forEach {
                val pos = LatLng.from(it.y.toDouble(),it.x.toDouble())
                val options = LabelOptions.from(pos).setStyles(R.drawable.ic_pin).setTexts(it.place_name,"${it.address_name}, ${it.distance}m").setTag(it)
                kakaoMap.labelManager!!.layer!!.addLabel(options)
            }
            kakaoMap.setOnLabelClickListener { kakaoMap, layer, label ->
                label.apply {
                    val layout = GuiLayout(Orientation.Vertical)
                    layout.setPadding(16,16,16,16,)
                    layout.setBackground(R.drawable.base_msg, true)

                    texts.forEach { // 플레이스 이름 거리
                        val guiText = GuiText(it)
                        guiText.setTextSize(30)
                        guiText.setTextColor(Color.WHITE)
                        layout.addView(guiText)
                    }
                    val options : InfoWindowOptions = InfoWindowOptions.from(position)
                    options.body = layout
                    options.setBodyOffset(0f,-10f)
                    options.setTag( tag )
                    kakaoMap.mapWidgetManager!!.infoWindowLayer.removeAll()
                    kakaoMap.mapWidgetManager!!.infoWindowLayer.addInfoWindow(options)
                }//apply...
            }//setonlabelClickListener
            kakaoMap.setOnInfoWindowClickListener { kakaoMap, infoWindow, guiId ->
            val intent = Intent(requireContext(),PlaceDetailActivity::class.java)
            val place : Place = infoWindow.tag as Place
            val json : String = Gson().toJson(place)
            intent.putExtra("place", json)
            startActivity(intent)
            }
        }//onMapReady
    }//KakaoCallback..
}//Fragment..