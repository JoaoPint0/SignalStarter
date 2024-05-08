package com.mobile.common.state

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.LoadingState() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center).size(64.dp),
        strokeWidth = 8.dp,
        strokeCap = StrokeCap.Round
    )
}
