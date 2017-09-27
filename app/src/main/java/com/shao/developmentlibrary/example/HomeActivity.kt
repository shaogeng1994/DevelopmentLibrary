package com.shao.developmentlibrary.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.view.PagerAdapter



/**
 * Created by Administrator on 2017/9/18.
 */
open class HomeActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragments = listOf(HomeFragment(), HomeFragment())

        home_viewpager.adapter = ViewPagerAdapter(fragments, supportFragmentManager)

        home_tab.setupWithViewPager(home_viewpager)
        home_tab.getTabAt(0)?.text = "新闻"
        home_tab.getTabAt(1)?.text = "新闻"
    }


    internal inner class ViewPagerAdapter(private var fragments: List<Fragment>?, private val fragmentManager: FragmentManager)
        : PagerAdapter() {

        override fun getCount(): Int {
            if (fragments == null) return 0
            return fragments!!.size
        }

        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view === any
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = fragments!![position]
            if (!fragment.isAdded) {
                val ft = fragmentManager.beginTransaction()
                ft.add(fragment, fragment::class.java.simpleName)
                ft.commitAllowingStateLoss()

                fragmentManager.executePendingTransactions()
            }

            if (fragment.view?.parent == null) {
                container.addView(fragment.view)
            }

            return fragment.view!!
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            //        if (position >= getCount()) return;
            if (count > position) {
                container.removeView(container.getChildAt(position))
            }

        }

        override fun getItemPosition(`object`: Any?): Int {
            return POSITION_NONE
        }

        fun setFragments(fragments: List<Fragment>) {
            this.fragments = fragments
            notifyDataSetChanged()
        }
    }

}