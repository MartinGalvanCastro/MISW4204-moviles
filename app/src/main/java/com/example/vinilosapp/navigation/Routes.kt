package com.example.vinilosapp.navigation

object DetailRoutePrefix {
    const val ALBUM_DETALLE_SCREEN = "albumDetalleScreen"
}

object Routes {
    const val LOGIN_SCREEN = "loginScreen"
    const val ALBUMS_SCREEN = "albumsScreen"
    const val ARTISTAS_SCREEN = "artistasScreen"
    const val BANDAS_SCREEN = "bandasScreen"
    const val COLECCIONISTAS_SCREEN = "coleccionistasScreen"
    const val ALBUM_DETALLE_SCREEN = "${DetailRoutePrefix.ALBUM_DETALLE_SCREEN}/{albumId}"
}
