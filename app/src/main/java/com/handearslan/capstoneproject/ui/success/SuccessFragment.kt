package com.handearslan.capstoneproject.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    private val viewModel by viewModels<SuccessViewModel>()

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnContinueShopping.setOnClickListener {
                findNavController().navigate(SuccessFragmentDirections.successToHome())

            }
        }
        observeData()
    }

    private fun observeData()  {
        viewModel.successState.observe(viewLifecycleOwner) { state ->
            when (state) {

                is SuccessState.Success -> {
                    viewModel.clearCart(auth.currentUser?.uid.toString())
                }
                else -> {
                    Snackbar.make(requireView(),getString(R.string.fail),Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}