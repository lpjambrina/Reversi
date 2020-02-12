package tp.pr5.movimientos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public abstract class MovimientoFamiliaC4 extends Movimiento {

	/**
	 * LLama a la constructora de Movimiento
	 */
	public MovimientoFamiliaC4(int fil, int col, Ficha tur) {
		super(fil, col, tur);
	}
	
	/**
	 * Calcula la última fila ocupada de la columna actual. En caso de que 
	 * la columna esté llena, devuelve el valor -1
	 * 
	 * @param tab : tablero
	 * @return fila : última fila ocupada de la columna dada
	 */
	public int ultimaOcupada(Tablero tab) {
		int fila = 0;
		boolean encontrado = false;

		while ((fila < tab.getFilas()) && !encontrado) {
			if (tab.fichaVacia(fila, columna))
				encontrado = true;
			else
				fila++;
		}

		// Si devuelve un -1 es porque toda la columna estaba llena y no se puede insertar ninguna ficha
		if (!encontrado)
			fila = -1;

		return fila;
	}
	
	/**
	 * Devuelve el color del jugador actual
	 * 
	 * @return turno : BLANCA o NEGRA
	 */
	public Ficha getJugador() {
		
		return turno;
	}
	
	/**
	 * Devuelve la columna del movimiento
	 * @return columna
	 */
	public int getColumna() {
		
		return columna;
	}
	
	/**
	 * Devuelve la fila del tablero
	 * @return fila	
	 */
	public int getFila() {
		
		return fila;
	}
}

