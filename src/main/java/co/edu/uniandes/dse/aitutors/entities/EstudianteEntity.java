package co.edu.uniandes.dse.aitutors.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@Data
public class EstudianteEntity extends UsuarioEntity {
    

    @PodamExclude
    @ManyToMany(mappedBy = "estudiantes")
    private List<CursoEntity> cursos =  new ArrayList<>();
    
}
