package com.lym2024.newtptest.adapter

import android.content.Context
import com.lym2024.newtptest.data.Place
import com.lym2024.newtptest.databinding.FragmentPlaceMapBinding

class PlaceAdapter(val context: Context, val documents : List<Place>) {

    inner class VH(binding: FragmentPlaceMapBinding)
}