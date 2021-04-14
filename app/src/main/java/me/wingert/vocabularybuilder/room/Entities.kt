package me.wingert.vocabularybuilder.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.wingert.vocabularybuilder.VocabWord


@Entity(tableName = "vocabulary_words")
data class DatabaseVocabWord(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    var word : String,

    var definition : String? = null,

    @ColumnInfo(name = "user_id")
    var userId : Int,

    @ColumnInfo(name = "added_date_time")
    var addedDateTime : String?,

    @ColumnInfo(name = "modified_date_time")
    var modifiedDateTime : String?
)

fun List<DatabaseVocabWord>.asDomainModel() : List<VocabWord> {
    return map {
        VocabWord(
            id = it.id,
            word = it.word,
            definition = it.definition
        )
    }
}


@Entity(tableName = "users")
data class DatabaseUser(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName : String? = null,

    @ColumnInfo(name = "last_name")
    var lastName : String? = null,

    @ColumnInfo(name = "display_name")
    var displayName : String? = null,

    var email : String,

    @ColumnInfo(name = "is_email_verified")
    var isEmailVerified : Boolean,

    @ColumnInfo(name = "firebase_id")
    var firebaseId : String,

    @ColumnInfo(name = "provider_id")
    var providerId : String? = null,

    @ColumnInfo(name = "account_created_date_time")
    var accountCreatedDateTime : String,

    @ColumnInfo(name = "last_sign_in_date_time")
    var lastSignInDateTime : String,

)

