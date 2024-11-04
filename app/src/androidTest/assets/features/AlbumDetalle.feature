Feature: Album Detalle

  Scenario: Ver detalle de un album como invitado
    When Un usuario invitado ingresa a Vinilos App
    Then Puede ver el listado de albumes
    Then Puede ingresar a ver el detalle de un album
    And El album tiene caratula
    And El album tiene descripcion
    And El album tiene fecha de publicacion
    And El album tiene disquera
    And El album tiene genero
    And El album tiene canciones
    And El album tiene artistas
    And El album tiene comentarios