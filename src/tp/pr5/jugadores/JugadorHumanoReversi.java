package tp.pr5.jugadores;

import java.util.InputMismatchException;
import java.util.Scanner;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.juegos.ReglasReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public class JugadorHumanoReversi implements Jugador {
	Scanner sc;
	
	/**
	 * Constructor que recibe el scanner
	 * @param sc : scanner de entrada
	 */
	public JugadorHumanoReversi(Scanner sc) {
		
		this.sc = sc;
	}
	
	/**
	 * Solicita al usuario la creación de un nuevo movimiento. 
	 * Si la casilla no pertenece al tablero, lanza una excepción
	 * @param factoria : juego actual
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws MovimientoInvalido {
		int columna, fila;
		
		try {
			System.out.print("Introduce la columna: ");
			columna = sc.nextInt() - 1;
			System.out.print("Introduce la fila: ");
			 fila = sc.nextInt() - 1;
		} catch (InputMismatchException e) {
			sc.nextLine();
			throw new MovimientoInvalido("Movimiento no valido. El tablero es de: " + ReglasReversi.FILAS_RE + " x " + ReglasReversi.COLUMNAS_RE);
		}
		
		return factoria.creaMovimiento(fila, columna, color);
	}	
}
