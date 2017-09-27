package com.shao.developmentlibrary.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * Created by Administrator on 2017/4/19.
 */

abstract class CommonAdapter<T, E : ViewDataBinding>(private val mLayout: Int, private val mData: List<T>)
    : BaseQuickAdapter<T, CommonAdapter.CommonViewHolder<E>>(mLayout, mData) {
    private val mViewDataBinding: E? = null


    public override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<E> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<E>(inflater, mLayoutResId, parent, false)
        binding.executePendingBindings()
        val holder = CommonViewHolder<E>(binding.root)
        holder.viewDataBinding = binding
        return holder
    }

    override fun convert(commonViewHolder: CommonViewHolder<E>, t: T) {
        onConvert(commonViewHolder, t)
        commonViewHolder.viewDataBinding?.executePendingBindings()
    }

    protected abstract fun onConvert(commonViewHolder: CommonViewHolder<E>, t: T)

    class CommonViewHolder<K : ViewDataBinding>(itemView: View) : BaseViewHolder(itemView) {

        var viewDataBinding: K? = null
    }
}
