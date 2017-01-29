package com.epsi.tower_defense;

/**
 * Created by Jacques on 10/01/2017.
 */
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.*;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.awt.image.GifImageDecoder;

//import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.awt.image.ImageObserver.WIDTH;
import static javafx.scene.Cursor.cursor;


public class Graph extends Application {
        /*private static final String IMAGECOULOIR = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/IN_1.png";
        private static final String VIRAGEDROITBAS = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/LN_1.png";
        private static final String CROISEMENT3 = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/TN_1.png";
        private static final String END = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/EN_1.png";*/
        private static final String CHEMIN = "C:\\Users\\Jacques\\Documents\\GitHub\\Tower_Defense\\tower-defense\\ressources\\img";
   // Image imageChemin = new Image("file:ressources\\img\\tour.png");
        //final ImageCursor cursorNew = ImageCursor.chooseBestCursor(new Image[]{imageChemin}, 0,0);
        private  Plateau plateau;
        int MAX;
        Stage stage;

        /*final Image image2 = new Image("file:"+VIRAGEDROITBAS);
        final Image image3 = new Image("file:"+CROISEMENT3);
        final Image image4 = new Image("file:"+END);
        final Image image5 = new Image("file:"+ARR);*/

       public static void main(String[] args) {

           Application.launch(Graph.class, args);

           /*creerPlateau();

           Plateau plateau = new Plateau("ressources/terrainTest.json");

           plateau.run();*/
        }
        @Override
        public void start(Stage primaryStage) {

           /*
           Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512 - 64, 256 );
        root.getChildren().add( canvas );
            */
           stage = primaryStage;
            stage.getIcons().setAll(new Image("file:ressources\\img\\Tour\\tour2.png"));
                    // stage.setFullScreen(true);
             plateau = new Plateau("ressources/terrainTest.json");
             MAX =(int)(Math.sqrt(plateau.terrain.size() + plateau.casesChemin.size()))*100;
             Group root = new Group();
            Scene theScene = new Scene( root );
            stage.setScene( theScene );
             Canvas canvas = new Canvas (MAX,  MAX);
            Canvas canvas2 = new Canvas (MAX,  MAX);
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gc2 = canvas2.getGraphicsContext2D();
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            double coordoneeX = t.getSceneX();
                            double coordoneeY = t.getSceneY();
                            onclic(coordoneeX, coordoneeY);
                        }
                    });
            canvas2.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            double coordoneeX = t.getSceneX();
                            double coordoneeY = t.getSceneY();
                            onclic(coordoneeX, coordoneeY);
                        }
                    });
           /* canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            double coordoneeX = t.getSceneX();
                            double coordoneeY = t.getSceneY();
                            onSurvole(coordoneeX, coordoneeY, gc);
                        }
                    });*/
            final GridPane gridpane = new GridPane();
            // gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);

            gridpane.setVgap(10);

            final long startNanoTime = System.nanoTime();
            final Label or = new Label( Integer.toString(plateau.or)+" Or");
            final Label gameOver = new Label( "Game Over");
            final Label vague = new Label( "Vague: "+ plateau.vague);

            List<String> test = javafx.scene.text.Font.getFamilies();

            or.setFont(new Font(test.get(1),50));
            or.setTextFill(Color.web("#FFD700"));
            gameOver.setFont(new Font(test.get(11),90));
            gameOver.setTextFill(Color.web("#F44336"));

            GridPane.setHalignment(or, HPos.CENTER);
            gridpane.add(or,
                    0, 0);


            root.getChildren().add(canvas);
            root.getChildren().add(canvas2);
            root.getChildren().add(gridpane);
            AffichageGraphyqueDecor  agd= new AffichageGraphyqueDecor(gc);
            AffichageGraphyqueMouvement  agm= new AffichageGraphyqueMouvement(gc2);
            //ag.run();
            //Deplacement deplacement = new Deplacement();
            //deplacement.run();

            Thread T1 = new Thread(agd);
            //Thread T2 = new Thread(deplacement);
            Thread T3 = new Thread(agm);

            T1.start();
            //T2.start();
            T3.start();

            new AnimationTimer()
            {
                public void handle(long now)
                {
                    if (plateau.pv <0){
                        gridpane.add(gameOver, 6, 20);


                    }
                    else {
                        or.setText(Integer.toString(plateau.or) + " Or");
                    }








                        /*Platform.runLater(new Runnable() {
                            @Override public void run() {
                                affichage(gc);
                            }
                        });*/
                        /*Task task = new Task() {
                            @Override
                            protected Object call() throws Exception {

                                affichage(gc);
                                try {

                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    //e.printStackTrace();
                                    System.out.print("test");
                                }


                                return null;
                            }
                        };
                        Thread th = new Thread(task);
                        th.();










                            Task task2 = new Task() {
                                @Override
                                protected Object call() throws Exception {

                                        plateau.deplacerEnnemis();
                                        try {

                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    return null;
                                }
                            };
                            Thread th2 = new Thread(task2);
                            th2.start();





                        //plateau.deplacerEnnemis();
                        /*try {

                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*/

                }

            }.start();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(plateau.pv <=0){

                        gridpane.add(gameOver, 10, 3);
                        gridpane.add(vague, 3, 10);
                    }
                    else {

                        plateau.attaquer();
                    }

                }
            }, 0, 2000);


            stage.show();
        }







    private void onSurvole(double coordoneeX, double coordoneeY, GraphicsContext gc) {
        int caseX = (int) coordoneeY;
        int caseY = (int) coordoneeX;
        for (Case caseTerrain : plateau.terrain) {
            if (caseTerrain.getX() == caseX && caseTerrain.getY()==caseY){
                    Circle circle = new Circle();
                    circle.setCenterX(caseTerrain.getX()*100 +50);
                    circle.setCenterY(caseTerrain.getY()*100 +50);
                    circle.setRadius(caseTerrain.getTour().portee);
                   // gc.fillOval(caseTerrain.getX()*100 +50, caseTerrain.getY()*100 +50, caseTerrain.getTour().portee , 300 );
                gc.fillOval(30,30,30,30);
                }
            }
        }


    private void affichageDecor(GraphicsContext gc) {



        for (Case caseTerrain : plateau.terrain) {
            Image imageTerrain;
            if (caseTerrain.getTour() != null) {
                Tour tour = caseTerrain.getTour();

                if (caseTerrain.getTour().attaque) {
                    imageTerrain = new Image("file:ressources\\img\\Tour\\tourTire" + String.valueOf(tour.niveau) + ".png");
                    gc.drawImage(imageTerrain, caseTerrain.y, caseTerrain.x, inc, inc);
                } else {
                    imageTerrain = new Image("file:ressources\\img\\Tour\\tour" + String.valueOf(tour.niveau) + ".png");
                    gc.drawImage(imageTerrain, caseTerrain.y, caseTerrain.x, inc, inc);
                }

            } else {

                imageTerrain = new Image("file:ressources\\img\\terrain.png");
                gc.drawImage(imageTerrain, caseTerrain.y, caseTerrain.x, inc, inc);

            }

        }
        Image imageChemin;
        for (Case caseChemin : plateau.getChemin()) {
            imageChemin = new Image("file:ressources\\img\\chemin.png");
            gc.drawImage(imageChemin, caseChemin.y, caseChemin.x, inc, inc);

        }
    }
    private void affichageMouvement(GraphicsContext gc)
        {
            gc.clearRect(0, 50, MAX, MAX);
            Image imageEnnemni;
            for (Ennemi ennemi  :plateau.listEnnemi) {
                if (ennemi.vivant) {
                    imageEnnemni = new Image("file:ressources\\img\\Viking"+ennemi.direction+"\\Viking"+String.valueOf(ennemi.sprite)+".png");
                    gc.drawImage(imageEnnemni, ennemi.coorY, ennemi.coorX - 20, 100, 100);
                }
            }
            Image nextVague = new Image("file:ressources\\img\\NextWave.png");


            gc.drawImage(nextVague, 800, 0, 100, 50);


        }


    private void onclic(double coordoneeX, double coordoneeY) {
        //TODO: modifier
           if (coordoneeX >= 800 && coordoneeY <= 50){
               //nouvelleVague();

               Task task = new Task() {
                   @Override
                   protected Object call() throws Exception {
                       for (int i=1; i<=plateau.vague; i++) {
                           plateau.genererNouveauxEnnemis();
                           try {

                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                       plateau.vague++;
                       return null;
                   }
               };
                   Thread th = new Thread(task);
                    th.start();
           }
           else {
               onclicAddTour(coordoneeX,coordoneeY);
           }

    }
    private void onclicAddTour(double coordoneeX, double coordoneeY) {
        int caseX = (int) coordoneeY/100;
        int caseY = (int) coordoneeX/100;
        for (Case caseTerrain : plateau.terrain) {
            if (caseTerrain.getX() == caseX*100 && caseTerrain.getY()==caseY*100){
                if (plateau.or >= 50 && caseTerrain.tour ==null) {
                    Tour tour = new Tour("tour1", 200, false, 30, 0, 1);
                    caseTerrain.setTour(tour);
                    plateau.or = plateau.or - 50;
                }
                else if (plateau.or >= 50 && caseTerrain.tour !=null && caseTerrain.tour.niveau == 1){
                    Tour tour = caseTerrain.tour;
                    plateau.or -= 50;
                    tour.niveau = 2;
                    tour.degat =75;
                    tour.portee = 250;
                }
            }

        }

    }

    static int inc = 100;


    /**
     * Lancement de la partie, va boucler jusqu'à ce que le plateau n'ai plus de vie
     */
    public  void nouvelleVague(){

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                plateau.genererNouveauxEnnemis();

            }
        }, 300, 5000);

    }


    /*public class MonThread extends Thread {
        GraphicsContext gc;
        public  MonThread(GraphicsContext gc){
            this.gc = gc;
        }

        @Override

        public void run() {
            for (Case caseTerrain: plateau.terrain){
                if (caseTerrain.getTour() != null)
                {
                    Image imageChemin = new Image("file:ressources\\img\\tour.png");

                    gc.drawImage(imageChemin, caseTerrain.y , caseTerrain.x  ,inc,inc);
                }
                else {

                    Image imageChemin = new Image("file:ressources\\img\\terrain.png");
                    gc.drawImage(imageChemin, caseTerrain.y , caseTerrain.x , inc, inc);
                }
            }
            for (Case caseChemin: plateau.getChemin()){
                imageChemin = new Image("file:ressources\\img\\chemin.png");
                gc.drawImage(imageChemin, caseChemin.y , caseChemin.x  ,inc,inc);
            }
            for (Ennemi ennemi  :plateau.listEnnemi) {
                if (ennemi.vivant) {
                    imageChemin = new Image("file:ressources\\img\\Viking\\Viking"+String.valueOf(ennemi.sprite)+".png");
                    gc.drawImage(imageChemin, ennemi.coorY, ennemi.coorX - 20, 100, 100);
                }
            }
            Image imageChemin = new Image("file:ressources\\img\\NextWave.png");


            gc.drawImage(imageChemin, 8 * inc, 0 * inc  ,200,100 );

        }

    }*/

    class AffichageGraphyqueMouvement implements Runnable{
        GraphicsContext gc;
        public AffichageGraphyqueMouvement(GraphicsContext gc) {
            this.gc=gc;
        }
        public void run(){
                while (plateau.pv >0) {

                    Platform.runLater(new Runnable() {
                                          @Override
                                          public void run() {
                                              plateau.deplacerEnnemis();
                                              affichageMouvement(gc);
                                          }
                                      });

                    try {

                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.out.println(" Error");
                    }

                }
        }
    }

    class AffichageGraphyqueDecor implements Runnable{
        GraphicsContext gc;
        public AffichageGraphyqueDecor(GraphicsContext gc) {
            this.gc=gc;
        }

    public void run(){
        while (plateau.pv >0) {


            affichageDecor(gc);
            try {
                    /*int tempo = (int)(Math.random()*1000);
                    System.out.println(message + " " + tempo);*/
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(" Error");
            }


        }
    }
}
    class Deplacement implements Runnable{


        public void run() {
            while (plateau.pv > 0) {
                plateau.deplacerEnnemis();

                try {
                   /* int tempo = (int)(Math.random()*1000);
                    System.out.println(message + " " + tempo);*/
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println(" Error");
                }
            }
        }


    }
}

