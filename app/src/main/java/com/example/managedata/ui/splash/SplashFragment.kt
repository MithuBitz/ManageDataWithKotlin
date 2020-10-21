package com.example.managedata.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.managedata.R

const val PERMISSION_REQUEST_CODE = 1001 // Value will be anything

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //IF permission is granted by the user
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {
            displayMainFragment()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMainFragment()
            } else {
                Toast.makeText(requireContext(),"Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayMainFragment() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        navController.navigate(R.id.action_nav_main, null,
            NavOptions.Builder() //is used to prevent go back again to splash screen
                .setPopUpTo(R.id.splashFragment, true)
                .build()
            )
    }
}