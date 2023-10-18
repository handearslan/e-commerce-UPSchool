package com.handearslan.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.handearslan.capstoneproject.MainApplication
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.data.model.AddToCartResponse
import com.handearslan.capstoneproject.data.model.CartItem
import com.handearslan.capstoneproject.data.model.GetProductDetailResponse
import com.handearslan.capstoneproject.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductDetail(args.id)

        auth = Firebase.auth

        auth.currentUser?.uid



        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddCart.setOnClickListener {
                addToCart(args.id)
            }

            ivEmptyFav.setOnClickListener{
                ivEmptyFav.visibility = View.GONE
                ivFav.visibility = View.VISIBLE
            }
        }
    }

    private fun getProductDetail(id: Int) {
        MainApplication.productService?.getProductDetail(id)?.enqueue(object :
            Callback<GetProductDetailResponse> {
            override fun onResponse(
                call: Call<GetProductDetailResponse>,
                response: Response<GetProductDetailResponse>
            ) {
                val result = response.body()

                if (result?.status == 200 && result.product != null) {
                    with(binding) {
                        val product = result.product
                        Glide.with(binding.ivProduct).load(product.imageOne).into(ivProduct)
                        tvTitle.text = product.title
                        tvPrice.text = "${product.price} ₺"
                        tvDescription.text = product.description
                        // RatingBar'ı bağla ve ürünün puanını ayarla
                        product.rate?.let { nonNullRate ->
                            ratingBar.rating = nonNullRate.toFloat()
                        } ?: run {
                            // Eğer rate null ise RatingBar'ı varsayılan bir değerle ayarlayabilirsiniz.
                            ratingBar.rating = 0.0f // Varsayılan değer
                        }

                        if (product.saleState == true) {
                            // Eğer ürün indirimli ise, indirimli fiyatı göster
                            tvSalePrice.visibility = View.VISIBLE
                            tvSalePrice.text = "${product.salePrice} ₺"
                            tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


                        } else {
                            // İndirimli ürün değilse, indirimli fiyatı gizle
                            tvSalePrice.visibility = View.GONE
                            tvPrice.paintFlags = 0
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("GetProductDetail", t.message.orEmpty())
            }
        })
    }

    private fun addToCart(id: Int) {
        val cartItem = CartItem(
            userId = auth.currentUser?.uid.orEmpty(),
            productId = id,
        )

        MainApplication.cartService?.addToCart(cartItem)?.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(call: Call<AddToCartResponse>, response: Response<AddToCartResponse>) {
                val result = response.body()

                if (result?.status == 200) {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                Log.e("AddToCart", t.message.orEmpty())
            }
        })
    }

}