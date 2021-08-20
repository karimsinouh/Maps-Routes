package com.karimsinouh.mapsroutes.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.karimsinouh.mapsroutes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private var map:GoogleMap ?= null

    private lateinit var vm:MainViewModel

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



    }


    private fun subscribeToObserver(){
        vm.markers.observe(this){markers->
            markers.forEach { marker->
                map?.addMarker(marker)
                Log.d("wtf","new marker")
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