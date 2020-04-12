package com.onpu.bookar.view.saved

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onpu.bookar.R
import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.view.BookListAdapter
import kotlinx.android.synthetic.main.fragment_saved_books.*

class SavedBookFragment: Fragment(R.layout.fragment_saved_books), BookListAdapter.OnBookClickedListener {

    private val viewModel: SavedBookViewModel by lazy { ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())[SavedBookViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initLoading()
        with(toolbar) {
            setTitle(R.string.saved_books)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { findNavController().popBackStack() }
            setOnMenuItemClickListener { findNavController().popBackStack() }
        }
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = BookListAdapter(this)
        viewModel.booksLd.observe(viewLifecycleOwner, Observer {
            when(it) {
                SavedBookViewModel.LoadingProcess.Loading -> {
                    progress.isVisible = true
                    noResult.isVisible = false
                    rvList.isVisible = false
                }
                is SavedBookViewModel.LoadingProcess.Loaded -> {
                    (rvList.adapter as BookListAdapter).books = it.books
                    (rvList.adapter as BookListAdapter).notifyDataSetChanged()
                    progress.isVisible = false
                    noResult.isVisible = it.books.isEmpty()
                    rvList.isVisible = it.books.isNotEmpty()
                }
            }
        })
    }

    override fun onBookClicked(bookModel: BookModel) {
        findNavController().navigate(SavedBookFragmentDirections.actionToInfo(bookModel.id))
    }

    override fun onFavouriteClicked(bookModel: BookModel) {
        viewModel.onSaveClicked(bookModel)
    }
}