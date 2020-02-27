import java.io.File;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
public class Lab3 extends Application {

    private boolean locked = false;
    private Rectangle[][] recList = new Rectangle[9][9];
    private Boolean[][] recBool = new Boolean[9][9];
    private Label[][] recNum = new Label[9][9];
    private Boolean[][] numBool = new Boolean[9][9];
    private int recIndex = 0;
    private int recIn = 0;
    private int cycleSpeed;
    private int timeRow;
    private int timeColumn;
    private int timeIndex;
    private boolean playing;
    private boolean back;
    private boolean soundPlaying;
    private boolean solveState = false;

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
    	Scene primaryScene = new Scene(root, 733, 800);
        primaryStage.setTitle("Sudoku Solver");

        Button solve = new Button("Solve");
        solve.setTranslateX(5);
        solve.setTranslateY(755);
        solve.setVisible(false);

        ImageView pause = new ImageView("pauseButton.png");
        pause.setFitWidth(30);
        pause.setFitHeight(30);

        ImageView play = new ImageView("playButton.png");
        play.setFitWidth(30);
        play.setFitHeight(30);

        Button pausePlay = new Button();
        pausePlay.setTranslateX(5);
        pausePlay.setTranslateY(750);
        pausePlay.setVisible(false);

        ImageView stop = new ImageView("stopButton.png");
        stop.setFitWidth(30);
        stop.setFitHeight(30);       

        Button stopB = new Button();
        stopB.setGraphic(stop);
        stopB.setTranslateX(60);
        stopB.setTranslateY(750);
        stopB.setVisible(false);

        Button reset = new Button("Reset");
        reset.setTranslateX(5);
        reset.setTranslateY(755);
        reset.setVisible(false);

        ImageView soundOn = new ImageView("soundOn.png");
        soundOn.setFitWidth(30);
        soundOn.setFitHeight(30);

        ImageView soundOff = new ImageView("soundOff.png");
        soundOff.setFitWidth(30);
        soundOff.setFitHeight(30);

        Button soundB = new Button();
        soundB.setTranslateX(115);
        soundB.setTranslateY(750);
        soundB.setVisible(false);

        Timeline solving = new Timeline();

        Media sound = new Media(new File("thinkingMusic.m4a").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        Alert error = new Alert(AlertType.ERROR);
        error.setContentText("Invalid input");

        for (int x = 0; x < 3; x++) {
            for (int i = 0; i < 3; i++) {
                Rectangle r = new Rectangle(x * 245, i * 245, 245, 245);
                root.getChildren().add(r);
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                Rectangle rec = new Rectangle();
                rec.setTranslateX(i * 80 + ((i + 3) / 3 * 3));
                rec.setTranslateY(x * 80 + ((x + 3) / 3 * 3));
                rec.setWidth(80);
                rec.setHeight(80);
                rec.setFill(Color.WHITE);
                rec.setStroke(Color.BLACK);

                recList[x][i] = rec;
                recBool[x][i] = false;
                recNum[x][i] = new Label();
                numBool[x][i] = false;

                root.getChildren().addAll(rec, recNum[x][i]);

                final int predex = x;
                final int index = i;

                recList[predex][index].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (!solveState) {
                            recList[predex][index].setFill(Color.GREEN);
                        }
                    }
                });

