package com.shao.developmentlibrary.adapter.viewmodel

import com.shao.developmentlibrary.BR
import com.shao.developmentlibrary.R

class SimpleLoadMoreViewModel: BaseLoadMoreViewModel(R.layout.view_load_more, BR.loadMoreViewModel) {

    override fun isStatusDefault() = mLoadMoreStatus == STATUS_DEFAULT

    override fun isStatusLoading() = mLoadMoreStatus == STATUS_LOADING

    override fun isStatusFail() = mLoadMoreStatus == STATUS_FAIL

    override fun isStatusEnd() = mLoadMoreStatus == STATUS_END

    override fun setStatusDefault() {
        mLoadMoreStatus = STATUS_DEFAULT
        notifyChange()
    }

    override fun setStatusLoading() {
        mLoadMoreStatus = STATUS_LOADING
        notifyChange()
    }

    override fun setStatusFail() {
        mLoadMoreStatus = STATUS_FAIL
        notifyChange()
    }

    override fun setStatusEnd() {
        mLoadMoreStatus = STATUS_END
        notifyChange()
    }

}