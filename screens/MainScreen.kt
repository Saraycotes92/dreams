package com.cristhianbonilla.oraculo.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristhianbonilla.oraculo.DreamState
import com.cristhianbonilla.oraculo.DreamViewModel
import com.cristhianbonilla.oraculo.MainActivity
import com.cristhianbonilla.oraculo.R
import com.cristhianbonilla.oraculo.router.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: DreamViewModel) {
    val scrollState = rememberScrollState()
    var text by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val screenHeight = LocalConfiguration.current.screenHeightDp

    val state by viewModel.state.collectAsState()

    var error by remember { mutableStateOf("") }

    LaunchedEffect(key1 = viewModel) {
        viewModel.state.collect { state ->
            when (state) {
                is DreamState.SuccessDream -> {
                    val dreamState = state as DreamState.SuccessDream
                    navController.navigate(Screen.DetailScreen.withArgs(dreamState.meaning))
                    viewModel.setState(DreamState.InitState)
                }

                is DreamState.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }

                is DreamState.RequestDreamMeaning -> {
                    // Nothing
                }

                else -> {
                    // Handle other states
                }
            }
        }
    }

    val topSpacing = (screenHeight * 0.3f).dp
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Agregar la imagen de fondo
        Image(
            painter = painterResource(id = R.mipmap.backgroundapp),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            if (state is DreamState.Loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topSpacing))
                    CircularProgressIndicator()
                    Text(
                        text = "Estamos consultando al oráculo tu sueño...",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp) // Añade un espacio entre el spinner y el texto.
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Espacio para mover el LottieAnimation hacia abajo.
                    Spacer(modifier = Modifier.height(topSpacing))
                    Text(
                        text = "Ingresa tu sueño",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                            viewModel.setText(text)
                            if (it.isNotBlank()) {
                                error = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 400.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { (context as MainActivity).showRewardedAd() }) {
                        Text("Interpretar tu sueño")
                    }
                }
            }

        }
    }
}
