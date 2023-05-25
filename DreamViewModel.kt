package com.cristhianbonilla.oraculo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristhianbonilla.oraculo.domain.GptRepository
import com.cristhianbonilla.oraculo.domain.GptRequest
import com.cristhianbonilla.oraculo.domain.MessageRequest
import com.cristhianbonilla.oraculo.util.ResultDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DreamViewModel @Inject constructor(
    private val gptRepository: GptRepository
) : ViewModel() {


    val state = MutableStateFlow<DreamState>(DreamState.InitState)

    val _dreamText = MutableLiveData<String>()
    val dreamText: LiveData<String> get() = _dreamText
    fun getDream(dreamText: String) {
        val gptRequest = GptRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                MessageRequest(
                    role = "system",
                    content = "Eres un interprete de sueños  experto , das respuestas claras a los significados , y solo eres capaz de interpretar sueños , no sabes interpretar algo diferente"
                ),
                MessageRequest(
                    role = "user",
                    content = dreamText
                )
            )
        )

        viewModelScope.launch {
            setState(DreamState.Loading)
            when (
                val result = gptRepository.getDreamMeaningFromGpt(gptRequest = gptRequest)
            ) {
                is ResultDomain.Success -> {
                    setState(DreamState.SuccessDream(result.data?.choices?.first()?.message?.content.orEmpty()))
                }

                is ResultDomain.Error -> setState(DreamState.Error)
            }
        }
    }

    suspend fun setState(dreamState: DreamState) {
        state.emit(dreamState)
    }

    fun setText(text: String) {
        _dreamText.value = text
    }
}