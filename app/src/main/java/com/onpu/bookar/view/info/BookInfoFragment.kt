package com.onpu.bookar.view.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.onpu.bookar.R
import kotlinx.android.synthetic.main.fragment_book_info.*

class BookInfoFragment : Fragment(R.layout.fragment_book_info) {

    private val args: BookInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        initData()
    }

    private fun initData() {
        val details = args.bookInfo.details
        Glide.with(requireContext()).load(details.images.thumbnail).into(image)
        price.text = args.bookInfo.saleInfo.listPrice.amount.toString()
        priceCurrency.text = args.bookInfo.saleInfo.listPrice.currencyCode
        title.text = details.title
        authors.text = details.authors.joinToString(", ")
        categories.text = details.categories.joinToString(", ")
        description.text = details.description
    }
}