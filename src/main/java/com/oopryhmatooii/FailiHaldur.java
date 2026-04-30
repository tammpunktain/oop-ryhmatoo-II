package com.oopryhmatooii;

import java.io.*;
import java.util.Scanner;

public class FailiHaldur {

    public static void kirjutaFaili(Garaaž garaaz){
        File file = new File("andmed.csv");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            for (Auto a : garaaz.getAutod()) {
                bw.write(
                        a.getMark()+";" +
                        a.getMudel()+";" +
                        a.getAasta()+";" +
                        a.getLabiSoit()+";" +
                        a.getKytust()+";" +
                        a.getKytusekulu()
                );
                bw.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("viga");
        }
    }

    public static void loeFailist(Garaaž garaaz) {
        File file = new File("andmed.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))
        {
            String rida = br.readLine();
            while (rida != null){
                String[] osad = rida.split(";");
                garaaz.lisaAuto(new Auto(
                        osad[0],
                        osad[1],
                        Integer.parseInt(osad[2]),
                        Integer.parseInt(osad[3]),
                        Double.parseDouble(osad[4]),
                        Double.parseDouble(osad[5])));
                rida = br.readLine();
            }
        }
        catch(IOException e){
            System.out.println("viga");
        }
    }




}
