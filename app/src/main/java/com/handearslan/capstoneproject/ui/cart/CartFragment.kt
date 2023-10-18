package com.handearslan.capstoneproject.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.handearslan.capstoneproject.MainApplication
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.data.model.CartItem
import com.handearslan.capstoneproject.data.model.GetCartProductsResponse
import com.handearslan.capstoneproject.data.model.GetProductsResponse
import com.handearslan.capstoneproject.databinding.FragmentCartBinding
import com.handearslan.capstoneproject.ui.home.HomeFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private lateinit var auth: FirebaseAuth

    private val cartAdapter = CartAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.uid


        getCartProducts(id)

        with(binding) {
            rvCart.adapter = cartAdapter
        }

    }

    private fun getCartProducts(id: Int) {
        val userId = auth.currentUser!!.uid
        MainApplication.cartService?.getCartProducts(userId)?.enqueue(object : Callback<GetCartProductsResponse> {

            override fun onResponse(call: Call<GetCartProductsResponse>, response: Response<GetCartProductsResponse>) {
                val result = response.body()

                if (result?.status == 200) {
                    cartAdapter.submitList(result.products.orEmpty())
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetCartProductsResponse>, t: Throwable) {
                Log.e("GetCartProducts", t.message.orEmpty())
            }
        })
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.cartToDetail(id))
    }
}