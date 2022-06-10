package ru.mirea.kryazhin.mireaproject.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Maps
import ru.mirea.kryazhin.mireaproject.R

class MapsFragment : Fragment() {
    val REQUEST_LOCATION = 1
    private var mMap: GoogleMap? = null

    private val callback: OnMapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap
            mMap?.setOnMapClickListener(OnMapClickListener { latLng ->
                mMap?.addMarker(
                    MarkerOptions().title("Где я?")
                        .snippet("Новое место").position(latLng)
                )
            })
            // разрешения
            // разрешения
            if (ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )
            } else {
                val locationResult = LocationServices
                    .getFusedLocationProviderClient(activity!!)
                    .lastLocation
            }
            mMap?.setMapType(GoogleMap.MAP_TYPE_NORMAL)
            mMap?.setMyLocationEnabled(true)
            mMap?.getUiSettings()?.isZoomControlsEnabled = true
            mMap?.setTrafficEnabled(true)
            // Add a markers
            setMarkers()
        }

        private fun setMarkers() {
            val mirea1 = LatLng(55.670005, 37.479894)
            val cameraPosition = CameraPosition.Builder().target(
                mirea1
            ).zoom(12f).build()
            mMap!!.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(cameraPosition)
            )
            mMap!!.addMarker(
                MarkerOptions().title("МИРЭА г. Москва, Проспект Вернадского, д. 78")
                    .snippet("Координаты: 55.670005, 37.479894").position(mirea1)
            )
            val mirea2 = LatLng(55.661984, 37.476789)
            mMap!!.addMarker(
                MarkerOptions().title("МИТХТ г. Москва, Проспект Вернадского, д. 86")
                    .snippet("Координаты: 55.661984, 37.476789").position(mirea2)
            )
            val mirea3 = LatLng(55.793690, 37.701722)
            mMap!!.addMarker(
                MarkerOptions().title("МГУПИ г. Москва, ул. Стромынка, д.20")
                    .snippet("Координаты: 55.793690, 37.701722").position(mirea3)
            )
            val mirea4 = LatLng(55.731751, 37.574918)
            mMap!!.addMarker(
                MarkerOptions().title("МИРЭА г. Москва, улица Малая Пироговская, д. 1, стр. 5")
                    .snippet("Координаты: 55.731751, 37.574918").position(mirea4)
            )
            val mirea5 = LatLng(55.764957, 37.741394)
            mMap!!.addMarker(
                MarkerOptions().title("МИРЭАг. Москва, 5-я улица Соколиной Горы, д. 22")
                    .snippet("Координаты: 55.764957, 37.741394").position(mirea5)
            )
            val mirea6 = LatLng(55.728787, 37.573177)
            mMap!!.addMarker(
                MarkerOptions().title("МИРЭА г. Москва, ул. Усачева, д.7/1")
                    .snippet("Координаты: 55.728787, 37.573177").position(mirea6)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}