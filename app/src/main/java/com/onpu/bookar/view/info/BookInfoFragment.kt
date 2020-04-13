package com.onpu.bookar.view.info

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
                BookInfoViewModel.LoadingProcess.Loading -> {
                }
                is BookInfoViewModel.LoadingProcess.Loaded -> it.books?.let { book -> initData(book) }
            }
        })
    }

    private fun initData(bookModel: BookModel) {
        val details = bookModel.details
        details.images?.let { images ->
            images.thumbnail?.let {
                Glide.with(requireContext()).load(it).into(image)
            } ?: image.setImageResource(R.drawable.ic_book_image)
        } ?: image.setImageResource(R.drawable.ic_book_image)
        price.text =
            bookModel.saleInfo.listPrice?.amount?.toString()
                ?: run {
                    buy.isVisible = false
                    getString(R.string.not_for_sale)
                }
        priceCurrency.text = bookModel.saleInfo.listPrice?.currencyCode
        title.text = details.title
        details.authors?.let {
            authors.text = it.joinToString(", ")
        } ?: kotlin.run {
            authors.isVisible = false
            authorsTv.isVisible = false
        }
        details.categories?.let {
            categories.text = it.joinToString(", ")
        } ?: kotlin.run {
            categories.isVisible = false
            categoriesTv.isVisible = false
        }
        details.description?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                description.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            } else {
                description.text = Html.fromHtml(it)
            }
        } ?: kotlin.run {
            descriptionTv.isVisible = false
            description.isVisible = false
        }

        preview.setOnClickListener {
            bookModel.details.previewLink?.let {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            } ?: Snackbar.make(
                this.requireView(),
                getString(R.string.cant_open_preview),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        buy.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(bookModel.saleInfo.buyLink))
            startActivity(browserIntent)
        }
    }
}