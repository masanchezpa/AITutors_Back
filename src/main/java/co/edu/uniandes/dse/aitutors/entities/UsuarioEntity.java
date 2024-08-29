package co.edu.uniandes.dse.aitutors.entities;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email;
    private ArrayList<Object> comentarios;

    //@OneToMany
    //private Comentario comentarios;

    //@OneToMany
    //private Artefacto artefacto = new ArtefactoEntity();

    
}
