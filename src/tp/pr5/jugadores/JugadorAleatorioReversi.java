package tp.pr5.jugadores;

import java.util.Random;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.juegos.ReglasReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public class JugadorAleatorioReversi implements Jugador { 

	/**
	 * Crea un movimiento v�lido (dentro del tablero). En el juego Reversi creamos una fila y columna ambas aleatorias
	 * @param factoria : juego actual
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento creado
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		Random rnd = new Random();
		int fila = -1, columna = -1;
		boolean movimientoValido = false;
		Movimiento mov = null;
		
		while (!movimientoValido) {
			columna = rnd.nextInt(tab.getColumnas()); // Entre 0 y ancho - 1
			fila = rnd.nextInt(tab.getFilas()); // Entre 0 y alto - 1
			mov = factoria.creaMovimiento(fila, columna, color);
			
			if ((tab.fichaVacia(fila, columna)) && (ReglasReversi.flanqueable(tab, mov))) 
				// Si la casilla esta ocupada se crea un nuevo movimiento
				movimientoValido = true;
		}

		return mov;
	}
}
