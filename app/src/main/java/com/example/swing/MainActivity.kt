package com.example.swing

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.swing.MainActivityUiState.*
import com.example.swing.core.data.util.NetworkMonitor
import com.example.swing.core.model.DarkThemeConfig
import com.example.swing.ui.SwApp
import com.example.swing.ui.core.theme.SwTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()
    private var uiState: MainActivityUiState by mutableStateOf(Loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()
        setupUiStateObserver()
        enableEdgeToEdge()
        setupContent()
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                Loading -> true
                is Success -> false
            }
        }
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }
    }


    private fun setupContent() {
        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)
            val disableDynamicTheming = shouldDisableDynamicTheming(uiState)

            LaunchedEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme }
                )
            }

            SwTheme(
                darkTheme = darkTheme,
                disableDynamicTheming = disableDynamicTheming,
            ) {
                when (uiState) {
                    Loading -> Unit
                    is Success -> SwApp(networkMonitor)
                }
            }
        }
    }

    @Composable
    private fun shouldDisableDynamicTheming(
        uiState: MainActivityUiState
    ): Boolean = when (uiState) {
        Loading -> false
        is Success -> !uiState.userData.useDynamicColor
    }


    @Composable
    private fun shouldUseDarkTheme(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        Loading -> isSystemInDarkTheme()
        is Success -> when (uiState.userData.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }

}

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
