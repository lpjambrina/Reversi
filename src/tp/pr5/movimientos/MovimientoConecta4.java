package tp.pr5.movimientos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoConecta4 extends MovimientoFamiliaC4 {

	/**
	 * Llama a la constructora de MovimientoFamiliaC4
	 */
	public MovimientoConecta4(int fil, int col, Ficha tur) {
		super(fil, col, tur);
	}

	/**
	 * Inserta una ficha en la columna insertada por el jugador y la fila
	 * calculada por ultimaOcupada()
	 * 
	 * @param tab : tablero
	 * @return ok : true si la columna no estï¿½ llena
	 */
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {

		fila = ultimaOcupada(tab); // Delvuelve -1 si toda la columna esta ocupada

		if (fila != -1)
			tab.ponerFicha(fila, columna, turno);

		else
			throw new MovimientoInvalido("Columna llena");
	}

	/**
	 * Después de desapilar un movimiento pone el valor VACIA en la casilla
	 * correspondiente
	 * 
	 * @param tab : tablero
	 */
	public void undo(Tablero tab) {

		tab.ponerFichaVacia(fila, columna);
	}
}
