package co.idearun.data.model.search

import co.idearun.data.model.form.Form
import java.io.Serializable

data class SearchData(
    var forms: List<Form>? = null,
) : Serializable