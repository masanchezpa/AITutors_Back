package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Entity
@Data
public class UsuarioEntity extends BaseEntity {

    private String nombre;
    private String email;

    @PodamExclude
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios =new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArtefactoEntity> artefactos =new ArrayList<>();

}
