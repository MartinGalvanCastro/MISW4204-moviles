Feature: Artista Detalle

  Scenario: Ver detalle de un artista como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de artistas
    Then Puede ver el listado de artistas
    Then Puede ingresar a ver el detalle de un artista
    And El artista tiene informacion
    And El artista tiene premios
    And El artista tiene albumes