                recList[predex][index].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (!recBool[predex][index]) {
                            recList[predex][index].setFill(Color.WHITE);
                        }
                    }
                });

                recList[predex][index].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (!solveState) {
                            if (mouseEvent.getClickCount() == 1) {
                                clear();

                                recList[predex][index].setFill(Color.GREEN);
                                recBool[predex][index] = true;
                            } else if (mouseEvent.getClickCount() == 2) {
                                if (recList[predex][index].getFill().equals(Color.GREEN)) {
                                    recList[predex][index].setFill(Color.WHITE);
                                    recBool[predex][index] = false;
                                } else {
                                    recList[predex][index].setFill(Color.GREEN);
                                    recBool[predex][index] = true;
                                }
                            } 
                        }
                    }
                });
            }
        }

        primaryScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clear();
            }
        });

        primaryScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (!solveState) {
                int counter0 = -1;
                int counter1 = -1;

                for (int x = 0; x < recBool.length; x++) {
                    for (int i = 0; i < recBool[x].length; i++) {
                        if (recBool[x][i]) {
                            counter0 = x;
                            counter1 = i;
                        }
                    }
                }

                final int track0 = counter0;
                final int track1 = counter1;

                if (track0 > -1 && track1 > -1) {
                    recNum[track0][track1].setFont(new Font(50));

                    numBool[track0][track1] = true;

                    String textNum = "";

                    if (key.getCode() == KeyCode.DIGIT1) {
                        textNum = "1";
                    } else if (key.getCode() == KeyCode.DIGIT2) {
                        textNum = "2";
                    } else if (key.getCode() == KeyCode.DIGIT3) {
                        textNum = "3";
                    } else if (key.getCode() == KeyCode.DIGIT4) {
                        textNum = "4";
                    } else if (key.getCode() == KeyCode.DIGIT5) {
                        textNum = "5";
                    } else if (key.getCode() == KeyCode.DIGIT6) {
                        textNum = "6";
                    } else if (key.getCode() == KeyCode.DIGIT7) {
                        textNum = "7";
                    } else if (key.getCode() == KeyCode.DIGIT8) {
                        textNum = "8";
                    } else if (key.getCode() == KeyCode.DIGIT9) {
                        textNum = "9";
                    } else if (key.getCode() == KeyCode.BACK_SPACE) {
                        recNum[track0][track1].setText("");
                        numBool[track0][track1] = false;
                    }

                    if (!textNum.equals("")) {
                        timeRow = track0;
                        timeColumn = track1;
                        timeIndex = Integer.valueOf(textNum);

                        if (rowCheck() && columnCheck() && sectionCheck()) {
                            recNum[track0][track1].setText(textNum);
                        } else {
                            error.show();
                        }
                    }

                    Platform.runLater(() -> {
                        recNum[track0][track1].setTranslateX(recList[track0][track1].getTranslateX() + ((80 - recNum[track0][track1].prefWidth(-1)) / 2));
                        recNum[track0][track1].setTranslateY(recList[track0][track1].getTranslateY() + 7);
                    });
                }

                int labelCount = 0;
                for (int x = 0; x < recNum.length; x++) {
                    for (int i = 0; i < recNum[x].length; i++) {
                        if (recNum[x][i].getText().toString().matches("[1-9]")) {
                            labelCount++;
                        }
                    }
                }

                if (labelCount > 0) {
                    solve.setVisible(true);
                } else {
                    solve.setVisible(false);
                }
            }
        });

        solve.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                solveState = true;

                solve.setVisible(false);
                pausePlay.setVisible(true);
                stopB.setVisible(true);
                soundB.setVisible(true);

                pausePlay.setGraphic(pause);
                soundB.setGraphic(soundOn);

                cycleSpeed = 1000;
                timeRow = 0;
                timeColumn = 0;
                timeIndex = 1;
                playing = true;
                back = false;
                soundPlaying = true;
                mediaPlayer.setMute(false);

                clear();

                mediaPlayer.play();

                solving.getKeyFrames().add(new KeyFrame(Duration.millis(1),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            if (timeRow == 9) {
                                mediaPlayer.stop();
                                pausePlay.setVisible(false);
                                stopB.setVisible(false);
                                soundB.setVisible(false);
                                reset.setVisible(true);
                                solving.stop();
                            } else if (timeColumn < 9) {
                                if (!numTrue()) {
                                    if (timeRow < 9) {
                                        recNum[timeRow][timeColumn].setText(String.valueOf(timeIndex));
                                        recNum[timeRow][timeColumn].setFont(new Font(50));
                                        recNum[timeRow][timeColumn].setTextFill(Color.GREY);
                                        recNum[timeRow][timeColumn].setTranslateX(recList[timeRow][timeColumn].getTranslateX() + ((80 - recNum[timeRow][timeColumn].prefWidth(-1)) / 2));
                                        recNum[timeRow][timeColumn].setTranslateY(recList[timeRow][timeColumn].getTranslateY() + 7);

                                        if (rowCheck() && columnCheck() && sectionCheck()) {
                                            timeColumn++;
                                            timeIndex = 1;
                                            back = false;
                                        } else {
                                            numCheck();
                                        }
                                    }
                                }
                            } else {
                                timeRow++;
                                timeColumn = 0;
                            }
                        }
                    }
                ));

                solving.setCycleCount(Animation.INDEFINITE);
                solving.play();
            } 
        });

        pausePlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                if (playing) {
                    pausePlay.setGraphic(play);
                    solving.pause();
                    playing = false;
                    mediaPlayer.pause();
                } else {
                    pausePlay.setGraphic(pause);
                    solving.play();
                    playing = true;
                    mediaPlayer.play();
                }
            } 
        });

        stopB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                boardClear(solving, pausePlay, stopB, soundB);
            } 
        });

        soundB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                if (soundPlaying) {
                    soundB.setGraphic(soundOff);
                    mediaPlayer.setMute(true);
                    soundPlaying = false;
                } else {
                    soundB.setGraphic(soundOn);
                    mediaPlayer.setMute(false);
                    soundPlaying = true;
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                reset.setVisible(false);
                boardClear(solving, pausePlay, stopB, soundB);
            } 
        });

        root.getChildren().addAll(solve, pausePlay, stopB, soundB, reset);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(false); //Preventing the window from resizing
	}

    private void clear() {
        for (int x = 0; x < recList.length; x++) {
            for (int i = 0; i < recList[x].length; i++) {
                recList[x][i].setFill(Color.WHITE);
                recBool[x][i] = false;
            }
        }
    }

    private void boardClear(Timeline solving, Button pausePlay, Button stopB, Button soundB) {
        solving.stop();

        solveState = false;

        pausePlay.setVisible(false);
        stopB.setVisible(false);
        soundB.setVisible(false);

        cycleSpeed = 100;
        timeRow = 0;
        timeColumn = 0;
        timeIndex = 1;
        playing = true;
        back = false;
        soundPlaying = true;

        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                recList[x][i].setFill(Color.WHITE);
                recBool[x][i] = false;
                recNum[x][i].setText("");
                recNum[x][i].setTextFill(Color.BLACK);
                numBool[x][i] = false;
            }
        }
    }

    private boolean numTrue() {
        if (timeRow != 9) {
            if (numBool[timeRow][timeColumn]) {
                if (!back) {
                    if (timeColumn == 8) {
                        timeRow++;
                        timeColumn = 0;
                    } else {
                        timeColumn++;
                    }
                } else {
                    if (timeColumn == 0) {
                        timeRow--;
                        timeColumn = 8;
                    } else {
                        timeColumn--;
                    }
                }

                return numTrue();
            }
        }
    
        return false;
    }

    private void numCheck() {
        if (timeIndex == 9) {
            recNum[timeRow][timeColumn].setText("");
            
            if (timeColumn == 0) {
                timeRow--;
                timeColumn = 8;
            } else {
                timeColumn--;
            }

            back = true;

            if (!numTrue()) {
                if (Integer.valueOf(recNum[timeRow][timeColumn].getText().toString()) == 9) {
                    numCheck();
                } else {
                    timeIndex = Integer.valueOf(recNum[timeRow][timeColumn].getText().toString()) + 1;
                }
            }
        } else {
            timeIndex++;
        }
    }

    private boolean rowCheck() {
        for (int x = 0; x < timeColumn; x++) {
            if (String.valueOf(timeIndex).equals(recNum[timeRow][x].getText().toString())) {
                return false;
            }
        }

        for (int x = timeColumn + 1; x < 9; x++) {
            if (String.valueOf(timeIndex).equals(recNum[timeRow][x].getText().toString())) {
                return false;
            }
        } 

        return true;
    }

    private boolean columnCheck() {
        for (int x = 0; x < timeRow; x++) {
            if (String.valueOf(timeIndex).equals(recNum[x][timeColumn].getText().toString())) {
                return false;
            }
        }

        for (int x = timeRow + 1; x < 9; x++) {
            if (String.valueOf(timeIndex).equals(recNum[x][timeColumn].getText().toString())) {
                return false;
            }
        } 

        return true;
    }

    private boolean sectionCheck() {
        int startX = timeRow / 3 * 3;
        int startY = timeColumn / 3 * 3;

        int endX = startX + 3;
        int endY = startY + 3;

        for (int x = startX; x < endX; x++) {
            for (int i = startY; i < endY; i++) {
                if (x != timeRow && i != timeColumn) {
                    if (String.valueOf(timeIndex).equals(recNum[x][i].getText().toString())) {
                        return false;
                    }
                }
            }
        } 

        return true;
    }
}
