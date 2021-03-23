package me.wingert.vocabularybuilder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVocabWord::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract val wordDao : WordDao

    companion object {

        @Volatile
        private var INSTANCE : WordDatabase? = null

        fun getInstance(context: Context) : WordDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, WordDatabase::class.java, "vocabulary_words_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}