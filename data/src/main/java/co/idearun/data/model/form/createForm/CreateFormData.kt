package co.idearun.data.model.form.createForm

import co.idearun.data.model.form.Form
import java.io.Serializable

data class CreateFormData(
    var form: Form? = null
): Serializable