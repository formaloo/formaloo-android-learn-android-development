package co.idearun.data.model.form

import java.io.Serializable

data class CalculationItem(
    var slug: String? = null,
    var score: String? = null,
    var answer: String?=null,
    var form_score_type: String?=null,
    var operand: String?=null
): Serializable
