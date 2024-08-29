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
import uk.co.jemos.podam.common.PodamExclude;


@Entity
@Data
public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email;


    @PodamExclude
    @OneToMany
    private List<ComentarioEntity> comentarios;

    @PodamExclude
    @OneToMany
    private List<ArtefactoEntity> artefacto;


}
