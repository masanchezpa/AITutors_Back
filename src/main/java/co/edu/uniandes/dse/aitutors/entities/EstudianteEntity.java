package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class EstudianteEntity extends BaseEntity {
    private ArrayList<Object> cursos;
    
    
    //@OneToMany
    //private Curso cursos;

    //@OneToMany
    //private Curso curso;
    
}
