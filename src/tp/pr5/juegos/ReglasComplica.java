package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public class ReglasComplica extends ReglasFamiliaC4 {
	public static final int FILAS_CO = 7; 
	public static final int COLUMNAS_CO = 4; 

	/**
	 * Crea el tablero con las dimensiones correspondientes
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(FILAS_CO, COLUMNAS_CO);

		return tab;
	}
	
	/**
	 * En Complica siempre empiezan a jugar las blancas
	 * @return BLANCA
	 */
	public Ficha turnoInicial() {
		
		return Ficha.BLANCA;
	}

	/**
	 * Indica si un movimiento se puede hacer, es decir, si la columna está
	 * entre 0 y el ancho del tablero
	 * 
	 * @return true si el movimiento se puede realizar
	 */
	public void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {
		// return ((mov.getColumna() >= 0) && (mov.getColumna() < COLUMNAS_CO));
		if ((mov.getColumna() < 0) || (mov.getColumna() >= COLUMNAS_CO))
			throw new MovimientoInvalido("Posicion incorrecta. Las dimensiones son de: " + FILAS_CO + " x " + COLUMNAS_CO);
	}

	/**
	 * Indica si hay un grupo de cuatro fichas iguales en alguna de las cuatro
	 * direcciones
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha
	 * @return colorGrupo : color del grupo. VACIA si no hay grupo de ninguno de los dos jugadores
	 */
	public Ficha hayGrupo(Tablero tab, int fila, int col) {
		Ficha colorGrupo = Ficha.VACIA;

		if (comprobarVertical(tab, fila, col)
				|| comprobarHorizontal(tab, fila, col)
				|| comprobarDiagonalCreciente(tab, fila, col)
				|| comprobarDiagonalDecreciente(tab, fila, col))

			colorGrupo = tab.getCasilla(fila, col);

		return colorGrupo;
	}

	/**
	 * Comprueba si hay un ganador
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento hecho
	 * @return colorGrupo : color de la ficha de la que hay un grupo
	 */
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha colorGrupo = Ficha.VACIA;
		int contBlanca = 0, contNegra = 0;

		for (int i = 0; i < FILAS_CO; i++) {
			for (int j = 0; j < COLUMNAS_CO; j++) {

				colorGrupo = hayGrupo(tab, i, j);

				if (colorGrupo == Ficha.BLANCA)
					contBlanca++;
				else if (colorGrupo == Ficha.NEGRA)
					contNegra++;
			}
		}

		if (contBlanca != contNegra) {
			if (contNegra > 0)
				colorGrupo = Ficha.NEGRA;

			else if (contBlanca > 0)
				colorGrupo = Ficha.BLANCA;
		}

		else
			colorGrupo = Ficha.VACIA;

		return colorGrupo;
	}

	
	/**
	 * En Complica nunca hay tablas
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento hecho
	 * @return false
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {

		return false;
	}
}
