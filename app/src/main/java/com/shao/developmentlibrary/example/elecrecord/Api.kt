package com.shao.developmentlibrary.example.elecrecord

import com.shao.developmentlibrary.api.BaseRequest

/**
 * Created by Administrator on 2017/10/12.
 */
class ElectricityListReq(): BaseRequest() {

    override fun getAction() = "list"

    override fun toMap(): HashMap<String, String> = createBaseMap()

}

class RecordElectricityReq(val electricity: String, val recordTime: String): BaseRequest() {

    override fun getAction() = "addRecord"

    override fun toMap(): Map<String, String> = createBaseMap().apply {
        put("electricity", electricity)
        put("recordTime", recordTime)
    }
}