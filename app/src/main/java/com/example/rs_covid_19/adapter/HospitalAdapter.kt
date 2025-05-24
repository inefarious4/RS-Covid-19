//package com.example.rs_covid_19.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.rs_covid_19.R
//import com.example.rs_covid_19.model.Hospital
//
//class HospitalAdapter(
//    private val hospitals: List<Hospital>,
//    private val onProvinceClick: (String) -> Unit
//) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {
//
//    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameText: TextView = itemView.findViewById(R.id.tvName)
//        val addressText: TextView = itemView.findViewById(R.id.tvAddress)
//        val provinceText: TextView = itemView.findViewById(R.id.tvRegion)
//        val imgLogo: ImageView = itemView.findViewById(R.id.imgLogo)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_hospital, parent, false)
//        return HospitalViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
//        val hospital = hospitals[position]
//        holder.nameText.text = hospital.name
//        holder.addressText.text = hospital.address
//        holder.provinceText.text = hospital.region
//
//        holder.provinceText.setOnClickListener {
//            onProvinceClick(hospital.region)
//        }
//
//        val context = holder.itemView.context
//        val imageName = "rs_$position"
//        val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
//
//        if (resId != 0) {
//            holder.imgLogo.setImageResource(resId)
//        } else {
//            holder.imgLogo.setImageResource(R.drawable.placeholder) // fallback
//        }
//    }
//
//    override fun getItemCount(): Int = hospitals.size
//}

package com.example.rs_covid_19.adapter

import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rs_covid_19.MapsActivity
import com.example.rs_covid_19.R
import com.example.rs_covid_19.model.Hospital
import java.io.IOException
import java.util.*

class HospitalAdapter(
    private val hospitals: List<Hospital>,
    private val onProvinceClick: (String) -> Unit
) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tvName)
        val addressText: TextView = itemView.findViewById(R.id.tvAddress)
        val provinceText: TextView = itemView.findViewById(R.id.tvRegion)
        val imgLogo: ImageView = itemView.findViewById(R.id.imgLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.nameText.text = hospital.name
        holder.addressText.text = hospital.address
        holder.provinceText.text = hospital.region

        val context = holder.itemView.context

        val imageName = "rs_$position"
        val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (resId != 0) {
            holder.imgLogo.setImageResource(resId)
        } else {
            holder.imgLogo.setImageResource(R.drawable.placeholder)
        }

        holder.provinceText.setOnClickListener {
            onProvinceClick(hospital.region)
        }

        holder.itemView.setOnClickListener {
            val fullAddress = "${hospital.name}, ${hospital.address}, ${hospital.region}"
            val geocoder = Geocoder(context, Locale.getDefault())

            try {
                val addresses = geocoder.getFromLocationName(fullAddress, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val location = addresses[0]
                    val lat = location.latitude
                    val lng = location.longitude

                    val intent = Intent(context, MapsActivity::class.java).apply {
                        putExtra("latitude", lat)
                        putExtra("longitude", lng)
                        putExtra("name", hospital.name)
                    }
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memuat lokasi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = hospitals.size
}