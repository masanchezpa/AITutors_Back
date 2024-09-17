package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TutorIADetailDTO extends TutorIADTO {
    private List<AccionDTO> acciones = new ArrayList<>();
}
