package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasJuego;
import tp.pr5.juegos.ReglasReversi;
import tp.pr5.jugadores.Jugador;
import tp.pr5.jugadores.JugadorHumanoReversi;
import tp.pr5.jugadores.JugadorAleatorioReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoReversi;

public class FactoriaJuegoReversi implements FactoriaJuego {
	
	/**
	 * Crea las reglas para el juego Conecta 4
	 * @return reglas
	 */
	public ReglasJuego creaReglas() {
		
		return new ReglasReversi();
	}
	
	/**
	 * Crea un movimiento para Reversi. Inicializa los atributos de Movimiento con los valores de los argumentos
	 * @param fila, col : coordenadas del movimiento
	 * @param turno : color del jugador actual
	 * @return movimiento
	 */
	public Movimiento creaMovimiento(int fila, int col, Ficha turno) {
		
		return new MovimientoReversi(fila, col, turno);
	}
	
	/**
	 * Devuelve la variable jugador con un jugador para Reversi de tipo aleatorio
	 * @return jugador
	 */
	public Jugador creaJugadorAleatorio() {
		
		return new JugadorAleatorioReversi();
	}
	
	
	/**
	 * Devuelve la variable jugador con un jugador para Reversi de tipo humano
	 * @param sc : scanner de entrada
	 * @return jugador 
	 */
	public Jugador creaJugadorHumano(Scanner sc) {
		
		return new JugadorHumanoReversi(sc);
	}
}
