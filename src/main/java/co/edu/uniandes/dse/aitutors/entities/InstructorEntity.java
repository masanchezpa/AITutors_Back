package co.edu.uniandes.dse.aitutors.entities;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

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
    @OneToMany(mappedBy = "Instructor", cascade = CascadeType.PERSIST)
    private List<CursoEntity> cursos = new ArrayList<>();
    
}
