package com.example.myapplication.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.provider.Telephony.Mms.Addr
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShareLocationBinding
import com.example.myapplication.model.Location
import com.example.myapplication.service.LocationService
import com.example.myapplication.ui.view_model.ClientViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.util.Locale

class ShareLocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentShareLocationBinding? = null
    private val binding get() = _binding!!
    private var currentMarker: Marker? = null
    private val clientViewModel: ClientViewModel by activityViewModels()
    private val locationService: LocationService = LocationService()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareLocationBinding.inflate(inflater, container, false)

        binding.locationInfoBlock.visibility = View.INVISIBLE
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager
            ?.beginTransaction()
            ?.add(binding.mapView.id, mapFragment)
            ?.commit()
        mapFragment.getMapAsync(this)

        binding.inputLocation.setOnClickListener {
            findNavController().navigate(R.id.action_shareLocationFragment_to_sharePhoneFragment)
        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener { latLng ->
            currentMarker?.remove()
            currentMarker = googleMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            var address: Address? = getAddressFromLocation(latLng.latitude, latLng.longitude)
            if(address != null) {
                locationService
                val location: Location = Location(1,
                    address.getAddressLine(0),
                    address.locality,
                    address.subLocality,
                    address.thoroughfare,
                    address.subThoroughfare,
                    latLng,
                    null,
                    null
                    )
                location.client = clientViewModel.client.value
            }

            val region = address?.subLocality?.let { "Region: $it\n" } ?: ""
            val street = address?.thoroughfare?.let { "Street: $it\n" } ?: ""
            val streetNumber = address?.subThoroughfare?.let { "Street number: $it" } ?: ""
            val title = region + street + streetNumber
            currentMarker?.title = title
            binding.locationInfoBlock.apply {
                visibility = View.VISIBLE
                text = title
            }
            viewLifecycleOwner.lifecycleScope.launch {
                val locationId: Int? = locationService.getLocationsMaxId()
                val coordinates: LatLng = clientViewModel.location.value?.coordinates!!
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses: Address? = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)?.get(0)
                if(locationId != null) {
                    val location: Location? =
                        addresses?.let { Location(locationId + 1 ,
                            it.getAddressLine(0),
                            it.locality,
                            it.subLocality,
                            it.thoroughfare,
                            it.subThoroughfare,
                            coordinates,
                            null,
                            clientViewModel.client.value)
                        }
                }
            }
            currentMarker?.showInfoWindow()
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val jsonAddress = JSONObject()
                jsonAddress.put("country", address.countryName ?: "N/A") // COUNTRY
                jsonAddress.put("countryCode", address.countryCode ?: "N/A") // COUNTRY CODE
                jsonAddress.put("adminArea", address.adminArea ?: "N/A") // OBLAS'T
                jsonAddress.put("subAdminArea", address.subAdminArea ?: "N/A")
                jsonAddress.put("locality", address.locality ?: "N/A") // CITY
                jsonAddress.put("subLocality", address.subLocality ?: "N/A") // NEIGHBORHOOD
                jsonAddress.put("thoroughfare", address.thoroughfare ?: "N/A") // STREET
                jsonAddress.put("subThoroughfare", address.subThoroughfare ?: "N/A") // STREET NUMBER
                jsonAddress.put("postalCode", address.postalCode ?: "N/A") // POSTAL CODE
                jsonAddress.put("subLocality", address.subLocality ?: "N/A")

                Log.d("Address", jsonAddress.toString())
                // Here you can update the UI or ViewModel with the address details

                return address
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("GeocoderError", "Unable to get address from location.")
        }
        return null
    }

}