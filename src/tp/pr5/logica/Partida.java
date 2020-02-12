package tp.pr5.logica;

import java.util.ArrayList;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoInvalido;

public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean terminada;
	private Pila pila;
	private ArrayList<Observador> observadores;

	/**
	 * Constructora de Partida. Crea el tablero, inicializa la pila y asigna
	 * BLANCA como turno inicial
	 * 
	 * @param reg : reglas del juego correspondiente
	 */
	public Partida(ReglasJuego reg) { 
		pila = new Pila();
		this.terminada = false;
		this.reglas = reg;
		this.tablero = reg.inicializaTablero();
		this.turno = reg.turnoInicial();
		observadores = new ArrayList<Observador>();
	}

	public Movimiento creaMov(FactoriaJuego factoria, Jugador tipoJug) {
		Movimiento mov = null;
		// Llama a getMovimiento() del tipoJugador que le corresponda, pasandole
		// la factoria correspondiente

		try {
			mov = tipoJug.getMovimiento(factoria, tablero, turno);
		} catch (MovimientoInvalido e) {
			for (Observador ob : observadores) {
				ob.onMovimientoIncorrecto(e.getMessage());
				ob.onMovimientoEnd(tablero, turno, turno); // al ser un movimiento incorrecto, el turno no cambia
				ob.onCambioTurno(turno);
			}
		}

		return mov;
	}
	
	/**
	 * Genera un movimiento automático
	 * @param factoria
	 * @param tipoJug : aleatorio
	 * @return movimiento generado
	 */
	public Movimiento getMovAutomatico(FactoriaJuego factoria, Jugador tipoJug ) {
		Movimiento mov = null;
		
		for (Observador ob : observadores) 
			ob.onMovimientoStart(turno);
		
		try {
		 Thread.sleep(2000);
		} catch(InterruptedException e) {
			return null;
		}
		
		try {
			mov = tipoJug.getMovimiento(factoria, tablero, turno);
		} catch (MovimientoInvalido e) { }
		
		return mov;
	}

	/**
	 * Mé½todo que a través de tablero.toString() devuelve todo el tablero en una
	 * sola cadena para mostrarlo en Controlador
	 * 
	 * @return cadena : contiene todo el tablero
	 */
	public String toString() {
		String cadena = "";

		cadena = tablero.toString();

		return cadena;
	}

	/**
	 * Cambia el tipo de Juego. Pone todas las casillas VACIAS, el turno es de
	 * las BLANCAS, y vacia la pila
	 * 
	 * @param reg : reglas del juego correspondiente
	 */
	public void cambioJuego(ReglasJuego reg) {
		this.reglas = reg;
		this.tablero = reg.inicializaTablero(); // Se pone con las dimensiones nuevas
		turno = reg.turnoInicial();
		pila.resetPila();
		for (Observador ob : observadores)
			ob.onCambioJuego(tablero, turno);
	}

	/**
	 * Reinicia el juego
	 * 
	 */
	public void reset() {
		this.tablero = this.reglas.inicializaTablero(); // Se inicializa las fichas a vacio
		pila.resetPila();
		this.turno = reglas.turnoInicial();
		for (Observador ob : observadores)
			ob.onResetPartida(tablero, turno);
	}

	/**
	 * Introduce una ficha del turno actual en la casilla correspondiente. Por
	 * último, apila el movimiento, comprueba si hay un ganador y cambia el
	 * turno
	 * 
	 * @param mov : movimiento a ejecutar
	 * @return ok : true si se ha insertado la ficha
	 */
	public void ejecutaMovimiento(Movimiento mov) {
		Ficha color, turnoAnterior = Ficha.VACIA;

		try {
			reglas.movimientoValido(tablero, mov);
			mov.ejecutaMovimiento(tablero); // reglas y mov lanzan expception

			pila.apilar(mov); // Apilamos en la pila, es decir, la incrementamos
			color = reglas.comprobarGanador(tablero, mov);

			if ((color == Ficha.VACIA) && !reglas.hayTablas(tablero, mov)) {
				turnoAnterior = turno;
				turno = reglas.SiguienteTurno(tablero, getTurno());
			
				for (Observador ob : observadores) {
					ob.onMovimientoEnd(tablero, turnoAnterior, turno);
					ob.onCambioTurno(turno);
				}
			}

			else {
				terminada = true;
				ganador = color;
				for (Observador ob : observadores)
					ob.onPartidaTerminada(tablero, ganador);
			}
																
		} catch (MovimientoInvalido e) {
			for (Observador ob : observadores) {
				ob.onMovimientoIncorrecto(e.getMessage());
				ob.onMovimientoEnd(tablero, turnoAnterior, turno);
				ob.onCambioTurno(turno);
			}
		}
	}
	
	/**
	 * Cambia el turno y crea un jugador nuevo
	 */
	public void creaJugador() {
		
		for (Observador ob : observadores)
			ob.onCambioTurno(turno);	
	}

	/**
	 * Método para saber de quién es el turno actual.
	 * 
	 * @return turno
	 */
	public Ficha getTurno() {

		return turno;
	}

	/**
	 * Método que indica si la partida se ha terminado
	 * 
	 * @return terminada : true si la partida ha terminado
	 */
	public boolean getTerminada() {

		return terminada;
	}

	/**
	 * Método para obtener el ganador
	 * 
	 * @return ganador : BLANCA, NEGRA o VACIA
	 */
	public Ficha getGanador() {

		return ganador;
	}
	
	/**
	 * Finaliza la partida
	 * @return true
	 */
	public boolean setTerminada() {
		
		return terminada = true;
	}

	/**
	 * Deshace el último movimiento en caso de que la pila tenga elementos y
	 * pone la correspondiente ficha VACIA. Por último, borra el movimiento de
	 * la pila y cambia el turno
	 * 
	 * @return ok : true si se ha podido desapilar el movimiento
	 */
	public void deshacer() {

		boolean hayMas = false;

		try {
			hayMas = pila.hayMovimientos();

			Movimiento mov = pila.obtenerUltimoMov();
			mov.undo(tablero);
			pila.desapilar();
			turno = mov.getJugador();
			if (pila.getNumUndo() == 0)
				hayMas = false;

		} catch (PilaVacia e) {
			for (Observador ob : observadores) 
				ob.onUndoNotPossible(turno); // Notifica que no se puede deshacer
		}
		
		finally {
			for (Observador ob : observadores) {
				ob.onUndo(tablero, hayMas, turno); // Notifica deshace
				ob.onCambioTurno(turno); // Notifica cambia turno
			}
		}
	}

	/**
	 * Añade los observadores
	 */
	public void addObserver(Observador o) {
		
		observadores.add(o);
	}

	/**
	 * Elimina los observadores
	 */
	public void removeObserver(Observador o) {
		
		observadores.remove(o);
	}
}
