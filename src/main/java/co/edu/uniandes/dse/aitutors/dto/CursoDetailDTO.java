package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CursoDetailDTO extends CursoDTO{
    private List<EstudianteDTO> estudiantes = new ArrayList<>();
    private List<TemaDTO> temas = new ArrayList<>();
}
