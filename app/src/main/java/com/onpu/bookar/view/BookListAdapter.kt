package com.onpu.bookar.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.onpu.bookar.R
import com.onpu.bookar.model.data.BookModel
import kotlinx.android.synthetic.main.item_book.view.*

class BookListAdapter(private val listener: OnBookClickedListener, val isSavedList: Boolean) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    var books = listOf<BookModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        with(holder.itemView) {
            name.text = book.details.title
            initImage(book.details.images.thumbnail, image)
            if (isSavedList)
                save.setImageResource(R.drawable.ic_star)
            else
                save.setImageResource(R.drawable.ic_star_border)
            save.setOnClickListener { listener.onFavouriteClicked(book) }
            setOnClickListener { listener.onBookClicked(book) }
        }
    }

    private fun initImage(uri: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(uri)
            .centerCrop()
            .into(imageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnBookClickedListener {
        fun onBookClicked(bookModel: BookModel)
        fun onFavouriteClicked(bookModel: BookModel)
    }
}