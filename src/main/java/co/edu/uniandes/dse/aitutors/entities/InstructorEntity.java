package co.edu.uniandes.dse.aitutors.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@Data
public class InstructorEntity extends UsuarioEntity {
    

    @PodamExclude
    @OneToMany
    private List<CursoEntity> cursos;
    
}
