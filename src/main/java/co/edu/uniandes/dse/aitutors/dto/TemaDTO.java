package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class TemaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private TutorIADTO tutorIA;
    private CursoDTO curso;
}
