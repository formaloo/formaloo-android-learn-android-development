package co.idearun.data.local.convertor

import androidx.room.TypeConverter
import co.idearun.data.model.cat.Category
import co.idearun.data.model.form.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class FormConverters {
    @TypeConverter
    fun fromThemeConfig(data: ThemeConfig?): String? {
        val type = object : TypeToken<ThemeConfig>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toThemeConfig(json: String?): ThemeConfig? {
        val type = object : TypeToken<ThemeConfig>() {}.type
        return Gson().fromJson(json, type)
    }
    @TypeConverter
    fun fromFormsOwner(data: FormsOwner?): String? {
        val type = object : TypeToken<FormsOwner>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toFormsOwner(json: String?): FormsOwner? {
        val type = object : TypeToken<FormsOwner>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromWidgetSettings(data: WidgetSettings?): String? {
        val type = object : TypeToken<WidgetSettings>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toWidgetSettings(json: String?): WidgetSettings? {
        val type = object : TypeToken<WidgetSettings>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromDate(data: Date?): String? {
        val type = object : TypeToken<Date>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toDate(json: String?): Date? {
        val type = object : TypeToken<Date>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromForm(data: Form?): String? {
        val type = object : TypeToken<Form>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toForm(json: String?): Form? {
        val type = object : TypeToken<Form>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromCategory(data: Category?): String? {
        val type = object : TypeToken<Category>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toCategory(json: String?): Category? {
        val type = object : TypeToken<Category>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromFieldsList(data: ArrayList<Fields>?): String? {
        val type = object : TypeToken<ArrayList<Fields>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toFieldsList(json: String?): ArrayList<Fields>? {
        val type = object : TypeToken<ArrayList<Fields>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromFields(data: HashMap<String,Any?>?): String? {
        val type = object : TypeToken<HashMap<String,Any?>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toFields(json: String?): HashMap<String,Any?>? {
        val type = object : TypeToken<HashMap<String,Any?>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromFieldsSlugs(data: List<String>?): String? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toFieldsSlugs(json: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromMutiPart(data: HashMap<String, Fields>?): String? {
        val type = object : TypeToken<HashMap<String, Fields>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toMutiPart(json: String?): HashMap<String, Fields>? {
        val type = object : TypeToken<HashMap<String, Fields>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromReqHash(data: HashMap<String, String>?): String? {
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toReqHash(json: String?): HashMap<String, String>? {
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromCalculationItems(data: ArrayList<CalculationItem>?): String? {
        val type = object : TypeToken<ArrayList<CalculationItem>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toCalculationItems(json: String?): ArrayList<CalculationItem>? {
        val type = object : TypeToken<ArrayList<CalculationItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromChoiceItems(data: ArrayList<ChoiceItem>?): String? {
        val type = object : TypeToken<ArrayList<ChoiceItem>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toChoiceItems(json: String?): ArrayList<ChoiceItem>? {
        val type = object : TypeToken<ArrayList<ChoiceItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromAny(data: Any?): String? {
        val type = object : TypeToken<Any>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toAny(json: String?): Any? {
        val type = object : TypeToken<Any>() {}.type
        return Gson().fromJson(json, type)
    }
}