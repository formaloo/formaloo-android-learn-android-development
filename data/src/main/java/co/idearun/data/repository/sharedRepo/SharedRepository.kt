package co.idearun.data.repository.sharedRepo

import android.content.SharedPreferences
import co.idearun.common.Constants
import org.json.JSONObject

interface SharedRepository {
    fun saveLessonProgress(progress: Map<String?, Int?>)
    fun retrieveLessonProgress(): HashMap<String?, Int?>
    fun saveLastLesson(formSlug: String?)
    fun getLastLesson(): String?
}


class SharedRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SharedRepository {

    override fun saveLessonProgress(progress: Map<String?, Int?>) {
        val jsonObject = JSONObject(progress)
        val lessonsProgressString = jsonObject.toString()
        sharedPreferences.edit().putString(Constants.PREFERENCES_PROGRESS, lessonsProgressString)
            .apply()
    }

    override fun retrieveLessonProgress(): HashMap<String?, Int?> {
        val outputMap = hashMapOf<String?, Int?>()

        sharedPreferences.getString(Constants.PREFERENCES_PROGRESS, null)?.let {
            val jsonObject = JSONObject(it)
            val keysItr = jsonObject.keys()
            while (keysItr.hasNext()) {
                val key = keysItr.next()
                val value = jsonObject[key] as Int?
                outputMap[key] = value
            }
        }


        return outputMap
    }

    override fun saveLastLesson(formSlug: String?) {
        sharedPreferences.edit().putString(Constants.PREFERENCES_LAST_Lesson, formSlug).apply()

    }


    override fun getLastLesson(): String? {
        return sharedPreferences.getString(Constants.PREFERENCES_LAST_Lesson, "")
    }

}