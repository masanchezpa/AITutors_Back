package co.edu.uniandes.dse.aitutors.entities;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
    private ArtefactoEntity artefacto;


}
