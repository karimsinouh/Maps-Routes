package com.karimsinouh.mapsroutes.util

import com.google.android.gms.maps.model.MarkerOptions


fun MarkerOptions?.asString()=
    "${this?.position?.latitude ?:0},${this?.position?.longitude?:0}"