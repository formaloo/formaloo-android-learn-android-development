package co.idearun.data.model.search

import java.io.Serializable

data class SearchRes(
    var status: Int? = null,
    var data: SearchData? = null
) : Serializable {
    companion object {
        fun empty() = SearchRes(0, null)

    }

    fun toSearchRes() = SearchRes(status, data)
}