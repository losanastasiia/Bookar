package com.onpu.bookar.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onpu.bookar.R
import com.onpu.bookar.model.BookModel
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_book.view.*

class BookListAdapter(private val listener: OnBookClickedListener): RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private val books = mutableListOf<BookModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        with(holder.itemView) {
            name.text = book.name
            save.setImageResource(R.drawable.ic_star)
            save.setOnClickListener { listener.onFavouriteClicked(book) }
            setOnClickListener { listener.onBookClicked(book) }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface OnBookClickedListener {
        fun onBookClicked(bookModel: BookModel)
        fun onFavouriteClicked(bookModel: BookModel)
    }
}