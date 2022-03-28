package com.example.petminder.ui.feedAdd

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petminder.databinding.FragmentMapsBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.Location
import com.example.petminder.models.PetModel
import com.example.petminder.ui.exceriseAdd.ExerciseFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber


private const val  ARG_EDIT = "edit"
private const val  ARG_Loc = "location"

class MapsFragment : Fragment(), OnMapReadyCallback , GoogleMap.OnMarkerDragListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentMapsBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var map: GoogleMap
    var location = Location()
    var edit = false

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        setHasOptionsMenu(true)
        Timber.i("Map started")

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMapsBinding.inflate(inflater,container,false)
        val root = fragBinding.root
        arguments?.let {
            edit = it.getBoolean(ARG_EDIT)
            location = it.getParcelable(ARG_Loc)!!
        }
        Timber.i("Map view created")
        fragBinding.map.onCreate(savedInstanceState)
        fragBinding.map.onResume()
        fragBinding.map.getMapAsync(this)

        return root
    }


    override fun onResume() {
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance(pet: PetModel, exercise: ExerciseModel) =
            MapsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        ExerciseFragment().setExerciseLocation(location)
        _fragBinding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
            map = googleMap
            val loc = LatLng(location.lat, location.lng)
            val options = MarkerOptions()
                .title("Placemark")
                .snippet("GPS : $loc")
                .draggable(true)
                .position(loc)
            map.addMarker(options)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
            map.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragStart(p0: Marker) {
    }


}