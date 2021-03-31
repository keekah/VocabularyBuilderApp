package me.wingert.vocabularybuilder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Insert
    fun insert(word: DatabaseVocabWord)

    @Insert
    fun insertAll(words: List<DatabaseVocabWord>)

    @Update
    fun update(word: DatabaseVocabWord)

    @Delete
    fun deleteWord(word: DatabaseVocabWord)

    @Query("SELECT * FROM vocabulary_words WHERE id = :id")
    fun getWord(id: Int) : DatabaseVocabWord?

    @Query("SELECT * FROM vocabulary_words WHERE word = :word")
    fun getWord(word: String) : DatabaseVocabWord?

    @Query("SELECT * FROM vocabulary_words ORDER BY id DESC")
    fun getAllWords() : LiveData<List<DatabaseVocabWord>>

    @Query("SELECT * FROM vocabulary_words WHERE definition IS NOT NULL ORDER BY id DESC")
    fun getDefinedWords() : LiveData<List<DatabaseVocabWord>>

    @Query("SELECT * FROM vocabulary_words WHERE definition IS NULL ORDER BY id DESC")
    fun getUndefinedWords() : LiveData<List<DatabaseVocabWord>>

    @Query("DELETE FROM vocabulary_words")
    fun clear()

}