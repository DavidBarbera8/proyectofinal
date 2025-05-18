package com.example.noticiero.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noticiero.Data.Aplication
import com.example.noticiero.Entities.UsuarioEntity
import com.example.noticiero.R
import com.example.noticiero.databinding.FragmentNoticiaListBinding
import com.example.noticiero.Adapter.Adapter
import com.example.noticiero.Entities.Noticia
import kotlinx.coroutines.launch

class NoticiaListFragment : Fragment(R.layout.fragment_noticia_list) {

    private lateinit var binding: FragmentNoticiaListBinding
    private var usuario: UsuarioEntity? = null
    private lateinit var adapter: Adapter
    private var noticiaList: MutableList<Noticia> = mutableListOf()
    private lateinit var mediaPlayer: MediaPlayer
    private var musicaPosicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuario = NoticiaListFragmentArgs.fromBundle(it).Usuario
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoticiaListBinding.inflate(inflater, container, false)
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




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter(
            noticiaList = noticiaList,
            onClick = { book ->
                val action = NoticiaListFragmentDirections.actionBookListFragmentToBookDetailFragment(book)
                findNavController().navigate(action)
            },
            onLongClick = { book ->
                val index = noticiaList.indexOf(book)
                if (index != -1) {
                    noticiaList.removeAt(index)
                    adapter.notifyItemRemoved(index)
                    Toast.makeText(requireContext(), "Libro eliminado", Toast.LENGTH_SHORT).show()
                }
            },

            onFavoriteClick = { book ->
                val index = noticiaList.indexOf(book)
                if (index != -1) {
                    book.esFavorita = !book.esFavorita
                    adapter.notifyItemChanged(index)
                    val msg = if (book.esFavorita) "Libro marcado como favorito" else "Libro desmarcado"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }
        )
            binding.recyclerView.adapter = adapter

            loadBooksFromDb()
        }

    private fun loadBooksFromDb() {
        lifecycleScope.launch {
            val booksFromDb = Aplication.baseDeDatos.bookDao().getAllBooks()
            noticiaList.clear()
            noticiaList.addAll(booksFromDb)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(musicaPosicion)
            mediaPlayer.start()
        }
        loadBooksFromDb()
    }
}
