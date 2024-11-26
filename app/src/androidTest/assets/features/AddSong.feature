Feature: Add Song

  Scenario: Agregar una cancion llamada test-e2e
    When Un usuario "Coleccionista" ingresa a Vinilos App
    And Navega al menu de "albumes"
    Then Puede ver el listado de "albumes"
    And Puede ingresar a ver el detalle de un "album"
    When Quiere agregar "una cancion"
    And "La cancion" se llama test-e2e-"cancion"
    Then Puede ingresar la informacion "la cancion" y crearlo
    And Navega a la vista anterior
    Then Puede ver una cancion llamada test-e2e-cancion