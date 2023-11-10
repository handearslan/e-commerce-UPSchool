package com.handearslan.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentPaymentBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val viewModel by viewModels<PaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ivBackPayment.setOnClickListener {
                findNavController().navigateUp()
            }

            btnCompletePayment.setOnClickListener {
                val addressTitle = etAddressTitle.text.toString()
                val address = etAddress.text.toString()
                val city = etCity.text.toString()
                val district = etDistrict.text.toString()
                val cardOwner = etCardOwner.text.toString()
                val cardNumber = etCardNumber.text.toString()
                val month = etMonth.text.toString()
                val year = etYear.text.toString()
                val cvc = etCvc.text.toString()
                val isValid = viewModel.checkInfo(
                    addressTitle,
                    address,
                    city,
                    district,
                    cardOwner,
                    cardNumber,
                    month,
                    year,
                    cvc
                )

                if (isValid) {
                    viewModel.clearCart()
                } else {
                    Snackbar.make(requireView(), R.string.invalid_payment, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
        observePayment()
    }

    private fun observePayment() {
        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PaymentState.ClearCart -> {
                    findNavController().navigate(R.id.paymentToSuccess)
                }

                is PaymentState.ShowSnackbar -> {
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}