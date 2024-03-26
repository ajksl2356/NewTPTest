package com.lym2024.newtptest.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.Gson
import com.lym2024.newtptest.activities.TitleDetailActivity
import com.lym2024.newtptest.data.Title
import com.lym2024.newtptest.databinding.RecyclerItemListFragmentBinding

class MyAdapter(val context: Context, val documents : List<Title>) : Adapter<MyAdapter.VH>() {
    inner class VH(val binding : RecyclerItemListFragmentBinding) : ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        val binding = RecyclerItemListFragmentBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }
    override fun getItemCount(): Int {
        return documents.size
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val qsd : Title = documents[position]
        var title : String = removeAllHtmlTags(qsd.title)
        var desc : String = removeAllHtmlTags(qsd.description)
        title = title.trim()
        holder.binding.tvTitle.text = title
        desc = desc.trim()
        holder.binding.tvDescription.text = desc
        // 아이템뷰를 클릭하였을때 상세정보페이지 url 을 보여주는 화면으로 이동
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, TitleDetailActivity::class.java)
            // 장소정보에 대한 데이터를 추가로 보내기 [ 객체는 추가데이터로 전송불가 --> Json 문자열로 변환 ]
            val gson = Gson()
            val s : String = gson.toJson(qsd) // 객체 --> json string
            intent.putExtra("url", s )
            context.startActivity(intent)
        }
    }  private fun removeAllHtmlTags(input: String): String {
        return input.replace(Regex("<.*?>"), "")
    }
}