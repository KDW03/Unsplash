package com.example.swing.feature.settings

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swing.core.model.DarkThemeConfig
import com.example.swing.feature.settings.SettingsUiState.Loading
import com.example.swing.feature.settings.SettingsUiState.Success
import com.example.swing.ui.core.icon.Icon
import com.example.swing.ui.core.icon.SwIcons
import com.example.swing.ui.core.theme.supportsDynamicTheming

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    SettingsScreen(
        modifier = modifier,
        uiState = uiState,
        onChangeDynamicColor = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
        onBackClick = onBackClick,
    )
}


@Composable
private fun SettingsScreen(
    uiState: SettingsUiState,
    onChangeDynamicColor: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        SettingTopBar(onBackClick = onBackClick)
        when (uiState) {
            Loading -> Unit
            is Success -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    SettingsPanel(
                        settings = uiState.settings,
                        supportDynamicColor = supportsDynamicTheming(),
                        onChangeDynamicColorPreference = onChangeDynamicColor,
                        onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = SwIcons.ArrowBack.imageVector,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ColumnScope.SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    AnimatedVisibility(visible = supportDynamicColor) {
        Column {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconBox(icon = SwIcons.Palette, contentDescriptionResource = R.string.dynamic)
                SettingsSectionTitle(text = stringResource(R.string.dynamic_color_preference))
            }
            Column(
                Modifier
                    .selectableGroup()
                    .padding(start = 16.dp)
            ) {
                SettingsThemeChooserRow(
                    text = stringResource(R.string.dynamic_color_yes),
                    selected = settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(true) },
                )
                SettingsThemeChooserRow(
                    text = stringResource(R.string.dynamic_color_no),
                    selected = !settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(false) },
                )
            }
        }
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconBox(icon = SwIcons.DarkMode, contentDescriptionResource = R.string.darkmode)
        SettingsSectionTitle(text = stringResource(R.string.dark_mode_preference))
    }
    Column(
        Modifier
            .selectableGroup()
            .padding(start = 16.dp)
    ) {
        SettingsThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_system_default),
            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
        )
        SettingsThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_light),
            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
        )
        SettingsThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_dark),
            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
        )
    }
}

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
internal fun IconBox(
    icon: Icon,
    @StringRes contentDescriptionResource: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
            .size(IconBoxSize),
        contentAlignment = Alignment.Center,
    ) {
        when (icon) {
            is Icon.ImageVectorIcon -> {
                Icon(
                    imageVector = icon.imageVector,
                    contentDescription = stringResource(id = contentDescriptionResource),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            is Icon.DrawableResourceIcon -> {
                Icon(
                    painter = painterResource(id = icon.resourceId),
                    contentDescription = stringResource(id = contentDescriptionResource),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

private val IconBoxSize = 32.dp
