package sample.PathfindingAlgorithms;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Thread.sleep;

// Java implementation of BFS (breadth first search) Algorithm
public class BFS implements Runnable  {
    char[][] matrix = new char[16][40];
    Rectangle[][] recMatrix;
    Rectangle start;
    Rectangle end;
    int startX;
    int startY;
    int endX;
    int endY;
    int[][] distanceMatrix = new int[16][40];
    boolean[][] visited = new boolean[16][40];
    public BFS(Rectangle[][] m) {
        for(int i=0; i<16; i++){
            for(int j=0; j<40; j++){

                if(m[i][j].getFill() == Color.BLACK){
                    matrix[i][j] = '0';
                }
                else if(m[i][j].getFill() == Color.TRANSPARENT){
                    matrix[i][j] = '*';
                }
                else if(m[i][j].getFill() == Color.GREEN){
                    matrix[i][j] = 's';
                    start = m[i][j];
                    startX = i;
                    startY = j;
                }
                else if(m[i][j].getFill() == Color.RED){
                    matrix[i][j] = 'd';
                    end = m[i][j];
                    endX = i;
                    endY = j;
                }
            }
        }
        recMatrix = m;
    }


    @Override
    public void run(){
        try {
            int distance = minDistance();
            if(distance!=-1) {
                List<Rectangle> recs = backtrack();
                colorPath(recs);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int minDistance() throws InterruptedException {
       QItem source = new QItem(0,0,0);
       visited = new boolean[16][40];
       for(int i=0; i<16; i++){
           for(int j=0; j<40; j++){
               if(matrix[i][j] == '0'){
                   visited[i][j] = true;
               }
               else{
                   visited[i][j] = false;
               }


               if(matrix[i][j] =='s'){
                   source.setRow(i);
                   source.setCol(j);
               }
           }
       }

       Queue<QItem> queue = new LinkedList<>();
       queue.offer(source);
       visited[source.getRow()][source.getCol()] = true;
       distanceMatrix[source.getRow()][source.getCol()] = 0;

       int speed = 10;
       while(!queue.isEmpty()) {
           QItem p = queue.peek();
           queue.poll();
           // Destination found;
           if (matrix[p.getRow()][p.getCol()] == 'd') {
               distanceMatrix[p.getRow()][p.getCol()] = p.getDist();
               return p.getDist();
           }

           //Moving UP
           sleep(speed);
           if (p.getRow() - 1 >= 0 && !visited[p.getRow() - 1][p.getCol()]) {
               queue.offer(new QItem(p.getRow() - 1, p.getCol(), p.getDist() + 1));
               visited[p.getRow() - 1][p.getCol()] = true;
               Rectangle r = recMatrix[p.getRow() - 1][p.getCol()];
               distanceMatrix[p.getRow() - 1][p.getCol()] = p.getDist()+1;
               FillTransition ft = new FillTransition(Duration.millis(400), r, Color.TRANSPARENT, Color.SKYBLUE);
               ft.play();
           }

           sleep(speed);
           //Moving down
           if (p.getRow() + 1 < 16 &&
                   !visited[p.getRow() + 1][p.getCol()]) {
               queue.offer(new QItem(p.getRow() + 1, p.getCol(), p.getDist() + 1));
               visited[p.getRow() + 1][p.getCol()] = true;
               Rectangle r = recMatrix[p.getRow() + 1][p.getCol()];
               distanceMatrix[p.getRow() + 1][p.getCol()] = p.getDist()+1;
               FillTransition ft = new FillTransition(Duration.millis(400), r, Color.TRANSPARENT, Color.SKYBLUE);
               ft.play();
           }

           sleep(speed);
           if (p.getCol() - 1 >= 0 && !visited[p.getRow()][p.getCol() - 1]) {
               queue.offer(new QItem(p.getRow(), p.getCol() - 1, p.getDist() + 1));
               visited[p.getRow()][p.getCol() - 1] = true;
               Rectangle r = recMatrix[p.getRow()][p.getCol() - 1];
               distanceMatrix[p.getRow()][p.getCol() - 1] = p.getDist()+1;
               FillTransition ft = new FillTransition(Duration.millis(400), r, Color.TRANSPARENT, Color.SKYBLUE);
               ft.play();
           }

           sleep(speed);
           if (p.getCol() + 1 < 40 && !visited[p.getRow()][p.getCol() + 1]) {
               queue.offer(new QItem(p.getRow(), p.getCol() + 1, p.getDist() + 1));
               visited[p.getRow()][p.getCol() + 1] = true;
               Rectangle r = recMatrix[p.getRow()][p.getCol() + 1];
               distanceMatrix[p.getRow()][p.getCol() + 1] = p.getDist()+1;
               FillTransition ft = new FillTransition(Duration.millis(400), r, Color.TRANSPARENT, Color.SKYBLUE);
               ft.play();
           }
           System.out.println(p.getDist());

       }
       return -1;
    }
private List<Rectangle> backtrack(){
        List<Rectangle> path = new ArrayList<Rectangle>();
 for(int i =0; i<16; i++){
     for(int j=0; j<40; j++){
         System.out.print(distanceMatrix[i][j] +  " ");
     }
     System.out.println();
 }
    int currentX=endX, currentY=endY;
 while(true){
     if(currentX==startX && currentY==startY){
         break;
     }
     List<QItem> neighbours = new ArrayList<>();
     //UP
     if(currentX - 1 >= 0 && visited[currentX - 1][currentY] && recMatrix[currentX-1][currentY].getFill() != Color.BLACK){
         neighbours.add(new QItem(currentX-1,currentY,distanceMatrix[currentX-1][currentY]));
     }
     //Down
     if(currentX + 1 < 16 && visited[currentX + 1][currentY] && recMatrix[currentX+1][currentY].getFill() != Color.BLACK){
         neighbours.add(new QItem(currentX+1,currentY,distanceMatrix[currentX+1][currentY]));
     }
     if(currentY - 1 >= 0 && visited[currentX][currentY - 1] && recMatrix[currentX][currentY-1].getFill() != Color.BLACK){
         neighbours.add(new QItem(currentX,currentY-1,distanceMatrix[currentX][currentY-1]));
     }
     if(currentY + 1 < 40 && visited[currentX][currentY + 1] && recMatrix[currentX][currentY+1].getFill() != Color.BLACK){
         neighbours.add(new QItem(currentX,currentY+1,distanceMatrix[currentX][currentY+1]));
     }

     neighbours.sort(Comparator.comparing(QItem::getDist));


     currentX = neighbours.get(0).getRow();
     currentY = neighbours.get(0).getCol();
     path.add(recMatrix[currentX][currentY]);
 }
 return path;
}

public void colorPath(List<Rectangle> list){
        for(Rectangle r: list){
            FillTransition ft = new FillTransition(Duration.millis(400), r, (Color)r.getFill(), Color.YELLOW);
            ft.play();
        }
    FillTransition ft = new FillTransition(Duration.millis(400),  recMatrix[endX][endY], (Color) recMatrix[endX][endY].getFill(), Color.YELLOW);
    ft.play();
}
public int manhattanDistance(){
        return Math.abs(startX-endX) + Math.abs(startY-endY);
}
}

