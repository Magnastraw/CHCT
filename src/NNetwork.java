import javafx.stage.Stage;

import java.util.Random;

public class NNetwork {
    private double[][] W;
    private double[][] X;
    private double S = 0;
    private double del;
    private Random random;
    private int size_x;
    private int size_y;
    private Stage primaryStage;

    public NNetwork(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void NNetworkSetUp(int size_x, int size_y) {
        this.size_x = size_x;
        this.size_y = size_y;
        W = new double[size_x][size_y];
        X = new double[size_x][size_y];
        random = new Random();
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                W[i][j] = -0.5 + (0.5 + 0.5) * random.nextDouble();
            }
        }
    }

    public void NetworkEv(double[][] input) {
        S = 0;
        X = input;
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                S += W[i][j] * X[i][j];
            }
        }
    }

    public String NetworkResult() {
        if (S >= 0) {
            del = 1;
            return "Крестик! S=" + S;
        } else {
            del = 0;
            return "Нолик! S=" + S;
        }
    }

    public void NetworkWork() {
        if (del == 0) {
            del = 1;
        } else if (del == 1) {
            del = -1;
        }
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                W[i][j] = W[i][j] + 0.5 * del * X[i][j];
            }
        }
    }

    public String getW() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                temp.append(W[i][j] + "\n");
            }
        }
        return temp.toString();
    }

    public void setW(double[][] W) {
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                this.W[i][j] = W[i][j];
            }
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
