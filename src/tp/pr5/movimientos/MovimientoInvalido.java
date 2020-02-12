package tp.pr5.movimientos;

public class MovimientoInvalido extends java.lang.Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Llama a la constructora de Exception con el mensaje a lanzar por la excepción
	 * @param mensaje
	 */
	public MovimientoInvalido(String mensaje) {
		
		super(mensaje);
	}
}
