package tp.pr5.control;

import java.awt.EventQueue;
import java.util.Scanner;

import org.apache.commons.cli.*;

import tp.pr5.control.factorias.*;
import tp.pr5.logica.Partida;
import tp.pr5.vistas.Swing.VistaSwing;
import tp.pr5.vistas.consola.VistaConsola;

public class Main {
	@SuppressWarnings("resource")
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Si no se introduce nada se empieza con CONECTA 4
		FactoriaJuego factoria = new FactoriaJuegoConecta4();
		Partida partida = new Partida(factoria.creaReglas());

		String juego = null, interfaz = null;
		Options options = new Options();
		CommandLineParser parser;
		CommandLine cmdLine = null;

		options.addOption("g", "game", true, "Tipo de juego (c4, co, gr, rv). Por defecto, c4.");
		options.addOption("h", "help", false, "Muestra esta ayuda.");
		options.addOption("u", "ui", true, "Tipo de interfaz (console, window). Por defecto, console.");
		options.addOption("x", "tamX", true, "Numero de columnas del tablero (solo para Gravity). Por defecto, 10.");
		options.addOption("y", "tamY", true, "Numero de filas del tablero (solo para Gravity). Por defecto, 10.");

		parser = new BasicParser();
		
		try {
			cmdLine = parser.parse(options, args);
			String argsNoEntendidos[] = cmdLine.getArgs();
			
			// Si todos los argumentos son validos
			if (argsNoEntendidos.length == 0) {
				
				// MOSTRAR LA AYUDA
				if (cmdLine.hasOption("h")) {
					new HelpFormatter().printHelp(Main.class.getCanonicalName(), options); // Muestra options
					System.exit(-1); // Para salir
				}

				// JUGAR
				else if (cmdLine.hasOption("g")) {
					juego = cmdLine.getOptionValue("g"); // Se lee el valor de -g: c4, co o gr

					// Si se elige CONECTA 4
					if (juego.equalsIgnoreCase("c4")) {
						factoria = new FactoriaJuegoConecta4();
						partida = new Partida(factoria.creaReglas());
					}

					// Si se elige COMPLICA
					else if (juego.equalsIgnoreCase("co")) {
						factoria = new FactoriaJuegoComplica();
						partida = new Partida(factoria.creaReglas());
					}

					// Si se elige GRAVITY
					else if (juego.equalsIgnoreCase("gr")) {
						// Si se introduce dimensiones para el juego gravity
						if (cmdLine.hasOption("y") && cmdLine.hasOption("x")) {
							int f = 0, c = 0;
							try {
								f = Integer.parseInt(cmdLine
										.getOptionValue("y"));
								c = Integer.parseInt(cmdLine
										.getOptionValue("x"));
							} catch (NumberFormatException e) {
								System.err.println("Las dimensiones de Gravity tienen que ser valores numericos");
								System.err.println("Use -h|--help para mas detalles.");
								System.exit(-1);
							}

							if ((f < 0) || (c < 0))
								throw new ParseException("Las dimensiones de Gravity tienen que ser positivas");

							factoria = new FactoriaJuegoGravity(f, c);
							partida = new Partida(factoria.creaReglas());
						}

						// Dimensiones por defecto 10 x 10
						else if (!cmdLine.hasOption("y") && !cmdLine.hasOption("x")) {
							factoria = new FactoriaJuegoGravity();
							partida = new Partida(factoria.creaReglas());
						}

						// Si se introduce una sola dimension para el juego Gravity
						else { 
							throw new ParseException("En el juego Gravity hay que introducir 2 dimensiones, 'x' e 'y'");
						}
					}
					
					// Si se elige REVERSI
					else if (juego.equalsIgnoreCase("rv")) {
						factoria = new FactoriaJuegoReversi();
						partida = new Partida(factoria.creaReglas());
					}
					

					// Si no es ningun juego existente
					else
						throw new ParseException("Juego '" + juego + "' incorrecto.");
				}
				
				// Interfaz
				 if (cmdLine.hasOption("u")) {
					interfaz = cmdLine.getOptionValue("u");
					
					// Si se elige consola
					if (interfaz.equalsIgnoreCase("console")) {
						ControlConsola control = new ControlConsola(partida,factoria, sc);
						final VistaConsola vista = new VistaConsola(sc, control);
						vista.run();
					}
					
					// Si se elige ventana
					else if (interfaz.equalsIgnoreCase("window")) {
						ControlSwing controlGrafica = new ControlSwing(partida, factoria);
						final VistaSwing vistaGrafica = new VistaSwing(controlGrafica);
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								vistaGrafica.setVisible(true);
							}
						});
					}
					
					// Si no es ninguna interfaz existente
					else 
						throw new ParseException("Interfaz '" + interfaz + "' incorrecta.");
				}
				 
				 else if (!cmdLine.hasOption("u")) { // Por defecto, interfaz consola
						ControlConsola control = new ControlConsola(partida,factoria, sc);
						final VistaConsola vista = new VistaConsola(sc, control);
						vista.run();
				 }
			}
			
			// Si hay argumentos que no existen			
			else {
				String noEntendidos = "";
				for (int i = 0; i < argsNoEntendidos.length; i++)
					noEntendidos += argsNoEntendidos[i] + " ";
				throw new ParseException("Argumentos no entendidos: " + noEntendidos);
			}
			
		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("Uso incorrecto: " + e.getMessage());
			System.err.println("Use -h|--help para mas detalles.");
			System.exit(-1); // Para salir
		}
	}
}
