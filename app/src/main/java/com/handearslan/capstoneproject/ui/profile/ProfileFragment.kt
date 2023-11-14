package com.handearslan.capstoneproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.gone
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.common.visible
import com.handearslan.capstoneproject.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        with(binding) {

            cardView.setOnClickListener {
                findNavController().setGraph(R.navigation.login_graph)
                viewModel.logOut()
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileState.Loading -> pbUser.visible()

                is ProfileState.SuccessState -> {
                    pbUser.gone()
                    tvUserEmail.text = state.email
                }

                is ProfileState.EmptyScreen -> {
                    pbUser.gone()

                }

                is ProfileState.ShowSnackbar -> {
                    pbUser.gone()

                }
            }
        }
    }
}