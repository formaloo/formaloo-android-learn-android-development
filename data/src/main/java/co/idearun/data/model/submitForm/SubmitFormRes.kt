package co.idearun.data.model.submitForm

import java.io.Serializable

data class SubmitFormRes(
    var status: Int? = null,
    var data: SubmitFormData? = null
) : Serializable {
    companion object {
        fun empty() = SubmitFormRes(0, null)

    }

    fun toSubmitFormRes() = SubmitFormRes(status, data)
}