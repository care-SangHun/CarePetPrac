package com.example.carepetprac.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.carepetprac.fragment.Fragment1
import com.example.carepetprac.fragment.Fragment2
import com.example.carepetprac.fragment.Fragment3
import com.example.carepetprac.fragment.Fragment4
import com.example.carepetprac.fragment.Fragment5

class ViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> Fragment1()
            1 -> Fragment2()
            2 -> Fragment3()
            3 -> Fragment4()
            4 -> Fragment5()
            else -> Fragment1()
        }
    }
}