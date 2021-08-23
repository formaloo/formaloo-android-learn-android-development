package co.idearun.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import co.idearun.data.model.form.Form
import java.text.SimpleDateFormat
import java.util.*

@Dao
abstract class FormDao : FormBuilderBaseDao<Form>() {

    @Query("SELECT * FROM Form ORDER BY updated_at_date  DESC")
    abstract fun getForms(): PagingSource<Int, Form>

    @Query("SELECT * FROM Form ORDER BY updated_at_date DESC")
    abstract fun getFormList(): List<Form>

    @Query("SELECT * FROM Form WHERE slug = :slug")
    abstract suspend fun getForm(slug: String): Form

    @Query("DELETE FROM Form WHERE slug = :slug")
    abstract suspend fun deleteForm(slug: String)

    // ---
    @Query("DELETE FROM Form")
    abstract suspend fun deleteAllFromTable()

    suspend fun save(form: Form) {
        insert(form.apply {
            created_at?.let {
                updated_at_date = converStrToDate(it)?.time
            }
        })
    }

    suspend fun save(forms: List<Form>) {
        insert(forms)
    }

    fun converStrToDate(time: String): Date? {
        val sdf2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        var date: Date? = null
        if (time.isNotEmpty()) {
            try {
                date = sdf2.parse(time)

            } catch (e: Exception) {


            }
        } else {

        }

        return date
    }
}