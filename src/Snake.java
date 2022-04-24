import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private int snake_length;
    private ArrayList<ArrayList<Integer>> snakeList;
    private int score;
    private String move;
    private boolean start;
    private boolean dead;

    public Snake(Integer snake_length, ArrayList<ArrayList<Integer>> snake, Integer score, String move, Boolean start, Boolean dead) {
        this.dead = dead;
        this.snake_length = snake_length;
        this.snakeList = snake;
        this.score = score;
        this.start = start;
        this.move = move;
    }

    public ArrayList<ArrayList<Integer>> getSnakeList() {
        return snakeList;
    }

    public void setSnakeList(ArrayList<ArrayList<Integer>> snake) {
        this.snakeList = snake;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return String.valueOf(this.score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSnake_length() {
        return snake_length;
    }

    public void setSnake_length(int snake_length) {
        this.snake_length = snake_length;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

}
