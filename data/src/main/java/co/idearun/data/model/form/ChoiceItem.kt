package co.idearun.data.model.form

import java.io.Serializable

data class ChoiceItem(
    var title: String? = null,
    var image: String?=null,
    var slug: String? = null
): Serializable
