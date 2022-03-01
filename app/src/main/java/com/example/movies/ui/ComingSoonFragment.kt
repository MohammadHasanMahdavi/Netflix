package com.example.movies.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movies.R
import com.example.movies.databinding.FragmentComingSoonBinding
import com.example.movies.data.models.Movie


class ComingSoonFragment : Fragment(R.layout.fragment_coming_soon) {
    val movie1 : Movie = Movie("1883", R.drawable.the_1883_2022,false)
    val movie2 : Movie = Movie("The kings man" , R.drawable.the_kings_man_2021,false)
    val movie3: Movie = Movie("The 355", R.drawable.the_355,false)

    private var _binding : FragmentComingSoonBinding? = null
    val  binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComingSoonBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            image1.setImageResource(movie1.id)
            image2.setImageResource(movie2.id)
            image3.setImageResource(movie3.id)

            name1.text = movie1.title
            name2.text = movie2.title
            name3.text = movie3.title

            shareButton1.setOnClickListener {
                sendTextToOtherApp(movie1.title)
            }
            shareButton2.setOnClickListener {
                sendTextToOtherApp(movie2.title)
            }
            shareButton3.setOnClickListener {
                sendTextToOtherApp(movie3.title)
            }
        }

    }
    fun sendTextToOtherApp(title: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, title)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}