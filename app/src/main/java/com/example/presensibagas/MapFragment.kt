package com.example.presensibagas

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
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
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mMap  : GoogleMap //VAR MAP
    lateinit var mLocationRequest: LocationRequest //VAR MINTA LOKASI
    private lateinit var myPosition : LatLng

    internal var mFusedLocationClient: FusedLocationProviderClient? = null  // IDK

    private var status =""
    var mLastLocation: Location? = null // VAR LOKASI TERAKHIR
    var mCurrentLocationMarker: Marker? = null  // MARKER LOKASI SAAT INI
    var cevestLocationMarker : Marker? = null   // MARKER LOKASI KANTOR
    var garisJarak : Polyline? = null           // GARIS
    var checkIn : String = ""



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
                //Masih salah
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
        return inflater.inflate(R.layout.fragment_map, container, false)
    }



    override fun onMapReady(p0: GoogleMap) {

    }
}