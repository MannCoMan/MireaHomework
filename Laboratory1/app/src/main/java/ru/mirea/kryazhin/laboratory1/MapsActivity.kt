package ru.mirea.kryazhin.laboratory1

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import ru.mirea.kryazhin.laboratory1.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private val REQUEST_LOCATION = 1
    private var mMap: GoogleMap? = null
    private var binding: ActivityMapsBinding? = null
    var pointList = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()
        // добавление кнопки определения местоположения
        // добавление кнопки определения местоположения
        mMap!!.isMyLocationEnabled = true
        // добавление кнопок изменнеия масштаба
        // добавление кнопок изменнеия масштаба
        mMap!!.uiSettings.isZoomControlsEnabled = true
        // отображение слоя загруженности дорог
        // отображение слоя загруженности дорог
        mMap!!.isTrafficEnabled = true
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

        // Add a marker in Sydney and move the camera

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        setUpMap()

        mMap!!.setOnMapClickListener(this)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            // permission has been granted, continue as usual
            val locationResult: Task<Location> = LocationServices
                .getFusedLocationProviderClient(this)
                .getLastLocation()
        }
    }

    private fun setUpMap() {
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        val mirea = LatLng(55.670005, 37.479894)
        val cameraPosition = CameraPosition.Builder().target(
            mirea
        ).zoom(12f).build()
        mMap!!.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(cameraPosition)
        )
        mMap!!.addMarker(
            MarkerOptions().title("МИРЭА")
                .snippet("Крупнейший политехнический ВУЗ").position(mirea)
        )
    }

    override fun onMapClick(latLng: LatLng) {
        drawMarker(latLng)
//        pointList.add(latLng);
    }

    private fun drawMarker(latLng: LatLng) {
        mMap!!.addMarker(
            MarkerOptions().title("Где я?")
                .snippet("Новое место").position(latLng)
        )
    }

}

private fun GoogleMap.setOnMapClickListener(mapsActivity: MapsActivity) {

}
