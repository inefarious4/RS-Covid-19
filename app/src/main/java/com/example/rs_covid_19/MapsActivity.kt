//package com.example.rs_covid_19
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//    private lateinit var mMap: GoogleMap
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
////    override fun onMapReady(googleMap: GoogleMap) {
////        mMap = googleMap
////        val province = intent.getStringExtra("province") ?: return
////
////        // For simplicity, we use hardcoded locations (you can use Geocoding API for dynamic)
////        val coordinates = when (province.lowercase()) {
////            "jakarta" -> LatLng(-6.2088, 106.8456)
////            "jawa barat" -> LatLng(-6.9034, 107.5731)
////            else -> LatLng(-2.5489, 118.0149) // Center of Indonesia
////        }
//        override fun onMapReady(googleMap: GoogleMap) {
//            val lat = intent.getDoubleExtra("latitude", 0.0)
//            val lng = intent.getDoubleExtra("longitude", 0.0)
//            val name = intent.getStringExtra("name") ?: "Rumah Sakit"
//
//            val location = LatLng(lat, lng)
//            googleMap.addMarker(MarkerOptions().position(location).title(name))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
//        mMap.addMarker(MarkerOptions().position(coordinates).title("Lokasi: $province"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 6f))
//    }
//}

package com.example.rs_covid_19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this) // Tunggu sampai peta siap
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = intent.getDoubleExtra("latitude", 0.0)
        val lng = intent.getDoubleExtra("longitude", 0.0)
        val name = intent.getStringExtra("name") ?: "Rumah Sakit"

        val coordinates = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(coordinates).title("Lokasi: $name"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
    }
}
