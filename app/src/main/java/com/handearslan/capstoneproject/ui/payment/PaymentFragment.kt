package com.handearslan.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentPaymentBinding
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ivBackPayment.setOnClickListener {
                findNavController().navigateUp()
            }

            btnCompletePayment.setOnClickListener {
                val addressTitle = etAddressTitle.text
                val address = etAddress.text
                val city = etCity.text
                val district = etDistrict.text
                val cardOwner = etCardOwner.text
                val cardNumber = etCardNumber.text
                val month = etMonth.text
                val year = etYear.text
                val cvc = etCvc.text

                when {
                    addressTitle.isNullOrEmpty() && address.isNullOrEmpty() && city.isNullOrEmpty() && district.isNullOrEmpty() && cardOwner.isNullOrEmpty() && cardNumber.isNullOrEmpty() && cvc.isNullOrEmpty() && month.isNullOrEmpty() && year.isNullOrEmpty() -> {
                        Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }

                    cardNumber?.length != 16 -> {
                        Toast.makeText(requireContext(), "Invalid card number", Toast.LENGTH_SHORT).show()
                    }

                    cvc?.length != 3 -> {
                        Toast.makeText(requireContext(), "Invalid cvc", Toast.LENGTH_SHORT).show()
                    }

                    year?.length != 4 -> {
                        Toast.makeText(requireContext(), "Invalid year", Toast.LENGTH_SHORT).show()
                    }

                    month?.length != 2 -> {
                        Toast.makeText(requireContext(), "Invalid month", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        findNavController().navigate(R.id.paymentToSuccess)
                    }
                }
            }
        }
    }
}