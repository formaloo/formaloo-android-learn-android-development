package co.idearun.data.model.form

import java.io.Serializable

data class WidgetSettings(
    var position: String?,
    var type: String? ,
    var once_per_user: Boolean?,
) : Serializable
