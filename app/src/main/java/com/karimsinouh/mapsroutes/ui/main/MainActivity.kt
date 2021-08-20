package com.karimsinouh.mapsroutes.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.analyzer.Direct
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.karimsinouh.mapsroutes.R
import com.karimsinouh.mapsroutes.databinding.ActivityMainBinding
import com.karimsinouh.mapsroutes.util.asString
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var map:GoogleMap ?= null
    private lateinit var vm:MainViewModel

    private val apiContext by lazy {
        GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        vm=ViewModelProvider(this).get(MainViewModel::class.java)

        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync {
            map=it

            it.setOnMapClickListener { latlng->
                vm.addMarker(latlng)
            }
        }

        subscribeToObserver()

        binding.calculateButton.setOnClickListener {
            linkMarkers()
        }

    }


    private fun linkMarkers(){
        if (vm.markers.value?.size?:0 >1){
            val from= vm.markers.value?.get(0)
            val to= vm.markers.value?.get(1)

            val request=DirectionsApi.getDirections(apiContext,from.asString(),to.asString())

            try {

                val result=request.await()

                vm.decodeRoute(result.routes[0]){
                    val polyLineOptions=PolylineOptions().addAll(vm.path).color(Color.RED).width(5f)
                    map?.addPolyline(polyLineOptions)
                }

            }catch (e:Exception){
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
                Log.d("wtf",e.message?:"")
            }


        }
    }


    private fun subscribeToObserver(){
        vm.markers.observe(this){markers->
            markers.forEach { marker->
                map?.addMarker(marker)
            }
        }
    }


    //lifecycle stuff
    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }


}