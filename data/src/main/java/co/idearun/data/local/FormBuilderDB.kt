package co.idearun.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.idearun.data.local.convertor.FormConverters
import co.idearun.data.local.dao.FormDao
import co.idearun.data.local.dao.FormKeysDao
import co.idearun.data.local.dao.SubmitDao
import co.idearun.data.model.form.Form
import co.idearun.data.model.form.SubmitEntity


@Database(
    entities = [Form::class, FormsKeys::class,SubmitEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FormConverters::class)
abstract class FormBuilderDB : RoomDatabase() {
    // DAO
    abstract fun formDao(): FormDao
    abstract fun formKeysDao(): FormKeysDao
    abstract fun submitDao(): SubmitDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FormBuilderDB::class.java,
                "FormBuilder.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}