package com.onpu.bookar.view.find

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onpu.bookar.R
import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.view.BookListAdapter
import kotlinx.android.synthetic.main.fragment_find_book.*

class FindBookFragment : Fragment(R.layout.fragment_find_book),
    BookListAdapter.OnBookClickedListener {

    private lateinit var viewModel: FindBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        )[FindBookViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setTitle(R.string.find_book)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        // инициализируем наш RecyclerView
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = BookListAdapter(this)
        find.setOnClickListener {
            if (query.text.isNotEmpty())
                viewModel.findBook(query.text.toString())
            else
                query.error = getString(R.string.type_book_title)
        }
    }

    override fun onBookClicked(bookModel: BookModel) {
        findNavController().navigate(FindBookFragmentDirections.actionToInfo(bookModel.id))
    }

    override fun onFavouriteClicked(bookModel: BookModel) {
        viewModel.onFavouriteChanged(bookModel)
    }

    // подписываемся на события загрузки данных, которые нам посылает наша вью модель
    private fun observeEvents() {
        viewModel.eventsLd.observe(viewLifecycleOwner, Observer {
            when (it) {
                FindBookViewModel.Events.StartLoad -> {
                    search.isVisible = false
                    loading.isVisible = true
                    errorTv.isVisible = false
                    rvList.isVisible = false
                }
                FindBookViewModel.Events.Finished -> {
                    loading.isVisible = false
                    errorTv.isVisible = false
                    rvList.isVisible = true
                }
                FindBookViewModel.Events.Error -> {
                    loading.isVisible = false
                    search.isVisible = false
                    errorTv.isVisible = true
                    rvList.isVisible = false
                }
            }
        })
        // заполняем на адаптер из RecyclerView данными
        viewModel.bookInfo.observe(viewLifecycleOwner, Observer {
            search.isVisible = it.books?.isEmpty() == true
            (rvList.adapter as BookListAdapter).books = it?.books?: emptyList()
            (rvList.adapter as BookListAdapter).notifyDataSetChanged()
        })
    }
}