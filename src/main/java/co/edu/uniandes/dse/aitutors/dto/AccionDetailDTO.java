package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AccionDetailDTO extends AccionDTO {
    private List<ArtefactoDTO> artefactos = new ArrayList<>();

}
