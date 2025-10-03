package com.smitcoderx.scratch.ui.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.R

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(LocalContentColor.current.copy(0.5f)),
            painter = painterResource(R.drawable.no_data),
            contentDescription = "Empty"
        )
        Text(
            "Woofs! Nothing Found",
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
        )
    }
}
