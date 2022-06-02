package com.example.cleandictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleandictionaryapp.feature_dictionary.domain.model.Meaning
import com.example.cleandictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey
    val id: Int? = null,
    val word: String,
    val phonetic: String? = null,
    val meanings: List<Meaning>
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            meanings = meanings,
            word = word,
            phonetic = phonetic
        )
    }
}