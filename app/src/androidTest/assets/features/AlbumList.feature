Feature: Album List


  Scenario: Ver listado de Albumes como invitado
    When Un usuario invitado ingresa a Vinilos App
    Then Puede ver el listado de albumes
    And Cada album tiene su nombre, su foto y el nombre del artista


  Scenario: Poder filtrar un album por nombre como invitado
    When Un usuario invitado ingresa a Vinilos App
    Then Puede ver el listado de albumes
    When Ingresa la palabra "Buscando"
    Then Solo puede ver el album "Buscando America"