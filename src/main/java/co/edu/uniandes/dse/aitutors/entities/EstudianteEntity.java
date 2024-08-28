package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class EstudianteEntity extends BaseEntity{
    private ArrayList<Object> cursos;
}
