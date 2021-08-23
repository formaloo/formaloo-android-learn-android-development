package co.idearun.data.model.form.createForm

import java.io.Serializable

data class CreateFormRes(
    var status: Int? = null,
    var data: CreateFormData? = null
) : Serializable {
    companion object {
        fun empty() = CreateFormRes(0, null)

    }

    fun toCreateFormRes() = CreateFormRes(status, data)
}