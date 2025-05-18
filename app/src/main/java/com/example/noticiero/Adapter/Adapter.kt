package com.example.noticiero.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.noticiero.Entities.Noticia
import com.example.noticiero.R
import com.example.noticiero.databinding.ItemNoticiaBinding

class Adapter(private val noticiaList: List<Noticia>, private val onClick: (Noticia) -> Unit,
              private val onLongClick: (Noticia) -> Unit,
              private val onFavoriteClick: (Noticia) -> Unit) :
    RecyclerView.Adapter<Adapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemNoticiaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = noticiaList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = noticiaList.size

    inner class BookViewHolder(private val binding: ItemNoticiaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noticia: Noticia) {
            val ivImagen = binding.bookImage
            binding.bookTitle.text = noticia.title
            binding.bookAuthor.text = noticia.author
            binding.bookDescription.text = noticia.description

            Glide.with(ivImagen.context)
                .load(noticia.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivImagen)

            binding.root.setOnClickListener { onClick(noticia) }

            binding.root.setOnLongClickListener {
                onLongClick(noticia)
                true
            }

            binding.btnLike.setOnClickListener { onFavoriteClick(noticia) }

            if (noticia.esFavorita) {
                binding.btnLike.setBackgroundResource(R.drawable.ic_favorite_filled)
            } else {
                binding.btnLike.setBackgroundResource(R.drawable.ic_favorite_border)
            }
        }
    }
}