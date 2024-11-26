package com.example.canvas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: StarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchView: SearchView = findViewById(R.id.search)

        val stars = listOf(
            Star("andrew garfield", 5, "https://i.pinimg.com/736x/ac/aa/07/acaa07a0eabfdafc195e454b76dc14dc.jpg"),
            Star("Natalie Dormer", 5, "https://shorturl.at/ACmat"),
            Star("Jackie Chan", 5, "https://shorturl.at/KVUK2"),
            Star("Will Smith", 5, "https://shorturl.at/wi5J8"),
            Star("Leonardo DiCaprio", 5, "https://shorturl.at/F18Sh"),
            Star("Jim Carreyn", 5, "https://tinyurl.com/42fnmeny"),
            Star("Paul Walker", 5, "https://tinyurl.com/mrb7sbf4"),
            Star("Chris Hemsworth", 5, "https://tinyurl.com/327wz4xy"),
            Star("Morgan Freeman", 5, "https://tinyurl.com/5rvyyjmk"),
        )

        adapter = StarsAdapter(stars, this, onStarClicked = { star -> showRatingDialog(star) }) { star ->
            shareStar(star)
        }

        val recyclerView: RecyclerView = findViewById(R.id.products_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = stars.filter { it.name.contains(newText ?: "", ignoreCase = true) }
                adapter.filterList(filteredList)
                return true
            }
        })


    }

    private fun showRatingDialog(star: Star) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_rating, null)
        val ratingInput = dialogView.findViewById<TextInputEditText>(R.id.etRating)

        AlertDialog.Builder(this)
            .setTitle("Modifier la note")
            .setView(dialogView)
            .setPositiveButton("Enregistrer") { _, _ ->
                val newRating = ratingInput.text.toString().toFloatOrNull()
                if (newRating != null && newRating in 1.0..5.0) {
                    star.rating = newRating.toInt()
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Entrez une note valide (1 à 5)", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun shareStar(star: Star) {
        val message = "Découvrez cette star : ${star.name}, notée ${star.rating} étoiles"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}