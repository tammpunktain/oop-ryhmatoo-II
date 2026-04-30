package com.oopryhmatooii;


public class Auto {
    private String mark;
    private String mudel;
    private int aasta;
    private int labiSoit;
    private double kytust; //liitrites
    private double kytusekulu; // L/100km
    private boolean onKatki = false;

    public Auto(String mark, String mudel, int aasta, int labiSoit, double kytust, double kytusekulu) {
        this.mark = mark;
        this.mudel = mudel;
        this.aasta = aasta;
        this.labiSoit = labiSoit;
        this.kytust = kytust;
        this.kytusekulu = kytusekulu;
    }


    /**
     * Simuleerib auto sõitmist
     * @param km näitab mitu kilomeetrit auto peab sõitma
     * @return näitab kas auto oli võimeline ilma vigadeta sõitma
     */
    public void soida(int km) {
        if (onKatki) {
            throw new IllegalStateException("Auto on katki");
        }
        double kulub = kytusekulu * (km / 100.0);
        if (kulub > kytust) {
            throw new IllegalStateException("Pole piisavalt kytust");
        }

        labiSoit += km;
        kytust -= kulub;
        double risk = Math.min(0.5, km / 100.0 * 0.15);

        if (Math.random() < risk) {
            onKatki = true;
        }
    }

    /**
     * Tangib autot
     * @param liitrid näitab mitu liitrit peab lisama auto kütusekogusele.
     */
    public void tangi(double liitrid) {
        if(onKatki) {
            throw new IllegalStateException("Auto on katki, ei saa tankida!");
        }
        if (liitrid <= 0) {
            throw new IllegalArgumentException("Kogus peab olema positiivne");
        }
        kytust += liitrid;
    }

    /**
     * remondib auto
     */
    public void remondi() {
        if (!onKatki) {
            throw new IllegalStateException("Auto on juba terve!");
        }
        onKatki = false;
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

    public int getAasta() {
        return aasta;
    }
}