package com.cristhianbonilla.oraculo.screens

import com.cristhianbonilla.oraculo.domain.GptRepository
import com.cristhianbonilla.oraculo.domain.GptRequest
import com.cristhianbonilla.oraculo.domain.MessageRequest
import com.cristhianbonilla.oraculo.domain.ResultDomain
import com.cristhianbonilla.oraculo.util.DreamState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DreamViewModelTest {

    @Mock
    private lateinit var mockGptRepository: GptRepository

    private lateinit var viewModel: DreamViewModel

    @Before
    fun setup() {
        viewModel = DreamViewModel(mockGptRepository)
    }

    @Test
    fun `getDream should call getDreamMeaningFromGpt with correct request`() = runBlockingTest {
        // Arrange
        val dreamText = "sample dream text"
        whenever(mockGptRepository.getDreamMeaningFromGpt(any())).thenReturn(ResultDomain.Success(any()))

        // Act
        viewModel.getDream(dreamText)

        // Assert
        verify(mockGptRepository).getDreamMeaningFromGpt(GptRequest(
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
        ))
    }

    @Test
    fun `getDream should update state to Loading before making the request`() = runBlockingTest {
        // Arrange
        val dreamText = "sample dream text"
        whenever(mockGptRepository.getDreamMeaningFromGpt(any())).thenReturn(ResultDomain.Success(any()))

        // Act
        viewModel.getDream(dreamText)

        // Assert
        assert(viewModel.state.value == DreamState.Loading)
    }

    // Similar tests can be written for other methods and other scenarios.
}
