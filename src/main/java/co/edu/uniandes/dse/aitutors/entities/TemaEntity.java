package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

/*
 * Clase que representa un Tema
 * @danielbolivar
 */

@Data
@Entity
public class TemaEntity extends BaseEntity {

    private String titulo;
    private String descripcion;


    @PodamExclude
    @OneToMany(mappedBy = "tema", fetch = FetchType.LAZY)
    private List<DocumentoEntity> documentos = new ArrayList<>();

    @PodamExclude
    @OneToOne
    private TutorIAEntity tutorIA;


    @PodamExclude
    @ManyToOne
    private CursoEntity curso;



}
