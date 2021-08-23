package co.idearun.data.model.cat.catList

import java.io.Serializable

data class CatListRes(
    var status: Int? = null,
    var data: CatListData? = null
) : Serializable {
    companion object {
        fun empty() = CatListRes(0, null)

    }

    fun toCatListRes() = CatListRes(status, data)
}