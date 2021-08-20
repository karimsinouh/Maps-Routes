package com.karimsinouh.mapsroutes.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.add(item:T){
    val list=this.value?.toMutableList() ?: mutableListOf<T>()
    list.add(item)
    this.value=list
}