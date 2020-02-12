package tp.pr5.jugadores;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public interface Jugador {
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws MovimientoInvalido;
}
