package com.example.canvas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StarsAdapter(private var stars: List<Star>, val context: Context, private val onStarClicked: (Star) -> Unit, private val onStarShareClicked: (Star) -> Unit) :
    RecyclerView.Adapter<StarsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val image: ImageView
        val share: ImageView
        val star1: ImageView
        val star2: ImageView
        val star3: ImageView
        val star4: ImageView
        val star5: ImageView

        init {
            // Define click listener for the ViewHolder's View
            name = view.findViewById(R.id.star_name)
            image = view.findViewById(R.id.star_image)
            share = view.findViewById(R.id.share)
            star1 = view.findViewById(R.id.star1)
            star2 = view.findViewById(R.id.star2)
            star3 = view.findViewById(R.id.star3)
            star4 = view.findViewById(R.id.star4)
            star5 = view.findViewById(R.id.star5)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.star_card, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val star = stars[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = star.name
        Glide.with(context).load(star.imageUrl).into(viewHolder.image)

        viewHolder.itemView.setOnClickListener { onStarClicked(star) }

        viewHolder.share.setOnClickListener { onStarShareClicked(star) }

        viewHolder.star1.setImageResource(if (star.rating >= 1) R.drawable.filled_star else R.drawable.star)
        viewHolder.star2.setImageResource(if (star.rating >= 2) R.drawable.filled_star else R.drawable.star)
        viewHolder.star3.setImageResource(if (star.rating >= 3) R.drawable.filled_star else R.drawable.star)
        viewHolder.star4.setImageResource(if (star.rating >= 4) R.drawable.filled_star else R.drawable.star)
        viewHolder.star5.setImageResource(if (star.rating >= 5) R.drawable.filled_star else R.drawable.star)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = stars.size

    fun filterList(filteredStars: List<Star>) {
        stars = filteredStars
        notifyDataSetChanged()
    }

}