package co.idearun.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

abstract class FormBuilderBaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(item: T)
}