package com.chamberly.chamberly.presentation.fragments

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Canvas
import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chamberly.chamberly.R
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.chamberly.chamberly.presentation.adapters.PlanAdapter
import com.chamberly.chamberly.presentation.adapters.FeatureAdapter
import com.chamberly.chamberly.presentation.adapters.ReviewAdapter
import com.chamberly.chamberly.presentation.adapters.PerkAdapter
import com.chamberly.chamberly.models.Plan
import com.chamberly.chamberly.models.Perk
import com.chamberly.chamberly.models.Feature
import com.chamberly.chamberly.models.Review
import androidx.navigation.fragment.findNavController
import android.util.Log


class PaywallFragment : Fragment() {

    private val collapsedItemsCount = 5
    private var isCollapsed = true

    private lateinit var dotLayout: LinearLayout
    private lateinit var viewPager: ViewPager2

    private lateinit var reviewdotLayout: LinearLayout

    private lateinit var selectedPlan: Plan
    private lateinit var adapter: PlanAdapter

    private lateinit var featureAdapter: FeatureAdapter
    private lateinit var recyclerViewFeatures: RecyclerView

    private lateinit var exitButton: ImageView

    private lateinit var features: List<Feature>
    private lateinit var viewMoreButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_paywall, container, false)

        val plans = listOf(
            Plan(isBestDeal = true, title = "YEARLY PLAN", price = "360 KR", details = "•30 kr/month\n•Free 7-days trial"),
            Plan(isBestDeal = false, title = "MONTHLY PLAN", price = "39 KR", details = "•39 kr/month\n•Free 3-days trial")
        )

        val perks = listOf(
            Perk(imageResId = R.drawable.perk_hashtag, rainbowText = "Endless topics to follow", text = "Follow vast range of topics. Stay connected to the discussions that matter to you most, without any limits."),
            Perk(imageResId = R.drawable.perk_chat, rainbowText = "Direct messaging to listeners", text = "Connect directly with listeners who are ready to support you."),
            Perk(imageResId = R.drawable.perk_avatar, rainbowText = "Custom avatars & badges", text = "10 Super badges. Unlimited avatars. Showcase your achievements and stand out in the community."),
            Perk(imageResId = R.drawable.perk_access, rainbowText = "Unlimited access to listener AI", text = "Whether it's day or night, your virtual companion is always here to help."),
            Perk(imageResId = R.drawable.perk_age, rainbowText = "Age group match", text = "Engage in conversations with those who understand your life stage and experiences."),
            Perk(imageResId = R.drawable.perk_infinity, rainbowText = "Unlimited chat time", text = "Enjoy uninterrupted conversations without time limits. Talk freely and extensively with listeners and venters alike.")

        )

        features = listOf(
            Feature("", "Free", 1.1, false), //in current implementatoin, for header ony border and paid arg matters
            Feature("Faster matching of Chambers", R.drawable.ic_lock, R.drawable.paywall_features_tick, true),
            Feature("Direct messaging", R.drawable.ic_lock, R.drawable.paywall_features_tick, true),
            Feature("Unlimited topics", R.drawable.ic_lock, R.drawable.paywall_features_tick, true),
            Feature("Super badges", R.drawable.ic_lock, "10", true),
            Feature("Access to AI listener™", "30", R.drawable.ic_infinity, true),
            Feature("Chat with Plupi", "Limited", R.drawable.ic_infinity, true),
            Feature("Follow communities", "Limited", R.drawable.ic_infinity, true),
            Feature("Backdated journal entries & insight", "Limited", R.drawable.paywall_features_tick,true),
            Feature("Curated reading/listening materials", "Limited", R.drawable.paywall_features_tick, true),
            Feature("Back dated journal insights", R.drawable.ic_lock, R.drawable.paywall_features_tick, true),
            Feature("Unlimited Avatars", R.drawable.ic_lock, R.drawable.paywall_features_tick, true),
            Feature("Direct messaging to contributors", R.drawable.ic_lock, R.drawable.paywall_features_tick, true)
        )

        val reviews = listOf(
            Review("Useful app for anonymous chatting", "Its a really nice app to chat anonymously with people around the world and it helps you vent out with people without getting judged by them.", "By anshoool, 05/06/2024"),
            Review("Saviour application", "This app has helped in creating a safe space for people to come and talk their heart out. Thank you team for bringing such a beautiful initiative to reality and ensuring mental wellbeing of its users Wish you snuggly hug from my side", "all_love❤, 18/01/2022"),
            Review("Chamberly: Your Digital Haven for Emotional Release", "This app has been helpful to me. Knowing there's always someone to talk to, 24x7, has brought me comfort during times when I needed someone. I'm appreciate the support it provides. I can recommend it!", "Sampadb, 03/04/2024")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_plans)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.isNestedScrollingEnabled = false
        adapter = PlanAdapter(plans) { plan ->
            selectedPlan = plan
        }
        recyclerView.adapter = adapter

        val button: Button = view.findViewById(R.id.subscription_button)
        button.setOnClickListener {
            if (::selectedPlan.isInitialized) {
                Toast.makeText(context, "Selected Plan: ${selectedPlan.title}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No Plan Selected", Toast.LENGTH_SHORT).show()
            }
        }

        val unlock = view.findViewById<TextView>(R.id.unlock)
        applyGradientToText(unlock)

        val advunlock = view.findViewById<TextView>(R.id.unlock_advanced)
        applyGradientToText(advunlock)

        val advunlock2 = view.findViewById<TextView>(R.id.unlock_advanced2)
        applyGradientToText(advunlock2)

        exitButton = view.findViewById(R.id.cross)
        exitButton.setOnClickListener {
            findNavController().navigate(R.id.action_paywallFragment_to_mainFragment)
        }

        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = PerkAdapter(perks)

        dotLayout = view.findViewById(R.id.dot_layout)
        setupDots(6, dotLayout)

        reviewdotLayout = view.findViewById(R.id.review_dot_layout)
        setupDots(3, reviewdotLayout)

        val viewPagerReviews = view.findViewById<ViewPager2>(R.id.review_view_pager)
        viewPagerReviews.adapter = ReviewAdapter(reviews)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position, dotLayout)
            }
        })

        viewPagerReviews.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position, reviewdotLayout)
            }
        })




        recyclerViewFeatures = view.findViewById(R.id.recycler_features)
        featureAdapter = FeatureAdapter(features)
        recyclerViewFeatures.isNestedScrollingEnabled = false
        recyclerViewFeatures.layoutManager = LinearLayoutManager(context)
        recyclerViewFeatures.adapter = featureAdapter


        viewMoreButton = view.findViewById(R.id.view_more_button)

        setRecyclerViewHeight(collapsedItemsCount)


        viewMoreButton.setOnClickListener {
            isCollapsed = !isCollapsed
            setRecyclerViewHeight(if (isCollapsed) collapsedItemsCount else features.size)
            featureAdapter.toggleCollapse()
            viewMoreButton.text = if (isCollapsed) "View More" else "Collapse"
        }


        return view
    }





    private fun setupDots(size: Int, layout: LinearLayout) {
        val mediumDotSize = 8 // Medium dot size in dp
        val dotSpacing = 8

        layout.removeAllViews()
        for (i in 0 until size) {
            val dot = ImageView(context)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = dotSpacing.toDp()
                marginEnd = dotSpacing.toDp()
            }

            dot.layoutParams = params
            dot.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dot_unselected))
            dot.layoutParams.width = mediumDotSize.toDp()
            dot.layoutParams.height = mediumDotSize.toDp()
            layout.addView(dot)
        }
        updateDots(0, layout) // Initial update to set the first dot as large
    }

    private fun updateDots(position: Int, layout: LinearLayout) {
        val largeDotSize = 12 // Large dot size in dp
        val mediumDotSize = 8 // Medium dot size in dp

        for (i in 0 until layout.childCount) {
            val dot = layout.getChildAt(i) as ImageView
            if (i == position) {
                dot.layoutParams.width = largeDotSize.toDp()
                dot.layoutParams.height = largeDotSize.toDp()
            } else {
                dot.layoutParams.width = mediumDotSize.toDp()
                dot.layoutParams.height = mediumDotSize.toDp()
            }
            dot.requestLayout()
        }
    }

    private fun Int.toDp(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }



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
    }

    private fun setRecyclerViewHeight(itemCount: Int) {

        recyclerViewFeatures.post {
            val layoutParams = recyclerViewFeatures.layoutParams
            val displayMetrics = resources.displayMetrics
            val deviceWidth = displayMetrics.widthPixels

            //the problem is, with higher width screen, 2 liner text becomes one line, and so average size/row decreases
            //the alternative is calculating the full length of list by loading big view first, the feasibility of doing so on first startup then displaying collapsed view needs to be checked.
            val itemHeightResId = if (deviceWidth > 1600) {
                R.dimen.feature_size_large
            } else {
                R.dimen.feature_size
            }

            val itemHeight = resources.getDimensionPixelSize(itemHeightResId)
            val dividerHeight = recyclerViewFeatures.itemDecorationCount
            layoutParams.height = (itemCount * itemHeight) + (dividerHeight * (itemCount - 1))
            recyclerViewFeatures.layoutParams = layoutParams
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        featureAdapter = FeatureAdapter(features)
//        recyclerViewFeatures.adapter = featureAdapter
//
//
//        recyclerViewFeatures.post {
//            featureAdapter.toggleCollapse()
//        }
//    }

}
