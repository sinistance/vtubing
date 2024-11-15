package io.github.sinistance.vtubing.mypage.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    MyPageScreenContent()
}

@Composable
fun MyPageScreenContent(
    modifier: Modifier = Modifier,
    uiState: MyPageUiState = MyPageUiState(),
) {

}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenContentPreview() {
    MyPageScreenContent()
}