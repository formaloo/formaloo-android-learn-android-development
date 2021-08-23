package co.idearun.data.model.form

import java.io.Serializable

data class Fields(
    var slug: String? = null,
    var title: String? = null,
    var description: String? = null,
    var logo: String? = null,
    var type: String? = null,
    var sub_type: String? = null,
    var answer_description: String? = null,
    var created_at: String? = null,
    var thumbnail_type: String? = null,
    var position: Int? = null,
    var required: Boolean? = null,
    var unique: Boolean? = null,
    var json_key: Any? = null,
    var max_length: String? = null,
    var max_size: String? = null,
    var min_value: String? = null,
    var max_value: String? = null,
    var from_date: String? = null,
    var to_date: String? = null,
    var from_time: String? = null,
    var to_time: String? = null,
    var file_type: String? = null,
    var currency: String? = null,
    var rating_type: String? = null,
    var phone_type: String? = null,
    var is_calculatable: Boolean? = null,
    var calculation_items: ArrayList<CalculationItem>? = null,
    var choice_items: ArrayList<ChoiceItem>? =  null,
    var choice_groups: ArrayList<ChoiceItem>? =  null


):Serializable