package uabc.iec.examen1;

import java.util.ArrayList;

public class Jugador {

    private int puntos;
    private String nombre;
    private ArrayList<Carta> cartas;

    public Jugador(String n){
        puntos = 0;
        nombre = n;
        cartas = new ArrayList<Carta>();
    }

    public int getPuntos(){
        return puntos;
    }

    public int getPuntosParciales() {
        int puntosCartaUltima = 0;
        boolean as = false;
        if(cartas.get(cartas.size()-1).getValor() > 10)
            puntosCartaUltima = 10;
        else
            puntosCartaUltima = cartas.get(cartas.size()-1).getValor();

        return puntos - puntosCartaUltima;
    }

    public ArrayList<Carta> getCartas(){ return cartas; }

    public String getNombre(){ return nombre; }

    public void agregarCarta(Carta cartaNueva){
        cartas.add(cartaNueva);
    }

    public void calcularPuntos(){
        puntos = 0;
        int as = 0;
        for (Carta c: cartas) {
            if(c.getValor()>10)
                puntos += 10;
            else if (c.getValor()==1)
                as += 1;
            else
                puntos += c.getValor();

            if(as>0)
                for(int i=0;i<as;i++)
                    if(puntos+11<=21)
                        puntos+=11;
                    else
                        puntos+=1;
        }
    }

}
