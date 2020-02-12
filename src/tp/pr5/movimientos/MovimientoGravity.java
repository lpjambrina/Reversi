package tp.pr5.movimientos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoGravity extends MovimientoFamiliaC4 {

	/**
	 * Constructora que llama a la de MovimientoFamiliaC4
	 * @param fila, col : valores del movimiento
	 * @param turno : jugador actual
	 */
	public MovimientoGravity(int fila, int columna, Ficha turno) {
		super(fila, columna, turno);
	}

	/**
	 * Calcula la distancia de la ficha a los 4 bordes del tablero
	 * @param tab : tablero
	 */
	public void calculaDistancias(Tablero tab) {

		int arriba = tab.getFilas() - 1 - fila;
		int abajo = fila;
		int izquierda = columna;
		int derecha = tab.getColumnas() - 1 - columna;

		desplaza(tab, arriba, abajo, izquierda, derecha);
	}

	/**
	 * Desplaza la ficha hacia del borde más cercano
	 * @param tab : tablero
	 * @param arriba : distancia al borde superior
	 * @param abajo : distancia al borde inferior
	 * @param izquierda : distancia al borde izquierdo
	 * @param derecha : distancia al borde derecho
	 */
	private void desplaza(Tablero tab, int arriba, int abajo, int izquierda, int derecha) {
		int filaAux, colAux;

		// Diagonal superior izquierda 
		if ((arriba == izquierda) && (arriba != 0) && (arriba < derecha) && (arriba < abajo)) {
			filaAux = tab.getFilas() - 1;
			colAux = 0;
			
			while ((filaAux > fila) && (colAux < columna)
					&& !tab.fichaVacia(filaAux, colAux)) {
				filaAux--; // Devuelve la fila
				colAux++;  // Devuelve la columna
			}
			
			fila = filaAux;
			columna = colAux;
		}

		// Diagonal superior derecha 
		if ((arriba == derecha) && (arriba != 0) && (arriba < izquierda) && (arriba < abajo)) {
			filaAux = tab.getFilas() - 1;
			colAux = tab.getColumnas() - 1;
			
			while ((filaAux > fila) && (colAux > columna) && !tab.fichaVacia(filaAux, colAux)) {
				filaAux--; // Devuelve la fila
				colAux--;  // Devuelve la columna
			}

			fila = filaAux;
			columna = colAux;
		}

		// Diagonal inferior izquierda 
		if ((abajo == izquierda) && (abajo != 0) && (abajo < derecha) && (abajo < arriba)) {
			filaAux = 0;
			colAux = 0;
			
			while ((filaAux < fila) && (colAux < columna) && !tab.fichaVacia(filaAux, colAux)) {
				filaAux++; // Devuelve la fila
				colAux++;  // Devuelve la columna
			}

			fila = filaAux;
			columna = colAux;
		}

		// Diagonal inferior derecha 
		if ((abajo == derecha) && (abajo != 0) && (abajo < izquierda) && (abajo < arriba)) {
			filaAux = 0;
			colAux = tab.getColumnas() - 1;
			 
			while ((filaAux < fila) && (colAux > columna) && !tab.fichaVacia(filaAux, colAux)) {
				filaAux++; // Devuelve la fila
				colAux--;  // Devuelve la columna
			}

			fila = filaAux;
			columna = colAux;
		}

		// Arriba 
		if ((arriba != 0) && (arriba < abajo) && (arriba < izquierda) && (arriba < derecha)) {
			filaAux = tab.getFilas() - 1;
			
			while (filaAux > fila && !tab.fichaVacia(filaAux, columna)) 
				filaAux--; 

			// Solo devuelve fila, la columna permanece constante
			fila = filaAux;

		}

		// Abajo 
		if ((abajo != 0) && (abajo < arriba) && (abajo < izquierda) && (abajo < derecha)) {
			filaAux = 0;
			
			while (filaAux < fila && !tab.fichaVacia(filaAux, columna)) 
				filaAux++; 

			// Solo devuelve fila, la columna permanece constante
			fila = filaAux;
		}

		// Izquierda 
		if ((izquierda != 0) && (izquierda < derecha) && (izquierda < arriba) && (izquierda < abajo)) {
			colAux = 0;
			
			while (colAux < columna && !tab.fichaVacia(fila, colAux)) 
				colAux++; 

			// Solo devuelve columna, la fila permanece constante
			columna = colAux;
		}

		// Derecha 
		if ((derecha != 0) && (derecha < izquierda) && (derecha < arriba) && (derecha < abajo)) {
			colAux = tab.getColumnas() - 1;
			
			while (colAux > columna && !tab.fichaVacia(fila, colAux)) 
				colAux--; 

			// Solo devuelve columna, la fila permanece constante
			columna = colAux;
		}
	}

	/**
	 * Ejecuta el movimiento de Gravity desplazando la ficha al borde más cernano. Produce una excepción si el 
	 * movimiento no es válido
	 * @param tab : tablero
	 */
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		
		calculaDistancias(tab);
		tab.ponerFicha(fila, columna, turno);
	}

	/**
	 * Deshace un movimiento poniendo la casilla VACIA
	 * @param tab : tablero
	 */
	public void undo(Tablero tab) {
		
		tab.ponerFichaVacia(fila, columna);
	}
}
