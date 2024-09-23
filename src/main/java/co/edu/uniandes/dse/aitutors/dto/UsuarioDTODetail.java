package co.edu.uniandes.dse.aitutors.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioDTODetail extends UsuarioDTO {
    private List<ArtefactoDTO> artefactos = new ArrayList<>();
    private List<ComentarioDTO> comentarios = new ArrayList<>();
}
