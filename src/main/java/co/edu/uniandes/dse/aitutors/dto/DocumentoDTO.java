package co.edu.uniandes.dse.aitutors.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {

    private Long id;
    private String tipo;
    private String contenido;
    private TemaDTO tema;
}
