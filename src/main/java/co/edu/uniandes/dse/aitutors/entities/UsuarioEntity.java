package co.edu.uniandes.dse.aitutors.entities;

import java.util.*;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class UsuarioEntity {
    private String nombre;
    private String email;
    private ArrayList<Object> comentarios;
}
