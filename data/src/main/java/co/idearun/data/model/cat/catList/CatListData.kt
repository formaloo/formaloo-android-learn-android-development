package co.idearun.data.model.cat.catList

import co.idearun.data.model.cat.Category
import java.io.Serializable

data class CatListData(
    var categorys: List<Category>? = null
): Serializable
