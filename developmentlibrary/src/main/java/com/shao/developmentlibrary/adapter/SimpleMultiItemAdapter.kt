package com.shao.developmentlibrary.adapter

import com.shao.developmentlibrary.adapter.base.BaseMultiItemAdapter
import com.shao.developmentlibrary.adapter.base.DataBindingAdapter

/**
 * Created by Administrator on 2018/1/6.
 */
class SimpleMultiItemAdapter<T: BaseMultiItemAdapter.MultiItemEntity>
    : BaseMultiItemAdapter<T, DataBindingAdapter.ViewHolder> (ArrayList()) {


    override fun onBindContent(holder: ViewHolder?, t: T?) {
        holder?.binding?.setVariable(getVariableIdByType(t?.itemType), t)
    }
}