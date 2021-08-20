package com.karimsinouh.mapsroutes.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karimsinouh.mapsroutes.util.add

class MainViewModel: ViewModel() {

    //markers
    private val _markers=MutableLiveData<List<MarkerOptions>>()
    val markers:LiveData<List<MarkerOptions>> =_markers

    fun addMarker(position:LatLng){
        val options=MarkerOptions().position(position).title("Some Random Place")
        _markers.add(options)
        Log.d("wtf","new marker from vm")
    }


}