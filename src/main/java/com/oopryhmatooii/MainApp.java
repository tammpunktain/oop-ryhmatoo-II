package com.oopryhmatooii;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class MainApp extends Application {

    private Garaaž garaaz;


    public void start(Stage lava){
        garaaz = new Garaaž();
        FailiHaldur.loeFailist(garaaz); //juba olemasolevad andmed failist
        BorderPane juur = new BorderPane();
        Scene stseen = new Scene(juur, 900,600);

        //Pealkiri yleval
        VBox yleval = new VBox();
        Label pk = new Label(garaaz.getNimi());
        pk.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label juhis = new Label("Siin saad teha erinevaid toiminguid oma garaazis olevate soiodukitega.");
        juhis.setStyle("-fx-font-size: 15px; -fx-font-style: italic;");
        yleval.getChildren().addAll(pk, juhis);
        yleval.setAlignment(Pos.CENTER);
        yleval.setPadding(new Insets(10));
        juur.setTop(yleval);

        //Peavalikud all
        HBox allPeavalik = new HBox(10);
        Button lisaAuto = new Button("Lisa Garaazi auto");
        Button valiAuto = new Button("Vali garaazist auto");//yhendab ka kuvaauto siia.
        Button eemaldaAuto = new Button("Eemalda garaazist auto"); //Tulevikus see laheb alles parast auto valimist valikusse
        allPeavalik.setPadding(new Insets(13));
        allPeavalik.getChildren().addAll(lisaAuto, valiAuto, eemaldaAuto);
        allPeavalik.setAlignment(Pos.CENTER);
        juur.setBottom(allPeavalik);

        //Vajutades peavaliku nuppu eraldi syndmu
        lisaAuto.setOnAction(e -> {
            VBox vorm = new VBox();

            TextField mark = new TextField();
            mark.setPromptText("Auto mark");

            TextField mudel = new TextField();
            mudel.setPromptText("Auto mudel");

            TextField aasta = new TextField();
            aasta.setPromptText("Autoi valjalaskeaasta");

            TextField ls = new TextField();
            ls.setPromptText("Auto labisoit");

            TextField kytust = new TextField();
            kytust.setPromptText("Palju kytsi paagis");

            TextField kytsiKulu = new TextField();
            kytsiKulu.setPromptText("Sisesta keskmisne kystikulu");

            Button nupp = new Button("Sisesta");

            nupp.setOnAction(e2 -> {
                Auto auto = new Auto(
                        mark.getText(),
                        mudel.getText(),
                        Integer.parseInt(aasta.getText()),
                        Integer.parseInt(ls.getText()),
                        Double.parseDouble(kytust.getText()),
                        Double.parseDouble(kytsiKulu.getText())
                );
                garaaz.lisaAuto(auto);
                FailiHaldur.kirjutaFaili(garaaz);
            });

            vorm.getChildren().addAll(mark, mudel, aasta, ls, kytust, kytsiKulu, nupp);
            vorm.setAlignment(Pos.CENTER);
            vorm.setSpacing(10);
            vorm.setPadding(new Insets(75));
            juur.setCenter(vorm);


        });

        valiAuto.setOnAction(e -> {
            ListView<Auto> lw = new ListView();
            lw.getItems().addAll(garaaz.getAutod());
            juur.setCenter(lw);


        });

        eemaldaAuto.setOnAction(e -> {
        });



        lava.setTitle("Autosimulaator");
        lava.setScene(stseen);
        lava.show();
    }





    public static void main(String[] args) {
        launch();
    }
}
