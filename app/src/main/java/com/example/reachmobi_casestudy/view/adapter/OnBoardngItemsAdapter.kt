package com.example.reachmobi_casestudy.view.adapter

/**
 * Adapter class to bind the items for the onboarding walkthroug screen
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.model.OnboardingItem


class OnBoardngItemsAdapter(private val onBoardngItems: List<OnboardingItem>)
    :RecyclerView.Adapter<OnBoardngItemsAdapter.OnBoardingItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container, parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardngItems[position])

    }

    override fun getItemCount(): Int {
        return onBoardngItems.size
    }


    inner class OnBoardingItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imageOnBoarding = view.findViewById<ImageView>(R.id.onBoardingImage)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDesription)

        fun bind(onBoardngItem: OnboardingItem){
            imageOnBoarding.setImageResource(onBoardngItem.onBoardingImage)
            textTitle.text = onBoardngItem.title
            textDescription.text = onBoardngItem.description
        }
    }

}