package com.rexmo.buslocationtracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.rexmo.buslocationtracker.maps.MapsActivity
import com.rexmo.buslocationtracker.permissions.LocationRequestPermissions

class MainActivity : AppCompatActivity() {
    lateinit var i:TextView
    lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val userPermission=LocationRequestPermissions()
   // private var showLocation:TextView=findViewById(R.id.txtShowLocation)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 200)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(200)
            .setMaxUpdateDelayMillis(200)
            .build()
       getCurrentLocation()
        i=findViewById(R.id.txtShowLocation)


    }

   /* private fun getCurrentLocation() {

            if (checkPermissions())
            {
                if (isLocationEnabled()) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermission()
                       //return
                        }
                    else {

                            try {
                                fusedLocationProviderClient.lastLocation.addOnSuccessListener { task: Location? ->
                                    if (task != null) {
                                        location = task
                                        Toast.makeText(this,"$task",Toast.LENGTH_SHORT).show()
                                        //txtShow.text = "Longitude=${location.longitude} latitude=${location.latitude}"

                                    } else {
                                        //txtShow.text="Can't fetch location"
                                        Toast.makeText(this, "Cant fetch Location", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                            catch (e: Exception) {

                                //else {
                                ///open settings since location is not enabled
                                Toast.makeText(this, "ENABLE LOCATION", Toast.LENGTH_SHORT).show()
                                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                startActivity(i)

                                //onRestart()

                                // }
                            }
                        }



                }
                else{
                    Toast.makeText(this,"Turn on location",Toast.LENGTH_SHORT).show()
                }


            }
            else {
                //request permission
                requestPermission()
                Toast.makeText(this,"its not working",Toast.LENGTH_SHORT).show()
            }






        }



    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.CAMERA
            ),
            PERMISSION
        )
       // getCurrentLocation()
    }
    companion object {
        private const val PERMISSION = 100
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }








    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
        {
            return true
        }
        return false
    }*/

    private fun getCurrentLocation() {

        if (userPermission.checkPermissions(this))
        {
            if (userPermission.isLocationEnabled(this)) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    userPermission.requestPermission(this)
                    //return
                }
                else {

                    try {

                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    for (location in locationResult.locations) {

                                        i.text="$location"



                                    }
                                    // Few more things we can do here:
                                    // For example: Update the location of user on server
                                }
                            },
                            Looper.myLooper()
                        )
                    }
                    catch (e: Exception) {

                        //else {
                        ///open settings since location is not enabled
                        Toast.makeText(this, "Can't fetch location", Toast.LENGTH_SHORT).show()



                        //onRestart()

                        // }
                    }
                }



            }
            else{
                Toast.makeText(this,"Turn on location",Toast.LENGTH_SHORT).show()
                userPermission.enableLocation(this)
            }

        }
        else {
            //request permission
            userPermission.requestPermission(this)

        }

    }

}