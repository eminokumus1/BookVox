package com.eminokumus.bookvox.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eminokumus.bookvox.ScreenType
import com.eminokumus.bookvox.databinding.ItemBookBinding
import com.eminokumus.bookvox.model.Book

class HomeAdapter(val screenType: ScreenType) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var bookList = mutableListOf<Book>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onBookItemClickListener: OnBookItemClickListener? = null

    class HomeViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindHome(item: Book, onBookItemClickListener: OnBookItemClickListener?) {
            setImageWithGlide(binding.bookImage, item.imageUrl)
            binding.bookNameText.text = item.name
            binding.bookAuthorText.text = item.author

            binding.root.setOnClickListener {
                onBookItemClickListener?.onItemClick(item)
            }
        }

        fun bindSearch(item: Book, onBookItemClickListener: OnBookItemClickListener?) {
            setImageWithGlide(binding.bookImage, item.imageUrl)
            binding.bookNameText.text = item.name
            binding.bookAuthorText.visibility = View.GONE

            binding.root.setOnClickListener {
                onBookItemClickListener?.onItemClick(item)
            }
        }

        private fun setImageWithGlide(imageView: ImageView, imageUrl: String?) {
            imageUrl.let {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = bookList[position]
        if (screenType == ScreenType.HOME) {
            holder.bindHome(item, onBookItemClickListener)
        } else if (screenType == ScreenType.SEARCH) {
            holder.bindSearch(item, onBookItemClickListener)
        }
    }

}

interface OnBookItemClickListener {
    fun onItemClick(book: Book)
}