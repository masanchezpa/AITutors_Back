package co.edu.uniandes.dse.aitutors.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;

@Data
@Entity
public class TutorIAEntity extends BaseEntity{
    private String nombre;
    private String especialidad;

    @PodamExclude
    @OneToMany(
         mappedBy = "accion",
         fetch=FetchType.LAZY)
    private List<AccionEntity> acciones=new ArrayList<>();

    @PodamExclude
    @OneToOne
    private TemaEntity tema;
}
