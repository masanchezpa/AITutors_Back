package co.edu.uniandes.dse.aitutors.entities;

import jakarta.persistence.ManyToOne;

public class DocumentoEntity extends BaseEntity {

    private String tipo;
    private String contenido;

    // @ManyToOne
    // private TemaEntity tema= new TemaEntity();

}
