package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;
import tp.pr5.movimientos.MovimientoReversi;

public class ReglasReversi implements ReglasJuego {
	public static final int FILAS_RE = 8;
	public static final int COLUMNAS_RE = 8;

	/**
	 * Crea el array del tablero con las dimensiones de Reversi
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(FILAS_RE, COLUMNAS_RE);

		// Fichas iniciales en el centro del tablero
		tab.ponerFicha(tab.getFilas() / 2, tab.getColumnas() / 2 - 1, Ficha.NEGRA);
		tab.ponerFicha(tab.getFilas() / 2 - 1, tab.getColumnas() / 2, Ficha.NEGRA);

		tab.ponerFicha(tab.getFilas() / 2, tab.getColumnas() / 2, Ficha.BLANCA);
		tab.ponerFicha(tab.getFilas() / 2 - 1, tab.getColumnas() / 2 - 1, Ficha.BLANCA);

		return tab;
	}

	/**
	 * En Reversi siempre empiezan a jugar las negras
	 * @return NEGRA
	 */
	public Ficha turnoInicial() {

		return Ficha.NEGRA;
	}

	/**
	 * Indica si un movimiento es válido comprobando si se encuentra dentro de las dimensiones del tablero
	 * y si flanquea alguna ficha del otro color
	 * 
	 * @param tab : tablero
	 * @param mov : movimiento a ejecutar
	 */
	public void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {

		// Comprueba que se cumplan las dimensiones del tablero
		if ((mov.getColumna() < 0) || (mov.getColumna() >= COLUMNAS_RE)|| (mov.getFila() < 0) || (mov.getFila() >= FILAS_RE)) 
			throw new MovimientoInvalido("Posicion incorrecta. Las dimensiones son de: " + FILAS_RE + " x " + COLUMNAS_RE);

		// Comprueba que encierra a alguna ficha
		else if (!flanqueable(tab, mov))
			throw new MovimientoInvalido("No se encierra a ninguna ficha con esas direcciones");
	}

	/**
	 * Indica si un movimiento flanquea alguna ficha del otro color
	 * @param tab : tablero
	 * @param mov : movimiento
	 * @param turno
	 * @param despF, despC : coordenadas para comprobar todas las direcciones
	 * @return true si hay flanqueo
	 */
	public static boolean buscaFlanqueo(Tablero tab, Movimiento mov, Ficha turno, int despF, int despC) {
		int fila = mov.getFila() + despF;
		int col = mov.getColumna() + despC;
		int numFlanqueos = 0;
		boolean encontrado = false;

		while ((fila >= 0) && (fila < tab.getFilas()) && (col >= 0)
				&& (col < tab.getColumnas())
				&& ((tab.getCasilla(fila, col) != Ficha.VACIA) && !encontrado)) {
			if (tab.getCasilla(fila, col) == turno)
				encontrado = true;
			else {
				fila += despF;
				col += despC;
				numFlanqueos++;
			}
		}

		return ((numFlanqueos > 0) && encontrado);
	}

	/**
	 * Busca un flanqueo en todas las direcciones
	 * @param tab : tablero
	 * @param mov : movimiento
	 * @return true si hay flanqueo en alguna dirección
	 */
	public static boolean flanqueable(Tablero tab, Movimiento mov) {
		boolean flanquea = false;
		Ficha turno = mov.getJugador();

		flanquea = (buscaFlanqueo(tab, mov, turno, 1, 0) ||    // vertical arriba
				buscaFlanqueo(tab, mov, turno, -1, 0) ||       // vertical abajo
				buscaFlanqueo(tab, mov, turno, 0, 1) ||        // horizontal derecha
				buscaFlanqueo(tab, mov, turno, 0, -1) ||       // horizontal izquierda
				buscaFlanqueo(tab, mov, turno, 1, -1) ||       // diagonal superior izquierda
				buscaFlanqueo(tab, mov, turno, 1, 1) ||        // diagonal superior derecha
				buscaFlanqueo(tab, mov, turno, -1, -1) ||      // diagonal inferior izquierda
		        buscaFlanqueo(tab, mov, turno, -1, 1));        // diagonal inferior derecha

		return flanquea;
	}
	
	/**
	 * Función que comprueba si hay algún movimiento posible en todo el tablero para el color indicado.
	 * En caso de que no lo haya, no se cambia el turno.
	 * @param tab : tablero
	 * @param turno
	 * @return true si se puede hacer algún movimiento
	 */
	private  boolean hayMovimientosPosibles(Tablero tab, Ficha turno) {
		boolean flanquea = false;
		int i = 0, j = 0;
		MovimientoReversi mov = null;

		while ((i < tab.getFilas()) && (!flanquea)) { 
			j = 0;
			while ((j < tab.getColumnas()) && !flanquea) {
				if (tab.fichaVacia(i, j)) { 
					mov = new MovimientoReversi(i, j, turno);
					if (flanqueable(tab, mov))
						flanquea = true;
				}
				j++;
			}
			i++;
		}

		return flanquea;
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

		if ((turno == Ficha.BLANCA) && (hayMovimientosPosibles(tab, Ficha.NEGRA)))
			siguiente = Ficha.NEGRA;
		else if ((turno == Ficha.NEGRA) && (hayMovimientosPosibles(tab, Ficha.BLANCA)))
			siguiente = Ficha.BLANCA;
		
		else
			siguiente = turno;

		return siguiente;
	}

	/**
	 * Cuenta las fichas blancas de todo el tablero
	 * @param tab : tablero
	 * @return número de fichas blancas
	 */
	private int cuentaBlancas(Tablero tab) {
		int numBlancas = 0;

		for (int i = 0; i < tab.getFilas(); i++)
			for (int j = 0; j < tab.getColumnas(); j++)
				if (tab.getCasilla(i, j) == Ficha.BLANCA)
					numBlancas++;

		return numBlancas;
	}

	/**
	 * Cuenta las fichas negras de todo el tablero
	 * @param tab : tablero
	 * @return número de fichas negras
	 */
	private int cuentaNegras(Tablero tab) {
		int numNegras = 0;

		for (int i = 0; i < tab.getFilas(); i++)
			for (int j = 0; j < tab.getColumnas(); j++)
				if (tab.getCasilla(i, j) == Ficha.NEGRA)
					numNegras++;

		return numNegras;
	}

	/**
	 * Indica el ganador de la partida, aquel que tenga más fichas en el tablero cuando esté lleno
	 * @param tab : tablero
	 * @param mov : movimiento
	 * @return BLANCA, NEGRA ó VACIA
	 */
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha ganador = Ficha.VACIA;

		if (tableroLleno(tab) || ((!hayMovimientosPosibles(tab, Ficha.BLANCA)) && (!hayMovimientosPosibles(tab, Ficha.NEGRA)))) {

			if (cuentaBlancas(tab) > cuentaNegras(tab))
				ganador = Ficha.BLANCA;

			else if (cuentaBlancas(tab) < cuentaNegras(tab))
				ganador = Ficha.NEGRA;
		}

		return ganador;
	}

	/**
	 * Indica si la partida ha terminado en tablas
	 * @param tab : tablero
	 * @param mov : movimiento
	 * @return true si la partida acaba en tablas
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {

		return ((tableroLleno(tab)) && (cuentaNegras(tab) == cuentaBlancas(tab)));
	}

	/**
	 * Indica si el tablero está lleno
	 * 
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
}