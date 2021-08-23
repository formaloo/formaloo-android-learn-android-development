package co.idearun.data.model.form
import java.io.Serializable

data class FormsOwner(
    var first_name: String? = null,
    var last_name: String? = null,
    var id: Int? = null,
    var username: String? = null,
    var email: String? = null,
    var user_type: String? = null
) : Serializable
