package tp.pr5.logica;

public enum TipoJuego {
	CONECTA4, COMPLICA, GRAVITY, REVERSI;
	
	/**
	 * El único juego para el que se pueden elegir la dimensiones del tablero es Gravity
	 * @param juego
	 * @return true -> Gravity
	 */
	public static boolean esRedimensionable(TipoJuego juego) {

		if (juego == TipoJuego.GRAVITY)
			return true;

		else
			return false;
	}
}