package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EstudianteDetailDTO extends EstudianteDTO {
    private List<CursoDTO> cursos = new ArrayList<>();
}
