package ir.hirkancorp.data.common.static_data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class TempDataRepository<T> {

    private val dataFlow = MutableStateFlow<Pair<String, T?>>("" to null)

    suspend fun saveData(key: String, value: T) = dataFlow.emit(key to value)

    fun getData(key: String): Flow<T?> = dataFlow.filter { it.first == key }.map { it.second }
}


