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
import com.handearslan.capstoneproject.common.gone
import com.handearslan.capstoneproject.common.visible
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
                viewModel.checkInfo(
                    etAddressTitle.text.toString(),
                    etAddress.text.toString(),
                    etCity.text.toString(),
                    etDistrict.text.toString(),
                    etCardOwner.text.toString(),
                    etCardNumber.text.toString(),
                    etMonth.text.toString(),
                    etYear.text.toString(),
                    etCvc.text.toString()
                )
                viewModel.clearCart()
            }
        }

        observePayment()
    }

    private fun observePayment() = with(binding) {
        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PaymentState.Loading -> pbPayment.visible()

                is PaymentState.SuccessState -> {
                    pbPayment.gone()
                    findNavController().navigate(R.id.paymentToSuccess)
                }

                is PaymentState.ShowSnackbar -> {
                    pbPayment.gone()
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}