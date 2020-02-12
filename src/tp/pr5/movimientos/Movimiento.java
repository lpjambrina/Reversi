package tp.pr5.movimientos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public abstract class Movimiento {
	protected int columna;
	protected int fila;
	protected Ficha turno;

	/**
	 * Constructora de Movimiento. Inicializa los atributos columna y turno con los
	 * datos de la jugada introducidos por el usuario. Fila se inicializa a -1 para
	 * calcularla posteriormente
	 * 
	 * @param col : columna introducida por el usuario
	 * @param turno : turno del jugador actual
	 */
	public Movimiento(int fil, int col, Ficha turno) {
		this.fila = fil;
		this.columna = col;
		this.turno = turno;
	}
	
	public abstract void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido;
	public abstract void undo(Tablero tab);
	public abstract Ficha getJugador();
	public abstract int getFila();
	public abstract int getColumna();
}
