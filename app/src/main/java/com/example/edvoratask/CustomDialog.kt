package com.example.edvoratask

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.filter_dialog.*

class CustomDialog(val list1 : List<ridesItem>) : DialogFragment() {

    lateinit var dialogInterface :Custom_DialogInterface

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        val inflater = activity?.layoutInflater

        val view  = inflater?.inflate(R.layout.filter_dialog,null)

        val stateTv = view?.findViewById<AutoCompleteTextView>(R.id.stateTV)
        val cityTv = view?.findViewById<AutoCompleteTextView>(R.id.cityTV)

        val stateList = ArrayList<String>()
        val cityList = ArrayList<String>()

        val stateCity : MutableMap<String,ArrayList<String>> = HashMap()

        for(data in list1){

            if(!stateCity.containsKey(data.state))
            {
                val arr = ArrayList<String>()
                arr.add(data.city)
                stateCity[data.state] = arr
            }
            else
            {
                val arr = stateCity[data.state]
                if(!arr!!.contains(data.city)) {
                    arr!!.add(data.city)
                }
                stateCity[data.state] = arr

            }

        }
        Log.i("Map",stateCity.toString())



        for(data in list1){
            if(!stateList.contains(data.state))
            stateList.add(data.state)
            if(!cityList.contains(data.city))
            cityList.add(data.city)

        }

        stateTv?.setAdapter(ArrayAdapter(requireContext(),R.layout.dropdown_item,stateList))
        cityTv?.setAdapter(ArrayAdapter(requireContext(),R.layout.dropdown_item,cityList))

        stateTv?.doAfterTextChanged {
            val l = stateCity[stateTv?.text.toString()]
            cityTv?.setAdapter(ArrayAdapter(requireContext(),R.layout.dropdown_item,l!!))
        }




        builder.setView(view)
            .setNegativeButton("Cancel",object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    dialog!!.dismiss()

                }



            })
            .setPositiveButton("Ok",object:DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    val selectedState : String = stateTv?.text.toString()
                    val selectedCity : String = cityTv?.text.toString()

                    dialogInterface.sendText(selectedState,selectedCity)

                }


            })






        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialogInterface = context as Custom_DialogInterface


    }


    public interface Custom_DialogInterface{
        fun sendText(selectedState:String , selectedCity:String)
    }



}