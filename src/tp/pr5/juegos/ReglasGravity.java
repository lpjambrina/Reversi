package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public class ReglasGravity extends ReglasFamiliaC4 {

	private int filas_gr; // Dimensiones
	private int columnas_gr;

	/**
	 * Constructor con argumentos para construir el tablero de Gravity con las
	 * dimensiones correspondientes. Si hay que dibujar el tablero por defecto,
	 * los argumentos serán 10
	 * 
	 * @param fila, columna : dimensiones del tablero
	 */
	public ReglasGravity(int fila, int columna) {
		filas_gr = fila;
		columnas_gr = columna;
	}

	/**
	 * Crea el tablero vacío con las dimensiones por defecto (10 x 10)
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(filas_gr, columnas_gr);

		return tab;
	}
	

	/**
	 * Crea el tablero con las dimensiones introducidas por el usuario
	 * 
	 * @param filas, columnas : coordenadas del tablero
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero(int filas, int columnas) {

		Tablero tab = new Tablero(filas, columnas);

		return tab;
	}
	
	/**
	 * En Gravity siempre empiezan a jugar las blancas
	 * @return BLANCA
	 */
	public Ficha turnoInicial() {
		
		return Ficha.BLANCA;
		
	}
	
	/**
	 * Indica si un movimiento de Gravity es válido. Comprueba que las coordenadas se encuentren dentro 
	 * de las dimensiones del tablero y que la posición esté vacía. Lanza una excepción si el movimiento no es válido
	 * @param tab : tablero
	 * @param mov : movimiento a ejecutar 
	 */
	public void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {

		if ((mov.getColumna() < 0) || (mov.getColumna() >= columnas_gr) || (mov.getFila() < 0) || (mov.getFila() >= filas_gr))
			throw new MovimientoInvalido("Posicion incorrecta. Las dimensiones son de: " + filas_gr + " x " + columnas_gr);

		else if (!tab.fichaVacia(mov.getFila(), mov.getColumna()))
			throw new MovimientoInvalido("Casilla ocupada.");
	}
	
	/**
	 * Comprueba si hay un grupo de 4 fichas en posición vertical partir de la ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha
	 * @return true si hay un grupo de 4 fichas iguales
	 */
	protected boolean comprobarVertical(Tablero tab, int fila, int col) {
		int coincidentes = 1, f = fila, alto = tab.getFilas();
		
		// Comrpueba hacia arriba
		while ((f < alto - 1) && (tab.getCasilla(f + 1, col) == tab.getCasilla(f, col)) && (coincidentes < 4)) {
			f++;
			coincidentes++;
		}
			f = fila;
		
		// Comprueba hacia abajo
		while ((f > 0) && (tab.getCasilla(f - 1, col) == tab.getCasilla(f, col)) && (coincidentes < 4)) {
			coincidentes++;
			f--;
		}
		
		return (coincidentes == 4);
	}	
}
