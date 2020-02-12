package tp.pr5.jugadores;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.juegos.ReglasConecta4;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

import java.util.Random;

public class JugadorAleatorioConecta4 implements Jugador {

	/**
	 * Crea un movimiento aleatorio y válido (dentro del tablero)
	 * @param factoria : juego correspondiente
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento creado
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		Random rnd = new Random();
		boolean movimientoValido = false;
		int columna = -1;

		while (!movimientoValido) { // Si la casilla correspondiente a las coordenadas creadas esta ocupada, 
			 						// se crean unas nuevas
			columna = rnd.nextInt(ReglasConecta4.COLUMNAS_C4);
			// Devuelve un entero entre 0 y COLUMNAS_C4

			if (tab.fichaVacia(ReglasConecta4.FILAS_C4 - 1, columna))
				movimientoValido = true;
		}

		return factoria.creaMovimiento(-1, columna, color);
	}
}
