Feature: Banda Detalle

  Scenario: Ver detalle de una banda como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de bandas
    Then Puede ver el listado de bandas
    Then Puede ingresar a ver el detalle de una banda
    And La banda tiene una foto
    And La banda tiene descripcion
    And La banda tiene fecha de nacimiento
    And La banda tiene premios
    And La banda tiene albumes