package com.example.cleandictionaryapp.feature_dictionary.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleandictionaryapp.core.util.Resource
import com.example.cleandictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.cleandictionaryapp.feature_dictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
): ViewModel() {

    private val _wordInfoLiveData = MutableLiveData<Resource<List<WordInfo>>>()
    val wordInfoLiveData: LiveData<Resource<List<WordInfo>>> = _wordInfoLiveData

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo(query).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _wordInfoLiveData.postValue(Resource.Success(result.data))
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Неизвестная ошибка"
                        ))
                    }
                    is Resource.Loading -> {
                        _wordInfoLiveData.postValue(Resource.Loading())
                    }
                }
            }.launchIn(this)
        }

    }

    sealed class UIEvent{
        data class ShowSnackbar(val message: String): UIEvent()
    }
}