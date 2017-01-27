package com.epsi.tower_defense;

/**
 * Created by Jacques on 10/01/2017.
 */
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.awt.image.GifImageDecoder;

//import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static java.awt.image.ImageObserver.WIDTH;
import static javafx.scene.Cursor.cursor;


public class Graph extends Application {
        /*private static final String IMAGECOULOIR = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/IN_1.png";
        private static final String VIRAGEDROITBAS = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/LN_1.png";
        private static final String CROISEMENT3 = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/TN_1.png";
        private static final String END = "/Users/richard/Documents/travail/cours/algoRil16/atelierIA/assets/EN_1.png";*/
        private static final String CHEMIN = "C:\\Users\\Jacques\\Documents\\GitHub\\Tower_Defense\\tower-defense\\ressources\\img";
    Image imageChemin = new Image("file:ressources\\img\\tour.png");
        final ImageCursor cursorNew = ImageCursor.chooseBestCursor(new Image[]{imageChemin}, 0,0);
        private  Plateau plateau;
        int MAX;
        Stage stage;

        /*final Image image2 = new Image("file:"+VIRAGEDROITBAS);
        final Image image3 = new Image("file:"+CROISEMENT3);
        final Image image4 = new Image("file:"+END);
        final Image image5 = new Image("file:"+ARR);*/
        public static String dungeon2 =
                "X X X X X X X X X X\n"+
                        "X X f - - - - - t X\n"+
                        "X X X X X X X X l X\n"+
                        "X X X + v X X X l X\n"+
                        "X X X X l X X X l X\n"+
                        "X X X X p - - - m X\n"+
                        "X X X X X X X X e X\n"+
                        "X X X X X X X X X X\n" ;
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
           // stage.setFullScreen(true);
             plateau = new Plateau("ressources/terrainTest.json");
             MAX =(int)(Math.sqrt(plateau.terrain.size() + plateau.casesChemin.size()))*100;
             Group root = new Group();
            Scene theScene = new Scene( root );
            stage.setScene( theScene );
             Canvas canvas = new Canvas (MAX,  MAX);
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
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
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            final long startNanoTime = System.nanoTime();
            final Label or = new Label( Integer.toString(plateau.or)+" Or");
            or.setFont(new Font(50));
            or.setTextFill(Color.web("#FFD700"));


            GridPane.setHalignment(or, HPos.CENTER);
            gridpane.add(or, 3, 3);

