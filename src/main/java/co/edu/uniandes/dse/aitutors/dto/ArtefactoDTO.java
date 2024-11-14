package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class ArtefactoDTO {
    private Long id;
    private String titulo;
    private String tipo;
    private String contenido;
    private UsuarioDTO autor;
    private AccionDTO accion;
    private Boolean isVisible;
}
