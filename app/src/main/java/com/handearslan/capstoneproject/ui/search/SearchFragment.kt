package com.handearslan.capstoneproject.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.handearslan.capstoneproject.MainApplication
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.data.model.GetProductsResponse
import com.handearslan.capstoneproject.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

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
                        performSearch(newText)
                    } else {
                        searchAdapter.submitList(emptyList())
                    }
                    return true
                }
            })
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }


    private fun performSearch(query: String) {

        MainApplication.productService?.getSearchResult(query)
            ?.enqueue(object : Callback<GetProductsResponse> {
                override fun onResponse(
                    call: Call<GetProductsResponse>,
                    response: Response<GetProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        searchAdapter.submitList(result.products.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("SearchFragment", t.message.orEmpty())
                }
            })
    }
}
