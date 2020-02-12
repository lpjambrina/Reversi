package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public abstract class ReglasFamiliaC4 implements ReglasJuego {
	
	/**
	 * Comprueba si hay un grupo de 4 fichas en posición vertical a partir de la ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha
	 * @return true si hay un grupo de 4 fichas iguales
	 */
	protected boolean comprobarVertical(Tablero tab, int fila, int col) {
		int coincidentes = 1;
		
		// Solo comprueba hacia abajo
		while ((fila > 0)
				&& (tab.getCasilla(fila - 1, col) == tab.getCasilla(fila, col))
				&& (coincidentes < 4)) {
			coincidentes++;
			fila--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en posición horizontal partiendo de la ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha
	 * @return true si hay un grupo de fichas
	 */
	protected boolean comprobarHorizontal(Tablero tab, int fila, int col) {
		int coincidentes = 1, ancho = tab.getColumnas(), c = col;

		while ((c < ancho - 1)
				&& (tab.getCasilla(fila, c + 1) == tab.getCasilla(fila, c))
				&& (coincidentes < 4)) {
			coincidentes++;
			c++;
		}
		c = col; // Volvemos a asignar la columna de la
				 // ficha introducida para comprobar ahora hacia la izquierda

		while ((c > 0) && (tab.getCasilla(fila, c - 1) == tab.getCasilla(fila, c))
				&& (coincidentes < 4)) {
			coincidentes++; // Se incrementa el contador para mirar las coincidentes tanto de este 
							// sentido como las de la derecha (si habia acumuladas)
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en la diagonal creciente a partir
	 * de una ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha
	 * @return true si hay un grupo de 4 fichas iguales
	 */
	protected boolean comprobarDiagonalCreciente(Tablero tab, int fila, int col) {
		int f = fila, c = col, alto = tab.getFilas(), ancho = tab.getColumnas(), coincidentes = 1;

		// DIAGONAL SUPERIOR DERECHA
		while ((f < alto - 1) && (c < ancho - 1)
				&& (tab.getCasilla(f + 1, c + 1) == tab.getCasilla(f, c))
				&& (coincidentes < 4)) {
			coincidentes++;
			f++;
			c++;
		}
		f = fila; // Volvemos a asignar, pero esta vez ambos valores
		c = col;

		// DIAGONAL INFERIOR IZQUIERDA
		while ((f > 0) && (c > 0)
				&& (tab.getCasilla(f - 1, c - 1) == tab.getCasilla(f, c))
				&& (coincidentes < 4)) {
			coincidentes++; // Se incrementa el contador para mirar las coincidentes tanto de 
							// este sentido como las de la derecha (si habia acumuladas)
			f--;
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en dirección diagonal decreciente 
	 * a partir de una ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila, col : coordenadas de la ficha 
	 * @return true si hay un grupo de 4 fichas
	 */
	protected boolean comprobarDiagonalDecreciente(Tablero tab, int fila, int col) {
		int f = fila, c = col, alto = tab.getFilas(), ancho = tab.getColumnas(), coincidentes = 1;

		// DIAGONAL SUPERIOR IZQUIERDA
		while ((f < alto - 1) && (c > 0)
				&& (tab.getCasilla(f + 1, c - 1) == tab.getCasilla(f, c))
				&& (coincidentes < 4)) {
			coincidentes++;
			f++;
			c--;
		}

		f = fila; // Asignamos otra vez los valores iniciales para seguir comparando
		c = col;

		// DIAGONAL INFERIOR DERECHA
		while ((f > 0) && (c < ancho - 1)
				&& (tab.getCasilla(f - 1, c + 1) == tab.getCasilla(f, c))
				&& (coincidentes < 4)) {
			coincidentes++; // Incrementamos el contador de la diagonal decreciente
							// (y si habia algo del noroeste se acumula)
			f--;
			c++;
		}

		return (coincidentes == 4);
	}
	
	/**
	 * Comprueba si, tras realizar el último movimiento, hay un ganador
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento
	 * @return color del ganador. Si no hay ganador, devuelve VACIA
	 */
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {

		boolean ganador = false;

		if (comprobarVertical(tab, mov.getFila(), mov.getColumna())
				|| comprobarHorizontal(tab, mov.getFila(), mov.getColumna())
				|| comprobarDiagonalCreciente(tab, mov.getFila(), mov.getColumna())
				|| comprobarDiagonalDecreciente(tab, mov.getFila(), mov.getColumna())) {
			ganador = true;
		}

		if (ganador)
			return mov.getJugador();

		else
			return Ficha.VACIA;
	}
	
	/**
	 * Cambia el turno del jugador para la siguiente jugada
	 * 
	 * @param tab : tablero
	 * @param turno : turno actual
	 * @return siguiente : siguiente turno
	 */
	public Ficha SiguienteTurno(Tablero tab, Ficha turno) {
		Ficha siguiente;

		if (turno == Ficha.BLANCA)
			siguiente = Ficha.NEGRA;
		else
			siguiente = Ficha.BLANCA;

		return siguiente;
	}
	
	/**
	 * Indica si el tablero está lleno
	 * @param tab : tablero
	 * @return true si el tablero está lleno
	 */
	public boolean tableroLleno(Tablero tab) {
		boolean lleno = true;
		int fila = tab.getFilas() - 1, col = 0;

		// Se sale del bucle cuando encuentra una ficha vacia
		while (fila >= 0 && lleno) {
			col = 0;
			while (col < tab.getColumnas() && lleno) {
				if (tab.fichaVacia(fila, col))
					lleno = false;
				else
					col++;
			}

			fila--;
		}

		return lleno;
	}

	
	/**
	 * Indica si la partida ha terminado en tablas
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento
	 * @return hayTablas : true si la partida acaba en tablas
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		boolean hayTablas = false;

		if (tableroLleno(tab))
			hayTablas = true;

		return hayTablas;
	}
}
