package com.chamberly.chamberly.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.chamberly.chamberly.R
import com.chamberly.chamberly.models.Plan

class PlanAdapter(private val plans: List<Plan>, private val onPlanSelected: (Plan) -> Unit) :
    RecyclerView.Adapter<PlanAdapter.PaywallViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    class PaywallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planContainer: ConstraintLayout = itemView.findViewById(R.id.plan_container)
        val planTitle: TextView = itemView.findViewById(R.id.plan_title)
        val planPrice: TextView = itemView.findViewById(R.id.plan_price)
        val planDetails: TextView = itemView.findViewById(R.id.plan_details)
        val sparkle: ImageView = itemView.findViewById(R.id.sparkle)
        val bestDealBadge: ImageView = itemView.findViewById(R.id.best_deal_badge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaywallViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)
        return PaywallViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaywallViewHolder, position: Int) {
        val plan = plans[position]
        holder.planTitle.text = plan.title
        holder.planPrice.text = plan.price
        holder.planDetails.text = plan.details

        if (plan.isBestDeal) {
            holder.sparkle.visibility = View.VISIBLE
            holder.bestDealBadge.visibility = View.VISIBLE
        } else {
            holder.sparkle.visibility = View.INVISIBLE
            holder.bestDealBadge.visibility = View.INVISIBLE
        }

        if (position == selectedPosition) {
            holder.planContainer.setBackgroundResource(R.drawable.plan_selected)
        } else {
            holder.planContainer.setBackgroundResource(R.drawable.plan_cards)
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            onPlanSelected(plan)
        }
    }

    override fun getItemCount(): Int = plans.size
}