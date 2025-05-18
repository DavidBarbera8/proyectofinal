package com.example.noticiero.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.noticiero.R
import com.example.noticiero.databinding.FragmentNoticiaDetailBinding

class NoticiaDetailFragment : Fragment(R.layout.fragment_noticia_detail) {

    private lateinit var binding: FragmentNoticiaDetailBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var musicaPosicion: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoticiaDetailBinding.inflate(inflater, container, false)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musica)
        mediaPlayer.isLooping = true
        mediaPlayer.seekTo(musicaPosicion)
        mediaPlayer.start()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        musicaPosicion = mediaPlayer.currentPosition
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(musicaPosicion)
            mediaPlayer.start()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val book = NoticiaDetailFragmentArgs.fromBundle(requireArguments()).Book
        binding.tvTitle.text = book.title
        binding.tvAuthor.text = book.author
        binding.tvDescription.text = book.description

        Glide.with(requireContext())
            .load(book.imageUrl)
            .error(R.drawable.image_recipe_not_found)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.ivBookImage)

    }
}