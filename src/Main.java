import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NNetwork model = new NNetwork(primaryStage);
        NNetworkController controller = new NNetworkController(model);
        controller.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

/*

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Main extends Application {

    Button button;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    PixelReader pixelReader;
    WritableImage writableImage = new WritableImage(150,150);
    GraphicsContext gc;
    Canvas canvas;
    double[][] input;
    double[][] W;
    NNetwork test;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        HBox root = new HBox();
        VBox button_layer = new VBox();
        final FileChooser fileChooser = new FileChooser();
        button_layer.setSpacing(5);
        button_layer.setAlignment(Pos.BASELINE_LEFT);
        root.setSpacing(5);
        StackPane holder = new StackPane();
        holder.setAlignment(Pos.TOP_LEFT);
        canvas = new Canvas(150,150);
        final Scene scene = new Scene(root, 300, 250);

        gc = canvas.getGraphicsContext2D();

        test = new NNetwork((int)canvas.getHeight(),(int)canvas.getWidth());
        button = new Button("Распознать");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.snapshot(null,writableImage);
                pixelReader = writableImage.getPixelReader();
                input=new double[(int)canvas.getHeight()][(int)canvas.getWidth()];
                for(int i=0;i<(int)canvas.getHeight();i++){
                   for(int j=0;j<(int)canvas.getWidth();j++){
                       Color color = pixelReader.getColor(i, j);
                        if((color.getBlue()!=1)&&(color.getGreen()!=1)&&(color.getRed()!=1)) {
                                input[i][j]=1;
                        } else input[i][j]=0;
                   }
                }
                test.NetworkEv(input);
            }
        });

        button1 = new Button("Обучить");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                test.NetworkWork();
            }
        });

        button2 = new Button("Стереть");

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            }
        });

        button3 = new Button("Загрузить");

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                W=new double[(int)canvas.getHeight()][(int)canvas.getWidth()];
                fileChooser.setTitle("Загрузить");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                   test.setW(readFile(file));
                }
            }
        });

        button4 = new Button("Сохранить файл");

        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    SaveFile(test.getW(), file);
                }
            }
        });

        canvas.addEventHandler(MouseEvent.ANY,mouseHandler);

        holder.getChildren().addAll(canvas);
        holder.setStyle("-fx-background-color: white");
        root.setStyle("-fx-background-color: lightgray");
        holder.setMaxSize(150,150);
        button_layer.getChildren().addAll(button,button1,button2,button3,button4);
        root.getChildren().addAll(holder,button_layer);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                gc.beginPath();
                gc.moveTo(mouseEvent.getX(), mouseEvent.getY());
                gc.stroke();
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                gc.lineTo(mouseEvent.getX(), mouseEvent.getY());
                gc.stroke();
            }

        }

    };

    private double[][] readFile(File file){
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            for(int i=0;i< canvas.getHeight();i++){
                for(int j=0;j< canvas.getWidth();j++){
                    if((text = bufferedReader.readLine())!=null){
                        W[i][j]=Double.valueOf(text);
                        System.out.println(W[i][j]);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return W;
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

*/
/*
}*/
