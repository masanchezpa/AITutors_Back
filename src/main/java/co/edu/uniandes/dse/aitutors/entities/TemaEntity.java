package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/*
 * Clase que representa un Tema
 * @danielbolivar
 */

@Data
@Entity
public class TemaEntity extends BaseEntity {

    private String titulo;
    private String descripcion;



    // @OneToMany(mappedBy = "tema", fetch = FetchType.LAZY)
    // private List<DocumentoEntity> documentos = new ArrayList<>();

    // @OneToOne
    // private TutorIAEntity tutorIA = new TutorIAEntity();

    // @ManyToOne
    // private CursoEntity curso = new CursoEntity();

}
