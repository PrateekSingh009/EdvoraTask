package com.example.edvoratask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class ListAdapter(val list : List<ridesItem>,val activity: MainActivity,val station_code : Int) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val img : ImageView = view.findViewById(R.id.map)
        val city : TextView = view.findViewById(R.id.cityName)
        val state : TextView = view.findViewById(R.id.stateName)
        val rideId : TextView = view.findViewById(R.id.rideId)
        val origin : TextView = view.findViewById(R.id.originStation)
        val distance : TextView = view.findViewById(R.id.distance)
        val date : TextView = view.findViewById(R.id.date)
        val stationP : TextView = view.findViewById(R.id.stationPath)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ride_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.city.text = list[position].city
        holder.state.text = list[position].state
        holder.rideId.text = "Rides : "+list[position].id.toString()
        holder.origin.text = "Origin Station : "+list[position].origin_station_code.toString()
        holder.distance.text = "Distance : "+"Distance : 0"
        holder.date.text = "Data : "+list[position].date
        holder.stationP.text = "station_path : "+list[position].station_path.toString()
        Glide
            .with(activity)
            .load(list[position].map_url)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(holder.img)

        if(list[position].station_path.contains(station_code))
            holder.distance.text = "Distance : 0"
        else if(list[position].station_path.contains(station_code+1))
            holder.distance.text = "Distance : 1"
        else
            holder.distance.text = "Distance : 2"




    }

    override fun getItemCount(): Int = list.size
}