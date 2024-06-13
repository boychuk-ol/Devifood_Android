package com.example.myapplication.fragment

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TabStopSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderProcessingBinding
import com.example.myapplication.service.LocationService
import com.example.myapplication.ui.view_model.ClientViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class OrderProcessingFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentOrderProcessingBinding? = null
    private val binding get() = _binding!!
    private val clientViewModel: ClientViewModel by activityViewModels()
    private var currentMarker: Marker? = null
    private val locationService: LocationService = LocationService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderProcessingBinding.inflate(inflater, container, false)
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager
            .beginTransaction()
            .add(binding.mapView2.id, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)

        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.black)
        binding.radioButtonHusband.buttonTintList = colorStateList
        binding.radioButtonKitten.buttonTintList = colorStateList

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = clientViewModel.location.value
        if (location != null) {
            val latLng = LatLng(location.coordinates!!.latitude, location.coordinates.longitude)
            currentMarker = googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

            Log.d("LOCATION_CHECK", location.street)
            binding.cityValue.text = location.city
            binding.regionValue.text = location.neighborhood
            binding.streetValue.text = location.street
            binding.streetNumberValue.text = location.streetNumber
        }

        googleMap.setOnMapClickListener { latLng ->
            findNavController().navigate(R.id.action_orderProcessingFragment_to_shareLocationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
