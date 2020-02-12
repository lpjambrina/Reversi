package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasGravity;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.*;
import tp.pr5.logica.Ficha;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoGravity;

public class FactoriaJuegoGravity implements FactoriaJuego {
	
	// Dimensiones del tablero
	private int numFilGR;
	private int numColGr; 
	
	// Dimensiones por defecto
	public static final int FILAS_GR = 10;
	public static final int COLUMNAS_GR = 10;

	/**
	 * Constructor con argumentos que se llamara cuando el usuario ha introducido 
	 * unas dimensiones para el tablero de Gravity
	 * @param fila, columna : dimensiones introducidas por el usuario
	 */
	public FactoriaJuegoGravity (int fila, int columna) {
		numFilGR = fila;
		numColGr = columna;
	}
	
	/**
	 * Constructor sin argumentos que será llamado si el usuario no ha introducido unas dimensiones.
	 * En este caso el tablero tendrá las dimensiones por defecto (10 x 10)
	 */
	public FactoriaJuegoGravity () {
		numFilGR = FILAS_GR;
		numColGr = COLUMNAS_GR;
	}
	
	/**
	 * Crea las reglas para el juego Gravity
	 * @return reglas
	 */
	public ReglasJuego creaReglas() {
		
		return new ReglasGravity(numFilGR, numColGr);
	}
	
	/**
	 * Crea un movimiento para Gravity. Inicializa los atributos de Movimiento con los valores de los argumentos
	 * @param fila, col : coordenadas del movimiento
	 * @param turno : color del jugador actual
	 * @return movimiento
	 */
	public Movimiento creaMovimiento(int fila, int col, Ficha turno) {
		
		return new MovimientoGravity(fila, col, turno);
	}

	/**
	 * Devuelve la variable jugador con un jugador para Gravity de tipo aleatorio
	 * @return jugador
	 */
	public Jugador creaJugadorAleatorio() {
		
		return new JugadorAleatorioGravity();
	}

	/**
	 * Devuelve la variable jugador con un jugador para Gravity de tipo humano
	 * @param sc : scanner de entrada
	 * @return jugador 
	 */
	public Jugador creaJugadorHumano(Scanner sc) {

		return new JugadorHumanoGravity(sc);
	}
}
