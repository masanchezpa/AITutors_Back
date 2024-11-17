package co.edu.uniandes.dse.aitutors.entities;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

@Data
@Entity
public class TutorIAEntity extends BaseEntity{


    private String nombre;
    private String especialidad;

    @PodamExclude
    @OneToMany(mappedBy = "tutorIA",fetch=FetchType.LAZY)
    private List<AccionEntity> acciones=new ArrayList<>();

    @PodamExclude
    @OneToOne(mappedBy = "tutorIA", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TemaEntity tema;



}
