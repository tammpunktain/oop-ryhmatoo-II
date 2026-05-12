package com.oopryhmatooii;

public class Auto {
    private String mark;
    private String mudel;
    private int aasta;
    private int labiSoit;
    private double kytust; //liitrites
    private double kytusekulu; // L/100km
    private boolean onKatki = false;

    public Auto(String mark, String mudel, int aasta, int labiSoit, double kytust, double kytusekulu, boolean onKatki) {
        this.mark = mark;
        this.mudel = mudel;
        this.aasta = aasta;
        this.labiSoit = labiSoit;
        this.kytust = kytust;
        this.kytusekulu = kytusekulu;
        this.onKatki=onKatki;
    }
    //Auto lisamisel auto ikka töötab
    public Auto(String mark, String mudel, int aasta, int labiSoit, double kytust, double kytusekulu) {
        this(mark, mudel, aasta, labiSoit, kytust, kytusekulu, false);
    }


    /**
     * Simuleerib auto sõitmist
     * @param km näitab mitu kilomeetrit auto peab sõitma
     * @return näitab kas auto oli võimeline ilma vigadeta sõitma
     */
    public boolean soida(int km) {
        if (onKatki) {
            return false;
        }
        double kulub = kytusekulu * (km / 100.0);
        if (kulub > kytust) {
            return false;
        }
        labiSoit += km;
        kytust -= kulub;

        double risk = Math.min(0.5, km / 100.0 * 0.15);
        if (Math.random() < risk) {
            onKatki = true;
        }
        return true;
    }

    /**
     * Tangib autot
     * @param liitrid näitab mitu liitrit peab lisama auto kütusekogusele.
     */
    public void tangi(double liitrid) throws IllegalArgumentException{
        if(onKatki) {
            return;
        }
        if (liitrid <= 0) {
            throw new IllegalArgumentException("Peab pos olema!");
        }
        kytust += liitrid;
    }

    /**
     * remondib auto
     *
     * @return
     */
    public boolean remondi() {
        if (!onKatki) {
            return false;
        }
        onKatki = false;
        return true;
    }

    /**
     * Auto andmed
     * @return tagastab auto kõik auto andmed
     */
    @Override
    public String toString() {
        return "Mark: " + mark +
                ", mudel: " + mudel +
                ", aasta: " + aasta +
                ", läbisõit: " + labiSoit + " km" +
                ", kütust paagis: " + String.format("%.2f", kytust) + " L" +
                ", kütusekulu: " + String.format("%.2f", kytusekulu) + " L/100 km";
    }
    public String andmedReaks() {
        return mark + ";" + mudel + ";" + aasta + ";" + labiSoit + ";" + kytust + ";" + kytusekulu + ";" + onKatki;
    }

    /**
     * Kas auto on katki
     * @return tagastab onKatki väärtuse
     */
    public boolean isOnKatki() {
        return onKatki;
    }

    public double getKytust() {
        return kytust;
    }

    public double getKytusekulu() {
        return kytusekulu;
    }

    public String getMark() {
        return mark;
    }

    public String getMudel() {
        return mudel;
    }

    public int getLabiSoit() {
        return labiSoit;
    }


}