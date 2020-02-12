package tp.pr5.modosJuego;

import tp.pr5.control.ControlSwing;

public class HebraAutomatico extends Thread {
	ControlSwing ctrl;
	
	public HebraAutomatico(ControlSwing c) {
		this.ctrl = c;
	}
	
	/**
	 * Realiza un movimiento autom�tico
	 */
	public void run() {
		
		ctrl.ponerAutomatico();
	}

}
