package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasConecta4;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.*;
import tp.pr5.logica.Ficha;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoConecta4;

public class FactoriaJuegoConecta4 implements FactoriaJuego {
	
	/**
	 * Crea las reglas para el juego Conecta 4
	 * @return reglas
	 */
	public ReglasJuego creaReglas() {
		
		return new ReglasConecta4();
	}
	
	/**
	 * Crea un movimiento para Conecta 4. Inicializa los atributos de Movimiento con los valores de los argumentos
	 * @param fila, col : coordenadas del movimiento
	 * @param turno : color del jugador actual
	 * @return movimiento
	 */
	public Movimiento creaMovimiento(int fila, int col, Ficha turno) {
		
		return new MovimientoConecta4(fila, col, turno);
	}

	/**
	 * Devuelve la variable jugador con un jugador para Conecta 4 de tipo aleatorio
	 * @return jugador
	 */
	public Jugador creaJugadorAleatorio() {

		return new JugadorAleatorioConecta4();
	}

	/**
	 * Devuelve la variable jugador con un jugador para Conecta 4 de tipo humano
	 * @param sc : scanner de entrada
	 * @return jugador 
	 */
	public Jugador creaJugadorHumano(Scanner sc) {

		return new JugadorHumanoConecta4(sc);
	}
}
