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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shao.developmentlibrary.adapter.SimpleMultiItemAdapter
import com.shao.developmentlibrary.adapter.base.BaseMultiItemAdapter
import com.shao.developmentlibrary.adapter.base.DataBindingAdapter
import com.shao.developmentlibrary.example.databinding.FragHomeBinding

/**
 * Created by Administrator on 2017/9/19.
 */
open class HomeFragment: Fragment() {
    lateinit var binding: FragHomeBinding

    val mAdapter = SimpleMultiItemAdapter<BaseMultiItemAdapter.MultiItemEntity>().apply {
        addItemType(1, R.layout.item_home1, BR.item_home1_viewmodel)
        addItemType(2, R.layout.item_home2, BR.item_home2_viewmodel)
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        binding.fragHomeViewmodel = HomeViewModel()

        binding.homeRecycler.adapter = mAdapter
        binding.homeRecycler.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    companion object {
        @BindingAdapter("bind:common_adapter_items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, items: List<Any>) {
            val adapter = recyclerView.adapter as DataBindingAdapter<Any, *>
            adapter.setNewData(items)
        }
    }
}


class HomeViewModel: BaseObservable() {
    val data: ObservableArrayList<BaseMultiItemAdapter.MultiItemEntity> = ObservableArrayList()


    val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                1 -> {
                    data.clear()
                    data.add(Item1ViewModel())
                    data.add(Item1ViewModel())
                    data.add(Item1ViewModel())
                    data.add(Item1ViewModel())
                    data.add(Item2ViewModel())
                    data.add(Item2ViewModel())
                    data.add(Item2ViewModel())
                    data.add(Item2ViewModel())
                }
            }
        }
    }

    init {
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item2ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
        data.add(Item1ViewModel())
//        mHandler.sendEmptyMessageDelayed(1, 3000)
    }

}

class Item2ViewModel: BaseObservable(), BaseMultiItemAdapter.MultiItemEntity {


    override val itemType: Int
        get() = 2
}

class Item1ViewModel: BaseObservable(), BaseMultiItemAdapter.MultiItemEntity {

    override val itemType: Int
        get() = 1
}