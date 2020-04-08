package com.onpu.bookar.presentation.find

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onpu.bookar.R
import com.onpu.bookar.model.BookModel
import com.onpu.bookar.presentation.BookListAdapter
import kotlinx.android.synthetic.main.fragment_find_book.*

class FindBookFragment: Fragment(R.layout.fragment_find_book),
    BookListAdapter.OnBookClickedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setTitle(R.string.find_book)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = BookListAdapter(this)
    }

    override fun onBookClicked(bookModel: BookModel) {

    }

    override fun onFavouriteClicked(bookModel: BookModel) {
    }
}