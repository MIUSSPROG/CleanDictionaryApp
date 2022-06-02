package com.example.cleandictionaryapp.feature_dictionary.domain.model

import com.example.cleandictionaryapp.feature_dictionary.data.remote.dto.DefinitionDto

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)