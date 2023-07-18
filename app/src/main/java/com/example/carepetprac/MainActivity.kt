package com.example.carepetprac

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.carepetprac.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("LoginSP", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", true) // 변수 값을 "Hello, World!"로 저장
        editor.apply()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        viewPager = findViewById(R.id.viewPager)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        // ViewPager 페이지 변경 시 BottomNavigationView의 아이템 선택 상태 변경
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // position 에 해당하는 menu 를 check 상태로 만듬. 즉 그 position 메뉴로 이동
                bottomNavigationView.menu.getItem(position).isChecked = true

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}
        })

        // BottomNavigationView 아이템 선택 시 ViewPager 페이지 변경
        // 특이점? BottomNavigation 으로 페이지 이동하면 PageChangeListener 도 호출
        // 반대는 호출안됨
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.frag1 -> viewPager.currentItem = 0
                R.id.frag2 -> viewPager.currentItem = 1
                R.id.frag3 -> viewPager.currentItem = 2
                R.id.frag4 -> viewPager.currentItem = 3
                R.id.frag5 -> viewPager.currentItem = 4
            }
            true
        }

    }
}