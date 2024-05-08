package ir.hirkancorp.presenter.core.utils

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.MapTileIndex

class AppTileSource : OnlineTileSourceBase(
        TILE_NAME,
        MIN_ZOOM_LEVEL,
        MAX_ZOOM_LEVEL,
        TILE_SIZE,
        FILE_NAME_ENDING,
        listOf(BASE_URL).toTypedArray()
    ) {
    
    override fun getTileURLString(index: Long): String {
        val zoom = MapTileIndex.getZoom(index)
        val tileX = MapTileIndex.getX(index)
        val tileY = MapTileIndex.getY(index)
        return String.format(BASE_URL, tileX, tileY, zoom)
    }

    companion object {
        private const val BASE_URL = "https://dev.ruzmozd.ir/api/render_tile?x=%d&y=%d&z=%d"
        private const val TILE_NAME = "RuzmozdTileSource"
        private const val FILE_NAME_ENDING = ".png"
        private const val MIN_ZOOM_LEVEL = 10
        private const val MAX_ZOOM_LEVEL = 12
        private const val TILE_SIZE = 256
    }
}