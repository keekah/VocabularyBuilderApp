package me.wingert.vocabularybuilder.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVocabWord::class, DatabaseUser::class], version = 2, exportSchema = true)
abstract class VocabularyBuilderDB : RoomDatabase() {

    abstract val wordDao : WordDao

    companion object {

        @Volatile
        private var INSTANCE : VocabularyBuilderDB? = null

        fun getInstance(context: Context) : VocabularyBuilderDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, VocabularyBuilderDB::class.java, "vocabulary_words_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}