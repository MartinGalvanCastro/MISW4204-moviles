Feature: Artista List


  Scenario: Ver listado de artista como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de artistas
    Then Puede ver el listado de artistas
    And Cada artista tiene su nombre y su foto


  Scenario: Poder filtrar un artista por nombre como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de artistas
    Then Puede ver el listado de artistas
    When Ingresa la palabra "Blades"
    Then Solo puede ver los artistas que contengan la palabra "Blades"