package com.example.swing.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.swing.navigation.TopLevelDestination

@Composable
fun SwBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 32.dp)
            .then(modifier)
            .height(64.dp)
            .background(
                color = MaterialTheme.colorScheme
                    .surfaceColorAtElevation(2.dp)
                    .copy(
                        red = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).red * 0.95f,
                        green = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).green * 0.95f,
                        blue = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).blue * 0.95f
                    ),
                shape = RoundedCornerShape(percent = 100)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavBarItem(
                destination = destination,
                isSelected = selected,
                onClick = { onNavigateToDestination(destination) }
            )
        }
    }
}

@Composable
fun RowScope.NavBarItem(
    destination: TopLevelDestination,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val mutableInteraction = remember { MutableInteractionSource() }
    val selectedColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
        label = "selectedColor"
    )
    val selectedIconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "selectedIconColor"
    )
    Box(
        modifier = Modifier
            .height(64.dp)
            .weight(1f)
            .clickable(
                indication = null,
                interactionSource = mutableInteraction,
                onClick = {}
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .width(64.dp)
                .background(
                    color = selectedColor,
                    shape = RoundedCornerShape(percent = 100)
                )
                .clip(RoundedCornerShape(100))
                .clickable { onClick() },
        )
        Icon(
            modifier = Modifier
                .size(22.dp),
            imageVector = destination.bottomIcon,
            contentDescription = destination.name,
            tint = selectedIconColor
        )
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
