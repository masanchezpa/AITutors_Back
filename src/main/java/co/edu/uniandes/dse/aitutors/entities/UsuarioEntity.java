package co.edu.uniandes.dse.aitutors.entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@Data
public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email;
    
    
    @PodamExclude
    @OneToMany
    private ArrayList<ComentarioEntity> comentarios;

    @PodamExclude
    @OneToMany
    private ArtefactoEntity artefacto = new ArtefactoEntity();

    
}
