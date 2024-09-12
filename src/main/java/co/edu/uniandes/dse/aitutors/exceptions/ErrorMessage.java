package co.edu.uniandes.dse.aitutors.exceptions;



public final class ErrorMessage {
	public static final String ACCION_NOT_FOUND = "La accion con ese id no se encontro";
	public static final String ARTEFACTO_NOT_FOUND = "El artefacto con ese id no se encontro";
	public static final String COMENTARIO_NOT_FOUND = "El comentario con ese id no se encontro";
	public static final String CURSO_NOT_FOUND = "El curso con ese id no se encontro";
	public static final String DOCUMENTO_NOT_FOUND = "El documento con ese id no se encontro";
	public static final String ESTUDIANTE_NOT_FOUND = "El estudiante con ese id no se encontro";
    public static final String INSTRUCTOR_NOT_FOUND = "El instructor con ese id no se encontro";
    public static final String TEMA_NOT_FOUND = "El tema con ese id no se encontro";
    public static final String USUARIO_NOT_FOUND = "El usuario con ese id no se encontro";
    public static final String TUTORIA_NOT_FOUND = "El tutorIA con ese id no se encontro";

	private ErrorMessage() {
		throw new IllegalStateException("Utility class");
	}
}