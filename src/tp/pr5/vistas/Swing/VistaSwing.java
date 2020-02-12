package tp.pr5.vistas.Swing;

import java.awt.GridLayout;

import javax.swing.JFrame;

import tp.pr5.control.ControlSwing;
import tp.pr5.jugadores.TipoTurno;
import tp.pr5.logica.Ficha;
import tp.pr5.modosJuego.ModoJuego;
import tp.pr5.modosJuego.ModoJuegoAutomatico;
import tp.pr5.modosJuego.ModoJuegoHumano;

public class VistaSwing extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ControlSwing control;
	private JPanelDer pnlDer;
	private JPanelIzq pnlIzq;
	
	
	private ModoJuego modoBlancas;
	private ModoJuego modoNegras;
	
	
	public VistaSwing(ControlSwing c) {
		super("Practica 4 - TP");
		this.control = c;
		
		inicializoModoJuego();
		
		this.setLayout(new GridLayout(1,2,0,0));
		
		// PANEL IZQUIERDO
		pnlIzq = new JPanelIzq(this, c); // Creacion del panel
		this.add(pnlIzq);
		
		// PANEL DERECHO
		pnlDer = new JPanelDer(this, c); // Creacion del panel
		this.add(pnlDer);
		control.addObserver(pnlDer);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void inicializoModoJuego() {
		
		modoBlancas = new ModoJuegoHumano();
		modoNegras = new ModoJuegoHumano();
	}
	
	public void pongoModoJuego(Ficha color, TipoTurno modo) {
		if (color == Ficha.BLANCA && modo == TipoTurno.AUTOMATICO)
			modoBlancas = new ModoJuegoAutomatico(control);
		
		if (color == Ficha.BLANCA && modo == TipoTurno.HUMANO)
			modoBlancas = new ModoJuegoHumano();
		
		else if (color == Ficha.NEGRA && modo == TipoTurno.AUTOMATICO)
			modoNegras = new ModoJuegoAutomatico(control);
		
		else if (color == Ficha.NEGRA && modo == TipoTurno.HUMANO)
			modoNegras = new ModoJuegoHumano();
	}
	
	
	public void terminarModo(Ficha turno) {
		
		if (turno == Ficha.BLANCA)
			modoBlancas.terminar();
		else 
			modoNegras.terminar();
	}
	
	public void comenzarModo(Ficha turno) {
		
		if (turno == Ficha.BLANCA)
			modoBlancas.comenzar();
		else 
			modoNegras.comenzar();
		
	}
	
	public void deshacerModo(Ficha turno) {
		
		if (turno == Ficha.BLANCA)
			modoBlancas.deshacerPulsado();
		else
			modoNegras.deshacerPulsado();
	}

	
}
