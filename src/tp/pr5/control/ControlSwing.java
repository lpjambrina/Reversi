package tp.pr5.control;
 
import tp.pr5.control.factorias.*;
import tp.pr5.jugadores.Jugador;
import tp.pr5.logica.*;
import tp.pr5.movimientos.Movimiento;

public class ControlSwing {
    private Partida partida;
    private FactoriaJuego factoria;
    private Jugador JugMovAleatorio;
     
    public ControlSwing(Partida p, FactoriaJuego factoria) {
        this.partida = p; 
        this.factoria = factoria;
        JugMovAleatorio = factoria.creaJugadorAleatorio();
    }
     
    /**
     * Pone de forma aleatorioa. Obtiene un movimiento despuñes de la creación de 
     * un jugador aleatorio y ejecuta una jugada con el movimiento obtenido
     * 
     */
    public void poner() {
    	Movimiento mov; 
    	
    	// Se crea un jugador aleatorio para hacer el movimiento
		mov = partida.creaMov(factoria, JugMovAleatorio); 
		partida.ejecutaMovimiento(mov);
    }
    
    /**
     * Para poner una ficha en las coordenadas indicadas por el usuario con el ratón.
     */ 
    public void poner(int fila, int col) {
        Movimiento mov = factoria.creaMovimiento(fila, col, partida.getTurno());
        partida.ejecutaMovimiento(mov);         
    }
    
    /**
     * Realiza un movimiento automático a través de la hebra ModoJuegoAutomatico
     */
    public void ponerAutomatico() {
    	
    	Movimiento mov = partida.getMovAutomatico(factoria, JugMovAleatorio);
    	if(mov != null)
    	partida.ejecutaMovimiento(mov);    	
    }
    
    /**
     * Reinicia la partida (en un mismo juego)
     */
	public void reiniciar() {
		
		partida.reset();
	}
     
	/**
	 * Deshace un movimiento
	 */
	public void undo() {
		
		partida.deshacer();
	}
	
	/**
	 * Cambio de juego
	 * @param t : juego al que se quiere cambiar
	 * @param filas, cols : tamaño del tablero de Gravity elegido por el usuario
	 */
    public void reset(TipoJuego t, int filas, int cols) {
    	
    	if (t == TipoJuego.CONECTA4)
			this.factoria =  new FactoriaJuegoConecta4();
		else if (t == TipoJuego.COMPLICA)
			this.factoria =  new FactoriaJuegoComplica();
		else if (t == TipoJuego.GRAVITY) {
			if ((filas > 0) && (cols > 0))
				this.factoria = new FactoriaJuegoGravity(filas, cols);
			else // dimension por defecto 10x10
				this.factoria = new FactoriaJuegoGravity();
		}
		else
			this.factoria = new FactoriaJuegoReversi();
				
    	JugMovAleatorio = factoria.creaJugadorAleatorio();
    	partida.cambioJuego(factoria.creaReglas()); 
    }
    

	/**
	 * Añade observadores
	 */
	public void addObserver(Observador o) {
		
		partida.addObserver(o);
	}
    
	/**
	 * Elimina los observadores
	 */
	public void removeObserver(Observador o) {
		
		partida.removeObserver(o);
	} 
}