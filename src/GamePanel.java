import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;



public class GamePanel extends JPanel implements KeyListener, ActionListener{
    private int snake_length;
    private ArrayList<ArrayList<Integer>> snake_list1 = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> snake_list2 = new ArrayList<>();
    private int snake1_score;
    private int snake2_score;
    private String move1;
    private String move2;
    private boolean start;
    private boolean dead;
    private int food_X, food_Y;
    private Snake snake1 = new Snake(snake_length, snake_list1, snake1_score, move1, start, dead);
    private Snake snake2 = new Snake(snake_length, snake_list1, snake2_score, move2, start, dead);
    public Timer timer;
    public Random random = new Random();
    public JTextArea score1 = new JTextArea();
    public JTextArea score2 = new JTextArea();


    public GamePanel() {

        start = false;
        dead = false;
        snake1_score = 0;
        snake2_score = 0;
        snake_length = 3;
        food_X = 100 + 10 * random.nextInt(10);
        food_Y = 100 + 10 * random.nextInt(10);

        //Snake1
        snake_list1 = nestedList(100, 150, snake_list1);
        snake_list1 = nestedList(90, 150, snake_list1);
        snake_list1 = nestedList(80, 150, snake_list1);
        move1 = "R";

        snake1.setScore(snake1_score);
        snake1.setSnakeList(snake_list1);
        snake1.setDead(dead);
        snake1.setMove(move1);
        snake1.setStart(start);
        snake1.setSnake_length(snake_length);

        //Snake2
        snake_list2 = nestedList(100, 550, snake_list2);
        snake_list2 = nestedList(90, 550, snake_list2);
        snake_list2 = nestedList(80, 550, snake_list2);
        move2 = "R";

        snake2.setScore(snake2_score);
        snake2.setSnakeList(snake_list2);
        snake2.setDead(dead);
        snake2.setMove(move2);
        snake2.setStart(start);
        snake2.setSnake_length(snake_length);

        this.addKeyListener(this);
        this.requestFocus();
        this.setFocusable(true);


        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());

        JButton startGame= new JButton("START GAME");

        JButton quitGame= new JButton("QUIT GAME");

        JButton HELP= new JButton("HELP");

        JPanel that = this;

