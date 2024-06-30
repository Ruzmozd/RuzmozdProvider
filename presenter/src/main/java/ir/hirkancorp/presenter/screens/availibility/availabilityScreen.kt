package ir.hirkancorp.presenter.screens.availibility

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.components.Button
import ir.hirkancorp.presenter.core.components.ButtonWithProgressIndicator
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AvailabilityScreen(
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    Scaffold(
        topBar = {
            RuzmozdTopAppBar(title = "زمان فعالیت")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(
                            alpha = .3f
                        )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.Top
            ) { page ->
                LazyColumn {
                    items(10) { it ->
                    val time = Time(
                            id = 1378,
                            time = "$it:00:00",
                            value = "$it:00 - ${it + 1}",
                            day = "monday",
                            status = true,
                        )
                        AvailabilityItem(time = time)
                    }
                }
            }
            ButtonWithProgressIndicator(
                text = "Test",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                isLoading = false,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun AvailabilityScreenPreview() {
    RuzmozdProviderTheme {
        AvailabilityScreen()
    }
}