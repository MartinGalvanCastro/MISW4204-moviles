Feature: Coleccionista List


  Scenario: Ver listado de coleccionista como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de coleccionistas
    Then Puede ver el listado de coleccionistas
    And Cada coleccionista tiene su nombre, su correo y su telefono


  Scenario: Poder filtrar un coleccionistas por nombre como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de coleccionistas
    Then Puede ver el listado de coleccionistas
    When Ingresa la palabra "Manolo"
    Then Solo puede ver los coleccionistas que contengan la palabra "Manolo"