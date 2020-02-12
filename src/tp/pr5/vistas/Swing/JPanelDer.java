package tp.pr5.vistas.Swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tp.pr5.control.ControlSwing;
import tp.pr5.jugadores.TipoTurno;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;


public class JPanelDer extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;

	private ControlSwing control;
	private VistaSwing vtnPrincipal;

	// PANELES
	private JPanel pnlNorte;
	private JPanel pnlPart;
	private JPanel pnlJugadores;
	private JPanel pnlCambioJ;
	private JPanel pnlAceptoCambio;
	private JPanel pnlSalir;
	private JPanel pnlGravity;

	// BOTONES
	private JButton btnUndo;
	private JButton btnReset;
	private JButton btnCambiar;
	private JButton btnSalir;

	// COMBOBOX
	JComboBox<TipoTurno> cboJugBlancas;
	JComboBox<TipoTurno> cboJugNegras;
	JComboBox<TipoJuego> cboJuego;

	// ETIQUETAS
	private JLabel jNegras;
	private JLabel jBlancas;
	private JLabel filas;
	private JLabel cols;

	// TEXTOS DE ENTRADA
	private JTextField txtF;
	private JTextField txtC;

	public JPanelDer(VistaSwing ventana, ControlSwing c) {
		control = c;
		vtnPrincipal = ventana;
		
		Dimension dimBtn = new Dimension (150,50); // Dimension estandar para los botones
		Dimension dimTxt = new Dimension (50,20);  // Dimension estandar para los cuadros de texto de entrada
		
		setLayout(new BorderLayout()); // Creamos el borderLayout para poner los paneles en las diferentes zonas
	
		/** NORTE VENTANA
		 * 
		 * Contiene: panel de partida (pnlPart) y panel de gesti�n de jugadores (pnlJugadores)
		 * Va a constar de un borderLayout para situar los dos paneles que contiene  en dos zonas distinas
		 * 
		 */
		pnlNorte = new JPanel();  
		this.add(pnlNorte, BorderLayout.NORTH);  
		pnlNorte.setLayout(new BorderLayout());   
		
		/**NORTE PANEL NORTE
         *
		 * Contiene: bot�n deshacer (btnUndo) y bot�n reiniciar (btnReset)
		 */
		// CREACION PANEL PARTIDA
		pnlPart = new JPanel();
		pnlPart.setBorder(BorderFactory.createTitledBorder("Partida")); 
		
		//COMPONENTES PANEL PARTIDA
		pnlPart.setLayout(new FlowLayout()); // FlowLayout para que parezcan uno al lado del otro, sin estar pegados
		
		btnUndo = new JButton(new ImageIcon(getClass().getResource("iconos/undo.png"))); // Creamos el boton DESHACER
		btnUndo.setText("Deshacer");
		btnUndo.setPreferredSize(dimBtn); // Le a�adimos la dimesion por defecto
		btnUndo.setEnabled(false); // Deshabilitamos el boton de deshacer al comienzo
        btnUndo.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.undo();
	    	}
    	});
        
		btnReset = new JButton(new ImageIcon(getClass().getResource("iconos/reiniciar.png"))); // Creamos el boton REINICIAR
		btnReset.setText("Reiniciar");  
		btnReset.setPreferredSize(dimBtn); // Le a�adimos una dimension por defecto
		btnReset.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.reiniciar();
	    	}
    	});

		// A�adimos ambos al panel de partida
		pnlPart.add(btnUndo); 
		pnlPart.add(btnReset);
		pnlNorte.add(pnlPart, BorderLayout.NORTH); // A�adimos el panel partida al norte del panel norte
				
		/**SUR PANEL NORTE
		 * 
		 * Contiene: las dos etiquetas con jugador blancas y negras y sus respectivos comboBox
		 * 
		 * Creamos el panel de gesti�n de jugadores
		 * 
		 */
		pnlJugadores = new JPanel();
		
		pnlJugadores.setBorder(BorderFactory.createTitledBorder("Gestion de jugadores")); 
		this.add(pnlJugadores, BorderLayout.AFTER_LAST_LINE); // Situacion: zona centro
		pnlJugadores.setLayout(new GridLayout(2, 2));
		
		// COMPONENTES DEL PANEL DE GESTION DE JUGADORES
		jBlancas = new JLabel ("Jugador de blancas");
		cboJugBlancas = new JComboBox<TipoTurno>(TipoTurno.values()); // Los juegos que se tienen que mostrar
		jNegras = new JLabel ("Jugador de negras");
		cboJugNegras = new JComboBox<TipoTurno>(TipoTurno.values());
		
		pnlJugadores.add(jBlancas); 
		pnlJugadores.add(cboJugBlancas);
		
		cboJugBlancas.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if  (((TipoTurno)cboJugBlancas.getSelectedItem()) == TipoTurno.AUTOMATICO) 
	    		    	 vtnPrincipal.pongoModoJuego(Ficha.BLANCA, TipoTurno.AUTOMATICO);
	    		    
	    		    else 
	    		    	vtnPrincipal.pongoModoJuego(Ficha.BLANCA, TipoTurno.HUMANO);
	    	}
    	});
		
		pnlJugadores.add(jNegras); 
		pnlJugadores.add(cboJugNegras); 
		
		cboJugNegras.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if  (((TipoTurno)cboJugNegras.getSelectedItem()) == TipoTurno.AUTOMATICO) 
	    		    	 vtnPrincipal.pongoModoJuego(Ficha.NEGRA, TipoTurno.AUTOMATICO);
	    		    
	    		    else 
	    		    	vtnPrincipal.pongoModoJuego(Ficha.NEGRA, TipoTurno.HUMANO);
	    	}
    	});
		
		pnlNorte.add(pnlJugadores, BorderLayout.SOUTH); // A�adimos el panel de gestion de jugadres al sur del panel norte

		/**CENTRO VENTANA
		 * 
		 * Contiene: panel cambio de juego con un desplegable con todos los juegos y un panel 
		 * con un bot�n para la confirmaci�n del cambio solicitado por el usuario
		 * 
		 */
		// CREACION PANEL CAMBIO
		pnlCambioJ = new JPanel();
	
		pnlCambioJ.setLayout(new GridLayout(3, 1)); // Construye un dise�o frontera entre los componentes especificados
		pnlCambioJ.setBorder(BorderFactory.createTitledBorder("Cambio de Juego")); 
		this.add(pnlCambioJ, BorderLayout.CENTER); // Situacion: zona centro ventana
		
		// COMPONENTES
		cboJuego = new JComboBox<TipoJuego>(TipoJuego.values()); // Los juegos que se tienen que mostrar
		pnlCambioJ.add(cboJuego); // Insertamos el comboBox en el panel

		pnlGravity = new JPanel(); // Panel con las etiquetas y y cuadros de texto para insertar los n�meros
		
		pnlGravity.setLayout(new FlowLayout());
		
		filas = new JLabel("Filas"); // Creaci�n de la etiqueta Filas
		cols = new JLabel("Columnas"); // Creaci�n de la etiqueta Columnas
		
		txtF = new JTextField(); 
		txtF.setPreferredSize(dimTxt);
		txtC = new JTextField();
		txtC.setPreferredSize(dimTxt);
		
		pnlGravity.add(filas);
		pnlGravity.add(txtF);
		pnlGravity.add(cols);
		pnlGravity.add(txtC);
		
		pnlGravity.setVisible(false); // Inicializaci�n
		
		pnlCambioJ.add(pnlGravity); // Se a�de en el panel
		
		cboJuego.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if (TipoJuego.esRedimensionable((TipoJuego) cboJuego.getSelectedItem()))
	    		    	pnlGravity.setVisible(true); // Se muestran los cuadros de texto para filas y columnas
	    		    else
	    		    	pnlGravity.setVisible(false);
	    	}
    	});
		
		pnlAceptoCambio = new JPanel(); // Panel para introducir el boton de cambiar
		
		btnCambiar = new JButton(new ImageIcon(getClass().getResource("iconos/aceptar.png"))); // Creamos el boton Cambiar
		btnCambiar.setText("Cambiar"); 
		btnCambiar.setPreferredSize(dimBtn); // Le a�adimos una dimension por defecto
		btnCambiar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (TipoJuego.esRedimensionable((TipoJuego) cboJuego.getSelectedItem())) {
	    			int nFi, nCols;
	    			try {
	    				nFi = Integer.parseInt(txtF.getText());
	    				nCols = Integer.parseInt(txtC.getText());
	    			} catch (NumberFormatException exc) {
	    				nFi = 0;
	    				nCols = 0;
	    			}
	    			
	    			if ((nFi > 0) && (nCols > 0))
	    		    	control.reset((TipoJuego) cboJuego.getSelectedItem(), nFi, nCols);
	    			else // Dimensiones por defecto para Gravity
	    				control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);
	    		}
	    		
	    		else
	    			control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);	  
	    	}
    	});
		pnlAceptoCambio.add(btnCambiar); // Se a�ade el boton Cambiar al panel de aceptar el cambio
		pnlCambioJ.add(pnlAceptoCambio); // Insertamos un panel en otro
		
		setBorder(BorderFactory.createEmptyBorder(5,20,5,0)); // Fuera de los paneles: alto, izquierda, abajo, derecha
		
		/**SUR VENTANA
	     *
		 * Contiene: panel salir con el bot�n salir, para finalizar la jugada y cerrar la aplicaci�n
		 * 
		 */
		pnlSalir = new JPanel();
		pnlSalir.setBorder(BorderFactory.createEmptyBorder(40,30,0,20)); // Fuera del panel: alto, izquierda, abajo, derecha
		
		btnSalir = new JButton(new ImageIcon(getClass().getResource("iconos/exit.png"))); // Creacion del boton Salir
		btnSalir.setText("Salir"); 
		btnSalir.setPreferredSize(dimBtn); // Le a�adimos una dimension por defecto
		btnSalir.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
    		    quit();
    	}
		});
		pnlSalir.add(btnSalir);
		this.add(pnlSalir, BorderLayout.SOUTH); // Situacion: zona sur	
	}

	/**
	 * La partida notifica a los observadores que ha terminado la partida
	 * llamando a este m�todo
	 * 
	 * @param tabFin : tablero final de la partida
	 * @param ganador
	 */
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	btnUndo.setEnabled(false); // Se deshabilita el boton deshacer
		    }
		});
	}
 
	/**
	 * Notifica a los observadores que se ha iniciado la ejecuci�n de un
	 * movimiento
	 * 
	 * @param turno
	 */
	public void onMovimientoStart(Ficha turno) { 
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	cboJugBlancas.setEnabled(false);
				cboJugNegras.setEnabled(false);
				btnUndo.setEnabled(false);
		    }
		  });
	}

	/**
	 * Notifica a los obsrvadores que se ha terminado de realizar un movimiento
	 * 
	 * @param tab : tablero
	 */
	public void onMovimientoEnd(TableroInmutable tab, Ficha turnoAnterior, Ficha turnoSiguiente) {
		
		vtnPrincipal.terminarModo(turnoAnterior); // Termina la Hebra
		vtnPrincipal.comenzarModo(turnoSiguiente); // Comienza la Hebra
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	
				cboJugBlancas.setEnabled(true);
				cboJugNegras.setEnabled(true);
				btnUndo.setEnabled(true); // Se habilita el boton deshacer
		    }
		});
		
	}

	/**
	 * Notifica a los observadores que se ha deshecho un movimiento. Proporciona
	 * el estado final del tablero y si hay mas de un movimiento a deshacer o no
	 * 
	 * @param tab : tablero
	 * @param hayMas : true si hay m�s movimientos para deshacer
	 */
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		
		if (hayMas) { // Si se pueden deshacer mas movimientos, se habilita el boton deshacer
			btnUndo.setEnabled(true);
			vtnPrincipal.deshacerModo(turno);
		}

		else {
			btnUndo.setEnabled(false);
			vtnPrincipal.comenzarModo(turno);
		}
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
		
		vtnPrincipal.terminarModo(Ficha.BLANCA); // Terminan con ambas hebras
		vtnPrincipal.terminarModo(Ficha.NEGRA);
		
		vtnPrincipal.inicializoModoJuego(); // Inicializaci�n de ambos jugadores a humanos
		
		cboJugBlancas.setSelectedItem(TipoTurno.HUMANO); // Inicializamos los combos con Humanos
		cboJugNegras.setSelectedItem(TipoTurno.HUMANO);
		
		
		cboJugBlancas.setEnabled(true);
		cboJugNegras.setEnabled(true); // Se habilitan los modos de Juego
		btnUndo.setEnabled(false); // Se deshabilita el boton deshacer
	}
	
	/**
	 * Notifica a los observadores que una operaci�n deshacer no ha tenido �xito
	 */
	public void onUndoNotPossible(Ficha turno) {
		
		btnUndo.setEnabled(false);
		vtnPrincipal.comenzarModo(turno);
	}

	public void onCambioTurno(Ficha turno) { }	

	public void onMovimientoIncorrecto(String explicacion) { }

	/**
	 * Notifica a los observadores que se ha cambiado de juego
	 * 
	 * @param tab : tablero
	 * @param turno
	 */
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		
		vtnPrincipal.terminarModo(Ficha.BLANCA); // Terminan ambas hebras
		vtnPrincipal.terminarModo(Ficha.NEGRA);
		
		vtnPrincipal.inicializoModoJuego(); // Inicializaci�n de ambos jugadores a humanos
		
		cboJugBlancas.setSelectedItem(TipoTurno.HUMANO); // Inicializamos los combos con Humanos
		cboJugNegras.setSelectedItem(TipoTurno.HUMANO);
		
		cboJugBlancas.setEnabled(true); // Se habilitan los modos de juego
		cboJugNegras.setEnabled(true);
		btnUndo.setEnabled(false); 
	}

	/**
	 * Ventana para salir
	 */
	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"�Estas seguro de que deseas salir?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (n == 0) {
			System.exit(0);
		}
	}
}
