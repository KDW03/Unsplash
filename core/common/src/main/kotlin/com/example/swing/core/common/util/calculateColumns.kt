package com.example.swing.core.common.util

import android.content.res.Configuration

private fun calculateColumns(screenWidth: Int, orientation: Int): Int {
    val baseColumnWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 200 else 150
    val columns = (screenWidth / baseColumnWidth).coerceAtLeast(2)
    return columns.coerceAtMost(6)
}
