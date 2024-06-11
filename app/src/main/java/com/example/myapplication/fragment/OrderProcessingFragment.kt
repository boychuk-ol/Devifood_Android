package com.example.myapplication.fragment

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activity.ShareDataActivity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentOrderProcessingBinding
import com.example.myapplication.model.Location
import com.example.myapplication.service.LocationService
import com.example.myapplication.ui.view_model.CartViewModel
import com.example.myapplication.ui.view_model.ClientViewModel
import com.example.myapplication.ui.view_model.OrderProcessingViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.util.Locale

class OrderProcessingFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentOrderProcessingBinding? = null
    private val binding get() = _binding!!
    private val clientViewModel: ClientViewModel by activityViewModels()
    private var currentMarker: Marker? = null
    private val locationService: LocationService = LocationService()
    companion object {
        fun newInstance() = OrderProcessingFragment()
    }

    private val viewModel: OrderProcessingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderProcessingBinding.inflate(inflater, container, false)
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager
            ?.beginTransaction()
            ?.add(binding.mapView2.id, mapFragment)
            ?.commit()
        mapFragment.getMapAsync(this)

        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.black)
        binding.radioButtonHusband.buttonTintList = colorStateList
        binding.radioButtonKitten.buttonTintList = colorStateList

        return binding.root
    }
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener { latLng ->
            val intent: Intent = Intent(context, ShareDataActivity::class.java)
            startActivity(intent)
            findNavController().navigate(R.id.action_global_shareLocationFragment)
        }
    }

}