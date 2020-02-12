package tp.pr5.jugadores;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JugadorHumanoGravity implements Jugador {
	Scanner sc;
	
	/**
	 * Constructor que recibe el scanner
	 * @param sc : scanner de entrada
	 */
	public JugadorHumanoGravity(Scanner sc) {
		
		this.sc = sc;
	}
	
	/**
	 * Solicita al usuario las coordenadas (fila y columna) para la creación de un nuevo movimiento de Gravity.
	 * Lanza una excepción si las coordenadas no corresponden a una casilla del tablero
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
			throw new MovimientoInvalido("Movimiento no valido. El tablero es de: " + tab.getFilas() + " x " + tab.getColumnas());
		}
		
		return factoria.creaMovimiento(fila, columna, color);
	}
}

