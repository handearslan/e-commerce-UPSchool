package com.handearslan.capstoneproject.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter(
        onProductClick = ::onProductClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearch.adapter = searchAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText.length > 3) {
                        viewModel.getSearchResult(newText)
                    } else {
                        searchAdapter.submitList(emptyList())
                    }
                    return true
                }
            })
        }
        observeData()
    }

    private fun observeData()= with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.Loading -> {
                    pbSearch.visibility = View.VISIBLE
                }

                is SearchState.SuccessState -> {
                    pbSearch.visibility = View.GONE
                    searchAdapter.submitList(state.product)
                }

                is SearchState.EmptyScreen -> {
                    pbSearch.visibility = View.GONE
                    searchAdapter.submitList(emptyList())
                    Snackbar.make(requireView(), state.failMessage, Snackbar.LENGTH_SHORT).show()
                }

                is SearchState.ShowSnackbar -> {
                    pbSearch.visibility = View.GONE
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }


    }
    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }
}