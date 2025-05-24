package com.example.rs_covid_19

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rs_covid_19.adapter.HospitalAdapter
import com.example.rs_covid_19.model.Hospital
import com.example.rs_covid_19.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HospitalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dekontaminasi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        service.getHospitals().enqueue(object : Callback<List<Hospital>> {
            override fun onResponse(call: Call<List<Hospital>>, response: Response<List<Hospital>>) {
                if (response.isSuccessful) {
                    val hospitals = response.body() ?: emptyList()
                    adapter = HospitalAdapter(hospitals) { province ->
                        val intent = Intent(this@MainActivity, MapsActivity::class.java)
                        intent.putExtra("province", province)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Hospital>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Gagal ambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
