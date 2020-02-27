import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class
 * This extends from the JavaFX Application
 * @author Chelsea Li
 */
public class Lab4 extends Application {

    private int positionX = -1; //x-coordinate of player
    private int positionY = -1; //y-coordinate of player
    private ArrayList<ArrayList<Integer>> robotPos = new ArrayList<ArrayList<Integer>>(); //Coordinates of the robots

    //Score, steps, seconds keeping
    private int scoreCount = 0;
    private int stepCount = 0;
    private int count = 0;

    private Timeline theTime = new Timeline();

    private boolean change = false;

    private boolean done = true;

    //Alerts
    private Alert fileNotFound = new Alert(AlertType.ERROR);
    private Alert io = new Alert(AlertType.ERROR);

    //List of shuffled board coordinates
    private ArrayList<ArrayList<Integer>> coord = new ArrayList<ArrayList<Integer>>();

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
         launch(args);
    }
	
    /**
     * Creates main window
     * @param primaryStage
     */
	  @Override
    public void start(Stage primaryStage) {
    	//Window size
    	Group root = new Group();
    	Scene primaryScene = new Scene(root, 800, 600);
        primaryStage.setTitle("Robot Game Thing");

        //Game modes
        Label modeLab = new Label("Mode");
        modeLab.setFont(new Font(20));
        modeLab.setTranslateX(5);
        modeLab.setTranslateY(5);

        ComboBox<String> modes = new ComboBox<String>();
        modes.setTranslateX(65);
        modes.setTranslateY(5);
        modes.getItems().addAll(
            "Turn Based",
            "Time Based"
        );
        modes.getSelectionModel().selectFirst();

        //Difficulties
        Label difficultyLab = new Label("Difficulty");
        difficultyLab.setFont(new Font(20));
        difficultyLab.setTranslateX(5);
        difficultyLab.setTranslateY(35);

        ComboBox<String> difficulties = new ComboBox<String>();
        difficulties.setTranslateX(95);
        difficulties.setTranslateY(35);
        difficulties.getItems().addAll(
            "Easy",
            "Medium",
            "Hard"
        );
        difficulties.getSelectionModel().selectFirst();

        //Board sizes
        Label boardLab = new Label("Board Size");
        boardLab.setFont(new Font(20));
        boardLab.setTranslateX(5);
        boardLab.setTranslateY(65);

        ComboBox<String> boardSize = new ComboBox<String>();
        boardSize.setTranslateX(108);
        boardSize.setTranslateY(65);
        boardSize.getItems().addAll(
            "5x5",
            "10x10",
            "15x15"
        );
        boardSize.getSelectionModel().selectFirst();

        //Play button
        Button play = new Button("Play");
        play.setTranslateX(5);
        play.setTranslateY(105);

        fileNotFound.setContentText("Unable to open file 'HighScores.txt'");

        io.setContentText("Error reading file 'HighScores.txt'");

        /** 
         * When play button is pressed, the game window will appear.
         * Only one game window at a time.
         */
        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                String[] size = boardSize.getValue().toString().split("x");

                if (done) {
                    count = 0;
                    stepCount = 0;
                    scoreCount = 0;

                    Game(modes.getValue().toString(), difficulties.getValue().toString(), Integer.valueOf(size[0]));

                    done = false;
                }
            }
        });

        root.getChildren().addAll(modeLab, modes, difficultyLab, difficulties, boardLab, boardSize, play);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(false); //Preventing the window from resizing
	  }

    /**
     * Creates game window
     * @param mode
     * @param difficulty
     * @param boardSize
     */
    private void Game(String mode, String difficulty, int boardSize) {
        //Window settings
        Group root0 = new Group();
        Scene scene0 = new Scene(root0, 800, 600);
        Stage stage0 = new Stage();

        Rectangle backBoard = new Rectangle(10, 10, 375 + ((boardSize + 1) * 3), 375 + ((boardSize + 1) * 3)); //Back board

        //Score board
        Label score = new Label("Score: 0");
        score.setTranslateX(10);
        score.setTranslateY(10 + 375 + ((boardSize + 1) * 3) + 5);

        //Steps counter
        Label steps = new Label("Steps: 0");
        steps.setTranslateX(10);
        steps.setTranslateY(10 + 375 + ((boardSize + 1) * 3) + 20);

        root0.getChildren().addAll(backBoard, score, steps);

        //Creating the game board
        ArrayList<ArrayList<Rectangle>> recList = new ArrayList<ArrayList<Rectangle>>();

        for (int x = 0; x < boardSize; x++) {
            recList.add(new ArrayList<Rectangle>());

            for (int i = 0; i < boardSize; i++) {
                recList.get(x).add(new Rectangle(13 + (x * (375.0 / boardSize)) + (3 * x), 13 + (i * (375.0 / boardSize)) + (3 * i), 375.0 / boardSize, 375.0 / boardSize));
                recList.get(x).get(i).setFill(Color.WHITE);
                recList.get(x).get(i).setStroke(Color.BLACK);
                root0.getChildren().add(recList.get(x).get(i));
            }  
        }

        //Mixing the coordinates of the rectangles
        for (int x = coord.size() - 1; x >= 0; x--) {
          coord.remove(x);
        }

        for (int x = 0; x < boardSize; x++) {
          for (int i = 0; i < boardSize; i++) {
            coord.add(new ArrayList<Integer>());
            coord.get((x * boardSize) + i).add(x);
            coord.get((x * boardSize) + i).add(i);
          }
        }

        Collections.shuffle(coord);

        //Setting the coordinates of the player
        positionX = coord.get(0).get(0);
        positionY = coord.get(0).get(1);

        ImageView player = new ImageView("spongebob.png");
        player.setFitWidth(375.0 / boardSize);
        player.setFitHeight(375.0 / boardSize);
        player.setTranslateX(13 + (positionX * (375.0 / boardSize)) + (3 * positionX));
        player.setTranslateY(13 + (positionY * (375.0 / boardSize)) + (3 * positionY));

        //Creating robots
        ArrayList<ImageView> robots = new ArrayList<ImageView>();
        int num = 0;

        /**
         * If the difficulty is easy then 2 robots are created
         * If the difficulty is medium then 3 robots are created
         * If the difficulty is hard then 4 robots are created
         */
        if (difficulty.equals("Easy")) {
            num = 2;
        } else if (difficulty.equals("Medium")) {
            num = 3;
        } else if (difficulty.equals("Hard")) {
            num = 4;
        }

        for (int x = 0; x < num; x++) {
            int robotX = coord.get(x + 1).get(0);
            int robotY = coord.get(x + 1).get(1);

            robotPos.add(new ArrayList<Integer>());
            robotPos.get(x).add(robotX);
            robotPos.get(x).add(robotY);

            robots.add(new ImageView("karen.png"));
            robots.get(x).setFitWidth(375.0 / boardSize);
            robots.get(x).setFitHeight(375.0 / boardSize);
            robots.get(x).setTranslateX(13 + (robotX * (375.0 / boardSize)) + (3 * robotX));
            robots.get(x).setTranslateY(13 + (robotY * (375.0 / boardSize)) + (3 * robotY));
            root0.getChildren().add(robots.get(x));
        }

        String line = null;

        //Creating the score table
        final ObservableList<Scores> data = FXCollections.observableArrayList();
        final TableView<Scores> info = new TableView<Scores>(data);

        TableColumn<Scores, String> placesCol = new TableColumn<Scores, String>();
        placesCol.setMinWidth(50);
        placesCol.setCellValueFactory(new PropertyValueFactory<Scores, String>("place"));

        TableColumn<Scores, String> namesCol = new TableColumn<Scores, String>("Names");
        namesCol.setMinWidth(100);
        namesCol.setCellValueFactory(new PropertyValueFactory<Scores, String>("name"));

        TableColumn<Scores, String> scoresCol = new TableColumn<Scores, String>("Scores");
        scoresCol.setMinWidth(100);
        scoresCol.setCellValueFactory(new PropertyValueFactory<Scores, String>("score"));

        info.setLayoutX(475);
        info.setLayoutY(10);
        info.setPrefWidth(290);
        info.setPrefHeight(375 + ((boardSize + 1) * 3));

        info.getColumns().add(placesCol);
        info.getColumns().add(namesCol);
        info.getColumns().add(scoresCol);

        root0.getChildren().addAll(info, player);

        info.setFocusTraversable(false);

        //Entering the past scores
        File f = new File("HighScores.txt");

        //If the file doesn't exist, the program will create a new one
        try {
          if (!f.exists()) {
            f.createNewFile();
          }
        } catch(FileNotFoundException ex) {
            fileNotFound.show();
        } catch(IOException ex) {
            io.show();  
        }

        try {
            FileReader fileReader = new FileReader("HighScores.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String[] id = line.split("\\|");
                data.add(new Scores(Integer.valueOf(id[0]), id[1], Integer.valueOf(id[2])));

            }
   
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            fileNotFound.show();
        } catch(IOException ex) {
            io.show();  
        }

        //When the game window closes
        stage0.setOnCloseRequest(event -> {
            done = true;
            clear(robots);
        });

        //Calls timer is the mode is time based
        if (mode.equals("Time Based")) {
            timer(root0, scene0, stage0, boardSize, player, robots, score, steps);
        }

        controls(root0, scene0, stage0,boardSize, player, robots, score, steps); //Setting up the controls

        stage0.setScene(scene0);
        stage0.show();
        stage0.setResizable(false); //Preventing the window from resizing
    }

    /**
     * Sets up the controls
     * @param root0
     * @param scene0
     * @param stage0
     * @param boardSize
     * @param player
     * @param robots
     * @param score
     * @param steps
     */
    private void controls(Group root0, Scene scene0, Stage stage0, int boardSize, ImageView player, ArrayList<ImageView> robots, Label score, Label steps) {
        scene0.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            /**
             * If the Q key is pressed then the player will move diagonally up left.
             * This only works if the player isn't at the top left corner of the board.
             */
            if (key.getCode() == KeyCode.Q) {
                if (positionX > 0 && positionY > 0) {
                    player.setTranslateX(13 + ((positionX - 1) * (375.0 / boardSize)) + (3 * (positionX - 1)));
                    player.setTranslateY(13 + ((positionY - 1) * (375.0 / boardSize)) + (3 * (positionY - 1)));

                    positionX = positionX - 1;
                    positionY = positionY - 1;

                    change = true;
                }
              /**
               * If the W key is pressed then the player will move diagonally up right.
               * This only works if the player isn't at the top right corner of the board.
               */
            } else if (key.getCode() == KeyCode.W) {
                if (positionX < boardSize - 1 && positionY > 0) {
                    player.setTranslateX(13 + ((positionX + 1) * (375.0 / boardSize)) + (3 * (positionX + 1)));
                    player.setTranslateY(13 + ((positionY - 1) * (375.0 / boardSize)) + (3 * (positionY - 1)));

                    positionX = positionX + 1;
                    positionY = positionY - 1;
                    
                    change = true;
                }

              /**
               * If the A key is pressed then the player will move diagonally down left.
               * This only works if the player isn't at the bottom left corner of the board.
               */
            } else if (key.getCode() == KeyCode.A) {
                if (positionX > 0 && positionY < boardSize - 1) {
                    player.setTranslateX(13 + ((positionX - 1) * (375.0 / boardSize)) + (3 * (positionX - 1)));
                    player.setTranslateY(13 + ((positionY + 1) * (375.0 / boardSize)) + (3 * (positionY + 1)));

                    positionX = positionX - 1;
                    positionY = positionY + 1;

                    change = true;
                }
              /**
               * If the S key is pressed then the player will move diagonally down right.
               * This only works if the player isn't at the bottom right corner of the board.
               */
            } else if (key.getCode() == KeyCode.S) {
                if (positionX < boardSize - 1 && positionY < boardSize - 1) {
                    player.setTranslateX(13 + ((positionX + 1) * (375.0 / boardSize)) + (3 * (positionX + 1)));
                    player.setTranslateY(13 + ((positionY + 1) * (375.0 / boardSize)) + (3 * (positionY + 1)));

                    positionX = positionX + 1;
                    positionY = positionY + 1;

                    change = true;
                }
              /**
               * If the UP key is pressed then the player will move upwards.
               * This only works if the player isn't at the top of the board.
               */             
            } else if (key.getCode() == KeyCode.UP) {
                if (positionY > 0) {
                    player.setTranslateY(13 + ((positionY - 1) * (375.0 / boardSize)) + (3 * (positionY - 1)));
                    
                    positionY = positionY - 1;

                    change = true;
                }
              /**
               * If the DOWN key is pressed then the player will move downwards.
               * This only works if the player isn't at the bottom of the board.
               */ 
            } else if (key.getCode() == KeyCode.DOWN) {
                if (positionY < boardSize - 1) {
                    player.setTranslateY(13 + ((positionY + 1) * (375.0 / boardSize)) + (3 * (positionY + 1)));
                
                    positionY = positionY + 1; 

                    change = true;
                }
              /**
               * If the RIGHT key is pressed then the player will move to the right.
               * This only works if the player isn't at the right edge of the board.
               */ 
            } else if (key.getCode() == KeyCode.RIGHT) {
                if (positionX < boardSize - 1) {
                    player.setTranslateX(13 + ((positionX + 1) * (375.0 / boardSize)) + (3 * (positionX + 1)));
                    
                    positionX = positionX + 1; 

                    change = true;              
                }
              /**
               * If the LEFT key is pressed then the player will move left.
               * This only works if the player isn't at the left edge of the board.
               */ 
            } else if (key.getCode() == KeyCode.LEFT) {
                if (positionX > 0) {
                    player.setTranslateX(13 + ((positionX - 1) * (375.0 / boardSize)) + (3 * (positionX - 1)));
                
                    positionX = positionX - 1;

                    change = true;
                }
            }

            count = 0;

            if (change) { //When the character changes position
                //The robots changes positions
                for (int x = 0; x < robots.size(); x++) {
                    find((double) robotPos.get(x).get(0), (double) robotPos.get(x).get(1), x, boardSize);

                    robots.get(x).setTranslateX(13 + (robotPos.get(x).get(0) * (375.0 / boardSize)) + (3 * robotPos.get(x).get(0)));
                    robots.get(x).setTranslateY(13 + (robotPos.get(x).get(1) * (375.0 / boardSize)) + (3 * robotPos.get(x).get(1)));
                }

                //Every move is one step and one point
                scoreCount++;
                stepCount++;

                //Checking if the robots hit each other or the player is consumed
                if (check(root0, robots)) {
                    if (robots.size() == 0) { //If the player wins
                        add(true);
                    } else { //If the player loses
                        add(false);
                    }

                    stage0.close();
                    clear(robots);
                } else {
                    if (robots.size() == 1) { //If there's only one robot left on the board
                        int[] rand = summon(boardSize);
                        //Summoning another robot
                        int robotX = rand[0];
                        int robotY = rand[1];

                        robots.add(new ImageView("karen.png"));
                        robots.get(robots.size() - 1).setFitWidth(375.0 / boardSize);
                        robots.get(robots.size() - 1).setFitHeight(375.0 / boardSize);
                        robots.get(robots.size() - 1).setTranslateX(13 + (robotX * (375.0 / boardSize)) + (3 * robotX));
                        robots.get(robots.size() - 1).setTranslateY(13 + (robotY * (375.0 / boardSize)) + (3 * robotY));

                        robotPos.add(new ArrayList<Integer>());
                        robotPos.get(robotPos.size() - 1).add(robotX);
                        robotPos.get(robotPos.size() - 1).add(robotY);

                        root0.getChildren().add(robots.get(robots.size() - 1));
                    }
                }

                score.setText("Score: " + String.valueOf(scoreCount));
                steps.setText("Steps: " + String.valueOf(stepCount));

                change = false;
            }
        }); 

    }

    /**
     * Finding the best move for the robots
     * @param locationX
     * @param locationY
     * @param index
     * @param boardSize
     */
    private void find(double locationX, double locationY, int index, int boardSize) {
        ArrayList<ArrayList<Double>> mines = new ArrayList<ArrayList<Double>>(); //Keeping track of the possible moves and their distance from the player

        for (int x = 0; x < 9; x++) {
            //The robot's current position
            mines.add(new ArrayList<Double>());

            mines.get(x).add(locationX);
            mines.get(x).add(locationY);
            mines.get(x).add(Math.sqrt(Math.pow((locationX - positionX), 2) + Math.pow((locationY - positionY), 2)));
        }
        
        /**
         * The distance when the robot moves diagonally up left
         */
        if (locationX > 0 && locationY > 0) {
            mines.get(1).set(0, locationX - 1);
            mines.get(1).set(1, locationY - 1);
            mines.get(1).set(2, Math.sqrt(Math.pow(((locationX - 1) - positionX), 2) + Math.pow(((locationY - 1) - positionY), 2)));
        }
 
         /**
         * The distance when the robot moves diagonally down left
         */
        if (locationX > 0 && locationY < boardSize - 1) {
            mines.get(2).set(0, locationX - 1);
            mines.get(2).set(1, locationY + 1);
            mines.get(2).set(2, Math.sqrt(Math.pow(((locationX - 1) - positionX), 2) + Math.pow(((locationY + 1) - positionY), 2)));
        }

        /**
         * The distance when the robot moves diagonally up right
         */
        if (locationX < boardSize - 1 && locationY > 0) {
            mines.get(3).set(0, locationX + 1);
            mines.get(3).set(1, locationY - 1);
            mines.get(3).set(2, Math.sqrt(Math.pow(((locationX + 1) - positionX), 2) + Math.pow(((locationY - 1) - positionY), 2)));
        }
        
        /**
         * The distance when the robot moves diagonally down right
         */
        if (locationX < boardSize - 1 && locationY < boardSize - 1) {
            mines.get(4).set(0, locationX + 1);
            mines.get(4).set(1, locationY + 1);
            mines.get(4).set(2, Math.sqrt(Math.pow(((locationX + 1) - positionX), 2) + Math.pow(((locationY + 1) - positionY), 2)));
        }

        /**
         * The distance when the robot moves to the left
         */
        if (locationX > 0) {
            mines.get(5).set(0, locationX - 1);
            mines.get(5).set(1, locationY);
            mines.get(5).set(2, Math.sqrt(Math.pow(((locationX - 1) - positionX), 2) + Math.pow((locationY - positionY), 2)));
        }

        /**
         * The distance when the robot moves to the right
         */
        if (locationX < boardSize - 1) {
            mines.get(6).set(0, locationX + 1);
            mines.get(6).set(1, locationY);
            mines.get(6).set(2, Math.sqrt(Math.pow(((locationX + 1) - positionX), 2) + Math.pow((locationY - positionY), 2)));
        }
        
        /**
         * The distance when the robot moves upwards
         */
        if (locationY > 0) {
            mines.get(7).set(0, locationX);
            mines.get(7).set(1, locationY - 1);
            mines.get(7).set(2, Math.sqrt(Math.pow((locationX - positionX), 2) + Math.pow(((locationY - 1) - positionY), 2)));
        }
        
        /**
         * The distance when the robot moves downwards
         */
        if (locationY < boardSize - 1) {
            mines.get(8).set(0, locationX);
            mines.get(8).set(1, locationY + 1);
            mines.get(8).set(2, Math.sqrt(Math.pow((locationX - positionX), 2) + Math.pow(((locationY + 1) - positionY), 2)));
        }

        //Looking for the shortest distance
        double minX = mines.get(0).get(0);
        double minY = mines.get(0).get(1);
        double min = mines.get(0).get(2);

        for (int x = 1; x < 9; x++) {
            if (mines.get(x).get(2) < min) {
                minX = mines.get(x).get(0);
                minY = mines.get(x).get(1);
                min = mines.get(x).get(2);
            }
        }

        robotPos.get(index).set(0, (int) minX);
        robotPos.get(index).set(1, (int) minY);
    }

    /**
     * Checking if the robots collided with each other or the player
     * @param root0
     * @param robots
     * @return boolean
     */
    private boolean check(Group root0, ArrayList<ImageView> robots) {
        ArrayList<Integer> intList = new ArrayList<Integer>();

        for (int x = 0; x < robots.size(); x++) {
            if (robotPos.get(x).get(0) == positionX && robotPos.get(x).get(1) == positionY) { //If one of the robots collided witht the player
                return true;
            } else {
                if (x + 1 < robots.size()) {
                    for (int i = x + 1; i < robots.size(); i++) {
                        if (robotPos.get(x).equals(robotPos.get(i))) { //Keeping track of which robots are colliding with each other
                            intList.add(i);

                            if (!intList.contains(x)) {
                                intList.add(x);
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(intList);

        /**
         * Removing the robots that have collided with each other
         * Removing a robot adds 10 points
         */
        for (int x = intList.size() - 1; x >= 0; x--) {
            root0.getChildren().remove(robots.get(x));
            robots.remove(x);
            robotPos.remove(x);

            scoreCount = scoreCount + 10;
        }

        if (robots.size() == 0) { //If all of the robots are gone then the player wins
            return true;
        }

        return false;
    }

    /**
     * Summoning the new robot
     * @param boardSize
     * @return int
     */
    private int[] summon(int boardSize) {
        int[] set = new int[2];

        //Making sure that the robot is not summoned on the player or another robot
        for (int x = 0; x < coord.size(); x++) {
            if (coord.get(x).get(0) != positionX && coord.get(x).get(1) != positionY && coord.get(x).get(0) != robotPos.get(0).get(0) && coord.get(x).get(1) != robotPos.get(0).get(1)) {
                set[0] = coord.get(x).get(0);
                set[1] = coord.get(x).get(1);

                x = coord.size();
            }
        }

        return set;
    }

    /**
     * Allowing the user to enter their name for their score
     * @param win
     */
    private void add(boolean win) {
        //Window size
        Group root1 = new Group();
        Scene scene1 = new Scene(root1, 500, 200);
        Stage stage1 = new Stage();

        //Telling the user if they won or lost
        Label end = new Label();
        end.setTranslateX(5);
        end.setTranslateY(5);

        //Allowing the user to enter their name
        TextField userName = new TextField();
        userName.setPromptText("Enter your name");
        userName.setTranslateX(5);
        userName.setTranslateY(35);

        //Final score
        Label endScore = new Label("Score: " + String.valueOf(scoreCount));
        endScore.setTranslateX(5);
        endScore.setTranslateY(65);

        //Done button
        Button good = new Button("Done");
        good.setTranslateX(5);
        good.setTranslateY(100);

        if (win) {
            end.setText("You Win!");
        } else {
            end.setText("Game Over");
        }

        root1.getChildren().addAll(end, good, userName, endScore);

        /**
         * Saving the name and score
         * The user can close the window if they don't want to show their score
         */
        good.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                stage1.close();

                ArrayList<ArrayList<String>> text = new ArrayList<ArrayList<String>>();

                //Collecting the scores
                try {
                    FileReader fileReader = new FileReader("HighScores.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line = null;

                    while ((line = bufferedReader.readLine()) != null) {
                        String[] lineParts = line.split("\\|");
                        text.add(new ArrayList<String>());
                        text.get(text.size() - 1).add(lineParts[1]);
                        text.get(text.size() - 1).add(lineParts[2]);
                    }

                    text.add(new ArrayList<String>());
                    text.get(text.size() - 1).add(userName.getText().toString());
                    text.get(text.size() - 1).add(String.valueOf(scoreCount));

                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    fileNotFound.show(); 
                } catch (IOException ex) {
                    io.show();  
                }

                //Organizing the scofres
                for (int x = 0; x < text.size(); x++) {
                    if (x + 1 < text.size()) {
                        for (int i = x + 1; i < text.size(); i++) {
                            if (Integer.valueOf(text.get(x).get(1)) < Integer.valueOf(text.get(i).get(1))) {
                                ArrayList<String> old = text.get(x);

                                text.set(x, text.get(i));
                                text.set(i, old);
                            }
                        }
                    }
                }

                //Clearing the text file
                try {
                    PrintWriter write = new PrintWriter("HighScores.txt");
                    write.close();
                } catch (FileNotFoundException ex) {
                    fileNotFound.show();
                }

                //Writing the scores
                try {
                    FileWriter fileWriter = new FileWriter("HighScores.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    for (int i = 0; i < text.size(); i++) {
                        bufferedWriter.write((i + 1) + "|" + text.get(i).get(0) + "|" + text.get(i).get(1) + "\n");
                    }
                   
                    bufferedWriter.close();
                } catch (FileNotFoundException ex) {
                    fileNotFound.show();
                } catch (IOException ex) {
                    io.show();    
                }

                done = true;
            }
        });

        stage1.setOnCloseRequest(event -> {
            done = true;
        });

        stage1.setScene(scene1);
        stage1.show();
        stage1.setResizable(false); //Preventing the window from resizing
    }

    /**
     * Creating the timer
     * @param root0
     * @param scene0
     * @param stage0
     * @param boardSize
     * @param player
     * @param robots
     * @param score
     * @param steps
     */
    private void timer(Group root0, Scene scene0, Stage stage0, int boardSize, ImageView player, ArrayList<ImageView> robots, Label score, Label steps) {
        theTime.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (done) { //If the game ends, the timer will stop
                        theTime.stop();
                    } else {
                        if (count == 5) { //The robots will move every 5 seconds
                            count = 0;

                            //The robots changes positions
                            for (int x = 0; x < robots.size(); x++) {
                                find((double) robotPos.get(x).get(0), (double) robotPos.get(x).get(1), x, boardSize);

                                robots.get(x).setTranslateX(13 + (robotPos.get(x).get(0) * (375.0 / boardSize)) + (3 * robotPos.get(x).get(0)));
                                robots.get(x).setTranslateY(13 + (robotPos.get(x).get(1) * (375.0 / boardSize)) + (3 * robotPos.get(x).get(1)));
                            }

                            //Checking if the robots hit each other or the player is consumed
                            if (check(root0, robots)) {
                                if (robots.size() == 0) { //If the player wins
                                    add(true);
                                } else { //If the player loses
                                    add(false);
                                }

                                stage0.close();
                                clear(robots);
                            } else {
                                if (robots.size() == 1) { //If there's only one robot left on the board
                                    int[] rand = summon(boardSize);

                                    //Summoning another robot
                                    int robotX = rand[0];
                                    int robotY = rand[1];

                                    robots.add(new ImageView("karen.png"));
                                    robots.get(robots.size() - 1).setFitWidth(375.0 / boardSize);
                                    robots.get(robots.size() - 1).setFitHeight(375.0 / boardSize);
                                    robots.get(robots.size() - 1).setTranslateX(13 + (robotX * (375.0 / boardSize)) + (3 * robotX));
                                    robots.get(robots.size() - 1).setTranslateY(13 + (robotY * (375.0 / boardSize)) + (3 * robotY));

                                    robotPos.add(new ArrayList<Integer>());
                                    robotPos.get(robotPos.size() - 1).add(robotX);
                                    robotPos.get(robotPos.size() - 1).add(robotY);

                                    root0.getChildren().add(robots.get(robots.size() - 1));
                                }
                            }

                            score.setText("Score: " + String.valueOf(scoreCount));
                            steps.setText("Steps: " + String.valueOf(stepCount));
                        }

                        count++;
                    }
                }
            }
        ));

        theTime.setCycleCount(Animation.INDEFINITE);
        theTime.play();
    }

    /**
     * Clearing the old data
     * @param robots
     */
    private void clear(ArrayList<ImageView> robots) {
        for (int x = robotPos.size() - 1; x >= 0; x--) {
            for (int i = robotPos.get(x).size() - 1; i >= 0; i--) {
                robotPos.get(x).remove(i);
            }

            robotPos.remove(x);
        }

        for (int x = robots.size() - 1; x >= 0; x--) {
            robots.remove(x);
        }
    }
}
