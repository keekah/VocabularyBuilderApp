package me.wingert.vocabularybuilder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Insert
    fun insert(word: VocabularyWord)

    @Update
    fun update(word: VocabularyWord)

    @Delete
    fun deleteWord(word: VocabularyWord)

    @Query("SELECT * FROM vocabulary_words ORDER BY id DESC")
    fun getAllNights() : LiveData<List<VocabularyWord>>

    @Query("SELECT * FROM vocabulary_words ORDER BY id DESC LIMIT 1")
    fun getMostRecentWord() : VocabularyWord?

}