package com.example.myapplication.fragment

import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TabStopSpan
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
import com.google.android.gms.maps.model.StyleSpan
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

        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager
            ?.beginTransaction()
            ?.add(binding.mapView.id, mapFragment)
            ?.commit()
        mapFragment.getMapAsync(this)

        viewLifecycleOwner.lifecycleScope.launch {
            locationService.getLocationsMaxId()?.let { clientViewModel.updateLocationsMaxId(it) }
        }
        binding.locationInfoBlock.visibility = View.INVISIBLE
        binding.nextButton.visibility = View.GONE

        binding.inputLocation.setOnClickListener {
            findNavController().navigate(R.id.action_shareLocationFragment_to_inputLocationFragment)
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_shareLocationFragment_to_homeFragment)
        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener { latLng ->
            if (currentMarker != null) {
                currentMarker?.remove()
            }
            currentMarker = googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            val address: Address? = getAddressFromLocation(latLng.latitude, latLng.longitude)
            if (address != null) {
                val upperTitle: String = if (address.thoroughfare == null && (address.locality == null && address.subLocality == null)) {
                    "Please choose another location. Street or city is unknown\n"
                } else {
                    ""
                }

                val city = address.locality?.let { "City: $it" } ?: "City: Unknown"
                val region = address.subLocality?.let { "Region: $it" } ?: "Region: Unknown"
                val street = address.thoroughfare?.let { "Street: $it" } ?: "Street: Unknown"
                val streetNumber = address.subThoroughfare?.let { "Street number: $it" } ?: "Street number: Unknown"

                val title = upperTitle +
                        "$city\t$region\n" +
                        "$street\t$streetNumber"

                val spannableString = SpannableString(title)
                if (upperTitle.isNotEmpty()) {
                    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, upperTitle.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                val tabStop1 = 500
                val tabStop2 = 500
                spannableString.setSpan(TabStopSpan.Standard(tabStop1), upperTitle.length, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(TabStopSpan.Standard(tabStop2), upperTitle.length, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                binding.locationInfoBlock.apply {
                    visibility = View.VISIBLE
                    text = spannableString
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    val coordinates = LatLng(address.latitude, address.longitude)
                    val maxId = clientViewModel.getLocationsMaxId()
                    if (maxId != null) {
                        val location = address.let {
                            Location(
                                maxId + 1,
                                it.getAddressLine(0),
                                it.locality ?: "Unknown",
                                it.subLocality ?: null,
                                it.thoroughfare ?: "Unknown",
                                it.subThoroughfare ?: null,
                                coordinates,
                                null,
                                clientViewModel.client.value,
                            )
                        }
                        Log.d("LOCATION_LOG", location.locationID.toString())
                        Log.d("LOCATION_LOG", location.fullAddress)
                        Log.d("LOCATION_LOG", location.city)
                        location.neighborhood?.let { Log.d("LOCATION_LOG", it) }
                        Log.d("LOCATION_LOG", location.street)
                        location.streetNumber?.let { Log.d("LOCATION_LOG", it) }
                        Log.d("LOCATION_LOG", location.coordinates.toString())

                        binding.nextButton.visibility = View.VISIBLE
                        clientViewModel.updateLocation(location)
                        Log.d("LOCATION_LOG", clientViewModel.location.value?.coordinates.toString())
                        Log.d("LOCATION_LOG", clientViewModel.location.value?.fullAddress.toString())
                    } else {
                        binding.nextButton.visibility = View.GONE
                    }
                    currentMarker?.showInfoWindow()
                }
            }
        }

        // Check if there's a stored location in the ViewModel
        clientViewModel.location.value?.let { location ->
            placeMarkerOnMap(googleMap, location)
        }
    }

    private fun placeMarkerOnMap(googleMap: GoogleMap, location: Location) {
        val latLng = LatLng(location.coordinates!!.latitude, location.coordinates.longitude)
        if (currentMarker != null) {
            currentMarker?.remove()
        }
        currentMarker = googleMap.addMarker(MarkerOptions().position(latLng))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        val city = location.city.let { "City: $it" } ?: "City: Unknown"
        val region = location.neighborhood?.let { "Region: $it" } ?: "Region: Unknown"
        val street = location.street.let { "Street: $it" } ?: "Street: Unknown"
        val streetNumber = location.streetNumber?.let { "Street number: $it" } ?: "Street number: Unknown"

        val title = "$city\t$region\n$street\t$streetNumber"

        val spannableString = SpannableString(title)
        val tabStop1 = 500
        val tabStop2 = 500
        spannableString.setSpan(TabStopSpan.Standard(tabStop1), 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(TabStopSpan.Standard(tabStop2), 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.locationInfoBlock.apply {
            visibility = View.VISIBLE
            text = spannableString
        }
        clientViewModel.updateLocation(location)
        binding.nextButton.visibility = View.VISIBLE
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val jsonAddress = JSONObject()
                jsonAddress.put("country", address.countryName ?: "N/A")
                jsonAddress.put("countryCode", address.countryCode ?: "N/A")
                jsonAddress.put("adminArea", address.adminArea ?: "N/A")
                jsonAddress.put("subAdminArea", address.subAdminArea ?: "N/A")
                jsonAddress.put("locality", address.locality ?: "N/A")
                jsonAddress.put("subLocality", address.subLocality ?: "N/A")
                jsonAddress.put("thoroughfare", address.thoroughfare ?: "N/A")
                jsonAddress.put("subThoroughfare", address.subThoroughfare ?: "N/A")
                jsonAddress.put("postalCode", address.postalCode ?: "N/A")
                jsonAddress.put("subLocality", address.subLocality ?: "N/A")

                Log.d("Address", jsonAddress.toString())
                return address
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("GeocoderError", "Unable to get address from location.")
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
