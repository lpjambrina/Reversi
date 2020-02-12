package tp.pr5.vistas.consola;

import java.util.Scanner;

import tp.pr5.control.ControlConsola;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.control.factorias.FactoriaJuegoComplica;
import tp.pr5.control.factorias.FactoriaJuegoConecta4;
import tp.pr5.control.factorias.FactoriaJuegoGravity;
import tp.pr5.control.factorias.FactoriaJuegoReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;

public class VistaConsola implements Observador {
	private Scanner sc;
	private ControlConsola control;

	// OPCIONES
	private static final String PONER = "PONER";
	private static final String JUGAR = "JUGAR";
	private static final String DESHACER = "DESHACER";
	private static final String REINICIAR = "REINICIAR";
	private static final String AYUDA = "AYUDA";
	private static final String JUGADOR = "JUGADOR";
	private static final String SALIR = "SALIR";

	/**
	 * Constructor para inicializar la variable control y añadir los observadores
	 * @param in : scanner
	 * @param c : controlador
	 */
	public VistaConsola(Scanner in, ControlConsola c) {
		this.sc = in;
		control = c;
		control.addObserver(this);
	}

	/**
	 * Método principal que se encarga de todo el desarrollo del juego hasta que se acabe
	 * porque se ha acabado la partida o el usuario elige salir
	 */
	public void run() {
		control.reiniciar();
		String ejecucion = "";
		
		while (!ejecucion.equalsIgnoreCase(SALIR) && !control.finalizada()) {
			ejecucion = sc.next(); // pide
			
			// SI SE INTRODUCE PONER
			if (ejecucion.equalsIgnoreCase(PONER))
				control.poner();
			
			// SI SE INTRODUCE JUGAR
			else if (ejecucion.equalsIgnoreCase(JUGAR)) {
				boolean ok = true;
				ejecucion = sc.nextLine();
				ejecucion = ejecucion.trim();
				String arrayEjecucion[] = ejecucion.split(" ");
				FactoriaJuego factoria = null;
				
				if (!arrayEjecucion[0].equalsIgnoreCase("C4")
						&& !arrayEjecucion[0].equalsIgnoreCase("CO")
						&& !arrayEjecucion[0].equalsIgnoreCase("GR") 
						&& !arrayEjecucion[0].equalsIgnoreCase("RV"))
					System.err.println("No te entiendo");

				else {
					if (arrayEjecucion[0].equalsIgnoreCase("C4")) 
						factoria = new FactoriaJuegoConecta4();

					else if (arrayEjecucion[0].equalsIgnoreCase("CO")) 
						factoria = new FactoriaJuegoComplica();
					
					else if (arrayEjecucion[0].equalsIgnoreCase("GR")) {
						if (arrayEjecucion.length == 1) 
							factoria = new FactoriaJuegoGravity();

						// Se comienza con las dimensiones introducias
						else if (arrayEjecucion.length == 3) {
							int f = Integer.parseInt(arrayEjecucion[1]);
							int c = Integer.parseInt(arrayEjecucion[2]);

							if ((f <= 0) || (c <= 0)) {
								System.err.println("Las dimensiones de Gravity tienen que ser positivas.\n");
								ok = false;
								estado(control.getTurno());
							} 
							
							else
								factoria = new FactoriaJuegoGravity(f, c);
						}

						// Si introduce menos de dos parametros o mas de dos
						else if ((arrayEjecucion.length != 1) || (arrayEjecucion.length != 3)) {
							ok = false;
							System.err.println("No te entiendo: introduce gr [tamX tamY] o gr.\n");
							estado(control.getTurno());
						}
					}
					
					else if (arrayEjecucion[0].equalsIgnoreCase("RV")) 
						factoria = new FactoriaJuegoReversi();

					if (ok) 
						control.reset(factoria);
				}
			}

			// SI SE INTRODUCE DESHACER
			else if (ejecucion.equalsIgnoreCase(DESHACER))
				control.undo();

			// SI SE INTRODUCE REINICIAR
			else if (ejecucion.equalsIgnoreCase(REINICIAR))
				control.reiniciar();
			
			// SI SE INTRODUCE AYUDA
			else if (ejecucion.equalsIgnoreCase(AYUDA))
				ayuda();

			// SI SE INTRODUCE JUGADOR
			else if (ejecucion.equalsIgnoreCase(JUGADOR)) {
				String color = sc.next();
				String tipoJug = sc.next();
				if (!color.equalsIgnoreCase(ControlConsola.BLANCAS) && !color.equalsIgnoreCase(ControlConsola.NEGRAS)) {
					System.out.println("Color incorrecto. Elige 'blancas' o 'negras'");
					estado(control.getTurno()); 
				}
				else if (!tipoJug.equalsIgnoreCase(ControlConsola.HUMANO) && !tipoJug.equalsIgnoreCase(ControlConsola.ALEATORIO)) {
					System.out.println("Tipo de jugador incorrecto. Elige 'aleatorio' o 'humano'");
					estado(control.getTurno()); 
				}
				else
					control.eligeJugador(color, tipoJug);
			}
			
			// SI SE INTRODUCE SALIR
			else if (ejecucion.equalsIgnoreCase(SALIR))
				control.finalizar();
				
			// SI NO ES NINGUNA DE LAS ANTERIORES
			else {
				System.err.println("Opcion incorrecta. Intentalo de nuevo...\n");
				estado(control.getTurno());
			}
		}
	}

