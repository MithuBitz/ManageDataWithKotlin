package com.example.managedata.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.managedata.LOG_TAG
import com.example.managedata.R
import com.example.managedata.ui.shared.SharedViewModel


class DetailFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       //Enable the up navigation icon on the fragment
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //This line is important if you works with fragment
        setHasOptionsMenu(true)
        //Initialize the navController
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        //Initialize the viewModel
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.selectedMonster.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Selected Monster: ${it.monsterName}")
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }


}