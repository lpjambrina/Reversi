package tp.pr5.logica;

import tp.pr5.movimientos.Movimiento;

public class Pila {
	private Movimiento[] undo;
	private int numUndo;
	public static final int MAX_PILA = 10;

	public Pila() {
		this.undo = new Movimiento[MAX_PILA];
		this.numUndo = 0;
	}

	/**
	 * Indica si la pila est� llena
	 * 
	 * @return true si el n�mero de elementos de la pila (numUndo) es igual que el m�ximo (MAX_PILA)
	 */
	public boolean estaLlena() {

		return this.numUndo == MAX_PILA;
	}
	
	/**
	 * Devuelve el n�mero de elementos de la pila
	 * @return numUndo
	 */
	public int getNumUndo() {
		
		return numUndo;
	}
	
	/**
	 * Indica si la pila est� vac�a
	 * 
	 * @return true si el n�mero de elementos (numUndo) es cero
	 */
	public boolean hayMovimientos() throws PilaVacia {
		boolean hayMov = true;
		
		if (this.numUndo == 0) {
			hayMov = false;
			throw new PilaVacia();
		}
		
		return hayMov;
	}

	/**
	 * M�todo para apilar movimientos. Introduce en el array pila las posiciones donde se han hecho los �ltimos diez
	 * movimientos. Si la pila ya est� llena, todos los elementos se desplazan hacia la izquierda para borrar el
	 * primero que se insert� y el �ltimo se inserta al final.
	 * 
	 * @param columna : �ltimo movimiento para apilar
	 */
	public void apilar(Movimiento mov) {
		if (!estaLlena())
			undo[numUndo++] = mov;

		else {
			for (int i = 1; i < numUndo; i++)
				// Desplaza los elementos de la pila hacia la izquierda
				undo[i - 1] = undo[i];

			undo[numUndo - 1] = mov;
		}
	}

	/**
	 * Devuelve lo �ltimo que hay en la pila
	 * 
	 * @return undoStack[numUndo - 1] : �ltimo movimiento apilado
	 */
	public Movimiento obtenerUltimoMov() {

		return undo[numUndo - 1];
	}

	/**
	 * Una vez que se ha obtenido el �ltimo elemento de la pila, procedemos a borrarlo decrementando el contador
	 */
	public void desapilar() {

		numUndo--;
	}

	/**
	 * Al resetear el tablero, tambi�n hay que resetear la pila
	 */
	public void resetPila() {
		
		numUndo = 0;
	}
}
