package ir.hirkancorp.presenter.screens.requests

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.components.ErrorPage
import ir.hirkancorp.presenter.screens.register.components.RuzmozdTopAppBar
import ir.hirkancorp.presenter.screens.requests.components.LoadingRequestItem
import ir.hirkancorp.presenter.screens.requests.components.RequestItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RequestsScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestsScreenViewModel = koinViewModel()
) {

    val state = viewModel.state
    val requestsListState = state.requests?.collectAsLazyPagingItems()
    val refreshLoadState = requestsListState?.loadState?.refresh

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshLoadState == LoadState.Loading,
        onRefresh = {
            viewModel.onEvent(RequestsScreenEvent.LoadRequests)
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(RequestsScreenEvent.LoadRequests)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RuzmozdTopAppBar(title = stringResource(R.string.requests_screen_app_bar_title))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (requestsListState?.itemCount) {
                    0 -> Card(
                        modifier = Modifier.align(Alignment.Center),
                        backgroundColor = MaterialTheme.colors.primary.copy(alpha = .2f),
                        contentColor = MaterialTheme.colors.primary,
                        elevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(R.string.all_no_data),
                            style = MaterialTheme.typography.caption
                        )
                    }

                    else -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(requestsListState?.itemCount ?: 0) {
                                val item = requestsListState?.get(it)
                                item?.let {
                                    RequestItem(item = it)
                                    Divider()
                                }
                            }

                            when (refreshLoadState) {
                                is LoadState.Loading -> {
                                    items(15) {
                                        LoadingRequestItem()
                                    }
                                }

                                is LoadState.Error -> {
                                    item {
                                        ErrorPage(
                                            errorMessage = refreshLoadState.error.message
                                                ?: stringResource(id = R.string.all_unknown_error)
                                        )
                                    }
                                }

                                is LoadState.NotLoading -> {}

                                null -> {}
                            }
                        }
                        PullRefreshIndicator(
                            refreshing = refreshLoadState == LoadState.Loading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                        )
                    }
                }
            }
        }
    }
}