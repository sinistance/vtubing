package io.github.sinistance.vtubing.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.sinistance.vtubing.people.domain.entity.Person
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (Person) -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsState()
    HomeScreenContent(
        uiState = uiState.value,
        onItemClick = onItemClick,
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    onItemClick: (Person) -> Unit = {},
) {
    Column(modifier = modifier) {
        FilterTabs()
        GridContent(
            modifier = Modifier.fillMaxSize(),
            uiState,
            onItemClick
        )
    }
}

@Composable
private fun GridContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onItemClick: (Person) -> Unit = {},
) {
    if (uiState.loading) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier.padding(4.dp), columns = GridCells.Fixed(2)
        ) {
            uiState.people.forEach { person ->
                item(key = person.id) {
                    GridItem(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onItemClick(person) },
                        person,
                    )
                }
            }
        }
    }
}

@Composable
private fun GridItem(
    modifier: Modifier = Modifier,
    person: Person,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = person.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(text = person.name)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FilterTabs(
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("New", "All")
    SecondaryTabRow(
        modifier = modifier,
        selectedTabIndex = state
    ) {
        titles.forEachIndexed { index, title ->
            Tab(selected = state == index,
                onClick = { state = index },
                text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUiState(loading = true)
    )
}