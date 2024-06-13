package com.example.myapplication.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Client
import com.example.myapplication.model.Location

class ClientViewModel: ViewModel() {

    private val _client = MutableLiveData<Client>()
    val client: LiveData<Client> get() = _client

    private val _clientLocation = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _clientLocation

    private val _locationsMaxId = MutableLiveData<Int>()
    val locationsMaxId: LiveData<Int> get() = _locationsMaxId


    fun updateClient(client: Client) {
        _client.value = client
    }

    fun getClient(): Client? {
        return _client.value
    }

    fun updateLocation(location: Location) {
        _clientLocation.value = location
    }

    fun getLocation(): Location? {
        return _clientLocation.value
    }

    fun updateLocationsMaxId(locationsMaxId: Int) {
        _locationsMaxId.value = locationsMaxId
    }

    fun getLocationsMaxId(): Int? {
        return _locationsMaxId.value
    }
}