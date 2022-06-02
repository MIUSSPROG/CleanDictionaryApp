package com.example.cleandictionaryapp.feature_dictionary.domain.model

import com.example.cleandictionaryapp.feature_dictionary.data.remote.dto.MeaningDto
import com.example.cleandictionaryapp.feature_dictionary.data.remote.dto.PhoneticDto

data class WordInfo(
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String?
)