            root.getChildren().add(canvas);
            root.getChildren().add(gridpane);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime)
                {
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;


                    gc.clearRect(0, 0, MAX,MAX);
                    affichage(gc);
                    or.setText (Integer.toString(plateau.or)+" Or");


                    Boolean test = run();
                }

            }.start();

            // run(primaryStage);
           // fileSaveService.start();

            //run();
            stage.show();
        }
        private void affichage(GraphicsContext gc)
        {
            for (Case caseTerrain: plateau.terrain){
                if (caseTerrain.getTour() != null)
                {
                    Image imageChemin = new Image("file:ressources\\img\\tour.png");
                    gc.drawImage(imageChemin, caseTerrain.y * inc, caseTerrain.x * inc  ,inc,inc);
                }
                else {

                    Image imageChemin = new Image("file:ressources\\img\\terrain.png");
                    gc.drawImage(imageChemin, caseTerrain.y * inc, caseTerrain.x * inc, inc, inc);
                }
            }
            for (Case caseChemin: plateau.getChemin()){
                imageChemin = new Image("file:ressources\\img\\chemin.png");
                gc.drawImage(imageChemin, caseChemin.y * inc, caseChemin.x * inc  ,inc,inc);
            }
            for (Case caseChemin: plateau.getChemin()){
                Image imageChemin;
                if(!caseChemin.getListEnnemis().isEmpty()){
                    Ennemi enemi = caseChemin.listEnnemis.get(0);
                    if (caseChemin.horizontale) {
                        imageChemin = new Image("file:ressources\\img\\Viking.png");
                        gc.drawImage(imageChemin, (caseChemin.y * inc )+ enemi.deplacement, caseChemin.x * inc, inc, inc);
                        enemi.deplacement++;
                        try {
                            Thread.sleep(7 );
                        } catch (InterruptedException e) {
                            // Do nothing
                        }
                    }
                    else {
                        imageChemin = new Image("file:ressources\\img\\Viking.png");
                        gc.drawImage(imageChemin, caseChemin.y * inc, caseChemin.x * inc, inc, inc);
                    }
                }
                else {

                }




            }
            Image imageChemin = new Image("file:ressources\\img\\NextWave.png");


            gc.drawImage(imageChemin, 8 * inc, 0 * inc  ,200,100 );
        }

   /* private void affichageGraphique() {
        stage.setTitle("Tower defence");
       // stage.setFullScreen(true);
        Group root = new Group();
        Canvas canvas = new Canvas (MAX,  MAX);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        double coordoneeX = t.getSceneX();
                        double coordoneeY = t.getSceneY();
                        onclic(coordoneeX, coordoneeY);
                    }
                });
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc, plateau.terrain);
        Label or = new Label( Integer.toString(plateau.or)+" Or");
        or.setFont(new Font(50));
        or.setTextFill(Color.web("#FFD700"));

        root.getChildren().add(canvas);
        GridPane gridpane = new GridPane();
       // gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);


        GridPane.setHalignment(or, HPos.CENTER);
        gridpane.add(or, 0, 0);

        root.getChildren().add(gridpane);
        stage.setScene(new Scene(root));
        
        //stage.show();
    }*/
    private void onclic(double coordoneeX, double coordoneeY) {
           if (coordoneeX >= 800 && coordoneeY <= 100){
               nouvelleVague();
           }
           else {
               onclicAddTour(coordoneeX,coordoneeY);
           }

    }
    private void onclicAddTour(double coordoneeX, double coordoneeY) {
        int caseX = (int) (coordoneeY/100);
        int caseY = (int) (coordoneeX/100);
        for (Case caseTerrain : plateau.terrain) {
            if (caseTerrain.getX() == caseX && caseTerrain.getY()==caseY){
                if (plateau.or >= 50 && caseTerrain.tour ==null) {
                    Tour tour = new Tour("tour1", 2, false, 30, 0, 1);
                    caseTerrain.setTour(tour);
                    plateau.or = plateau.or - 50;
                }
            }

        }
        //affichageGraphique();
    }



        static int inc = 100;
       /* public  void drawShapes(GraphicsContext gc, ArrayList<Case> terrain) {
            gc.setFill(Color.GRAY);
            gc.setStroke(Color.GRAY);


            for (int i = 0 ; i < MAX; i= i + inc ){
                gc.strokeLine(i,0,i,MAX);
            }
            for (int i = 0 ; i < MAX; i= i + inc ){
                gc.strokeLine(0,i,MAX,i);
            }

            for (Case caseTerrain: terrain){
                if (caseTerrain.getTour() != null)
                {
                    Image imageChemin = new Image("file:ressources\\img\\tour.png");
                    gc.drawImage(imageChemin, caseTerrain.y * inc, caseTerrain.x * inc  ,inc,inc);
                }
                else {

                    Image imageChemin = new Image("file:ressources\\img\\terrain.png");
                    gc.drawImage(imageChemin, caseTerrain.y * inc, caseTerrain.x * inc, inc, inc);
                }
            }
            for (Case caseChemin: plateau.getChemin()){
                Image imageChemin;
                if(!caseChemin.getListEnnemis().isEmpty()){
                     imageChemin = new Image("file:ressources\\img\\Viking.png");
                }
                else {
                     imageChemin = new Image("file:ressources\\img\\chemin.png");
                }



                gc.drawImage(imageChemin, caseChemin.y * inc, caseChemin.x * inc  ,inc,inc);
            }
            Image imageChemin = new Image("file:ressources\\img\\NextWave.png");


            gc.drawImage(imageChemin, 8 * inc, 0 * inc  ,200,100 );

            //String []tabDungeon = dungeon2.split("\n");

            /*for (int i=0 ; i < tabDungeon.length ; i++){
                String []tabcrt = tabDungeon[i].split(" ");
                for (int c=0 ; c < tabcrt.length ; c ++){
                    switch (tabcrt[c].charAt(0)) {
                        case '-' :
                            //ImageView imageView = new ImageView(image1);
                            // imageView.setViewport(croppedPortion);
                            // imageView.setFitWidth(inc);
                            // imageView.setFitHeight(inc);
                            gc.drawImage(image1, inc * c,inc*i ,inc,inc);
                            break;
                        case 't':
                            gc.drawImage(image2, inc * c,inc*i ,inc,inc);
                            break;
                        case 'l':
                            ImageView iv = new ImageView(image1);
                            iv.setRotate(90);
                            SnapshotParameters params = new SnapshotParameters();
                            Image rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case 'm':
                            iv = new ImageView(image3);
                            iv.setRotate(90);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case '+':
                            iv = new ImageView(image5);
                            iv.setRotate(-90);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case 'f':
                            iv = new ImageView(image4);
                            iv.setRotate(180);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case 'e':
                            iv = new ImageView(image4);
                            iv.setRotate(90);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case 'p':
                            iv = new ImageView(image2);
                            iv.setRotate(180);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                        case 'v':
                            iv = new ImageView(image2);
                            iv.setRotate(0);
                            params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            rotatedImage = iv.snapshot(params, null);
                            gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);
                            break;
                    }
                }
            }
        }*/

    /**
     * Lancement de la partie, va boucler jusqu'à ce que le plateau n'ai plus de vie
     */
    public  void nouvelleVague(){
        plateau.genererNouveauxEnnemis();
    }
     public Boolean run() {

        boolean vagueTerminee = true;
        //while(plateau.pv > 0 ){
         //   vagueTerminee = true;
            /*for(Case caseChemin: plateau.casesChemin){
                if (!caseChemin.getListEnnemis().isEmpty()){
                    vagueTerminee = false;
                    break;

                }
            }

            if(vagueTerminee){
                plateau.vague++;
                //TODO gestion des tours


                //plateau.genererNouveauxEnnemis();z
            }*/

            /*new AnimationTimer() {
                @Override public void handle(long currentNanoTime) {

                    //root2.getChildren().removeAll();
                    Label or = new Label( Integer.toString(plateau.or)+" fyugkhlk");
                    or.setFont(new Font(50));
                    or.setTextFill(Color.web("#FFFFFF"));
                    GridPane gridpane = new GridPane();
                    // gridpane.setPadding(new Insets(5));
                    gridpane.setHgap(10);
                    gridpane.setVgap(10);
                    Group test = new Group();
                    test.getChildren().add(gridpane);
                    stage.getScene().setRoot(test);
                   //stage.getScene() = new Scene(root2);



                    //root.getChildren().add(gridpane);

                    //stage.setScene(new Scene(root));



                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }.start();*/

            /*try {
                Thread.sleep(100 );
            } catch (InterruptedException e) {
                // Do nothing
            }*/

           //test();

         /*try {

             TimeUnit.NANOSECONDS.sleep(500);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }*/
            plateau.deplacerEnnemis();
         /*try {

             TimeUnit.NANOSECONDS.sleep(500);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }*/
            plateau.attaquer();
        /* try {

             TimeUnit.NANOSECONDS.sleep(500);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }*/

       // }
       // System.out.println("Vous avez perdu après " + plateau.vague + " tours");
        return true;

    }



    private void test() {
        Runnable command = new Runnable() {
        @Override
        public void run() {
           /* Group root = new Group();

            Canvas canvas = new Canvas (MAX,  MAX);
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            double coordoneeX = t.getSceneX();
                            double coordoneeY = t.getSceneY();
                            onclic(coordoneeX, coordoneeY);
                        }
                    });
            GraphicsContext gc = canvas.getGraphicsContext2D();
            drawShapes(gc, plateau.terrain);
            Label or = new Label( Integer.toString(plateau.or)+" Or");
            or.setFont(new Font(50));
            or.setTextFill(Color.web("#FFD700"));

            root.getChildren().add(canvas);
            GridPane gridpane = new GridPane();
            // gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);


            GridPane.setHalignment(or, HPos.CENTER);
            gridpane.add(or, 0, 0);*/

            //root.getChildren().get(gridpane);
            Group root = (Group)stage.getScene().getRoot();
            root.getChildren().removeAll();
            Label or = new Label( Integer.toString(plateau.or)+" Or");
            or.setFont(new Font(50));
            or.setTextFill(Color.web("#FFFFFF"));
            GridPane gridpane = new GridPane();
            // gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);


            GridPane.setHalignment(or, HPos.CENTER);
            gridpane.add(or, 0, 0);

            root.getChildren().add(gridpane);

            //stage.setScene(new Scene(root));


        }
        };
        command.run();
       /* try {
            command.wait(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    /*Service<Void> fileSaveService = new Service<Void>(){

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>(){

                @Override
                protected Void call() throws Exception {
                    // Sauvegarder le fichier ici.

                    return null;
                }
            };
        }
    };*/



}

