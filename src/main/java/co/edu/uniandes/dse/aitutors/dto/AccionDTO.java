package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class AccionDTO {
    private Long id;
    private String nombre;
	private String descripcion;
	private Boolean habilitacion;
	private String objetivo;
    private TutorIADTO tutorIA;
}
