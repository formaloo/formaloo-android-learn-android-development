package co.idearun.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formsKeys")
data class FormsKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val current_page: Int
)