package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public interface ReglasJuego {

	public abstract Tablero inicializaTablero();
	public abstract Ficha turnoInicial();
	public abstract void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido;
	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);
	public abstract Ficha SiguienteTurno(Tablero tab, Ficha turno);
	public abstract boolean tableroLleno(Tablero tab);
	public abstract boolean hayTablas(Tablero tab, Movimiento mov);
}
