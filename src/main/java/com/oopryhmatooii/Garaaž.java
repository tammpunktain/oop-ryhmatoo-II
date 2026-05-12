package com.oopryhmatooii;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Garaaž {
    private ArrayList<Auto> autod;
    private String nimi;

    public Garaaž(String nimi) {
        if(nimi==null || nimi.trim().isEmpty()){
            this.nimi = "GARAAŽ";
        }else this.nimi=nimi;
        this.autod = new ArrayList<>();
    }

    /**
     * Lisab garaazi klassi Auto isendi
     * @param auto on klassi Auto isend
     */
    public void lisaAuto(Auto auto) {
        autod.add(auto);
    }

    /**
     * Kuvab autode nimekirja, mis on garaazi lisatud.
     */
    public void kuvaNimekiri() {
        if (autod.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < autod.size(); i++) {
            sb.append(i + 1).append(". ").append(autod.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Valib nimkirjast auto millega tegevusi teha
     * @param indeks on garaazi nimekirja järjekorranumber
     * @return tagastab klassi Auto isendi
     */
    public Auto valiAuto(int indeks) {
        if (indeks >= 1 && indeks <= autod.size()) {
            return autod.get(indeks - 1);
        }
        JOptionPane.showMessageDialog(null,"Vale indeks");
        return null;
    }


    /**
     * Eemaldab garaazist auto
     * @param indeks on garaazi nimekirja järjekorranumber
     */
    public void eemaldaAuto(int indeks) {
        if (indeks >= 0 && indeks < autod.size()) {
            autod.remove(indeks);
        }
    }
    public void salvestaFaili(String failinimi) throws Exception {
        try (PrintWriter pw = new PrintWriter(new File(failinimi))) {
            for (Auto a : autod) {
                pw.println(a.andmedReaks());
            }
        }
    }

    public int getAutodeArv() {
        return autod.size();
    }

    public String getNimi() {
        return nimi;
    }
    public ArrayList<Auto> getAutod() {
        return autod;
    }
}