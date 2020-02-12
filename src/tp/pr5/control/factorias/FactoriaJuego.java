package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.logica.Ficha;
import tp.pr5.movimientos.Movimiento;

public interface FactoriaJuego {
	
	public ReglasJuego creaReglas();
	public Movimiento creaMovimiento(int fila, int col, Ficha turno);
	public Jugador creaJugadorAleatorio();
	public Jugador creaJugadorHumano(Scanner sc);
}
