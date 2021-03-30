package me.wingert.vocabularybuilder

import me.wingert.vocabularybuilder.database.DatabaseVocabWord

data class VocabWord(

    val id: Int = 0,

    val word : String?,

    var definition : String? = null
)

fun asDatabaseVocabWord(vocab: VocabWord) : DatabaseVocabWord {
    return DatabaseVocabWord(vocab.id, vocab.word, vocab.definition)
}
