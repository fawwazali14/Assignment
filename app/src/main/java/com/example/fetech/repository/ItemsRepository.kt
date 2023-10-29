package com.example.fetech.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fetech.`interface`.FetechAPI
import com.example.fetech.models.items_data

class ItemsRepository(public val fetechAPI: FetechAPI) {

    private val itemsLiveData  = MutableLiveData<items_data>()

    val items : LiveData<items_data>
        get() = itemsLiveData


    suspend fun getData() {
        val response = fetechAPI.getData()
        if (response.body()!=null){
            itemsLiveData.postValue(response.body())
        }

    }
}
