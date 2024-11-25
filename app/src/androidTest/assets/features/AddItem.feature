Feature: Add Iten

  Scenario: Agregar un album llamado test-e2e
    When Un usuario "Coleccionista" ingresa a Vinilos App
    And Navega al menu de "albumes"
    And Quiere agregar "un album"
    And "El album" se llama test-e2e-"album"
    Then Puede ingresar la informacion "del album" y crearlo
    And Navega a la vista anterior
    When Ingresa la palabra "test-e2e"
    Then Solo puede ver las entidades "albumes" que contengan la palabra "test-e2e"