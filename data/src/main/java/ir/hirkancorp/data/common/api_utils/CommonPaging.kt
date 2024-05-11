package ir.hirkancorp.data.common.api_utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.ktor.client.statement.HttpResponse
import ir.hirkancorp.data.common.data_sources.DataSource

const val DEFAULT_PAGING_COUNT = 20
fun <T : Any> commonPaging(
    httpResponse: suspend (loadSize: Int, currentKey: Int) -> HttpResponse,
    pagingCount:Int = DEFAULT_PAGING_COUNT,
    converter: suspend (HttpResponse) -> List<T>,
) = Pager(
    config = PagingConfig(pageSize = pagingCount),
    pagingSourceFactory = {
        DataSource { loadSize, currentKey ->
            converter(httpResponse(loadSize, currentKey))
        }
    }
).flow