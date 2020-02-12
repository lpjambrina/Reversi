
package tp.pr5.movimientos;

import tp.pr5.juegos.ReglasReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoReversi extends Movimiento {
	public static final int MAX_DIRECCIONES = 8; 
	private int[] coordFila;
	private int[] coordCol;
	
	public MovimientoReversi(int fil, int col, Ficha tur) {
		super(fil, col, tur);
		this.coordFila = new int [MAX_DIRECCIONES];
		this.coordCol = new int [MAX_DIRECCIONES];
		inicializaCoordenadas();
	}
	
	/**
	 * Inicializa el array de las coordenadas de las direcciones
	 */
	private void inicializaCoordenadas() {
		
		for (int i = 0; i < MAX_DIRECCIONES; i++) {
			coordFila[i] = -1;
			coordCol[i] = -1;
		}
	}
	
	/**
	 * Gira todas las fichas flanquedas
	 * @param tab : tablero
	 * @param despF, despC : coordenadas para comprobar todas las direcciones
	 * @param pos : posición
	 */
	private void volteaFicha(Tablero tab, int despF, int despC, int pos) {
		int f = fila + despF;
		int c = columna + despC;
		boolean encontrado = false;

		while (!encontrado) {
			if (tab.getCasilla(f, c) == turno)
				encontrado = true;
			else {
				tab.ponerFicha(f, c, turno);
				f += despF;
				c += despC;
			}
		}
		
		// Guardamos la ultima coordenada a la que llega a voltear, en la pos que le pasemos
		coordFila[pos] = f;
		coordCol[pos] = c; 
	}
	
	/**
	 * Voltea las fichas de todas las direcciones en las que haya flanqueo
	 * @param tab : tablero
	 */
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {

		// VERTICAL ARRIBA: POS 0
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, 1, 0)) 
			volteaFicha(tab, 1, 0, 0);

		// VERTICAL ABAJO: POS 1
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, -1, 0)) 
			volteaFicha(tab, -1, 0, 1);

		// HORIZONTAL DERECHA: POS 2
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, 0, 1)) 
			volteaFicha(tab, 0, 1, 2);

		// HORIZONTAL IZQUIERDA: POS 3
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, 0, -1)) 
			volteaFicha(tab, 0, -1, 3);

		// DIAGONAL SUPERIOR IZQUIERDA: POS 4
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, 1, -1)) 
			volteaFicha(tab, 1, -1, 4);

		// DIAGONAL SUPERIOR DERECHA: POS 5
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, 1, 1)) 
			volteaFicha(tab, 1, 1, 5);
		
		// DIAGONAL INFERIOR IZQUIERDA: POS 6
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, -1, -1))
			volteaFicha(tab, -1, -1, 6);

		// DIAGONAL INFERIOR DERECHA: POS 7
		if (ReglasReversi.buscaFlanqueo(tab, this, turno, -1, 1)) 
			volteaFicha(tab, -1, 1, 7);
		
		tab.ponerFicha(fila, columna, turno); // Ponemos la ficha final que inserta el usuario
	}

	/**
	 * Cambia el color de todas las fichas que hay que deshacer
	 * @param tab : tablero
	 * @param despF, despC : coordenadas para moverse por todas las direcciones
	 * @param cambiarA : color al que cambiar las fichas
	 * @param pos : posición hasta la que hay que deshacer
	 */	
	private void cambiaColorUndo(Tablero tab, int despF, int despC, Ficha cambiarA, int pos) {
		int f = fila + despF;
		int c = columna + despC;
		
		while ((f != coordFila[pos]) || (c != coordCol[pos])) {
				tab.ponerFicha(f, c, cambiarA);
				f += despF;
				c += despC;		
		}
	}

	/**
	 * Cambia las fichas de todas las direcciones en las que hay que deshacer
	 * @param tab : tablero
	 */
	public void undo(Tablero tab) {
		Ficha cambiarA; // Color al que tienen que volver
		
		if (tab.getCasilla(fila, columna) == Ficha.BLANCA)
			cambiarA = Ficha.NEGRA;
		
		else
			cambiarA = Ficha.BLANCA;
		
		// VERTICAL ARRIBA: POS 0
		if ((coordFila[0] != -1) && (coordCol[0] != -1))
			cambiaColorUndo(tab, 1, 0, cambiarA, 0);
		
		// VERTICAL ABAJO: POS 1
		if ((coordFila[1] != -1) && (coordCol[1] != -1))
			cambiaColorUndo(tab, -1, 0, cambiarA, 1);	
		
		// HORIZONTAL DERECHA: POS 2
		if ((coordFila[2] != -1) && (coordCol[2] != -1))
			cambiaColorUndo(tab, 0, 1, cambiarA, 2);
		
		// HORIZONTAL IZQUIERDA: POS 3
		if ((coordFila[3] != -1) && (coordCol[3] != -1))
			cambiaColorUndo(tab, 0, -1, cambiarA, 3);
		
		// DIAGONAL SUPERIOR IZQUIERDA: POS 4
		if ((coordFila[4] != -1) && (coordCol[4] != -1))
			cambiaColorUndo(tab, 1, -1, cambiarA, 4);
	
		// DIAGONAL SUPERIOR DERECHA: POS 5
		if ((coordFila[5] != -1) && (coordCol[5] != -1))
			cambiaColorUndo(tab, 1, 1, cambiarA, 5);
	
		// DIAGONAL INFERIOR IZQUIERDA: POS 6
		if ((coordFila[6] != -1) && (coordCol[6] != -1))
			cambiaColorUndo(tab, -1, -1, cambiarA, 6);
		
		// DIAGONAL INFERIOR DERECHA: POS 7
		if ((coordFila[7] != -1) && (coordCol[7] != -1))
			cambiaColorUndo(tab, -1, 1, cambiarA, 7);
		
		tab.ponerFicha(fila, columna, Ficha.VACIA); // La ficha que teniamos en la pila se pone a VACIA
	}
	
	/**
	 * Devuelve el color del jugador actual
	 * 
	 * @return turno : BLANCA o NEGRA
	 */
	public Ficha getJugador() {
		
		return turno;
	}
	
	/**
	 * Devuelve la columna del movimiento
	 * 
	 * @return columna
	 */
	public int getColumna() {
		
		return columna;
	}
	
	/**
	 * Devuelve la fila del tablero
	 * 
	 * @return fila	
	 */
	public int getFila() {
		
		return fila;
	}
}
