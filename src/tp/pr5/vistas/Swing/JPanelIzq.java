package tp.pr5.vistas.Swing;

import java.awt.BorderLayout;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import tp.pr5.control.ControlSwing;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;

public class JPanelIzq extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;

	private ControlSwing control;
	private VistaSwing vtnPrincipal;
	
	// PANELES
	private JPanel pnlTab;
	private JPanel pnlFichas;
	private JPanel pnlTurno;
	private JPanel pnlRandom;
	
	// ETIQUETAS
	private JLabel lblTurno;
	
	// BOTONES
	private JButton btnRandom;
	private MiBoton btnFicha;
	
	MiBoton[][] botonesTab;
	
	public JPanelIzq (VistaSwing ventana, ControlSwing c) {
		vtnPrincipal = ventana;
		control = c;
		setLayout(new BorderLayout());
		
		/**
		 * CENTRO: Tablero
		 * 
		 * Contiene: panel Tablero con un panel con las fichas y un JLabel para
		 * poner el turno correspondiente
		 * 
		 */
		pnlTab = new JPanel();
		
		add(pnlTab, BorderLayout.CENTER);
		
		pnlTab.setLayout(new BorderLayout(0,40)); // Creamos otras zonas dentro de este panel, distanciadas
		pnlTab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		pnlTab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createEtchedBorder(BevelBorder.LOWERED)));

		pnlFichas = null;

		pnlTurno = new JPanel();
		pnlTurno.setBackground(Color.WHITE); // Pintamos el panel de color
		pnlTurno.setOpaque(true);
		pnlTab.add(pnlTurno, BorderLayout.SOUTH); // Situacion dentro de panel tablero: sur
		pnlTurno.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		pnlTurno.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createEtchedBorder(BevelBorder.LOWERED)));
		
		lblTurno = new JLabel("Juegan blancas"); // Creamos la etiqueta del turno
		lblTurno.setForeground(Color.BLUE); 
		pnlTurno.add(lblTurno);
		
		/**
		 * SUR: Poner Aleatorio
		 * 
		 * Contiene: panel con un JButton para crear un movimiento aleatorio
		 * 
		 */
		pnlRandom = new JPanel();
		
		add(pnlRandom, BorderLayout.SOUTH); // Situacion: sUR
		btnRandom = new JButton(new ImageIcon(getClass().getResource("iconos/random.png"))); // Creamos el boton para el movimiento aleatorio
		btnRandom.setText("Poner Aleatorio"); 
		btnRandom.setPreferredSize(new Dimension(175, 50)); // Dimension para el boton
		btnRandom.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.poner(); // Para poner aleatorio
	    	}
    	});
		pnlRandom.add(btnRandom);
		control.addObserver(this);
		
		c.reiniciar(); // Lo reiniciamos para colorear el tablero y poner la etiqueta
		
		setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); // Fuera del Panel: alto, izquierda, abajo, derecha
	}
	
	public class MiBoton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		// Clase para los botones de las fichas
		private int fila, columna;
		
		public MiBoton(int i, int j) {
			fila = i;
			columna = j;
		}

		public void actionPerformed(ActionEvent e) {
			control.poner(fila, columna);	
		}
		
		/**
		 * Devuelve la fila pulsada
		 * @return fila
		 */
		public int getFila() {
			
			return fila;
		}
		
		/**
		 * Devuelve la columna pulsada
		 * @return columna
		 */
		public int getColumna() {
			
			return columna;
		}
	}
	
	/**
	 * La partida notifica a los observadores que ha terminado la partida
	 * llamando a este método
	 * 
	 * @param tabFin : tablero final de la partida
	 * @param ganador
	 */
	public void onPartidaTerminada(final TableroInmutable tabFin, final Ficha ganador) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	for (int i = tabFin.getFilas() - 1; i >= 0; i--) { 
					for (int j = 0; j < tabFin.getColumnas(); j++) { 
						botonesTab[i][j].setEnabled(false); // Se deshabilitan los botones del tablero
					}
				}
				
				btnRandom.setEnabled(false); // Se deshabilita el boton aleatorio
			
				if (ganador == Ficha.BLANCA)
					lblTurno.setText("Ganan las blancas");

				else if (ganador == Ficha.NEGRA)
					lblTurno.setText("Ganan las negras");

				else
					lblTurno.setText("Partida en Tablas");
		
				dibujaTab(tabFin); // Dibuja el Tablero
		    }
		});
	}

	/**
	 * Notifica a los observadores que se ha iniciado la ejecución de un
	 * movimiento
	 * 
	 * @param turno
	 */
	public void onMovimientoStart(Ficha turno) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	btnRandom.setEnabled(false);
				
				for (int i = 0; i < botonesTab.length; i++)
					for (int j = 0; j < botonesTab[0].length; j++)
						botonesTab[i][j].setEnabled(false);
		    }
		  });
	
	}

	/**
	 * Notifica a los obsrvadores que se ha terminado de realizar un movimiento
	 * 
	 * @param tab : tablero
	 */
	public void onMovimientoEnd(final TableroInmutable tab, Ficha turnoAnterior, Ficha turnoSiguiente) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				btnRandom.setEnabled(true);
				dibujaTab(tab);
				
				for (int i = 0; i < botonesTab.length; i++)
					for (int j = 0; j < botonesTab[0].length; j++)
						botonesTab[i][j].setEnabled(true);
		    }
		  });
	}
	
	/**
	 * Notifica a los observadores que se ha deshecho un movimiento. Proporciona
	 * el estado final del tablero y si hay mas de un movimiento a deshacer o no
	 * 
	 * @param tab : tablero
	 * @param hayMas : true si hay más movimientos para deshacer
	 */
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		
		dibujaTab(tab);
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
		
		if (pnlFichas == null) 
			creaTablero(tabIni.getFilas(), tabIni.getColumnas());
		
		btnRandom.setEnabled(true); // Se habilita el poner Aleatorio
		
		for (int i = tabIni.getFilas() - 1; i >= 0; i--) { // Filas
			for (int j = 0; j < tabIni.getColumnas(); j++) { // Columnas
				botonesTab[i][j].setEnabled(true); // Se habilita el tablero
			}
		}
		
		dibujaTab(tabIni);
		estado(turno);
	}

	public void onUndoNotPossible(Ficha turno) { }
	
	/**
	 * Muestra el turno actual y solicita una jugada
	 * @param turno
	 */
	public void onCambioTurno(final Ficha turno) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	estado(turno);
		    }
		  });
	}

	public void onMovimientoIncorrecto(String explicacion) { }

	/**
	 * Notifica a los observadores que se ha cambiado de juego
	 * 
	 * @param tab : tablero
	 * @param turno
	 */	
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		int filas = tablero.getFilas(); 
		int cols = tablero.getColumnas(); 
		
		btnRandom.setEnabled(true);
		pnlTab.remove(pnlFichas); // Elimina lo creado
		creaTablero(filas, cols);
	    dibujaTab(tablero);
		pnlTab.revalidate();
		vtnPrincipal.pack(); // Redimensiona
		
		estado(turno); // Inicialización el panel del turno
	}
	
	/**
	 * Inserta en el panel del turno (pnlTurno) una etiqueta con el turno actual
	 * @param turno
	 */
	public void estado(Ficha turno) {
		if (turno == Ficha.BLANCA)
			lblTurno.setText("Juegan blancas");
		else 
			lblTurno.setText("Juegan negras");
	}
	
	/**
	 * Dibuja el tablero con el color de cada ficha
	 * @param tablero
	 */
	public void dibujaTab(TableroInmutable tablero) {
		int filas = tablero.getFilas();
		int columnas = tablero.getColumnas();
		
		for (int i = filas - 1; i >= 0; i--) { // Filas
			for (int j = 0; j < columnas; j++) { // Columnas
				
				if (tablero.getCasilla(i, j) == Ficha.VACIA)		
					botonesTab[i][j].setBackground(Color.LIGHT_GRAY);

				else if(tablero.getCasilla(i, j) == Ficha.NEGRA)
					botonesTab[i][j].setBackground(Color.BLACK);
				
				else if (tablero.getCasilla(i, j) == Ficha.BLANCA)
					botonesTab[i][j].setBackground(Color.WHITE);
				
				btnFicha.setOpaque(true);
			}
		}
	}
	
	/**
	 * Crea un array de botones para las fichas y las inicializa a VACIA
	 * @param filas
	 * @param columnas
	 */
	public void creaTablero(int filas, int columnas) {
		pnlFichas = new JPanel();

		pnlFichas.setLayout(new GridLayout(filas,columnas,1,1)); // Filas, columnas, grosores
		pnlFichas.setBackground(Color.DARK_GRAY); // Da color al interlineado de la cuadricula
		
		botonesTab = new MiBoton[filas][columnas];
		
		for (int i = filas - 1; i >= 0; i--) { // Filas
			for (int j = 0; j < columnas; j++) { // Columnas
				btnFicha = new MiBoton(i, j);
				btnFicha.addActionListener(btnFicha);	
				
				btnFicha.setBackground(Color.LIGHT_GRAY);
				btnFicha.setOpaque(true);
				pnlFichas.add(btnFicha);
				
				botonesTab[i][j] = btnFicha;
			}
		}
		
		pnlTab.add(pnlFichas, BorderLayout.CENTER); // Situacion dentro de Panel Tablero: Zona Centro
	}
}
