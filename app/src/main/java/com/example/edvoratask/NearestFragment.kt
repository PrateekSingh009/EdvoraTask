package com.example.edvoratask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_nearest.*
import kotlinx.android.synthetic.main.fragment_nearest.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NearestFragment(val list1 : List<ridesItem>,val station_code :Int) : Fragment() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_nearest, container, false)
        Log.i("LengthnEarest",list1.size.toString())


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ListAdapter(list1, activity as MainActivity, station_code)
        }


        return view
    }


}