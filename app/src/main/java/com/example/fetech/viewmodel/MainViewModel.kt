package com.example.fetech.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetech.`interface`.data_callback
import com.example.fetech.models.items_data
import com.example.fetech.models.items_dataItem
import com.example.fetech.repository.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val itemsRepo: ItemsRepository) : ViewModel() {

    val itemList: LiveData<items_data>
        get() = itemsRepo.items


    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepo.getData()
        }
    }

    fun cleanData(itemsData: items_data,dataCallback: data_callback) {

        val filteredData = itemsData.filter { !it.name.isNullOrBlank() }
        val sortedData = filteredData.sortedWith(compareBy({ it.listId }, { it.name.substringAfter(" ").toInt()  }))

        dataCallback.onDataCleaned(sortedData)


    }



}
