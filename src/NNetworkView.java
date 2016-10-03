import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NNetworkView {

    private Button button_rec;
    private Button button_train;
    private Button button_clear;
    private Button button_load;
    private Button button_save;
    private Button button_exit;
    private Label text_result;
    private HBox root;
    private VBox buttons;
    private StackPane holder;
    private Scene scene;
    private PixelReader pixelReader;
    private WritableImage writableImage = new WritableImage(150, 150);
    private GraphicsContext gc;
    private Canvas canvas;
    private double[][] input_X;
    private double[][] input_W;
    private NNetwork network;


    public NNetworkView(NNetwork network) {
        this.network = network;


        root = new HBox();
        buttons = new VBox();
        holder = new StackPane();

        buttons.setSpacing(5);
        buttons.setAlignment(Pos.BASELINE_LEFT);

        root.setSpacing(5);

        holder.setAlignment(Pos.TOP_LEFT);
        holder.setMaxSize(150, 150);

        canvas = new Canvas(150, 150);

        network.NNetworkSetUp((int) canvas.getHeight(), (int) canvas.getWidth());

        gc = canvas.getGraphicsContext2D();

        button_rec = new Button("Распознать");
        button_train = new Button("Обучить");
        button_clear = new Button("Стереть");
        button_load = new Button("Загрузить");
        button_save = new Button("Сохранить");
        button_exit = new Button("Выйти");

        button_rec.setMaxWidth(Double.MAX_VALUE);
        button_train.setMaxWidth(Double.MAX_VALUE);
        button_clear.setMaxWidth(Double.MAX_VALUE);
        button_load.setMaxWidth(Double.MAX_VALUE);
        button_save.setMaxWidth(Double.MAX_VALUE);
        button_exit.setMaxWidth(Double.MAX_VALUE);


        text_result = new Label();

        canvas.addEventHandler(MouseEvent.ANY, mouseHandler);

        holder.getChildren().addAll(canvas);

        holder.setStyle("-fx-background-color: white");
        root.setStyle("-fx-background-color: lightgray");


        buttons.getChildren().addAll(button_rec, button_train, button_clear, button_load, button_save, button_exit);
        root.getChildren().addAll(holder, buttons, text_result);

        scene = new Scene(root, 450, 250);
    }

    public void show(final Stage stage) {
        stage.setTitle("Перцептронный нейрон");
        stage.setScene(scene);
        stage.show();
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

    public void setUpButtons(final Stage primaryStage) {

        final FileChooser fileChooser = new FileChooser();

        button_rec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.snapshot(null, writableImage);
                pixelReader = writableImage.getPixelReader();
                input_X = new double[(int) canvas.getHeight()][(int) canvas.getWidth()];
                for (int i = 0; i < (int) canvas.getHeight(); i++) {
                    for (int j = 0; j < (int) canvas.getWidth(); j++) {
                        Color color = pixelReader.getColor(i, j);
                        if ((color.getBlue() != 1) && (color.getGreen() != 1) && (color.getRed() != 1)) {
                            input_X[i][j] = 1;
                        } else input_X[i][j] = 0;
                    }
                }
                network.NetworkEv(input_X);
                text_result.setText(network.NetworkResult());
            }
        });


        button_train.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                network.NetworkWork();
            }
        });

        button_clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });

        button_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input_W = new double[(int) canvas.getHeight()][(int) canvas.getWidth()];
                fileChooser.setTitle("Загрузить");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    network.setW(readFile(file));
                }
            }
        });

        button_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileChooser.setTitle("Сохранить");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    SaveFile(network.getW(), file);
                }
            }
        });

        button_exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    private double[][] readFile(File file) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            for (int i = 0; i < canvas.getHeight(); i++) {
                for (int j = 0; j < canvas.getWidth(); j++) {
                    if ((text = bufferedReader.readLine()) != null) {
                        input_W[i][j] = Double.valueOf(text);
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
        return input_W;
    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
