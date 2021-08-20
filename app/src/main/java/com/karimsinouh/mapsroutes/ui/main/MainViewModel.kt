package com.karimsinouh.mapsroutes.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.model.DirectionsRoute
import com.karimsinouh.mapsroutes.util.add

class MainViewModel: ViewModel() {

    //markers
    private val _markers=MutableLiveData<List<MarkerOptions>>()
    val markers:LiveData<List<MarkerOptions>> =_markers
    fun addMarker(position: LatLng){
        val options=MarkerOptions().position(position).title("Some Random Place")
        _markers.add(options)
    }

    val path= mutableListOf<LatLng>()

    fun decodeRoute(route:DirectionsRoute,onFinish:()->Unit){

        path.clear()

        route.legs?.forEach { leg->

            leg.steps?.forEach { step->

                if (step.steps!=null && step.steps.isNotEmpty()){

                    step.steps.forEach {nestedStep->

                        val polyLine=nestedStep.polyline

                        polyLine?.let {
                            if (it.decodePath().isNotEmpty()){
                                val latLngs=it.decodePath().map { latLng->
                                    LatLng(latLng.lat,latLng.lng)
                                }
                                path.addAll(latLngs)
                            }
                        }

                    }

                }else{

                    val polyLine=step.polyline
                    polyLine?.let {
                        if (it.decodePath().isNotEmpty()){
                            val latLngs=it.decodePath().map { latLng->
                                LatLng(latLng.lat,latLng.lng)
                            }
                            path.addAll(latLngs)
                        }
                    }
                }

            }

        }

        Log.d("wtf","size: ${path.size}")

        if (path.isNotEmpty()){
            onFinish()
        }

    }

}