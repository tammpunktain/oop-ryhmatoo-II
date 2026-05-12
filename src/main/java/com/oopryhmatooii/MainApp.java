package com.oopryhmatooii;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainApp extends Application {

    private Garaaž garaaz = new Garaaž("Minu Garaaž");
    private ObservableList<Auto> autoObservableList;
    private final String FAILINIMI = "garaaz.txt";

    @Override
    public void start(Stage primaryStage) {
        // 1. Loe andmed failist sisse
        loeFailist();
        autoObservableList = FXCollections.observableArrayList(garaaz.getAutod());

        primaryStage.setTitle("Garaaži Haldussüsteem 2.0");

        // --- KOMPONENDID ---
        Label pealkiri = new Label(garaaz.getNimi());
        pealkiri.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ListView<Auto> listView = new ListView<>(autoObservableList);

        // Tekstiväli lühututvustuse jaoks
        TextArea infoAla = new TextArea("Vali nimekirjast auto, et näha detaile või sooritada tegevusi.\n" +
                "Kustutamiseks kasuta klaviatuuril 'Delete' klahvi.");
        infoAla.setEditable(false);
        infoAla.setPrefHeight(80);

        Button lisaNupp = new Button("Lisa Auto");
        Button soidaNupp = new Button("Sõida (50km)");
        Button tangiNupp = new Button("Tangi (10L)");
        Button remondiNupp = new Button("Remondi");

        // --- PAIGUTUS (LAYOUT) ---
        VBox nupud = new VBox(10, lisaNupp, soidaNupp, tangiNupp, remondiNupp);
        nupud.setPadding(new Insets(10));

        BorderPane juur = new BorderPane();
        juur.setPadding(new Insets(15));
        juur.setTop(pealkiri);
        juur.setCenter(listView);
        juur.setRight(nupud);
        juur.setBottom(infoAla);

        // --- SÜNDMUSED (EVENTS) ---

        // HIIR: Lisa auto (lihtsustatud dialoog)
        lisaNupp.setOnAction(e -> {
            // Loome dialoogiakna
            Dialog<Auto> dialog = new Dialog<>();
            dialog.setTitle("Lisa uus auto");
            dialog.setHeaderText("Sisesta auto andmed:");

            // Lisa nupud "Salvesta" ja "Tühista"
            ButtonType salvestaNuppTüüp = new ButtonType("Salvesta", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(salvestaNuppTüüp, ButtonType.CANCEL);

            // Loome sisestusväljad
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField markVäli = new TextField();
            markVäli.setPromptText("Mark");
            TextField mudelVäli = new TextField();
            mudelVäli.setPromptText("Mudel");
            TextField aastaVäli = new TextField();
            aastaVäli.setPromptText("Aasta");
            TextField lsVäli = new TextField();
            lsVäli.setPromptText("Läbisõit");
            TextField kytusVäli = new TextField();
            kytusVäli.setPromptText("Kütus paagis");
            TextField kuluVäli = new TextField();
            kuluVäli.setPromptText("Kütusekulu");

            grid.add(new Label("Mark:"), 0, 0);
            grid.add(markVäli, 1, 0);
            grid.add(new Label("Mudel:"), 0, 1);
            grid.add(mudelVäli, 1, 1);
            grid.add(new Label("Aasta:"), 0, 2);
            grid.add(aastaVäli, 1, 2);
            grid.add(new Label("Läbisõit:"), 0, 3);
            grid.add(lsVäli, 1, 3);
            grid.add(new Label("Kütus (L):"), 0, 4);
            grid.add(kytusVäli, 1, 4);
            grid.add(new Label("Kulu (L/100km):"), 0, 5);
            grid.add(kuluVäli, 1, 5);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == salvestaNuppTüüp) {
                    try {
                        return new Auto(
                                markVäli.getText(),
                                mudelVäli.getText(),
                                Integer.parseInt(aastaVäli.getText()),
                                Integer.parseInt(lsVäli.getText()),
                                Double.parseDouble(kytusVäli.getText()),
                                Double.parseDouble(kuluVäli.getText()),
                                false // Uus auto on alguses terve
                        );
                    } catch (NumberFormatException ex) {
                        naitaViga("Palun sisesta numbriväljadesse korrektsed arvud!");
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(uusAuto -> {
                garaaz.lisaAuto(uusAuto);
                autoObservableList.add(uusAuto);
                salvestaFaili();
            });
        });

        // HIIR: Sõitmine
        soidaNupp.setOnAction(e -> {
            Auto valitud = listView.getSelectionModel().getSelectedItem();
            if (valitud != null) {
                boolean onnistus = valitud.soida(50);
                if (!onnistus) {
                    naitaViga("Sõit ebaõnnestus! Kontrolli kütust või auto seisukorda.");
                }
                listView.refresh();
                salvestaFaili();
            }
        });

        // HIIR: Tankimine
        tangiNupp.setOnAction(e -> {
            Auto valitud = listView.getSelectionModel().getSelectedItem();
            if (valitud != null) {
                try {
                    valitud.tangi(10.0); // Tangime 10L
                    listView.refresh();
                    salvestaFaili();
                } catch (IllegalArgumentException ex) {
                    naitaViga(ex.getMessage());
                }
            }
        });

        // HIIR: Remont
        remondiNupp.setOnAction(e -> {
            Auto valitud = listView.getSelectionModel().getSelectedItem();
            if (valitud != null) {
                if (valitud.remondi()) {
                    listView.refresh();
                    salvestaFaili();
                } else {
                    // Võid kasutada ka teist tüüpi teavitust (INFORMATION)
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setContentText("Auto on juba korras!");
                    info.showAndWait();
                }
            }
        });
        // KLAVIATUUR: Kustutamine Delete klahviga
        listView.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("DELETE")) {
                Auto valitud = listView.getSelectionModel().getSelectedItem();
                if (valitud != null) {
                    int indeks = listView.getSelectionModel().getSelectedIndex();
                    garaaz.eemaldaAuto(indeks);
                    autoObservableList.remove(valitud);
                    salvestaFaili();
                }
            }
        });

        // Aken
        Scene scene = new Scene(juur, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- ABI-MEETODID ---

    private void naitaViga(String teade) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(teade);
        alert.showAndWait();
    }

    private void salvestaFaili() {
        try {
            garaaz.salvestaFaili(FAILINIMI);
        } catch (Exception e) {
            System.out.println("Viga salvestamisel: " + e.getMessage());
        }
    }

    private void loeFailist() {
        if (!Files.exists(Paths.get(FAILINIMI))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(FAILINIMI))) {
            String rida;
            while ((rida = br.readLine()) != null) {
                String[] osad = rida.split(";");
                if (osad.length >= 7) {
                    Auto a = new Auto(
                            osad[0],
                            osad[1],
                            Integer.parseInt(osad[2]),
                            Integer.parseInt(osad[3]),
                            Double.parseDouble(osad[4]),
                            Double.parseDouble(osad[5]));
                            Boolean.parseBoolean(osad[6]);
                    garaaz.lisaAuto(a);
                }
            }
        } catch (IOException e) {
            System.out.println("Viga lugemisel: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}