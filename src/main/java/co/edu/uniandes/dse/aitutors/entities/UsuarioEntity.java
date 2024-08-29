package co.edu.uniandes.dse.aitutors.entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Entity
@Data
public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email;


    @PodamExclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios =new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArtefactoEntity> artefactos = new ArrayList<>();

}
