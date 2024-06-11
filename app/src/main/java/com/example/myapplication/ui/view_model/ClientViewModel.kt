package com.example.myapplication.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Client
import com.example.myapplication.model.Location

class ClientViewModel : ViewModel() {

    // LiveData to hold client data
    private val _client = MutableLiveData<Client>()
    val client: LiveData<Client> get() = _client

    // LiveData to hold location data
    private val _clientLocation = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _clientLocation

    // Function to update client data
    fun updateClient(client: Client) {
        _client.value = client
    }

    // Function to get the client data
    fun getClient(): Client? {
        return _client.value
    }

    // Function to update location data
    fun updateLocation(location: Location) {
        _clientLocation.value = location
    }

    // Function to get the location data
    fun getLocation(): Location? {
        return _clientLocation.value
    }
}