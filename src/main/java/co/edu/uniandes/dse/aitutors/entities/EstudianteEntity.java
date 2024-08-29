package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;


import javax.persistence.Entity;
import javax.persistence.OneToMany;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@Data
public class EstudianteEntity extends BaseEntity {
    
    
    @PodamExclude
    @OneToMany
    private ArrayList<CursoEntity> cursos;
    
}
