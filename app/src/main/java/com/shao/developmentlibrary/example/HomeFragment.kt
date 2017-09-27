package com.shao.developmentlibrary.example

import android.annotation.SuppressLint
import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.shao.developmentlibrary.adapter.MultipleItemAdapter
import com.shao.developmentlibrary.example.databinding.FragHomeBinding

/**
 * Created by Administrator on 2017/9/19.
 */
open class HomeFragment: Fragment() {
    lateinit var binding: FragHomeBinding




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        binding.fragHomeViewmodel = HomeViewModel()

        binding.homeRecycler.adapter = Adapter(ArrayList(0))
        binding.homeRecycler.layoutManager = LinearLayoutManager(context)

        return binding.root
    }


    class Adapter(data: List<MultipleItemAdapter.MultipleItem<Any>>): MultipleItemAdapter(data) {



        override fun onConvert(commonViewHolder: CommonViewHolder, t: MultipleItem<Any>) {
            when (commonViewHolder.itemViewType) {
                1 -> commonViewHolder.viewDataBinding?.setVariable(BR.item_home1_viewmodel, Item1ViewModel())
                2 -> commonViewHolder.viewDataBinding?.setVariable(BR.item_home2_viewmodel, Item2ViewModel())
            }
        }


    }

    class Item1: MultipleItemAdapter.MultipleItem<String>(1, "a", R.layout.item_home1)
    class Item2: MultipleItemAdapter.MultipleItem<Int>(2, 1, R.layout.item_home2)


    companion object {
        @BindingAdapter("bind:common_adapter_items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, items: ArrayList<Any>) {
            val adapter = recyclerView.adapter as BaseQuickAdapter<Any, *>
            adapter.setNewData(items)
        }
    }
}


class HomeViewModel: BaseObservable() {
    val data: ObservableArrayList<Any> = ObservableArrayList()


    val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                1 -> {
                    data.clear()
                    data.add(HomeFragment.Item1())
                    data.add(HomeFragment.Item1())
                    data.add(HomeFragment.Item1())
                    data.add(HomeFragment.Item1())
                    data.add(HomeFragment.Item2())
                    data.add(HomeFragment.Item2())
                    data.add(HomeFragment.Item2())
                    data.add(HomeFragment.Item2())
                }
            }
        }
    }

    init {
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item2())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
        data.add(HomeFragment.Item1())
//        mHandler.sendEmptyMessageDelayed(1, 3000)
    }

}

class Item2ViewModel: BaseObservable() {

}

class Item1ViewModel: BaseObservable() {

}