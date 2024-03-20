package com.lym2024.newtptest.fragments
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lym2024.newtptest.adapter.MyAdapter
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.data.Title
import com.lym2024.newtptest.databinding.FragmentPlaceFavorBinding
class PlaceFavorFragment : Fragment() {
    lateinit var binding: FragmentPlaceFavorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceFavorBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
    }
    private fun loddata(){
        val db = requireContext().openOrCreateDatabase("title", Activity.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT * FROM favor",null)
        cursor?.apply {
            moveToFirst()
            val titleList : MutableList<Title> = mutableListOf()
            val title : String = getString(0)             // 제목
            val type : String = getString(1)             // 기간
            val period : String = getString(2)           // 기간
            val eventPeriod : String  = getString(3)     // 장소
            val charge : String = getString(4)           // 금액
            val contactPoint : String  = getString(5)    // 문의 안내
            val url : String  = getString(6)             // URL
            val imageObject : String   = getString(7)    // 이미지 썸네일
            val description : String  = getString(8)     // 설명
            val viewCount : String = getString(9)
        }
//        val db = requireContext().openOrCreateDatabase("place", Activity.MODE_PRIVATE, null)
//        val cursor = db.rawQuery("SELECT * FROM favor",null)
//        cursor?.apply {
//            moveToFirst()
//            val placeList : MutableList<Place> = mutableListOf()
//            val id : String = getString(0)
//            val place_name : String = getString(1)
//            val category_name : String = getString(2)
//            val phone : String = getString(3)
//            val address_name : String = getString(4)
//            val road_address_name : String = getString(5)
//            val x : String = getString(6)
//            val y : String = getString(7)
//            val place_url : String = getString(8)
//            val distance : String = getString(9)
//            val place : Place = Place(id, place_name, category_name, phone, address_name, road_address_name, x, y, place_url, distance)
//            placeList.add(place)
//            moveToNext()
//            binding.recyclerView.adapter = MyAdapter(requireContext(), placeList)
//        }
    }
}