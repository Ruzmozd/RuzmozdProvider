package ir.hirkancorp.presenter.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
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