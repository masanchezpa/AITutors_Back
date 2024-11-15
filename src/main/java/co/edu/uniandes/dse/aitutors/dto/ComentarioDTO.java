package co.edu.uniandes.dse.aitutors.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ComentarioDTO {
    private Long id;
    private String contenido;
    private Date fecha;
    private UsuarioDTO autor;
    private ArtefactoDTO artefacto;
}
