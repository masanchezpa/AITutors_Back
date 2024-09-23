package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ArtefactoDetailDTO extends ArtefactoDTO {
    private List<ComentarioDTO> comentarios = new ArrayList<>();
}
