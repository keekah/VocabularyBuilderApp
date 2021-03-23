package me.wingert.vocabularybuilder.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.wingert.vocabularybuilder.VocabWord

// Defines the vocab_word table

@Entity(tableName = "vocabulary_words")
data class DatabaseVocabWord(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var word : String?,

    var definition : String? = null
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

