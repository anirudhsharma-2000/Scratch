package com.smitcoderx.scratch.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.R
import com.smitcoderx.scratch.ui.theme.Typography

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    Column(modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(stringResource(R.string.app_name), style = Typography.displayMedium)
        SearchBar { query = it }

    }
}

@Composable
private fun SearchBar(query: (String) -> Unit) {
    var search by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = search,
        minLines = 1,
        shape = RoundedCornerShape(20.dp),
        onValueChange = { search = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { query(search) }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Search here 🔎",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            )
        }
    )
}
