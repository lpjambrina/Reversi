package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public class ReglasConecta4 extends ReglasFamiliaC4 {
	public static final int FILAS_C4 = 6; 
	public static final int COLUMNAS_C4 = 7;
	
	/**
	 * Crea el array del tablero con las dimensiones de Conecta 4
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(FILAS_C4, COLUMNAS_C4);

		return tab;
	}
	
	/**
	 * En Conecta4 siempre empiezan a jugar las blancas
	 * @return BLANCA
	 */
	public Ficha turnoInicial() {
		
		return Ficha.BLANCA;
	}

	/**
	 * Indica si un movimiento se puede hacer, es decir, si la columna está entre 0 y el 
	 * ancho del tablero
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento hecho
	 * @return true si se puede hacer el movimiento
	 */
	public void  movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {

		if ((mov.getColumna() < 0) || (mov.getColumna() >= COLUMNAS_C4))
			throw new MovimientoInvalido("Posicion incorrecta. Las dimensiones son de: " + FILAS_C4 + " x " + COLUMNAS_C4);
	}
}