	/**
	 * La partida notifica a los observadores que ha terminado la partida
	 * llamando a este método
	 * 
	 * @param tabFin : tablero final de la partida
	 * @param ganador
	 */
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		
		System.out.println(tabFin.toString()); // muestra el tablero
		mostrarGanador(ganador);
	}

	/**
	 * Notifica a los observadores que se ha iniciado la ejecución de un
	 * movimiento
	 * 
	 * @param turno
	 */
	public void onMovimientoStart(Ficha turno) { }

	/**
	 * Notifica a los obsrvadores que se ha terminado de realizar un movimiento
	 * 
	 * @param tab : tablero
	 */
	public void onMovimientoEnd(TableroInmutable tab, Ficha turnoAnterior, Ficha turnoSiguiente) {
		
		System.out.println(tab.toString()); // Dibuja el tablero 
	}

	/**
	 * Notifica a los observadores que se ha deshecho un movimiento. Proporciona
	 * el estado final del tablero y si hay mas de un movimiento a deshacer o no
	 * 
	 * @param tab : tablero
	 * @param hayMas : true si hay más movimientos para deshacer
	 */
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		System.out.println(tab.toString());
		
		if (hayMas)
			System.out.println("Quedan mas movimientos para deshacer");
		
		else
			System.err.println("Ya no quedan mas movimientos para deshacer");
	}

	/**
	 * Notifica que se ha reiniciado la partida, proporcionando a los
	 * observadores el estado inicial del tablero y el color que se pone
	 * primero
	 * 
	 * @param tabIni : tablero inicial
	 * @param turno
	 */
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		
		System.out.println(tabIni.toString());
		estado(turno);
	}

	/**
	 * Notifica a los observadores que una operación deshacer no ha tenido éxito
	 */
	public void onUndoNotPossible(Ficha turno) {
		
		System.err.println("Imposible deshacer");
	}

	/**
	 * Notifica a los obsrvadores que se ha cambiado de turno
	 * 
	 * @param turno
	 */
	public void onCambioTurno(Ficha turno) {
		estado(turno);
	}

	/**
	 * Notifica que se ha producido un movimiento incorrecto y proporciona un
	 * String con la explicación del problema
	 * 
	 * @param explicacion
	 */
	public void onMovimientoIncorrecto(String explicacion) {
		
		System.err.println(explicacion + "\n");
	}

	/**
	 * Notifica a los observadores que se ha cambiado de juego
	 * 
	 * @param tab : tablero
	 * @param turno
	 */
	public void onCambioJuego(TableroInmutable tab, Ficha turno) {
		
		System.out.println(tab.toString());
		estado(turno);
	}

	/**
	 * Indica de quién es el turno actual y solicita la jugada a realizar
	 */
	public static void estado(Ficha turno) {
		if (turno == Ficha.NEGRA)
			System.out.print("Juegan negras \nQue quieres hacer? ");

		else if (turno == Ficha.BLANCA)
			System.out.print("Juegan blancas \nQue quieres hacer? ");
	}

	/**
	 * Informa sobre el ganador de la partida si lo ha habido. En caso contrario
	 * indica que la partida ha quedado en tablas
	 */
	public static void mostrarGanador(Ficha ganador) {
		if (ganador == Ficha.BLANCA)
			System.out.println("Ganan las blancas");

		else if (ganador == Ficha.NEGRA)
			System.out.println("Ganan las negras");

		else
			System.out.println("Partida en tablas");
	}

	/**
	 * Muestra un texto de ayuda si el usuario introduce el comando "AYUDA"
	 */
	public void ayuda() {
		System.out.println("Los comandos disponibles son:\n");
		System.out.println(PONER + ": utilizalo para poner la siguiente ficha.");
		System.out.println(DESHACER + ": deshace el ultimo movimiento hecho en la partida.");
		System.out.println(JUGAR + " [c4|co|gr] [tamX tamY]: cambia el tipo de juego.");
		System.out.println(JUGADOR + "[blancas|negras] [humano|aleatorio]: cambia el tipo de jugador.");
		System.out.println(SALIR + ": termina la aplicacion.");
		System.out.println(AYUDA + ": muestra esta ayuda.\n");
	}
}
