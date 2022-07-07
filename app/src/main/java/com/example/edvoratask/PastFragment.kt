package com.example.edvoratask

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class PastFragment(val list1 : List<ridesItem>,val station_code :Int) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_past,container,false)

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")
        val dateToday = current.format(formatter)
        val pastList = ArrayList<ridesItem>()

        for(data in list1){

            val d1 = "02/28/2022"

            var d2 = data.date
            d2 = d2.substring(0,11)

            val sdf = SimpleDateFormat("MM/dd/yyyy")

            val firstDate: Date = sdf.parse(d1)
            val secondDate: Date = sdf.parse(d2)

            val cmp = firstDate.compareTo(secondDate)
            when {
                cmp > 0 -> {
                    Log.i("Date","${d1} is after${d2}")
                    pastList.add(data)
                }
                cmp < 0 -> {
                    Log.i("Date","${d1} is before ${d2}")
                    continue
                }
                else -> {
                    Log.i("Date","Both dates are equal")
                    pastList.add(data)
                }
            }



        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPast )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ListAdapter(pastList, activity as MainActivity, station_code)
        }




        return view
    }


}