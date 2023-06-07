package com.cristhianbonilla.oraculo.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cristhianbonilla.oraculo.DreamViewModel
import com.cristhianbonilla.oraculo.util.DreamState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreen_displaysDreamInputField() {
        // Arrange
        val mockViewModel = mock(DreamViewModel::class.java).apply {
            stub {
                on { state } doReturn MutableStateFlow(DreamState.InitState)
            }
        }

        // Act
        composeTestRule.setContent {
            MainScreen(navController = mock(), viewModel = mockViewModel)
        }

        // Assert
        composeTestRule.onNodeWithText("Ingresa tu sueño").assertExists()
    }

    // Other tests go here...
}
