package com.example.vinilosapp.navigation

object DetailRoutePrefix {
    const val ALBUM_DETALLE_SCREEN = "albumDetalleScreen"
    const val ARTISTA_DETALLE_SCREEN = "artistaDetalleScreen"
    const val BAND_DETALLE_SCREEN = "bandaDetalleScreen"
    const val COLECCIONISTA_DETALLE_SCREEN = "coleccionistaDetalle"
}

object Routes {
    const val ALBUMS_SCREEN = "albumsScreen"
    const val ARTISTAS_SCREEN = "artistasScreen"
    const val BANDAS_SCREEN = "bandasScreen"
    const val COLECCIONISTAS_SCREEN = "coleccionistasScreen"
    const val ALBUM_DETALLE_SCREEN = "${DetailRoutePrefix.ALBUM_DETALLE_SCREEN}/{albumId}"
    const val ARTISTA_DETALLE_SCREEN = "${DetailRoutePrefix.ARTISTA_DETALLE_SCREEN}/{artistaId}"
    const val BAND_DETALLE_SCREEN = "${DetailRoutePrefix.BAND_DETALLE_SCREEN}/{bandaId}"
    const val COLECCIONISTA_DETALLE_SCREEN = "${DetailRoutePrefix.COLECCIONISTA_DETALLE_SCREEN}/{coleccionistaId}"
    const val ADD_ALBUM_SCREEN = "addAlbum"
    const val ADD_SONG_SCREEN = "${DetailRoutePrefix.ALBUM_DETALLE_SCREEN}/{albumId}/addSong"
}
