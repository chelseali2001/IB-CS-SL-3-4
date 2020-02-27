import java.lang.Math; 
import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.beans.value.ChangeListener;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;

/**
 * @date 1/31/19
 * The Ulam Spiral
 * IDE - Sublime Text
 * Platform - MacBook Pro
 */
public class IA extends Application {

    private boolean locked = false;
    private static ArrayList<Rectangle> recList;
    private static ArrayList<Label> labelList;
    private static int counter = 1;

    static {
        recList = new ArrayList<Rectangle>();
        labelList = new ArrayList<Label>();
    }

    public static void main(String[] args) {
         launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        //Window settings
        Group root = new Group();
        Scene primaryScene = new Scene(root, 750, 420, Color.PINK);
        primaryStage.setTitle("The Ulam Spiral");

        //Spiral features
        Label boardSize = new Label("Ulam Spiral Size (Min. 2, Max. 39601):");
        boardSize.setTranslateX(5);
        boardSize.setTranslateY(5);

        TextField size = new TextField();
        size.setTranslateX(5);
        size.setTranslateY(25);
        size.setPrefWidth(60);

        Label tooLow = new Label("Too low");
        tooLow.setTranslateX(65);
        tooLow.setTranslateY(30);
        tooLow.setVisible(false);

        Label empty = new Label("Enter a value");
        empty.setTranslateX(65);
        empty.setTranslateY(30);
        empty.setVisible(false);

        Rectangle backRect = new Rectangle();
        backRect.setX(335);
        backRect.setY(5);
        backRect.setWidth(410);
        backRect.setHeight(410);
        backRect.setFill(Color.GREY);

        Rectangle topRect = new Rectangle();
        topRect.setX(340);
        topRect.setY(10);
        topRect.setWidth(400);
        topRect.setHeight(400);
        topRect.setFill(Color.WHITE);

        Label settings = new Label("Settings:");
        settings.setTranslateX(5);
        settings.setTranslateY(60);

        ToggleGroup group = new ToggleGroup();

        RadioButton dots = new RadioButton("Dots");
        dots.setTranslateX(5);
        dots.setTranslateY(80);
        dots.setToggleGroup(group);
        dots.setUserData("Dots");
        dots.setSelected(true);

        RadioButton numbers = new RadioButton("Numbers");
        numbers.setTranslateX(65);
        numbers.setTranslateY(80);
        numbers.setToggleGroup(group);
        numbers.setUserData("Numbers");

        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setTranslateX(5);
        colorPicker.setTranslateY(130);

        Label dotSettings = new Label("Dot Settings:");
        dotSettings.setTranslateX(5);
        dotSettings.setTranslateY(110);

        CheckBox conjectureF = new CheckBox("Show Conjecture F");
        conjectureF.setTranslateX(5);
        conjectureF.setTranslateY(165);

        final ColorPicker colorPicker0 = new ColorPicker();
        colorPicker0.setValue(Color.BLACK);
        colorPicker0.setTranslateX(150);
        colorPicker0.setTranslateY(160);
        colorPicker0.setVisible(false);

        Label numSettings = new Label("Numbers Settings:");
        numSettings.setTranslateX(5);
        numSettings.setTranslateY(110);
        numSettings.setVisible(false);

        ToggleGroup group0 = new ToggleGroup();

        RadioButton allNums = new RadioButton("Show all numbers");
        allNums.setTranslateX(5);
        allNums.setTranslateY(190);
        allNums.setVisible(false);
        allNums.setToggleGroup(group0);
        allNums.setUserData("Show all numbers");

        RadioButton someNums = new RadioButton("Only show primes");
        someNums.setTranslateX(5);
        someNums.setTranslateY(210);
        someNums.setVisible(false);
        someNums.setToggleGroup(group0);
        someNums.setUserData("Only show primes");

        Button create = new Button("Create");
        create.setTranslateX(5);
        create.setTranslateY(240);

        Button reset = new Button("Reset");
        reset.setTranslateX(5);
        reset.setTranslateY(240);
        reset.setVisible(false);

        /*
          Making sure the user only enters numbers.
          The user can only enter a number up to a certain max.
        */
        size.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                String trueNum = "";

                for (char character : newValue.toCharArray()) {
                    if (String.valueOf(character).matches("\\d")) {
                        trueNum += character;
                    }
                }

                String newTextValue = "";

                if (trueNum.length() > 0) {
                    if (dots.isSelected()) {
                        if ((Integer.parseInt(trueNum) > 0) && (Integer.parseInt(trueNum) <= 39601)) {
                            newTextValue = trueNum;
                        } else {
                            if (trueNum.length() == 5) {
                                newTextValue = trueNum.substring(0, 4);  
                            } else {
                                newTextValue = trueNum.substring(0, 5); 
                            }
                        }
                    } else {
                        if ((Integer.parseInt(trueNum) > 0) && (Integer.parseInt(trueNum) <= 361)) {
                            newTextValue = trueNum;
                        } else {
                            if (trueNum.length() == 3) {
                                newTextValue = trueNum.substring(0, 2);  
                            } else {
                                newTextValue = trueNum.substring(0, 3); 
                            }
                        }
                    }


                }

                final String finalValue = newTextValue;

                Platform.runLater(() -> {
                    locked = true;
                    size.setText(finalValue);
                    size.positionCaret(finalValue.length());
                    locked = false;
                });
            }
        });

        //Displaying certain settings based on the desired spiral
        dots.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { //If the user wants to create a spiral out of dots
                    boardSize.setText("Ulam Spiral Size (Min. 2, Max. 39601):");
                    size.clear();

                    dotSettings.setVisible(true);
                    colorPicker0.setVisible(false);

                    colorPicker.setValue(Color.BLACK);
                    colorPicker0.setValue(Color.BLACK);
                    conjectureF.setSelected(false);

                    numSettings.setVisible(false);
                    allNums.setVisible(false);
                    someNums.setVisible(false);
                } else { //If the user wants to create a spiral out of dots
                    boardSize.setText("Ulam Spiral Size (Min. 2, Max. 361):");
                    size.clear();

                    numSettings.setVisible(true);
                    colorPicker0.setVisible(false);
                    allNums.setVisible(true);
                    someNums.setVisible(true);

                    colorPicker.setValue(Color.BLACK);
                    colorPicker0.setValue(Color.BLACK);
                    conjectureF.setSelected(false);

                    allNums.setSelected(true);
                    someNums.setSelected(false);

                    dotSettings.setVisible(false);
                }
            }
        });

        //A color picker will appear if the user checks this checkbox
        conjectureF.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (new_val) {
                    colorPicker0.setVisible(true);
                } else {
                    colorPicker0.setVisible(false);
                }
            }
        });

        //Creating the spiral
        create.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                counter = 1;

                if (size.getText().isEmpty()) { //If the user doesn't enter a spiral size
                    tooLow.setVisible(false);
                    empty.setVisible(true);
                } else if (Integer.valueOf(size.getText()) < 2) { //If the spiral size is below the min
                    tooLow.setVisible(true);
                    empty.setVisible(false);
                } else {
                    tooLow.setVisible(false);
                    empty.setVisible(false);

                    //Creating the first dot/number
                    final int numRef;
                    Rectangle firstRec = new Rectangle();

                    Label firstLabel = new Label("1");
                    firstLabel.setFont(new Font(8));

                    if (dots.isSelected()) {
                        numRef = 2;

                        firstRec.setFill(Color.TRANSPARENT);
                        firstRec.setX(538);
                        firstRec.setY(209);                     
                    } else {
                        numRef = 20;

                        firstRec.setFill(Color.TRANSPARENT);

                        firstLabel.setVisible(false);

                        if (allNums.isSelected()) {
                            firstRec.setStroke(Color.BLACK);
                            firstLabel.setVisible(true);
                        }
                        
                        firstRec.setX(529);
                        firstRec.setY(199);

                        firstLabel.setTranslateX((double) 527 + ((double) (numRef - firstLabel.getLayoutBounds().getWidth()) / 2));
                        firstLabel.setTranslateY(204);
                    }

                    firstRec.setWidth(numRef);
                    firstRec.setHeight(numRef);

                    root.getChildren().add(firstRec);
                    recList.add(firstRec);

                    if (numbers.isSelected()) {
                        root.getChildren().add(firstLabel);
                        labelList.add(firstLabel);
                    }

                    //This creates dot/number one millisecond at a time
                    Timeline spiralAnimation = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                        Rectangle rec = new Rectangle();
                        rec.setWidth(numRef);
                        rec.setHeight(numRef);

                        Label recLabel = new Label(String.valueOf(counter + 1));
                        recLabel.setFont(new Font(8));

                        boolean rightCheck = recCheck(root, rec, recList.get(counter - 1).getX() + numRef, recList.get(counter - 1).getY());
                        boolean leftCheck = recCheck(root, rec, recList.get(counter - 1).getX() - numRef, recList.get(counter - 1).getY());
                        boolean topCheck = recCheck(root, rec, recList.get(counter - 1).getX(), recList.get(counter - 1).getY() - numRef);
                        boolean bottomCheck = recCheck(root, rec, recList.get(counter - 1).getX(), recList.get(counter - 1).getY() + numRef);

                        /*
                          Setting the position of the rectangles
                          The position of the rectangles are based on which rectangles are nearby
                        */
                        if (((!rightCheck) && (!leftCheck) && (!topCheck) && (!bottomCheck)) ||
                            ((!rightCheck) && (!leftCheck) && (topCheck) && (!bottomCheck)) ||
                            ((!rightCheck) && (leftCheck) && (topCheck) && (!bottomCheck)) ||
                            ((!rightCheck) && (leftCheck) && (topCheck) && (!bottomCheck))) {
                            rec.setX(recList.get(counter - 1).getX() + numRef);
                            rec.setY(recList.get(counter - 1).getY());
                        } else if (((!rightCheck) && (leftCheck) && (!topCheck) && (!bottomCheck)) ||
                                   ((!rightCheck) && (leftCheck) && (!topCheck) && (bottomCheck))) {
                            rec.setX(recList.get(counter - 1).getX());
                            rec.setY(recList.get(counter - 1).getY() - numRef);
                        } else if (((!rightCheck) && (!leftCheck) && (!topCheck) && (bottomCheck)) ||
                                   ((rightCheck) && (!leftCheck) && (!topCheck) && (bottomCheck))) {
                            rec.setX(recList.get(counter - 1).getX() - numRef);
                            rec.setY(recList.get(counter - 1).getY());
                        } else if (((rightCheck) && (!leftCheck) && (!topCheck) && (!bottomCheck)) || 
                                   ((rightCheck) && (!leftCheck) && (topCheck) && (!bottomCheck))) {
                            rec.setX(recList.get(counter - 1).getX());
                            rec.setY(recList.get(counter - 1).getY() + numRef);
                        }

                        //Setting the position of the numbers
                        Platform.runLater(() -> {
                            recLabel.setTranslateX((double) recList.get(Integer.valueOf(recLabel.getText()) - 1).getX() + ((double) (numRef - recLabel.getLayoutBounds().getWidth()) / 2));
                            recLabel.setTranslateY(recList.get(Integer.valueOf(recLabel.getText()) - 1).getY() + 5);
                        });
                        
                        counter++;

                        int count = 0;

                        for (int i = 2; i <= counter; i++) {
                            if (counter % i == 0) {
                                count++;
                            }
                        }

                        //Checking if the rectangle index is prime
                        if (count > 1) {
                            if (dots.isSelected()) {
                                rec.setVisible(false);
                            } else {
                                rec.setFill(Color.TRANSPARENT);
                                rec.setVisible(false);
                                recLabel.setVisible(false);

                                if (allNums.isSelected()) {
                                    rec.setStroke(Color.BLACK);
                                    rec.setVisible(true);
                                    recLabel.setVisible(true);
                                } 
                            }
                        } else {
                            //Checking to see if the rectangle index satisfies the conjecture
                            boolean rightNum = false;
                            if (conjectureF.isSelected()) {
                                for (int x = 0; x < counter; x++) {
                                    if (((4 * Math.pow(x, 2)) - (2 * x) + 41) == counter) {
                                        rightNum = true;
                                    }
                                }

                                if (rightNum) {
                                    rec.setFill(colorPicker0.getValue());
                                } else {
                                    rec.setFill(colorPicker.getValue());
                                }
                            } else {
                                rec.setFill(colorPicker.getValue());

                                if (numbers.isSelected()) {
                                    rec.setStroke(Color.BLACK);
                                } 
                            } 
                        }

                        root.getChildren().add(rec);
                        recList.add(rec);

                        if (numbers.isSelected()) {
                            root.getChildren().add(recLabel);
                            labelList.add(recLabel);
                        }

                    }));

                    spiralAnimation.setCycleCount(Integer.valueOf(size.getText()) - 1);
                    spiralAnimation.play();

                    spiralAnimation.setOnFinished(event -> 
                        reset.setVisible(true)
                    );

                    create.setVisible(false);
                }
            }
        });
        
        //Getting rid of the spiral
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                for (int x = recList.size() - 1; x >= 0 ; x--) {
                    root.getChildren().remove(recList.get(x));
                    recList.remove(x);

                    if (labelList.size() > 0) {
                        root.getChildren().remove(labelList.get(x));
                        labelList.remove(x);
                    }
                }

                create.setVisible(true);
                reset.setVisible(false);
            }
        });

        root.getChildren().addAll(boardSize, size, tooLow, empty, backRect, topRect, colorPicker, create, reset,
                                  settings, dots, numbers,
                                  dotSettings, conjectureF, colorPicker0,
                                  numSettings, allNums, someNums);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
   
    //Checking to see if the new rectangle overlaps another
    private boolean recCheck(Group root, Rectangle rec, double xCoord, double yCoord) {     
        rec.setX(xCoord);
        rec.setY(yCoord);

        boolean check = false;

        for (int x = 0; x < recList.size(); x++) {
            if ((recList.get(x).getX() == xCoord) && (recList.get(x).getY() == yCoord)) {
                check = true;
            }
        }

        return check;
    }
}
