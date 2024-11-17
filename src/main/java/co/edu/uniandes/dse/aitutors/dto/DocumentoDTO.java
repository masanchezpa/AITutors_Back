package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class DocumentoDTO {
    private Long id;
    private String tipo;
    private String nombre;
    private String contenido;
    private TemaDTO tema;
}
