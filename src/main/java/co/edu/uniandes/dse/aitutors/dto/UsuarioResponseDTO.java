package co.edu.uniandes.dse.aitutors.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String dtype;

    public UsuarioResponseDTO(Long id, String nombre, String email, String dtype) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.dtype = dtype;
    }

}
