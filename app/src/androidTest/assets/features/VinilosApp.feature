Feature: Vinilos App

  Scenario Outline: Ver detalle de <entidades> como <type>
    When Un usuario "<type>" ingresa a Vinilos App
    And Navega al menu de "<menu>"
    Then Puede ver el listado de "<entidades>"
    Then Puede ingresar a ver el detalle de un "<entidad>"
    And "<entidad>" tiene "<detalles>"

    Examples:
      | type           | menu          | entidades         | entidad       | detalles                                         |
      | Invitado       | artistas      | artistas          | artista       | informacion, premios, albumes                   |
      | Coleccionista  | artistas      | artistas          | artista       | informacion, premios, albumes                   |
      | Invitado       | bandas        | bandas            | banda         | informacion, premios, albumes                   |
      | Coleccionista  | bandas        | bandas            | banda         | informacion, premios, albumes                   |
      | Invitado       | coleccionistas| coleccionistas    | coleccionista | un correo, un telefono, albumes, artistas favoritos |
      | Coleccionista  | coleccionistas| coleccionistas    | coleccionista | un correo, un telefono, albumes, artistas favoritos |
      | Invitado       | albumes       | albumes           | album         | caratula, descripcion, fecha de publicacion, disquera, genero, canciones, artistas, comentarios |
      | Coleccionista  | albumes       | albumes           | album         | caratula, descripcion, fecha de publicacion, disquera, genero, canciones, artistas, comentarios |

  Scenario Outline: Ver listado de <entidades> como <type>
    When Un usuario "<type>" ingresa a Vinilos App
    And Navega al menu de "<menu>"
    Then Puede ver el listado de "<entidades>"
    And Cada "<entidad>" tiene "<detalles>"

    Examples:
      | type           | menu          | entidades         | entidad       | detalles                         |
      | Invitado       | artistas      | artistas          | artista       | su nombre y su foto              |
      | Coleccionista  | artistas      | artistas          | artista       | su nombre y su foto              |
      | Invitado       | bandas        | bandas            | banda         | su nombre, su foto               |
      | Coleccionista  | bandas        | bandas            | banda         | su nombre, su foto               |
      | Invitado       | coleccionistas| coleccionistas    | coleccionista | su nombre, su correo y su telefono |
      | Coleccionista  | coleccionistas| coleccionistas    | coleccionista | su nombre, su correo y su telefono |
      | Invitado       | albumes       | albumes           | album         | su nombre y su foto              |
      | Coleccionista  | albumes       | albumes           | album         | su nombre y su foto              |

  Scenario Outline: Poder filtrar <entidades> como <type> por nombre
    When Un usuario "<type>" ingresa a Vinilos App
    And Navega al menu de "<menu>"
    Then Puede ver el listado de "<entidades>"
    When Ingresa la palabra "<palabra>"
    Then Solo puede ver las entidades "<entidades>" que contengan la palabra "<palabra>"

    Examples:
      | type           | menu          | entidades         | palabra       |
      | Invitado       | artistas      | artistas          | Blades        |
      | Coleccionista  | artistas      | artistas          | Blades        |
      | Invitado       | bandas        | bandas            | Queen         |
      | Coleccionista  | bandas        | bandas            | Queen         |
      | Invitado       | coleccionistas| coleccionistas    | Manolo        |
      | Coleccionista  | coleccionistas| coleccionistas    | Manolo        |
      | Invitado       | albumes       | albumes           | Buscando      |
      | Coleccionista  | albumes       | albumes           | Buscando      |
