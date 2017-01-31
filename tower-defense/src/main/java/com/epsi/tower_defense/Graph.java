package com.epsi.tower_defense;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Graph extends Application {

        private  Plateau plateau;
        int MAX;
        Stage stage;


       public static void main(String[] args) {

           Application.launch(Graph.class, args);


        }
        @Override
        public void start(Stage primaryStage) {


            //final File file = new File("file:ressources\\Musique\\proftim.mp3");
           // final Media media = new Media(file.toURI().toString());
           stage = primaryStage;
            stage.getIcons().setAll(new Image("file:ressources\\img\\Tour\\tour2.png"));
                    // stage.setFullScreen(true);
             plateau = new Plateau("ressources/terrainTest.txt");
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

            final GridPane gridpane = new GridPane();
            // gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);

            gridpane.setVgap(10);

            final long startNanoTime = System.nanoTime();
            final Label or = new Label( Integer.toString(plateau.or)+" Or");
            final Label gameOver = new Label( "Game Over");
            final Label vague = new Label( "Vague: "+ plateau.vague);
            final Label pv = new Label( "pv: "+ plateau.pv);

            List<String> test = javafx.scene.text.Font.getFamilies();

            or.setFont(new Font(test.get(1),50));
            or.setTextFill(Color.web("#FFD700"));
            gameOver.setFont(new Font(test.get(11),90));
            gameOver.setTextFill(Color.web("#F44336"));
            vague.setFont(new Font(test.get(11),50));
            vague.setTextFill(Color.web("#F44336"));
            pv.setFont(new Font(test.get(90),1));
            pv.setTextFill(Color.web("#F44336"));

            GridPane.setHalignment(or, HPos.CENTER);
            gridpane.add(or,
                    0, 0);
            gridpane.add(vague,
                    20, 0);


            root.getChildren().add(canvas);
            root.getChildren().add(canvas2);
            root.getChildren().add(gridpane);
            AffichageGraphyqueDecor  agd= new AffichageGraphyqueDecor(gc);
            AffichageGraphyqueMouvement  agm= new AffichageGraphyqueMouvement(gc2);


            Thread T1 = new Thread(agd);
            //Thread T2 = new Thread(deplacement);
            Thread T3 = new Thread(agm);

            T1.start();

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
                        vague.setText( "Vague "+Integer.toString(plateau.vague) );
                    }


                }

            }.start();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(plateau.pv <=0){

                        gridpane.add(gameOver, 10, 3);
                        gridpane.add(vague, 3, 10);
                        gridpane.add(pv, 40, 3);
                    }
                    else {

                        plateau.attaquer();
                    }

                }
            }, 0, 2000);


            stage.show();
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
                if (ennemi.getVivant()) {
                    imageEnnemni = new Image("file:ressources\\img\\Viking"+ennemi.direction+"\\Viking"+String.valueOf(ennemi.sprite)+".png");
                    gc.drawImage(imageEnnemni, ennemi.coorY, ennemi.coorX - 20, 100, 100);
                }
            }
            Image nextVague = new Image("file:ressources\\img\\NextWave.png");


            gc.drawImage(nextVague, 800, 0, 100, 50);


        }


    private void onclic(double coordoneeX, double coordoneeY) {
           if (coordoneeX >= 800 && coordoneeY <= 50){
               //nouvelleVague();

               Task task = new Task() {
                   @Override
                   protected Object call() throws Exception {
                       for (int i=0; i<=plateau.vague; i++) {
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
        try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
    class Deplacement implements Runnable{


        public void run() {
            while (plateau.pv > 0) {
                plateau.deplacerEnnemis();

                try {

                    Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println(" Error");
                }
            }
        }
    }
}

