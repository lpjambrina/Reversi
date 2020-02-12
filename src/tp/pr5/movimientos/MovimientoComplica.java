package tp.pr5.movimientos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoComplica extends MovimientoFamiliaC4 {
	private Ficha colorUndo;

	/**
	 * Llama a la constructora de MovimientoFamiliaC4
	 */
	public MovimientoComplica(int fil, int col, Ficha tur) {
		super(fil, col,  tur); 
		this.colorUndo = Ficha.VACIA;
	}
	
	/**
	 * Pone la ficha del jugador en juego en la última casilla de la columna correspondiente.
	 * En caso de que la columna esté llena, la desplaza hacia abajo apilando la ficha inferior.
	 * 
	 * @param tab : tablero
	 */
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {

		this.fila = ultimaOcupada(tab);

		if (fila == -1) {
			// Si la columna está llena, desplaza hacia abajo
			colorUndo = tab.getCasilla(0, columna);
			desplazarAbajo(tab);
			fila = tab.getFilas() - 1;
			tab.ponerFicha(fila, columna, turno);
		} 
		
		else
			tab.ponerFicha(fila, columna, turno);
	}

	/**
	 * Deshace un movimiento. Si ya se ha desplazado la columna hacia abajo, la desplaza
	 * hacia arriba e inserta el movimiento desapilado en la fila inferior
	 * 
	 * @param tab : tablero
	 */
	public void undo(Tablero tab) {

		if (colorUndo != Ficha.VACIA) {
			desplazarArriba(tab);
			tab.ponerFicha(0, columna, colorUndo);
		}

		else
			tab.ponerFichaVacia(fila, columna);
	}
	
	/**
	 * Desplaza una posición hacia abajo todas las fichas de una columna
	 * 
	 * @param tab : tablero
	 */
	public void desplazarAbajo(Tablero tab) {
		Ficha color;

		for (int i = 0; i < tab.getFilas() - 1; i++) {
			color = tab.getCasilla(i + 1, columna);
			tab.ponerFicha(i, columna, color);
		}
	}

	/**
	 * Desplaza una posición hacia arriba todas las fichas de una columna
	 * 
	 * @param tab : tablero
	 */
	public void desplazarArriba(Tablero tab) {
		Ficha color;

		for (int i = tab.getFilas() - 2; i >= 0; i--) {
			color = tab.getCasilla(i, columna);
			tab.ponerFicha(i + 1, columna, color);
		}
	}
}
