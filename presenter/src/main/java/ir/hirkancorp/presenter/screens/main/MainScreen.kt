package ir.hirkancorp.presenter.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit
) {

    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.run {
            onEvent(MainScreenEvent.CheckIfAuthenticate)
            authChannel.collectLatest {
                if (it.not()) navigateToLoginScreen()
            }
        }
    }


    Scaffold(
        modifier = modifier
    ) {paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(text = "success login")
        }
    }
}