package tp.pr5.logica;

public interface Observador {
	
	void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);
	void onMovimientoStart(Ficha turno);
	void onMovimientoEnd(TableroInmutable tab, Ficha turnoAnterior, Ficha turnoSiguiente);
	void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno);
	void onResetPartida(TableroInmutable tabIni, Ficha turno);
	void onUndoNotPossible(Ficha turno);
	void onCambioTurno(Ficha turno);
	void onMovimientoIncorrecto(String explicacion);
	void onCambioJuego(TableroInmutable tablero, Ficha turno);		
}
