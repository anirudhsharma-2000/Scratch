package com.smitcoderx.scratch.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smitcoderx.scratch.R
import com.smitcoderx.scratch.data.home.Categories
import com.smitcoderx.scratch.data.home.Category
import com.smitcoderx.scratch.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: CategoryViewModel = viewModel()
    val createdCategories = viewModel.categories.collectAsStateWithLifecycle().value
    val categories = Categories.entries
    var animatedText by remember { mutableStateOf(categories[(0..4).random()]) }
    var openSheet by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            animatedText = categories[(0..4).random()]
        }
    }
    Column(modifier = modifier) {
        Column(Modifier.weight(.5f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(   // TODO: add scribble effect at back of the text
                text = stringResource(R.string.app_name),
                style = Typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.app_desc),
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier.padding(vertical = 20.dp),
                onClick = { openSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Create Category")
                Text(
                    text = "Create $animatedText",
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.85f)
        ) {
            itemsIndexed(createdCategories) { index, item ->
                CategoryCard(item)
            }
        }
        if (openSheet) CategoryBottomSheet(viewModel, categories) { openSheet = false }
    }
}

@Composable
private fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = {},
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color(category.color).copy(0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(.7f),
                text = category.name,
                style = Typography.displayLarge
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color(category.color), shape = CircleShape)
            )
        }
    }
}
