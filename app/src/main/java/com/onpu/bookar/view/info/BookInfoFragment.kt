package com.onpu.bookar.view.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.onpu.bookar.R
import com.onpu.bookar.model.data.BookModel
import kotlinx.android.synthetic.main.fragment_book_info.*

class BookInfoFragment : Fragment(R.layout.fragment_book_info) {

    private val args: BookInfoFragmentArgs by navArgs()

    private val viewModel: BookInfoViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        )[BookInfoViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        viewModel.getBookInfo(args.bookInfo)
        viewModel.bookInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                BookInfoViewModel.LoadingProcess.Loading -> {}
                is BookInfoViewModel.LoadingProcess.Loaded -> it.books?.let { book -> initData(book) }
            }
        })
    }

    private fun initData(bookModel: BookModel) {
        val details = bookModel.details
        Glide.with(requireContext()).load(details.images.thumbnail).into(image)
        price.text = bookModel.saleInfo.listPrice.amount.toString()
        priceCurrency.text = bookModel.saleInfo.listPrice.currencyCode
        title.text = details.title
        authors.text = details.authors.joinToString(", ")
        categories.text = details.categories.joinToString(", ")
        description.text = details.description
    }
}