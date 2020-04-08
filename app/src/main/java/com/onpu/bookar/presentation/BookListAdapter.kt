package com.onpu.bookar.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onpu.bookar.R
import com.onpu.bookar.model.BookModel
import kotlinx.android.synthetic.main.item_book.view.*

class BookListAdapter(private val listener: OnBookClickedListener): RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private val books = mutableListOf<BookModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            name.text = books[position].name
            setOnClickListener { listener.onBookClicked(books[position]) }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface OnBookClickedListener {
        fun onBookClicked(bookModel: BookModel)
        fun onFavouriteClicked(bookModel: BookModel)
    }
}