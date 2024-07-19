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


class FeatureAdapter(private val features: List<Feature>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val VIEW_TYPE_HEADER = 0
        private val VIEW_TYPE_ITEM = 1
        private var isCollapsed = true
        private val COLLAPSED_SIZE = 5

        override fun getItemViewType(position: Int): Int {
            return if (!features[position].border) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == VIEW_TYPE_HEADER) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feature_header, parent, false)
                HeaderViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feature, parent, false)
                FeatureViewHolder(view)
            }
        }

//    Implements the dynamic portion of features why checking what type the arg is and setting it and its visibility
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val feature = features[position]
            if (holder is FeatureViewHolder) {
                holder.featureText.text = feature.text

                when (val freeValue = feature.free) {
                    is String -> {
                        holder.freeText.text = freeValue
                        holder.freeText.visibility = View.VISIBLE
                        holder.freeIcon.visibility = View.GONE
                    }
                    is Int -> {
                        holder.freeIcon.setImageResource(freeValue)
                        holder.freeIcon.visibility = View.VISIBLE
                        holder.freeText.visibility = View.GONE
                    }
                }

                when (val paidValue = feature.paid) {
                    is String -> {
                        holder.paidText.text = paidValue
                        holder.paidText.visibility = View.VISIBLE
                        holder.paidIcon.visibility = View.GONE
                    }
                    is Int -> {
                        holder.paidIcon.setImageResource(paidValue)
                        holder.paidIcon.visibility = View.VISIBLE
                        holder.paidText.visibility = View.GONE
                    }
                }

                holder.divider.visibility = if (feature.border) View.VISIBLE else View.GONE
            } else if (holder is HeaderViewHolder) {
                holder.headerText.text = feature.text
                holder.headerBody.text = feature.free.toString()
            }
        }

        override fun getItemCount(): Int {
            return if (isCollapsed) COLLAPSED_SIZE else features.size
        }

        fun toggleCollapse() {
            isCollapsed = !isCollapsed
            notifyDataSetChanged()
        }

        inner class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val featureText: TextView = itemView.findViewById(R.id.feature_text)
            val freeText: TextView = itemView.findViewById(R.id.free_text)
            val plus: ImageView = itemView.findViewById(R.id.plus)
            val freeIcon: ImageView = itemView.findViewById(R.id.free_icon)
            val paidText: TextView = itemView.findViewById(R.id.paid_text)
            val paidIcon: ImageView = itemView.findViewById(R.id.paid_icon)
            val divider: View = itemView.findViewById(R.id.divider)
        }

        inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val headerText: TextView = itemView.findViewById(R.id.header_text)
            val headerBody: TextView = itemView.findViewById(R.id.free_text)
            val headerPlus: ImageView = itemView.findViewById(R.id.plus)
            val headerIcon: ImageView = itemView.findViewById(R.id.paid_icon)
        }

    }
