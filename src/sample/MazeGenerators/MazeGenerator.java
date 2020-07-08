package sample.MazeGenerators;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Arrays;

public class MazeGenerator implements Runnable{

    private Stack<Node> stack = new Stack<>();
    private Random rand = new Random();
    private int[][] maze;
    private Rectangle[][] matrix;

   public MazeGenerator(Rectangle[][] matrix) {
        maze = new int[16][40];
        this.matrix = matrix;
    }

    public void generateMaze() {
        stack.push(new Node(0,0));
        while (!stack.empty()) {
            Node next = stack.pop();
            if (validNextNode(next)) {
                maze[next.y][next.x] = 1;
                ArrayList<Node> neighbors = findNeighbors(next);
                randomlyAddNodesToStack(neighbors);
            }
        }
    }

    public int[][] getRawMaze() {
        return maze;
    }

    public String getSymbolicMaze() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 40; j++) {
                sb.append(maze[i][j] == 1 ? "*" : " ");
                sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean validNextNode(Node node) {
        int numNeighboringOnes = 0;
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && maze[y][x] == 1) {
                    numNeighboringOnes++;
                }
            }
        }
        return (numNeighboringOnes < 3) && maze[node.y][node.x] != 1;
    }

    private void randomlyAddNodesToStack(ArrayList<Node> nodes) {
        int targetIndex;
        while (!nodes.isEmpty()) {
            targetIndex = rand.nextInt(nodes.size());
            stack.push(nodes.remove(targetIndex));
        }
    }

    private ArrayList<Node> findNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(node, x, y)
                        && pointNotNode(node, x, y)) {
                    neighbors.add(new Node(x, y));
                }
            }
        }
        return neighbors;
    }

    private Boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < 40 && y < 16;
    }

    private Boolean pointNotCorner(Node node, int x, int y) {
        return (x == node.x || y == node.y);
    }

    private Boolean pointNotNode(Node node, int x, int y) {
        return !(x == node.x && y == node.y);
    }

    @Override
    public void run() {
        generateMaze();
        clearMatrix();
        fillMatrixWithMaze();
    }

    private void fillMatrixWithMaze() {
        for(int i=0; i<16; i++){
            for(int j=0; j<40;j++){
                if(maze[i][j]==1){
                    FillTransition ft = new FillTransition(Duration.millis(400), matrix[i][j], Color.TRANSPARENT, Color.BLACK);
                    ft.play();
                }
            }
        }
    }

    private void clearMatrix(){
        for(int i=0; i<16; i++){
            for(int j=0; j<40;j++){
                    FillTransition ft = new FillTransition(Duration.millis(400), matrix[i][j],(Color) matrix[i][j].getFill(), Color.TRANSPARENT);
                    ft.play();
                }
            }
        }
}