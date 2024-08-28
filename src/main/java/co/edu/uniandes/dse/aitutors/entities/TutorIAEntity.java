package co.edu.uniandes.dse.aitutors.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
public class TutorIAEntity extends BaseEntity{
    private String nombre;
    private String especialidad;

    // @OneToMany(
    //     mappedBy = "accion",
    //     fetch=FetchType.LAZY
    // )
    // private List<AccionEntity> acciones=new ArrayList<>();
    
    //@OneToOne
    //private TemaEntity tema=new TemaEntity();
}
