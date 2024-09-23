package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TemaDetailDTO extends TemaDTO {
    private List<DocumentoDTO> documentos = new ArrayList<>();

}
