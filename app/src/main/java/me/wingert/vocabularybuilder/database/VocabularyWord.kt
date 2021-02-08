package me.wingert.vocabularybuilder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Defines the vocab_word table

@Entity(tableName = "vocabulary_words")
data class VocabularyWord(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var word : String?,

    var definition : String? = null
)