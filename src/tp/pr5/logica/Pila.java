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
	 * Indica si la pila está llena
	 * 
	 * @return true si el número de elementos de la pila (numUndo) es igual que el máximo (MAX_PILA)
	 */
	public boolean estaLlena() {

		return this.numUndo == MAX_PILA;
	}
	
	/**
	 * Devuelve el número de elementos de la pila
	 * @return numUndo
	 */
	public int getNumUndo() {
		
		return numUndo;
	}
	
	/**
	 * Indica si la pila está vacía
	 * 
	 * @return true si el número de elementos (numUndo) es cero
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
	 * Método para apilar movimientos. Introduce en el array pila las posiciones donde se han hecho los últimos diez
	 * movimientos. Si la pila ya está llena, todos los elementos se desplazan hacia la izquierda para borrar el
	 * primero que se insertó y el último se inserta al final.
	 * 
	 * @param columna : último movimiento para apilar
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
	 * Devuelve lo último que hay en la pila
	 * 
	 * @return undoStack[numUndo - 1] : último movimiento apilado
	 */
	public Movimiento obtenerUltimoMov() {

		return undo[numUndo - 1];
	}

	/**
	 * Una vez que se ha obtenido el último elemento de la pila, procedemos a borrarlo decrementando el contador
	 */
	public void desapilar() {

		numUndo--;
	}

	/**
	 * Al resetear el tablero, también hay que resetear la pila
	 */
	public void resetPila() {
		
		numUndo = 0;
	}
}
