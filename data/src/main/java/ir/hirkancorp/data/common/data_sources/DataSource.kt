package ir.hirkancorp.data.common.data_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState

class DataSource<T : Any>(
    private val fetchData: suspend (loadSize: Int, currentKey: Int) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentKey = params.key ?: 1
        return try {
            val result = fetchData(params.loadSize, currentKey)
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if (result.size < params.loadSize) null else currentKey + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
