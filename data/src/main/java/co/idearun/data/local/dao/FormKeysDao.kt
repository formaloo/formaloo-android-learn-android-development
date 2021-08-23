package co.idearun.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import co.idearun.data.local.FormsKeys

@Dao
interface FormKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveFormsKeys(redditKey: FormsKeys)

    @Query("SELECT * FROM formsKeys ORDER BY id DESC")
    suspend fun getFormsKeys(): List<FormsKeys>

    @Query("DELETE FROM Form")
    abstract suspend fun deleteAllFromTable()

}