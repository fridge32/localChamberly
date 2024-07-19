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


class PerkAdapter(private val perks: List<Perk>) : RecyclerView.Adapter<PerkAdapter.PerkViewHolder>() {

    class PerkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val perkImage: ImageView = itemView.findViewById(R.id.perk_image)
        val perkRainbowText: TextView = itemView.findViewById(R.id.perk_rainbow_text)
        val perkText: TextView = itemView.findViewById(R.id.perk_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perk, parent, false)
        return PerkViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerkViewHolder, position: Int) {
        val perk = perks[position]
        holder.perkImage.setImageResource(perk.imageResId)
        holder.perkRainbowText.text = perk.rainbowText
        applyGradientToText(holder.perkRainbowText)
        holder.perkText.text = perk.text
    }

    override fun getItemCount(): Int = perks.size

    private fun applyGradientToText(textView: TextView) {
        val paint = textView.paint
        val width = paint.measureText(textView.text.toString())
        val textShader: Shader = LinearGradient(
            0f, 0f, width, 0f, intArrayOf(
                Color.parseColor("#7A7AFF"),
                Color.parseColor("#B2F84950")
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
    }}

