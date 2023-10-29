package com.example.fetech.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetech.R
import com.example.fetech.Retrofit
import com.example.fetech.adapter.SectionedRecyclerViewAdapter
import com.example.fetech.`interface`.FetechAPI
import com.example.fetech.`interface`.data_callback
import com.example.fetech.models.items_dataItem
import com.example.fetech.repository.ItemsRepository
import com.example.fetech.viewmodel.MainViewModel
import com.example.fetech.viewmodel.MainViewModelFactory
import retrofit2.create


class Page1 : Fragment(), data_callback {


    private lateinit var vm : MainViewModel
    private lateinit var  recyclerView : RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val api = Retrofit.retro().create(FetechAPI::class.java)
        val repo = ItemsRepository(api)
        recyclerView = view.findViewById(R.id.recylce)



        vm = ViewModelProvider(this,MainViewModelFactory(repo)).get(MainViewModel::class.java)
        vm.fetchData()
        vm.itemList.observe(viewLifecycleOwner) {
            Log.d("check_item",it.toString())
            vm.cleanData(it,this)
        }






    }

    fun createModifiedList(originalData: List<items_dataItem>): List<Any> {
        val modifiedList = mutableListOf<Any>()

        var previousListId: Int? = null

        for (item in originalData) {
            if (previousListId == null || previousListId != item.listId) {
                modifiedList.add(item.listId)
                previousListId = item.listId
            }

            modifiedList.add(item)
        }

        return modifiedList
    }


    override fun onDataCleaned(sortedData: List<items_dataItem>) {
        val sectioned_list = createModifiedList(sortedData)
        Log.d("checking",sortedData.toString())
        Log.d("checking",sectioned_list.toString())
        val adapter = SectionedRecyclerViewAdapter(sectioned_list)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }


}