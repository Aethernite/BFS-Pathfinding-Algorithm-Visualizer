package sample;

import javafx.animation.FillTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.MazeGenerators.MazeGenerator;
import sample.MazeGenerators.MazeGeneratorMIT;
import sample.PathfindingAlgorithms.BFS;

import java.util.List;
import java.util.stream.Collectors;


public class Controller {
    @FXML private Pane pane;
    private Rectangle[][] matrix = new Rectangle[16][40];
    private Rectangle start = null;
    private Rectangle end = null;
    private AlgorithmTool tool = AlgorithmTool.START;
    @FXML private Button startButton;
    @FXML private Button endButton;
    @FXML private Button wallButton;
    @FXML private Button startAlgorithm;
    @FXML private Button clearVisited;
    @FXML private Button generateMaze;
    @FXML private Button clearGrid;


    @FXML
    public void initialize(){
        initMatrix();
    }


    @FXML public void runAlgorithm(){
        if(start!=null && end!=null) {
            BFS dj = new BFS(matrix);
            Thread th = new Thread(dj);
            th.start();
        }
    }

    private void initMatrix(){
        ObservableList<Node> children = pane.getChildren();
        List<Rectangle> list = children
                .stream()
                .map(e -> (Rectangle) e)
                .collect(Collectors.toList());
        int count = 0;
        for(int i=0; i<16; i++){
            for(int j=0; j<40; j++){
                Rectangle r = list.get(count++);
                r.setOnMouseClicked((e) -> setNode(r));
                r.setOnMouseDragEntered(e -> setNode(r));
                matrix[i][j] = r;
            }
        }
        pane.setOnDragDetected(e -> pane.startFullDrag());
    }

    public void setNode(Rectangle rectangle){
        switch(tool){
            case START: {
                if (start != null) {
                    FillTransition ft = new FillTransition(Duration.millis(400), start, Color.GREEN, Color.TRANSPARENT);
                    ft.play();
                }
                FillTransition ft = new FillTransition(Duration.millis(400), rectangle, Color.TRANSPARENT, Color.GREEN);
                ft.play();
                start = rectangle;
            }
                break;
            case END: {
                if (end != null) {
                    FillTransition ft = new FillTransition(Duration.millis(400), end, Color.RED, Color.TRANSPARENT);
                    ft.play();
                }
                FillTransition ft = new FillTransition(Duration.millis(400), rectangle, Color.TRANSPARENT, Color.RED);
                ft.play();
                end = rectangle;
            }
                break;
            case WALL: {
                if (rectangle.getFill() == Color.BLACK) {
                    FillTransition ft = new FillTransition(Duration.millis(400), rectangle, Color.BLACK, Color.TRANSPARENT);
                    ft.play();
                } else {
                    FillTransition ft = new FillTransition(Duration.millis(400), rectangle, Color.TRANSPARENT, Color.BLACK);
                    ft.play();
                }
            }
                break;
        }
    }


    public void changeToolToStart(){
        tool = AlgorithmTool.START;
    }

    public void changeToolToEnd(){
        tool = AlgorithmTool.END;
    }

    public void changeToolToWall(){
        tool = AlgorithmTool.WALL;
    }


    @FXML public void clearAllVisitedNodes(){
        for(int i=0; i<16; i++){
            for(int j=0; j<40; j++){

                if(matrix[i][j].getFill() == Color.SKYBLUE || matrix[i][j].getFill() == Color.YELLOW){
                    matrix[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }
    }

    @FXML public void clearGrid(){
        for(int i=0; i<16; i++){
            for(int j=0; j<40; j++){
                    matrix[i][j].setFill(Color.TRANSPARENT);
            }
        }
    }
    @FXML public void generateMaze(){
        MazeGeneratorMIT mazeGenerator = new MazeGeneratorMIT(matrix,false);
        Thread th = new Thread(mazeGenerator);
        th.start();
    }

}
