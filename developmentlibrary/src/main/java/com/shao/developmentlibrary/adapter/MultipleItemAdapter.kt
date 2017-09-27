package com.shao.developmentlibrary.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import android.support.v7.widget.RecyclerView
import android.databinding.BindingAdapter
import android.util.Log


/**
 * Created by Administrator on 2017/9/19.
 */
open abstract class MultipleItemAdapter(data: List<MultipleItemAdapter.MultipleItem<Any>>)
    : BaseMultiItemQuickAdapter<MultipleItemAdapter.MultipleItem<Any>, MultipleItemAdapter.CommonViewHolder>(data) {

    val typeMap: HashMap<Int, Int> = HashMap()

    init {
        addLayout()
    }

    override fun setNewData(data: MutableList<MultipleItem<Any>>?) {
        super.setNewData(data)
        addLayout()
    }

    protected fun addLayout() {
        for (item in mData) {
            typeMap.put(item.itemType, item.layoutRes)
        }
        for ((key, value) in typeMap) {
            addItemType(key, value)
        }
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): MultipleItemAdapter.CommonViewHolder {
        if (viewType !in typeMap.keys) {
            return super.onCreateDefViewHolder(parent, viewType)
        }
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, typeMap[viewType]!!, parent, false)
        binding.executePendingBindings()
        val holder = MultipleItemAdapter.CommonViewHolder(binding.root)
        holder.viewDataBinding = binding
        return holder
    }

    override fun convert(helper: MultipleItemAdapter.CommonViewHolder, item: MultipleItemAdapter.MultipleItem<Any>) {
        onConvert(helper, item)
        helper.viewDataBinding?.executePendingBindings()
    }

    protected abstract fun onConvert(commonViewHolder: MultipleItemAdapter.CommonViewHolder, t: MultipleItemAdapter.MultipleItem<Any>)


    class CommonViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var viewDataBinding: ViewDataBinding? = null
    }

    open class MultipleItem<out T>(itemType: Int, val data: T, val layoutRes: Int) : MultiItemEntity {

        val type = itemType

        override fun getItemType(): Int = type

    }


}