        startGame.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    start = true;
                    snake1.setStart(start);
                    snake2.setStart(start);
                    that.requestFocus();
                }
            }
        );

        HELP.addActionListener(new ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent e)
                                        {
                                            JOptionPane.showMessageDialog(null, "Player1: Red Head, Green body; Control: W, A, S, D \n" +
                                                    "Player2: Green Head, Red body; Control: Arrow keys\n" +
                                                    "Rules: \n" +
                                                    "1. Snakes must not collide with each other\n" +
                                                    "2. Snakes must not touch the blue border\n" +
                                                    "3. One point is added when a snake eats an apple\n" +
                                                    "4. A player wins when he reaches 13 points", "HELP", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
        );

        quitGame.addMouseListener(new MouseAdapter() {    // 对jb_button按钮添加监听事件
                @Override
                public void mouseClicked(MouseEvent e) {    // 当鼠标点击时
                    System.exit(0);        // 退出
                }
            }
        );

        jPanel.add(startGame, BorderLayout.CENTER);
        jPanel.add(quitGame, BorderLayout.SOUTH);
        jPanel.add(HELP, BorderLayout.NORTH);
        jPanel.add(score1, BorderLayout.WEST);
        jPanel.add(score2, BorderLayout.EAST);
        jPanel.setSize(800,50);
        this.add(jPanel);

        timer = new Timer(100, this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        g.setColor(Color.BLUE);
        g.fillRect(0,90,820,10);
        g.fillRect(0,850,820,10);
        g.fillRect(820,90,10,800);
        g.fillRect(0,90,10,800);

        //Draw Game Screen
        g.setColor(Color.BLACK);
        g.fillRect(10,100,810,750);

        //Draw snake1
        Image.head.paintIcon(this, g, snake1.getSnakeList().get(0).get(0), snake1.getSnakeList().get(0).get(1));
        for(int i=1; i < snake1.getSnake_length() ;++i){
            Image.body.paintIcon(this, g, snake1.getSnakeList().get(i).get(0), snake1.getSnakeList().get(i).get(1));
        }

        //Draw snake2
        Image.body.paintIcon(this, g, snake2.getSnakeList().get(0).get(0), snake2.getSnakeList().get(0).get(1));
        for(int i=1; i < snake2.getSnake_length() ;++i){
            Image.head.paintIcon(this, g, snake2.getSnakeList().get(i).get(0), snake2.getSnakeList().get(i).get(1));
        }

        //Draw food
        Image.food.paintIcon(this, g, food_X, food_Y);
    }

    public ArrayList nestedList(Integer x, Integer y, ArrayList<ArrayList<Integer>> list2){
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(x);
        list1.add(y);
        list2.add(list1);
        return list2;
    }

    public ArrayList nestedList(Integer x, Integer y, Integer index, ArrayList<ArrayList<Integer>> list2){
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(x);
        list1.add(y);
        list2.add(index, list1);
        return list2;
    }

    public void grow(Snake snake) {
        if (snake.getSnakeList().get(0).get(0) == food_X && snake.getSnakeList().get(0).get(1) == food_Y){

            ArrayList<ArrayList<Integer>> temp = new ArrayList();
            try {
                temp = deepCopy(snake.getSnakeList());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            snake.setSnake_length(snake.getSnake_length() + 1);
            snake.setScore(snake.getScore() + 1);
            food_X = 100 + 10 * random.nextInt(73);
            food_Y = 100 + 10 * random.nextInt(68);
            snake.setSnakeList(nestedList(temp.get(0).get(0), temp.get(0).get(1), 1, snake.getSnakeList()));

            if (snake.getMove().equals("U")){
                snake.getSnakeList().get(0).set(1, temp.get(0).get(1) - 10);
            } else if (snake.getMove().equals("D")){
                snake.getSnakeList().get(0).set(1, temp.get(0).get(1) + 10);
            } else if (snake.getMove().equals("L")){
                snake.getSnakeList().get(0).set(0, temp.get(0).get(0) - 10);
            } else if (snake.getMove().equals("R")){
                snake.getSnakeList().get(0).set(0, temp.get(0).get(0) + 10);
            }
        }
    }

    public void move(Snake snake, String move) {
        if (snake.isStart() && !snake.isDead()) {
            snake.setMove(move);
            ArrayList<ArrayList<Integer>> temp = new ArrayList();
            try {
                temp = deepCopy(snake.getSnakeList());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i = snake.getSnake_length() - 1; i > 0; i--) {
                snake.getSnakeList().set(i, temp.get(i - 1));
            }

            if (snake.getMove().equals("U")) {
                snake.getSnakeList().get(0).set(1, snake.getSnakeList().get(0).get(1) - 10);
            } else if (snake.getMove().equals("D")) {
                snake.getSnakeList().get(0).set(1, snake.getSnakeList().get(0).get(1) + 10);
            } else if (snake.getMove().equals("L")) {
                snake.getSnakeList().get(0).set(0, snake.getSnakeList().get(0).get(0) - 10);
            } else if (snake.getMove().equals("R")) {
                snake.getSnakeList().get(0).set(0, snake.getSnakeList().get(0).get(0) + 10);
            }
        }
    }

    public void dead() {
        ArrayList<ArrayList<Integer>> list1 = snake1.getSnakeList();
        ArrayList<ArrayList<Integer>> list2 = snake2.getSnakeList();

        ArrayList<Integer> snake1_head = snake1.getSnakeList().get(0);
        ArrayList<Integer> snake2_head = snake2.getSnakeList().get(0);

        int snake1_headX = snake1_head.get(0);
        int snake1_headY = snake1_head.get(1);
        int snake2_headX = snake2_head.get(0);
        int snake2_headY = snake2_head.get(1);

        if (snake1_headX < 10 || snake1_headX > 810 || snake1_headY < 100 || snake1_headY > 840 ){
            snake1.setDead(true);
            snake1.setStart(false);
        }
        if (snake2_headX < 10 || snake2_headX > 810 || snake2_headY < 100 || snake2_headY > 840 ){
            snake2.setDead(true);
            snake2.setStart(false);
        }

        for(ArrayList<Integer> tmp : list1){
            int tmp_X = tmp.get(0);
            int tmp_Y = tmp.get(1);
            if (tmp_X == snake2_headX && tmp_Y == snake2_headY) {
                snake2.setDead(true);
                snake2.setStart(false);
            }
        }

        for(ArrayList<Integer> tmp : list2){
            int tmp_X = tmp.get(0);
            int tmp_Y = tmp.get(1);
            if (tmp_X == snake1_headX && tmp_Y == snake1_headY) {
                snake1.setDead(true);
                snake1.setStart(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode=e.getKeyCode();
        if(keycode == KeyEvent.VK_UP){
            move2 = "U";
        } else if(keycode == KeyEvent.VK_DOWN){
            move2 = "D";
        } else if(keycode == KeyEvent.VK_LEFT){
            move2 = "L";
        } else if(keycode == KeyEvent.VK_RIGHT){
            move2 = "R";
        } else if(keycode == KeyEvent.VK_S){
            move1 = "D";
        } else if(keycode == KeyEvent.VK_A){
            move1 = "L";
        } else if(keycode == KeyEvent.VK_D){
            move1 = "R";
        } else if(keycode == KeyEvent.VK_W){
            move1 = "U";
        }


        String snake1_move = snake1.getMove();
        String snake2_move = snake2.getMove();
        if((keycode == KeyEvent.VK_UP && snake2_move.equals("D")) || (keycode == KeyEvent.VK_DOWN && snake2_move.equals("U")) || (keycode == KeyEvent.VK_LEFT && snake2_move.equals("R")) || (keycode == KeyEvent.VK_RIGHT && snake2_move.equals("L"))){
            snake2.setDead(true);
            snake2.setStart(false);
        } else if((keycode == KeyEvent.VK_W && snake1_move.equals("D")) || (keycode == KeyEvent.VK_S && snake1_move.equals("U")) || (keycode == KeyEvent.VK_A && snake1_move.equals("R")) || (keycode == KeyEvent.VK_D && snake1_move.equals("L"))){
            snake1.setDead(true);
            snake1.setStart(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move(snake1, move1);
        grow(snake1);

        move(snake2, move2);
        grow(snake2);

        score1.setText("Player1 Score: " + snake1.getScore() + "");
        System.out.println(snake1.getScore());
        score2.setText("Player2 Score: " + snake2.getScore() + "");

        if (snake1.getScore() == 17 || snake2.getScore() == 17) {
            if (snake1.getScore() >= snake2.getScore()){
                JOptionPane.showMessageDialog(null, " Player1 Win ", "Game End", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, " Player2 Win ", "Game  End", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        }

        if (snake1.isDead() && snake2.isDead()) {
            JOptionPane.showMessageDialog(null, " LOSE ! ! ! ! ! ! ! ! ! ! ", "Game  End", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        dead();



        repaint();
    }

    public static ArrayList<ArrayList<Integer>> deepCopy(ArrayList<ArrayList<Integer>> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        ArrayList<ArrayList<Integer>> dest = (ArrayList<ArrayList<Integer>>) in.readObject();
        return dest;
    }
}
