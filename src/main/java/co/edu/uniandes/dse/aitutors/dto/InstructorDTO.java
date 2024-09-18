package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class InstructorDTO extends UsuarioDTO {
    // Esto va vacio debido a que Instructor en su forma "simple" no tiene atributos adicionales
    // Por ende retorna la informacion de UsuarioDTO
}
