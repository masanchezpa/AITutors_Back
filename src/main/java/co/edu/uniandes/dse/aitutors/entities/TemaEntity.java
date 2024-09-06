package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

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
    @OneToMany(mappedBy = "Tema", fetch = FetchType.LAZY)
    private List<DocumentoEntity> documentos = new ArrayList<>();

    @PodamExclude
    @OneToOne
    private TutorIAEntity tutorIA;


    @PodamExclude
    @ManyToOne
    private CursoEntity curso;



}
