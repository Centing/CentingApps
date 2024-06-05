package com.c241ps220.centingapps.views.Onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c241ps220.centingapps.R


class OnboardingViewPagerAdapter4(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment4.newInstance(
                context.resources.getString(R.string.title_onboarding1),
                context.resources.getString(R.string.desc_onboarding1),
                R.drawable.welcome_page
            )
            1 -> OnboardingFragment4.newInstance(
                context.resources.getString(R.string.title_onboarding2),
                context.resources.getString(R.string.desc_onboarding2),
                R.drawable.onboard2
            )
            else -> OnboardingFragment4.newInstance(
                context.resources.getString(R.string.title_onboarding3),
                context.resources.getString(R.string.desc_onboarding3),
                R.drawable.onboard3
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}