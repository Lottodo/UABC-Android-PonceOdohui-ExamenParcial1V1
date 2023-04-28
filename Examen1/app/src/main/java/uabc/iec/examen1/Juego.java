package uabc.iec.examen1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Juego {

    private static Jugador jugadorPLY,jugadorCPU;
    public int maxPuntos;

    public Juego(String nombre, int maxP){
        jugadorCPU = new Jugador("Crupier (CPU)");
        jugadorPLY = new Jugador(nombre);
        maxPuntos = maxP;

        Random rand = new Random();
        for (int i=0;i<2;i++) jugadorCPU.agregarCarta(new Carta(rand.nextInt(13-1)+1,rand.nextInt(4-1)+1));
        for (int i=0;i<2;i++) jugadorPLY.agregarCarta(new Carta(rand.nextInt(13-1)+1,rand.nextInt(4-1)+1));

        jugadorCPU.calcularPuntos();
        jugadorPLY.calcularPuntos();
    }

    public Jugador getJugadorCPU() { return jugadorCPU; }
    public Jugador getJugadorPLY() { return jugadorPLY; }

    public void darCartaJugadorPLY() {
        Random rand = new Random();
        jugadorPLY.agregarCarta(new Carta(rand.nextInt(13-1)+1,rand.nextInt(4-1)+1));
        jugadorPLY.calcularPuntos();
        if(isCPUOpenToCards())
            darCartaJugadorCPU();

        if(jugadorCPU.getCartas().size()==5){
            while(isCPUOpenToCards())
                darCartaJugadorCPU();
        }
    }

    public boolean isCPUOpenToCards() {
        return jugadorCPU.getPuntos() <= maxPuntos*0.75;
    }
    public void darCartaJugadorCPU() {
        Random rand = new Random();
        jugadorCPU.agregarCarta(new Carta(rand.nextInt(13-1)+1,rand.nextInt(4-1)+1));
        jugadorCPU.calcularPuntos();
    }

    @NotNull
    public static ArrayList<Carta> getCartasJugadorPLY() { return jugadorPLY.getCartas(); }
    @NotNull
    public static ArrayList<Carta> getCartasJugadorCPU() { return jugadorCPU.getCartas(); }

    public int verificarGanador() {
        int EMPATE = 0, GANADOR = 1, PERDEDOR = -1, ERROR = -2;
        jugadorCPU.calcularPuntos();
        jugadorPLY.calcularPuntos();

        if (jugadorPLY.getPuntos() == jugadorCPU.getPuntos())
            return EMPATE;
        if (jugadorPLY.getPuntos() > maxPuntos && jugadorCPU.getPuntos() > maxPuntos)
            return EMPATE;
        if(jugadorPLY.getPuntos() > maxPuntos && jugadorCPU.getPuntos() <= maxPuntos)
            return PERDEDOR;
        if(jugadorPLY.getPuntos() < jugadorCPU.getPuntos())
            return PERDEDOR;
        if(jugadorPLY.getPuntos() <= maxPuntos && jugadorCPU.getPuntos() > maxPuntos)
            return GANADOR;
        if(jugadorPLY.getPuntos() > jugadorCPU.getPuntos())
            return GANADOR;
        return ERROR;
    }

}
