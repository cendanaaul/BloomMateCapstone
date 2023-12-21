package com.cencen.bloommatecapstone.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cencen.bloommatecapstone.adapter.CartAdapter
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.databinding.FragmentCartBinding
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private val cartAdapter: CartAdapter by lazy { CartAdapter(onDeleteClickListener) }
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory.getInstance(
            requireContext()
        )
    }
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val onDeleteClickListener = object : CartAdapter.OnDeleteClickListener {
        override fun onDeleteClick(productId: String) {
            lifecycleScope.launch {
                cartViewModel.deleteProduct(productId).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Libraries.Loading -> {
                            // handle loading
                        }
                        is Libraries.Error -> {
                            // handle error
                        }
                        is Libraries.Success -> {
                            // handle success, if needed
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = cartAdapter

        }



        lifecycleScope.launch {
            cartViewModel.getCarts().observe(viewLifecycleOwner){result ->
                when (result) {
                    is Libraries.Loading -> {
                        // handle loading
                    }

                    is Libraries.Error -> {
                        //handle error
                    }

                    is Libraries.Success -> {
                        var total = 0
                        var listFlower = mutableListOf<String>()
                        result.data.forEach {
                            total += it.price.toInt() * it.quantity
                            listFlower.add(it.name)
                        }
                        cartAdapter.submitList(result.data)
                        binding.tvTotalPrice.text = "Total: Rp $total"
                        binding.btnCheckout.setOnClickListener{
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "This is my favorite flower: $listFlower")
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                    }
                }
            }
        }
//        lifecycleScope.launch {
//            cartViewModel.getTotalPrice().observe(viewLifecycleOwner) { totalPrice ->
//                binding.tvTotalPrice.text = "Total: Rp $totalPrice"
//            }
//        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}