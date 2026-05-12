//package com.oopryhmatooii;
//
//import javax.swing.*;
//
//public class Main {
//    public static void main(String[] args) {
//        //Garaazi loomine
//        String nimi = JOptionPane.showInputDialog("Sisesta oma garaazi nimi: ");
//        Garaaž garaaz = new Garaaž(nimi);
//
//
//        while (true) {
//
//            //Üldvalikud
//            Object[] variandid = new String[]{
//                    "Lisa garaaži auto",
//                    "Kuva garaažis olevad autod",
//                    "Vali garaažist auto",
//                    "Eemalda auto"
//            };
//            Object valitud = JOptionPane.showInputDialog(null,
//                    "Vali tegevus", "Sisestus",
//                    JOptionPane.INFORMATION_MESSAGE, null,
//                    variandid, variandid[0]);
//            if (valitud == null) break;
//
//            //Tegevused
//            if (valitud.equals("Lisa garaaži auto")) {
//                String mark = JOptionPane.showInputDialog("Sisesta auto mark: ");
//                String mudel = JOptionPane.showInputDialog("Sisesta auto mudel: ");
//                String sisend = JOptionPane.showInputDialog("Sisesta auto väljalaskeaasta: ");
//                if (sisend == null) continue;
//                int aasta;
//                try {
//                    aasta = Integer.parseInt(sisend);
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Vale number!");
//                    continue;
//                }
//                int ls = Integer.parseInt(JOptionPane.showInputDialog("Sisesta oma auto läbisõit: "));
//                double kytust = Double.parseDouble(JOptionPane.showInputDialog("Sisesta, kui palju on sul kütust paagis liitrites: "));
//                double kytusekulu = Double.parseDouble(JOptionPane.showInputDialog("Sisesta oma kytusekulu 100 km kohta liitrites: "));
//                Auto auto = new Auto(mark, mudel, aasta, ls, kytust, kytusekulu);
//                garaaz.lisaAuto(auto);
//
//            } else if (valitud.equals("Vali garaažist auto")) {
//                int indeks = Integer.parseInt(JOptionPane.showInputDialog("Sisesta auto järjekorranumber"));
//                Auto valitudAuto = garaaz.valiAuto(indeks);
//                tegevusedAutoga(valitudAuto);
//
//            } else if (valitud.equals("Kuva garaažis olevad autod")) {
//                garaaz.kuvaNimekiri();
//
//            } else if (valitud.equals("Eemalda auto")) {
//                String sisend = JOptionPane.showInputDialog("Sisesta eemaldatava auto järjekorranumber:");
//                if (sisend == null) continue;
//                try {
//                    int indeks = Integer.parseInt(sisend);
//                    garaaz.eemaldaAuto(indeks);
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Vale number!");
//                    continue;
//                }
//            }
//        }
//    }
//
//    /**
//     * Koondab autoga tehtavad valikud ja tegevused ühte staatilisse meetodi.
//     * @param auto on klassi Auto isend
//     */
//    public static void tegevusedAutoga(Auto auto) {
//        if (auto == null) return;
//        Object[] variandid = new String[]{"Sõida", "Tangi", "Remondi", "Tagasi"};
//
//        //Valikute tsükkel
//        while (true) {
//            String olek = auto.isOnKatki() ? "KATKI!!!" : "Korras";//Kas autoga saab sõita, või vajab parandust
//            Object valitud = JOptionPane.showInputDialog(null,
//                    "Mark: " + auto.getMark() +
//                            "\nMudel " + auto.getMudel() +
//                            "\nOlek: " + olek +
//                            "\nLäbisõit: " + auto.getLabiSoit() + " km" +
//                            "\nKütust: " + String.format("%.2f", auto.getKytust()) + " L",
//                    "Auto tegevused",
//                    JOptionPane.INFORMATION_MESSAGE, null,
//                    variandid, variandid[0]);
//
//            if (valitud == null || "Tagasi".equals(valitud)) return;
//
//            else if (valitud.equals("Sõida")) {
//                if (auto.isOnKatki()) {
//                    JOptionPane.showMessageDialog(null, "Auto on katki, ei saa sõita");
//                    continue;
//                } else {
//                    int soidetud = Integer.parseInt(JOptionPane.showInputDialog("Kui palju sõita soovid?"));
//                    if (soidetud <= 0) {
//                        JOptionPane.showMessageDialog(null, "Kilomeetrid peavad olema positiivsed!");
//                        continue;
//                    }
//                    auto.soida(soidetud);
//                }
//            }
//            else if (valitud.equals("Tangi")) {
//                if (auto.isOnKatki()) {
//                    JOptionPane.showMessageDialog(null, "Auto on katki, ei saa tankida");
//                    continue;
//                } else {
//                    String sisend = JOptionPane.showInputDialog("Mitu liitrit?");
//                    if (sisend == null) continue;
//                    double liitrid = Double.parseDouble(sisend);
//                    auto.tangi(liitrid);
//                }
//            }
//            else if (valitud.equals("Remondi")) {
//                auto.remondi();
//            }
//        }
//    }
//}