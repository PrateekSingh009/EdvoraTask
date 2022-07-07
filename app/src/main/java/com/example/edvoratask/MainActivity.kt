package com.example.edvoratask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_nearest.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://assessment.api.vweb.app/"


class MainActivity : AppCompatActivity(), CustomDialog.Custom_DialogInterface {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private var selectedCity: String = ""
    private var selectedState: String = ""

    private var station_code: Int = 0

    private lateinit var ridesList: ArrayList<ridesItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager = findViewById(R.id.viewPager)


        tabLayout = findViewById(R.id.tabLayout)

        getUserData()


    }


    private fun getUserData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitUserData = retrofitBuilder.getUser()

        retrofitUserData.enqueue(object : Callback<user?> {
            override fun onResponse(call: Call<user?>, response: Response<user?>) {

                val responseBody = response.body()!!
                Log.i("User", responseBody.name)

                user_name.text = responseBody.name
                Glide
                    .with(this@MainActivity)
                    .load(responseBody.url)
                    .centerCrop()
                    .placeholder(R.drawable.user)
                    .into(user_img)

                val station_code = responseBody.station_code


                this@MainActivity.station_code = station_code

                getRidesData(station_code)


            }

            override fun onFailure(call: Call<user?>, t: Throwable) {
                Log.i("Error", t.message.toString())
            }
        })

    }

    private fun getRidesData(station_code: Int) {


        val ridesList: ArrayList<ridesItem> = ArrayList()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<ridesItem>?> {
            override fun onResponse(
                call: Call<List<ridesItem>?>,
                response: Response<List<ridesItem>?>
            ) {
                val responseBody = response.body()!!

                for (data in responseBody) {
                    Log.i("Data", data.id.toString())
                    ridesList.add(data)

                }





                rideListUpdate(ridesList, station_code)


                filter.setOnClickListener {
                    CustomDialog(ridesList).show(supportFragmentManager, "Filter-Dialog")

                }

            }

            override fun onFailure(call: Call<List<ridesItem>?>, t: Throwable) {
                Log.i("Error", t.message.toString())
            }
        })


    }

    fun rideListUpdate(rideList: ArrayList<ridesItem>, station_code: Int) {


        val list1: ArrayList<ridesItem> = ArrayList() // containing the station
        val list2: ArrayList<ridesItem> = ArrayList() // first nearest
        val list3: ArrayList<ridesItem> = ArrayList() // second nearest


        for (data in rideList) {

            val list = data.station_path

            if (list.contains(station_code))
                list1.add(data)

            if (list.contains(station_code + 1))
                list2.add(data)
            if (list.contains(station_code + 2))
                list3.add(data)


        }

        list1.addAll(list2)
        list1.addAll(list3)

        this@MainActivity.ridesList = list1

        viewPager.adapter = ViewpagerAdpter(supportFragmentManager, list1, station_code)
        (viewPager.adapter as ViewpagerAdpter).notifyDataSetChanged()
        tabLayout.setupWithViewPager(viewPager)


    }

    override fun sendText(selectedState: String, selectedCity: String) {

        this.selectedCity = selectedCity
        this.selectedState = selectedState


        val newRidesList = ArrayList<ridesItem>()

        if (selectedState != "State" && selectedCity != "City") {
            for (data in ridesList) {
                if (data.city == selectedCity && data.state == selectedState)
                    newRidesList.add(data)

            }

        } else if (selectedState == "State") { // no state selected
            for (data in ridesList) {
                if (data.city == selectedCity)
                    newRidesList.add(data)

            }

        } else if (selectedCity == "City") { // no city selected

            for (data in ridesList) {
                if (data.state == selectedState)
                    newRidesList.add(data)

            }


        }



        Toast.makeText(this,"Length of filtered data "+newRidesList.size.toString(),Toast.LENGTH_SHORT).show()
//        rideListUpdate(newRidesList, station_code)






    }


}
