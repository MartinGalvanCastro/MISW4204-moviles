Feature: Banda List


  Scenario: Ver listado de bandas como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de bandas
    Then Puede ver el listado de bandas
    And Cada banda tiene su nombre, su foto


  Scenario: Poder filtrar un bandas por nombre como invitado
    When Un usuario invitado ingresa a Vinilos App
    And Navega al menu de bandas
    Then Puede ver el listado de bandas
    When Ingresa la palabra "Queen"
    Then Solo puede ver las bandas que contengan la palabra "Queen"