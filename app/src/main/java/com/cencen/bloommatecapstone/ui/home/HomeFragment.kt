package com.cencen.bloommatecapstone.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cencen.bloommatecapstone.adapter.CatalogAdapter
import com.cencen.bloommatecapstone.databinding.FragmentHomeBinding
import com.cencen.bloommatecapstone.ui.UserViewModel
import com.cencen.bloommatecapstone.ui.login.LoginActivity
import com.cencen.bloommatecapstone.ui.maps.MapsActivity
import com.cencen.bloommatecapstone.util.ViewModelProviderFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var catalogAdapter: CatalogAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        initialVM()
        initialView()
        initialCatalog()
        Log.d("HomeFragment", "LayoutManager: ${binding.rvItemProduct.layoutManager}")

    }

    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun initialVM() {
        val fact: ViewModelProviderFactory = ViewModelProviderFactory.getInstance(requireActivity())

        homeViewModel = ViewModelProvider(this, fact)[HomeViewModel::class.java]
        userViewModel = ViewModelProvider(this, fact)[UserViewModel::class.java]
    }

    private fun initialView() {
        catalogAdapter = CatalogAdapter()

        binding.rvItemProduct.adapter = catalogAdapter
        binding.rvItemProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        homeViewModel.getCatalog()

        binding.btnNearestFlorist.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initialCatalog() {
        homeViewModel.getCatalog().observe(viewLifecycleOwner) {
            Log.d("HomeFragment", "Catalog data received: ${it?.toString()}")
            catalogAdapter.submitData(lifecycle, it)
            showLoading(false)
        }
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }*/
}