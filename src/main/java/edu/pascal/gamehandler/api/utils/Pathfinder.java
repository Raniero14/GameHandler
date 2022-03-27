package edu.pascal.gamehandler.api.utils;

import java.util.Vector;

import static java.lang.Math.abs;

public class Pathfinder {

    //NORD = 0; SUD = 1
    //OVEST = 0; EST = 1
    public static Vector<String> TrovaStrada(int xAttuale, int yAttuale, int xArrivo, int yArrivo){

        Vector<String> percorso = new Vector<>();
        int orientamento = 0; int spostamentoY; int spostamentoX; boolean gira;
        //l'orientamento del robottino è verso Nord di default. Per evitare di dover gestire la direzione in modo approfondito,
        //dopo ogni mossa il robottino tornerà verso Nord.

        //region asse x

        spostamentoX = xAttuale - xArrivo;

        //controlla se deve girare
        if(spostamentoX != 0) {

            //se deve girare, in che direzione farlo?
            if (spostamentoX > 0) {
                gira = true;
            } else {
                gira = false;
            }

            if (gira) {
                percorso.add("giraSinistra");
            } else {
                percorso.add("giraDestra");
            }

            for(int i = 0; i < abs (spostamentoX); i++){
                percorso.add("sposta");
            }

            //finito il movimento, ritorna a guardare verso N
            if (gira) {
                percorso.add("giraDestra");
            } else {
                percorso.add("giraSinistra");
            }
        }

        //endregion

        //region asse y

        spostamentoY = yAttuale - yArrivo;

        //controlla se deve girare
        if(spostamentoY != 0) {

            //se deve girare, in che direzione farlo?
            if (spostamentoY > 0) {
                percorso.add("giraSinistra");
                percorso.add("giraSinistra");
            }

            for(int i = 0; i < abs (spostamentoY); i++){
                percorso.add("sposta");
            }

            if (spostamentoY > 0) {
                percorso.add("giraDestra");
                percorso.add("giraDestra");
            }
        }

        //endregion

        return percorso;
    }

}
