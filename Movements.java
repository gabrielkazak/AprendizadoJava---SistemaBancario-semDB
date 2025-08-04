package BancoSemDb;

import java.util.Date;

public class Movements {
    Date date;
    double value;
    String origin;
    String destiny;
    String type;

    public Movements(Date date, double value, String origin, String destiny, String type){
        this.date = date;
        this.value = value;
        this.origin = origin;
        this.destiny = destiny;
        this.type = type;
    }

    public Movements(Date date, double value, String origin,String type){
        this.date = date;
        this.value = value;
        this.origin = origin;
        this.type = type;
    }

    @Override
    public String toString() {
        String str = "[" + date + "] ";
        str += type + ": R$" + value;

        if (destiny != null) {
            str += " → Conta destino #" + destiny;
        }

        return str;
    }

}
