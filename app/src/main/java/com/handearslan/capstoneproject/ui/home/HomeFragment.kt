package com.handearslan.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.handearslan.capstoneproject.MainApplication
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.data.model.GetProductsResponse
import com.handearslan.capstoneproject.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val productAdapter = HomeProductAdapter(onProductClick = ::onProductClick)

    private val saleProductAdapter = HomeSaleProductAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProducts()

        with(binding) {
            // Tüm ürünleri gösteren adapter
            rvProducts.adapter = productAdapter

            // İndirimli ürünleri gösteren adapter
            rvSaleProduct.adapter = saleProductAdapter
        }
    }

    private fun getProducts() {
        MainApplication.productService?.getProducts()
            ?.enqueue(object : Callback<GetProductsResponse> {

                override fun onResponse(
                    call: Call<GetProductsResponse>,
                    response: Response<GetProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        productAdapter.submitList(result.products.orEmpty())
                        saleProductAdapter.submitList(
                            result.products.orEmpty().filter { it.saleState == true })
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            } )
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }
}