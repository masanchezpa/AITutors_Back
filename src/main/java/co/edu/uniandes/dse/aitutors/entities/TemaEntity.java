package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
