package co.edu.uniandes.dse.aitutors.entities;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

@Data
@Entity
public class TutorIAEntity extends UsuarioEntity{


    private String nombre;
    private String especialidad;

    @PodamExclude
    @OneToMany(
         mappedBy = "nombre",
         fetch=FetchType.LAZY)
    private List<AccionEntity> acciones=new ArrayList<>();

    @PodamExclude
    @OneToOne
    private TemaEntity tema;
}
