package co.idearun.data.model.form

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.idearun.data.model.cat.Category
import java.io.Serializable
import java.util.*

@Entity
data class Form(
 @PrimaryKey var slug: String,
 var created_at: String? = null,
 var updated_at: String? = null,
 var updated_at_date: Long? = null,
 var form_type: String? = null,
 var shared_access: String? = null,
 var logo: String? = null,
 var title: String? = null,
 var address: String? = null,
 var form_redirects_after_submit: String? = null,
 var copied_from: String? = null,
 var description: String? = null,
 var success_message: String? = null,
 var error_message: String? = null,
 var button_text: String? = null,
 var last_submit_time: String? = null,
 var primary_email: String? = null,
 var submit_count: Int = 0,
 var visit_count: Int = 0,
 var max_submit_count: String? = null,
 var submit_start_time: String? = null,
 var submit_end_time: String? = null,
 var needs_login: Boolean? = null,
 var send_emails_to: String? = null,
 var time_limit: String? = null,
 var submit_email_notif: Boolean? = null,
 var send_user_confirm: Boolean? = null,
 var submit_push_notif: Boolean? = null,
 var text_color: String? = null,
 var field_color: String? = null,
 var button_color: String? = null,
 var owner: FormsOwner? = null,
 var border_color: String? = null,
 var background_color: String? = null,
 var background_image: String? = null,
 var submit_text_color: String? = null,
 var theme_color: String? = null,
 var theme_name: String? = null,
 var active: Boolean? = null,
 var show_answers: Boolean? = null,
 var show_title: Boolean? = null,
 var shuffle_fields: Boolean? = null,
 var form_currency_type: String? = null,
 var show_calculations_score_result: Boolean? = null,
 var category: Category? = null,
 var theme_config: ThemeConfig? = null,
 var fields_list: ArrayList<Fields>? = null

) : Serializable
