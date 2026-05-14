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

    private Garaaž garaaž;
    private BorderPane juur;


    public void start(Stage lava){
        garaaž = new Garaaž();
        FailiHaldur.loeFailist(garaaž); //juba olemasolevad andmed failist
        juur = new BorderPane();
        Scene stseen = new Scene(juur, 900,600);

        //Pealkiri yleval
        VBox yleval = new VBox();
        Label pk = new Label(garaaž.getNimi());
        pk.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label juhis = new Label("Siin saad teha erinevaid toiminguid oma garaažis olevate soiodukitega.");
        juhis.setStyle("-fx-font-size: 15px; -fx-font-style: italic;");
        yleval.getChildren().addAll(pk, juhis);
        yleval.setAlignment(Pos.CENTER);
        yleval.setPadding(new Insets(10));
        juur.setTop(yleval);

        //Peavalikud all
        HBox allPeavalik = new HBox(10);
        Button lisaAuto = new Button("Lisa garaaži auto");
        Button eemaldaAuto = new Button("Eemalda garaažist auto"); //Tulevikus see laheb alles parast auto valimist valikusse
        allPeavalik.setPadding(new Insets(13));
        allPeavalik.getChildren().addAll(lisaAuto, eemaldaAuto);
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
            aasta.setPromptText("Auto valjalaskeaasta");

            TextField ls = new TextField();
            ls.setPromptText("Auto labisõit");

            TextField kütust = new TextField();
            kütust.setPromptText("Palju kütust paagis");

            TextField kytsiKulu = new TextField();
            kytsiKulu.setPromptText("Sisesta keskmisne kütusekulu");

            Button nupp = new Button("Sisesta");

            nupp.setOnAction(e2 -> {
                Auto auto = new Auto(
                        mark.getText(),
                        mudel.getText(),
                        Integer.parseInt(aasta.getText()),
                        Integer.parseInt(ls.getText()),
                        Double.parseDouble(kütust.getText()),
                        Double.parseDouble(kytsiKulu.getText())
                );
                garaaž.lisaAuto(auto);
                FailiHaldur.kirjutaFaili(garaaž);
                kuvaAutodeNimekiri();
            });

            vorm.getChildren().addAll(mark, mudel, aasta, ls, kütust, kytsiKulu, nupp);
            vorm.setAlignment(Pos.CENTER);
            vorm.setSpacing(10);
            vorm.setPadding(new Insets(75));
            juur.setCenter(vorm);


        });
        //Vajutades Eemalda garaažist auto nuppu
        eemaldaAuto.setOnAction(e -> {
            //Kui garaaž on tühi
            if (garaaž.getAutod().isEmpty()) {
                juur.setCenter(new Label("Garaažis pole ühtegi autot."));
                return;
            }

            ListView<Auto> lw = new ListView<>();
            lw.getItems().addAll(garaaž.getAutod());

            Label juhend = new Label("Vali eemaldatav auto ja vajuta (Eemalda valitud auto) nuppu:");
            Button eemalda = new Button("Eemalda valitud auto");
            eemalda.setDisable(true);

            lw.getSelectionModel().selectedItemProperty().addListener(
                    (obs, vana, uus) -> eemalda.setDisable(uus == null)
            );

            eemalda.setOnAction(e2 -> {
                Auto valitud = lw.getSelectionModel().getSelectedItem();
                if (valitud != null) {
                    int indeks = garaaž.getAutod().indexOf(valitud) + 1;
                    garaaž.eemaldaAuto(indeks);
                    FailiHaldur.kirjutaFaili(garaaž);
                    kuvaAutodeNimekiri();
                }
            });

            VBox paneel = new VBox(10, juhend, lw, eemalda);
            paneel.setPadding(new Insets(20));
            juur.setCenter(paneel);
        });



        lava.setTitle("Autosimulaator");
        lava.setScene(stseen);
        kuvaAutodeNimekiri();
        lava.show();
    }
    private void kuvaAutodeNimekiri() {
        if (garaaž.getAutod().isEmpty()) {
            juur.setCenter(new Label("Garaažis pole ühtegi autot."));
            return;
        }
        ListView<Auto> lw = new ListView<>();
        lw.getItems().addAll(garaaž.getAutod());

        Label juhend = new Label("Vali auto, et näha toiminguid:");

        lw.getSelectionModel().selectedItemProperty().addListener(
                (obs, vana, uus) -> {
                    if (uus != null) kuvaAutoTegevused(uus);
                }
        );

        VBox paneel = new VBox(8, juhend, lw);
        paneel.setPadding(new Insets(20));
        juur.setCenter(paneel);
    }
    private void kuvaAutoTegevused(Auto auto) {
        VBox paneel = new VBox(10);
        paneel.setPadding(new Insets(20));

        Label infoSilt = new Label(auto.kuvaInfo());
        infoSilt.setWrapText(true);

        Label olekuSilt = new Label(auto.isOnKatki() ? "AUTO ON KATKINE!" : "Auto on töökorras");

        Label teadeSilt = new Label();
        teadeSilt.setWrapText(true);

        TextField kmVali = new TextField();
        kmVali.setPromptText("Kilomeetrid");
        kmVali.setMaxWidth(200);
        Button soida = new Button("Sõida");

        soida.setOnAction(e -> {
            try {
                int km = Integer.parseInt(kmVali.getText().trim());
                auto.soida(km);
                FailiHaldur.kirjutaFaili(garaaž);
                if(!auto.isOnKatki()){
                    teadeSilt.setText("Sõitsid " + km + " km!");
                    uuendaAutoInfo(auto, infoSilt, olekuSilt);
                }else{
                    teadeSilt.setText("Sõitsid "+ km + " km, mille käigus läks auto katki!");
                }

            } catch (NumberFormatException ex) {
                teadeSilt.setText("Sisesta kehtiv kilomeetrite arv.");
            } catch (IllegalStateException ex) {
                teadeSilt.setText("Viga: " + ex.getMessage());
                uuendaAutoInfo(auto, infoSilt, olekuSilt);
            }
        valiAuto.setOnAction(e -> {
            ListView<Auto> lw = new ListView();
            lw.getItems().addAll(garaaz.getAutod());
            juur.setCenter(lw);
        });

        TextField liitridVali = new TextField();
        liitridVali.setPromptText("Liitrid");
        liitridVali.setMaxWidth(200);
        Button tangi = new Button("Tangi");

        tangi.setOnAction(e -> {
            try {
                double liitrid = Double.parseDouble(liitridVali.getText().trim());
                auto.tangi(liitrid);
                FailiHaldur.kirjutaFaili(garaaž);
                teadeSilt.setText("Tangiti " + liitrid + " L kütust.");
                uuendaAutoInfo(auto, infoSilt, olekuSilt);
            } catch (NumberFormatException ex) {
                teadeSilt.setText("Sisesta kehtiv liitrite arv.");
            } catch (IllegalStateException | IllegalArgumentException ex) {
                teadeSilt.setText("Viga: " + ex.getMessage());
            }
        });

        Button remondi = new Button("Remondi");
        remondi.setOnAction(e -> {
            try {
                auto.remondi();
                FailiHaldur.kirjutaFaili(garaaž);
                teadeSilt.setText("Auto remonditud!");
                uuendaAutoInfo(auto, infoSilt, olekuSilt);
            } catch (IllegalStateException ex) {
                teadeSilt.setText("Viga: " + ex.getMessage());
            }
        });

        Button tagasi = new Button("Tagasi nimekirja");
        tagasi.setOnAction(e -> kuvaAutodeNimekiri());

        HBox soitmineRida = new HBox(8, kmVali, soida);
        HBox tangimineRida = new HBox(8, liitridVali, tangi);

        paneel.getChildren().addAll(
                tagasi,
                infoSilt,
                olekuSilt,
                new Label("Sõida:"), soitmineRida,
                new Label("Tangi:"), tangimineRida,
                remondi,
                teadeSilt
        );

        juur.setCenter(paneel);
    }



    private void uuendaAutoInfo(Auto auto, Label infoSilt, Label olekuSilt) {
        infoSilt.setText(auto.kuvaInfo());
        if (auto.isOnKatki()) {
            olekuSilt.setText("AUTO ON KATKINE!");
        } else {
            olekuSilt.setText("Auto on töökorras");
        }
    }




    public static void main(String[] args) {
        launch();
    }
}
