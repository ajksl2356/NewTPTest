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
        loddata()
    }
    private fun loddata(){
        val db = requireContext().openOrCreateDatabase("url", Activity.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT * FROM fav",null)
        cursor?.apply {
            moveToFirst()
            val titleList : MutableList<Title> = mutableListOf()
            for( i in 0 until count ) {
                val title: String = getString(0)            // 제목
                val type: String = getString(1)             // 기간
                val period: String = getString(2)           // 기간
                val eventPeriod: String = getString(3)      //시간
                val eventSite : String = getString(4)       // 장소
                val charge: String = getString(5)           // 금액
                val contactPoint: String = getString(6)     // 문의 안내
                val url: String = getString(7)              // URL
                val imageObject: String = getString(8)      // 이미지 썸네일
                val description: String = getString(9)      // 설명
                val viewCount: String = getString(10)
                val title1: Title = Title(title, type, period, eventPeriod, eventSite, charge, contactPoint, url, imageObject, description, viewCount)
                titleList.add(title1)
                moveToNext()
            }
            binding.recyclerView.adapter = MyAdapter(requireContext(), titleList )
        }
    }
}