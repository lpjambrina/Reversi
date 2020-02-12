package tp.pr5.modosJuego;

import tp.pr5.control.ControlSwing;

public class ModoJuegoAutomatico implements ModoJuego {
	private ControlSwing ctrl;
	private HebraAutomatico hebra;
	
	public ModoJuegoAutomatico(ControlSwing c) {
		this.ctrl = c; 
	}

	/**
	 * Inicia la hebra para el juego automático e invoca al método run()
	 */
	public void comenzar() {
		if (hebra == null) {
			hebra = new HebraAutomatico(ctrl);
			hebra.start();
		}
	}

	/**
	 * Si la hebra aún está activa, la interrumpe y asigna a NULL para crearla después	
	 */
	public void terminar() {
		
		if (hebra != null) {
			hebra.interrupt();
			hebra = null;	
		}
	}


	/**
	 * Deshace un movimiento
	 */
	public void deshacerPulsado() {
		ctrl.undo();		
	}	
}
