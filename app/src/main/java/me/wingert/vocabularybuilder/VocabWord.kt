package me.wingert.vocabularybuilder

import me.wingert.vocabularybuilder.network.NetworkVocabWord
import me.wingert.vocabularybuilder.room.DatabaseVocabWord

data class VocabWord(

    val id: Int = 0,

    val word : String,

    var definition : String? = null,

    var userId : Int? = null,

    var addedDateTime : String? = null,

    var modifiedDateTime : String? = null
)

fun asDatabaseVocabWord(vocab: VocabWord) : DatabaseVocabWord {
    return DatabaseVocabWord(vocab.id, vocab.word, vocab.definition, vocab.userId, vocab.addedDateTime, vocab.modifiedDateTime)
}

fun asNetworkVocabWord(vocab: VocabWord) : NetworkVocabWord {
    return NetworkVocabWord(vocab.id, vocab.word, vocab.definition, vocab.userId, vocab.addedDateTime, vocab.modifiedDateTime)
}

