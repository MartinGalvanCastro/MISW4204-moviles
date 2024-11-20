Feature: Coleccionista Detalle

  Scenario: Ver detalle de un coleccionista como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de coleccionistas
    Then Puede ver el listado de coleccionistas
    Then Puede ingresar a ver el detalle de un coleccionista
    And El coleccionista tiene un correo
    And El coleccionista tiene un telefono
    And El coleccionista tiene albumes
    And El coleccionista tiene artistas favoritos