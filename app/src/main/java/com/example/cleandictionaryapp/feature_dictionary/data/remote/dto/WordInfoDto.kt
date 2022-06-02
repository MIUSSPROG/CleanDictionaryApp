package com.example.cleandictionaryapp.feature_dictionary.data.remote.dto

import com.example.cleandictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.cleandictionaryapp.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val word: String,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
){
    fun toWordInfoEntity(): WordInfoEntity{
        return WordInfoEntity(
            word = word,
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic
        )
    }

}