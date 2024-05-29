package com.example.swing.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swing.R
import com.example.swing.core.data.util.NetworkMonitor
import com.example.swing.navigation.SwNavHost
import com.example.swing.ui.core.icon.SwIcons
import com.example.swing.feature.search.R as searchR
import com.example.swing.feature.settings.R as settingsR

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SwApp(
    networkMonitor: NetworkMonitor,
    appState: SwAppState = rememberSwAppState(
        networkMonitor = networkMonitor
    )
) {

    SwBackground {
        Box(Modifier.fillMaxSize()) {
            val snackbarHostState = remember { SnackbarHostState() }
            val isOffline by appState.isOffline.collectAsStateWithLifecycle()
            val isScroll = remember { mutableStateOf(false) }

            val alpha by animateFloatAsState(
                targetValue = if (isScroll.value) 0.6f else 1f,
                label = "alpha"
            )

            val notConnectedMessage = stringResource(R.string.not_connected)

            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            val destination = appState.currentTopLevelDestination

            Scaffold(
                modifier = Modifier,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = {
                    SnackbarHost(snackbarHostState) { data ->
                        Box(modifier = Modifier.padding(bottom = 100.dp)) {
                            Snackbar(
                                snackbarData = data,
                            )
                        }
                    }
                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
                        SwNavHost(appState = appState, isScroll = isScroll)
                    }
                }
            }

            if (destination != null) {
                AnimatedVisibility(
                    visible = !isScroll.value,
                    enter = fadeIn(tween(150)),
                    exit = fadeOut(tween(150))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(alpha)
                    ) {
                        SwTopAppBar(
                            modifier = Modifier,
                            titleRes = destination.titleTextId,
                            navigationIcon = SwIcons.Search.imageVector,
                            navigationIconContentDescription = stringResource(
                                id = searchR.string.search,
                            ),
                            onNavigationClick = { appState.navigateToSearch() },
                            actionIcon = SwIcons.Settings.imageVector,
                            actionIconContentDescription = stringResource(
                                id = settingsR.string.settings
                            ),
                            onActionClick = {
                                appState.navigateToSetting()
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        SwBottomBar(
                            modifier = Modifier,
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                        )
                    }
                }
            }
        }
    }
}

