package com.example.presensibagas

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.presensibagas.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.MainScope
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    lateinit var mMap  : GoogleMap //VAR MAP
    lateinit var mLocationRequest: LocationRequest //VAR MINTA LOKASI
    private lateinit var mMapFragment: SupportMapFragment
    private lateinit var myPosition : LatLng

    internal var mFusedLocationClient: FusedLocationProviderClient? = null  // IDK

    private var status =""
    var mLastLocation: Location? = null // VAR LOKASI TERAKHIR
    var mCurrentLocationMarker: Marker? = null  // MARKER LOKASI SAAT INI
    var cevestLocationMarker : Marker? = null   // MARKER LOKASI KANTOR
    var garisJarak : Polyline? = null           // GARIS
    var checkIn : String = ""

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!



    internal var mLocationCallback: LocationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult){
//            binding = ActivityCheckPresensiBinding.inflate(layoutInflater)
            val locationList = locationResult?.locations
            Log.d("CEKUP","INI INTERNAL")
            if(locationList!!.isNotEmpty()){
                val location = locationList.last()
                Log.i("cekcek",location.toString())
                mLastLocation = location
                if(mCurrentLocationMarker != null){
                    mCurrentLocationMarker?.remove()
                }

                if(cevestLocationMarker !=null){
                    cevestLocationMarker?.remove()
                }

                if(garisJarak != null){
                    garisJarak?.remove()
                }
                // ============================= OUR LOCATION START
                myPosition = LatLng(location.latitude,location.longitude)
                val markerOptions = MarkerOptions()
                markerOptions.position(myPosition)

                markerOptions.title("Posisi sekarang "+location.latitude+","+location.longitude.toString())
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                mCurrentLocationMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15.0F))
                // ============================= OUR LOCATION END
                // ============================= KANTOR LOCATION START
                val cevestLocation = LatLng(-6.2347677,106.987864)

                cevestLocationMarker = mMap.addMarker(
                    MarkerOptions()
                        .position(cevestLocation)
                        .title("BBPVP BEKASI")
                        .icon(
                            BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                        )
                )

                val circleOptions = CircleOptions()
                circleOptions.center(cevestLocation)

                circleOptions.radius(1000.0)
                circleOptions.strokeColor(Color.RED)
                mMap.addCircle(circleOptions)
                // =========================== KANTOR LOCATION END
                // =========================== GARIS JARAK START
                val distance = FloatArray(2)
                Location.distanceBetween(
                    cevestLocation.latitude,cevestLocation.longitude,location.latitude,location.longitude,distance
                )

                if (distance[0] > circleOptions.radius){
                    status = "WFH"
                }else{
                    status = "WFO"
                }

                garisJarak = mMap.addPolyline(
                    PolylineOptions().add(cevestLocation,
                        LatLng(location.latitude, location.longitude)
                    )
                        .width(5.0F)
                        .color(Color.YELLOW)
                )
                // ========================== GARIS JARAK END
                val geocoder: Geocoder
                val addresses: List<Address>?
                val latitude = location.latitude
                val longitude = location.longitude

                geocoder = Geocoder(requireContext(), Locale.getDefault())

                addresses = geocoder.getFromLocation(latitude,longitude,1)

                val address =
                    addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                val citya = addresses[0].
//                val city = addresses[0].locality
//                val state = addresses[0].adminArea
//                val country = addresses[0].countryName
//                val postalCode = addresses[0].postalCode
//                val knownName = addresses[0].featureName // Only if available else return NULL


                checkIn = "<b>Posisi anda Saat ini</b> : $address " +
                        "</br> <b>Status</b> : $status"
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("CEKUP","INI ONCREATEVIEW")
        _binding = FragmentMapBinding.inflate(layoutInflater, container, false)

        var buttonCheck = binding.buttCheck
        var tvLokasi = binding.tvLokasi
        var tvStatus = binding.tvStatus

        buttonCheck.setOnClickListener() {
            val pesan = "Status Bekerja anda saat ini : $status"
            tvStatus.text = pesan
            tvLokasi.text = Html.fromHtml(checkIn)
            buttonCheck.text = "Sudah Absen"
            buttonCheck.isEnabled = false
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mMapFragment.getMapAsync(this)


        return binding.root

    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType= GoogleMap.MAP_TYPE_HYBRID
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 50000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return Toast.makeText(requireContext(),"Izinkan Akses Location", Toast.LENGTH_LONG).show()
        }
        mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        googleMap.isMyLocationEnabled = true

    }
}