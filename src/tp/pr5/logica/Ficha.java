package tp.pr5.logica;

public enum Ficha {
	VACIA, BLANCA, NEGRA;

	/**
	 * Convierte el enumerado FICHA en una cadena para dibujar el tablero
	 * 
	 * @return cadena : string de la ficha
	 */
	public String toString() {
		String cadena = "";

		switch (this) {
		case VACIA:
			cadena = " ";
			break;
		case BLANCA:
			cadena = "O";
			break;
		case NEGRA:
			cadena = "X";
			break;
		}

		return cadena;
	}
}