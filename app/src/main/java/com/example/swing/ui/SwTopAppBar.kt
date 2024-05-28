package com.example.swing.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun SwTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .height(55.dp)
            .background(
                color = MaterialTheme.colorScheme
                    .surfaceColorAtElevation(2.dp)
                    .copy(
                        red = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).red * 0.95f,
                        green = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).green * 0.95f,
                        blue = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).blue * 0.95f
                    ),
                shape = RoundedCornerShape(percent = 100)
            )
            .padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = onNavigationClick) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = navigationIconContentDescription,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        IconButton(onClick = onActionClick) {
            Icon(
                imageVector = actionIcon,
                contentDescription = actionIconContentDescription,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
