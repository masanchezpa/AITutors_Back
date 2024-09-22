package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class CursoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private InstructorDTO instructor;
}
