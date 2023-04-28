package uabc.iec.examen1;

public class Carta {

    private int valor;
    private int mano;
    public int C=1,D=2,E=3,T=4;

    public Carta(int v, int m){
        valor = v;
        mano = m;
    }

    public int getValor() {
        return valor;
    }

    public int getMano(){
        return mano;
    }

}
