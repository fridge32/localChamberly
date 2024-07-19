package com.chamberly.chamberly.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Shader
import android.graphics.LinearGradient
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.RemoteViews
import androidx.core.view.marginTop
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chamberly.chamberly.R
import com.chamberly.chamberly.models.Plan
import com.chamberly.chamberly.models.Perk
import com.chamberly.chamberly.models.Feature
import com.chamberly.chamberly.models.Review



class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boldText: TextView = itemView.findViewById(R.id.review_title)
        val normalText: TextView = itemView.findViewById(R.id.review_description)
        val byText: TextView = itemView.findViewById(R.id.review_author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.boldText.text = review.boldText
        holder.normalText.text = review.bodyText
        holder.byText.text = review.byText
    }

    override fun getItemCount(): Int = reviews.size
}

