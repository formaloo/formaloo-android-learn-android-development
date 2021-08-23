package co.idearun.data.model.form.formList

import java.io.Serializable

data class FormListRes(
    var status: Int? = null,
    var data: FormListData? = null
) : Serializable {
    companion object {
        fun empty() = FormListRes(0, null)
    }
    fun toFormListRes() = FormListRes(status, data)
}