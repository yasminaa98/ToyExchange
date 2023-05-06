package com.example.toyexchange.theme.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.toyexchange.theme.ui.fragments.AnnoncesFragment.ExchangeResultFragment
import com.example.toyexchange.theme.ui.fragments.Exchange.NotificationFragment

class ViewPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return NotificationFragment()
            }
            1 -> {
                return ExchangeResultFragment()
            }
            else -> {
                return NotificationFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Exchange requests"
            }
            1 -> {
                return "Exchange responses"
            }
        }
        return super.getPageTitle(position)
    }

}