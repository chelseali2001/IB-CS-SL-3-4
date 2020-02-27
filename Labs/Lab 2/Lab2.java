import java.util.ArrayList;
import java.util.Calendar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 * Main class
 * This extends from the JavaFX Application
 * @author Chelsea Li
 */
public class Lab2 extends Application {

    static Calendar calendar; //Calls for the time
    static ArrayList<Stage> stageList; //Keeping track of the stages

    private static boolean locked = false;

    static {
        stageList = new ArrayList<Stage>();
    }

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
    	Scene primaryScene = new Scene(root, 500, 500);
        primaryStage.setTitle("Menu");

        //Warning label for when the user wants the clock to show the date and digital clock
        Label resizeWarning = new Label("*Warning: If you want to show the date or the digital clock then you can't resize" + "\n  the analog clock");
        resizeWarning.setTranslateX(5);
        resizeWarning.setTranslateY(280);
        resizeWarning.setVisible(false);

        //Label indicatiting that the features are for creating a clock
        Label clockFeatures = new Label("Create a Clock");
        clockFeatures.setTranslateX(143);
        clockFeatures.setTranslateY(5);
        clockFeatures.setFont(new Font(30));

        //Entering a clock name
        TextField clockName = new TextField();
        clockName.setTranslateX(165);
        clockName.setTranslateY(40);
        clockName.setPromptText("Clock Name");

        ToggleGroup group = new ToggleGroup();

        //Setting the time yourself option
        RadioButton manual = new RadioButton("Manual");
        manual.setTranslateX(160);
        manual.setTranslateY(80);
        manual.setToggleGroup(group);
        manual.setUserData("Manual");

        //Selecting a timezone option
        RadioButton automatic = new RadioButton("Automatic");
        automatic.setTranslateX(245);
        automatic.setTranslateY(80);
        automatic.setToggleGroup(group);
        automatic.setSelected(true);
        automatic.setUserData("Automatic");

        //List of timezones
        Label timeZoneLabel = new Label("Time Zones:");
        timeZoneLabel.setTranslateX(75);
        timeZoneLabel.setTranslateY(105);
        timeZoneLabel.setFont(new Font(15));

        //The timezones
        ComboBox<String> timeZone = new ComboBox<String>();
        timeZone.setTranslateX(75);
        timeZone.setTranslateY(125);
        timeZone.getItems().addAll(
            "Your location",
            "ACT",
            "AET",
            "AGT",
            "ART",
            "AST",
            "BET",
            "BST",
            "CAT",
            "CET",
            "CNT",
            "CST",
            "CST6CDT",
            "CTT",
            "EAT",
            "ECT",
            "EET",
            "EST",
            "EST5EDT",
            "GB",
            "GB-Eire",
            "GMT",
            "GMT0",
            "HST",
            "IET",
            "IST",
            "JST",
            "MET",
            "MIT",
            "MST",
            "MST7MDT",
            "NET",
            "NST",
            "NZ",
            "NZ-CHAT",
            "PLT",
            "PNT",
            "PRC",
            "PRT",
            "PST",
            "PST8PDT",
            "ROK",
            "SST",
            "UCT",
            "UTC",
            "VST",
            "W-SU",
            "WET"
        );
        timeZone.getSelectionModel().selectFirst();

        //Changing the time features
        Label amPMtime = new Label("Select Time:");
        amPMtime.setTranslateX(65);
        amPMtime.setTranslateY(105);
        amPMtime.setFont(new Font(15));
        amPMtime.setVisible(false);

        //Entering the hour
        TextField manualHour = new TextField();
        manualHour.setTranslateX(65);
        manualHour.setTranslateY(125);
        manualHour.setPrefWidth(35);
        manualHour.setVisible(false);
        manualHour.setPromptText("00");

        //The colon between the hour and minute
        Label clockColon1 = new Label(":");
        clockColon1.setTranslateX(103);
        clockColon1.setTranslateY(127);
        clockColon1.setFont(new Font(15));
        clockColon1.setVisible(false);

        //Entering the minute
        TextField manualMinute = new TextField();
        manualMinute.setTranslateX(110);
        manualMinute.setTranslateY(125);
        manualMinute.setPrefWidth(35);
        manualMinute.setVisible(false);
        manualMinute.setPromptText("00");

        //The colon between the minute and the second
        Label clockColon2 = new Label(":");
        clockColon2.setTranslateX(148);
        clockColon2.setTranslateY(127);
        clockColon2.setFont(new Font(15));
        clockColon2.setVisible(false);

        //Entering the second
        TextField manualSecond = new TextField();
        manualSecond.setTranslateX(155);
        manualSecond.setTranslateY(125);
        manualSecond.setPrefWidth(35);
        manualSecond.setVisible(false);
        manualSecond.setPromptText("00");

        //AM PM setting
        ComboBox<String> ampmSetting = new ComboBox<String>();
        ampmSetting.setTranslateX(193);
        ampmSetting.setTranslateY(125);
        ampmSetting.getItems().addAll(
            "AM",
            "PM"
        );
        ampmSetting.getSelectionModel().selectFirst();
        ampmSetting.setVisible(false);

        //Selecting clock image
        Label clockTypeLabel = new Label("Clock Type:");
        clockTypeLabel.setTranslateX(280);
        clockTypeLabel.setTranslateY(105);
        clockTypeLabel.setFont(new Font(15));

        //Clock images
        ComboBox<String> clockType = new ComboBox<String>();
        clockType.setTranslateX(280);
        clockType.setTranslateY(125);
        clockType.getItems().addAll(
            "Standard Clock",
            "Weird Clock",
            "Silver Clock",
            "Old Clock",
            "Pocket Clock",
            "Wooden Clock",
            "Reading Clock"
        );
        clockType.getSelectionModel().selectFirst();

        //Showing the digital time option
        CheckBox showDClock = new CheckBox("Show digital clock");
        showDClock.setTranslateX(170);
        showDClock.setTranslateY(160);

        //Digital clock type
        ComboBox<String> dclockType = new ComboBox<String>();
        dclockType.setTranslateX(307);
        dclockType.setTranslateY(155);
        dclockType.getItems().addAll(
            "12-Hour AM-PM",
            "24-Hour"
        );
        dclockType.getSelectionModel().selectFirst();
        dclockType.setVisible(false);

        //Showing the date option
        CheckBox showDate = new CheckBox("Show date");
        showDate.setTranslateX(170);
        showDate.setTranslateY(185);

        //Showing the second hand option
        CheckBox showSecondHand = new CheckBox("Show second hand");
        showSecondHand.setTranslateX(170);
        showSecondHand.setTranslateY(210);

        //Creating the clock
        Button createClock = new Button("Create Clock");
        createClock.setTranslateX(200);
        createClock.setTranslateY(240);

        //Find an existing clock
        Label findClock = new Label("Find an Existing Clock");
        findClock.setTranslateX(100);
        findClock.setTranslateY(310);
        findClock.setFont(new Font(30));

        //List of existing clocks
        ObservableList<String> comboList = FXCollections.observableArrayList("Select an Existing Clock"); //List of clock names
        ObservableList<String> comboListCheck = FXCollections.observableArrayList("Select an Existing Clock"); //List of the clock indexes (to separate the clocks with the same name)
        ComboBox<String> listClocks = new ComboBox<String>();
        listClocks.setTranslateX(155);
        listClocks.setTranslateY(350);
        listClocks.setItems(comboList);
        listClocks.getSelectionModel().selectFirst();

        //Closing the main menu
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //Closing all of the other menus
                for (int x = 0; x < stageList.size(); x++) {
                    stageList.get(x).close();
                }
            }
        });

        //Checking to see if the user wants to show the digital clock
        showDClock.selectedProperty().addListener(new ChangeListener<Boolean>() { 
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    resizeWarning.setVisible(true);
                    dclockType.setVisible(true);
                } else {
                    if (!showDate.isSelected()) {
                        resizeWarning.setVisible(false);
                        dclockType.setVisible(false);
                    }
                }
            }
        });

        //Checking to see if the user wants to show the date
        showDate.selectedProperty().addListener(new ChangeListener<Boolean>() { 
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    resizeWarning.setVisible(true);
                } else {
                    if (!showDClock.isSelected()) {
                        resizeWarning.setVisible(false);
                    }
                }
            }
        });

        //Checking to see if the user wants to manually enter the time
        manual.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    timeZoneLabel.setVisible(false);
                    timeZone.setVisible(false);

                    amPMtime.setVisible(true);
                    manualHour.setVisible(true);
                    clockColon1.setVisible(true);
                    manualMinute.setVisible(true);
                    clockColon2.setVisible(true);
                    manualSecond.setVisible(true);
                    ampmSetting.setVisible(true);
                } else {
                    timeZoneLabel.setVisible(true);
                    timeZone.setVisible(true);

                    amPMtime.setVisible(false);
                    manualHour.setVisible(false);
                    clockColon1.setVisible(false);
                    manualMinute.setVisible(false);
                    clockColon2.setVisible(false);
                    manualSecond.setVisible(false);
                    ampmSetting.setVisible(false);
                }
            }
        });

        //Creating the clock
        createClock.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                primaryStage.setAlwaysOnTop(false);

                //Adding clock name to the list of existing clocks
                String clockTitle = "";
                if (clockName.getText().isEmpty()) {
                    clockTitle = "Clock " + ClockWindow.createdNum;
                } else {
                    clockTitle = clockName.getText().toString();
                }
                comboList.add(clockTitle);
                comboListCheck.add("Clock " + ClockWindow.createdNum);

                //Getting the time
                if (automatic.isSelected()) {
                    Clock newClock = new Clock(clockTitle, "Clock " + ClockWindow.createdNum, true, timeZone.getValue().toString(), clockType.getValue().toString(), showDClock.isSelected(), dclockType.getValue().toString(), showDate.isSelected(), showSecondHand.isSelected());
                } else {
                    String theHour = manualHour.getText();
                    String theMinute = manualMinute.getText();
                    String theSecond = manualSecond.getText();
                    String theAMPM = ampmSetting.getValue();

                    if ((theHour.equals("")) || ((theHour.equals("12")) && (ampmSetting.getValue().toString().equals("AM")))) {
                        theHour = "0";
                    }

                    if (theMinute.equals("")) {
                        theMinute = "0";
                    }

                    if (theSecond.equals("")) {
                        theSecond = "0";
                    }

                    if (theHour.equals("0") && theMinute.equals("0") && theSecond.equals("0")) {
                        theAMPM = "AM";
                    }

                    Clock newClock = new Clock(clockTitle, "Clock " + ClockWindow.createdNum, false, theHour + ":" + theMinute + ":" + theSecond + ":" + theAMPM, clockType.getValue().toString(), showDClock.isSelected(), dclockType.getValue().toString(), showDate.isSelected(), showSecondHand.isSelected());
                }

                //Getting the coordinates of the clock hands, digital clock, and date
                if (clockType.getValue().toString().equals("Standard Clock")) {
                    Hands newHands = new Hands(150, 130, 300, 325, 150, 0, 300, 575, 150, 0, 300, 575);
                    DateTime newDateTime = new DateTime(223, 125, 218, 400, 30, 239);
                } else if (clockType.getValue().toString().equals("Weird Clock")) {
                    Hands newHands = new Hands(144, 145, 300, 285, 144, 35, 300, 500, 144, 35, 300, 500);
                    DateTime newDateTime = new DateTime(245, 150, 243, 400, 20, 255);
                } else if (clockType.getValue().toString().equals("Silver Clock")) {
                    Hands newHands = new Hands(147, 115, 299, 365, 151, -5, 300, 615, 151, -5, 300, 615);
                    DateTime newDateTime = new DateTime(223, 125, 210, 420, 30, 239);
                } else if (clockType.getValue().toString().equals("Old Clock")) {
                    Hands newHands = new Hands(150, 150, 300, 290, 150, 15, 300, 575, 150, 15, 300, 575);
                    DateTime newDateTime = new DateTime(225, 175, 215, 400, 30, 242);
                } else if (clockType.getValue().toString().equals("Pocket Clock")) {
                    Hands newHands = new Hands(156, 177, 299, 285, 155, 25, 300, 595, 155, 25, 300, 595);
                    DateTime newDateTime = new DateTime(228, 180, 221, 445, 30, 248);
                } else if (clockType.getValue().toString().equals("Wooden Clock")) {
                    Hands newHands = new Hands(154, 140, 300, 285, 154, -4, 300, 575, 154, -4, 300, 575);
                    DateTime newDateTime = new DateTime(228, 180, 221, 375, 30, 245);
                } else if (clockType.getValue().toString().equals("Reading Clock")) {
                    Hands newHands = new Hands(151, 150, 302, 300, 151, 25, 302, 550, 151, 25, 302, 550);
                    DateTime newDateTime = new DateTime(228, 145, 220, 410, 30, 242);
                }

                ClockWindow.runMethod(primaryStage, comboList, comboListCheck);

                //Resetting the clock settings
                clockName.clear();
                automatic.setSelected(true);
                timeZone.getSelectionModel().selectFirst();
                clockType.getSelectionModel().selectFirst();
                showDClock.setSelected(false);
                dclockType.getSelectionModel().selectFirst();
                showDate.setSelected(false);
                showSecondHand.setSelected(false);
                listClocks.getSelectionModel().selectFirst();

                manualHour.clear();
                manualMinute.clear();
                manualSecond.clear();
                ampmSetting.getSelectionModel().selectFirst();
            }
        });

        //Getting the selected clock
        listClocks.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                int chosenIndex = listClocks.getSelectionModel().getSelectedIndex();
                int clockChoice = 0;

                boolean deleted = false;

                if (chosenIndex > 0) {
                    String chosenClock = comboListCheck.get(chosenIndex);

                    for (int x = 0; x < Clock.clockList.size() && !deleted; x++) {
                        if (Clock.clockList.get(x).getIndex().equals(chosenClock)) {
                            clockChoice = x;
                            deleted = true;
                        }
                    }

                    //Bringing to the desired clock on top and in the center of the screen
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    stageList.get(clockChoice).setX((primScreenBounds.getWidth() - stageList.get(clockChoice).getWidth()) / 2);
                    stageList.get(clockChoice).setY((primScreenBounds.getHeight() - stageList.get(clockChoice).getHeight()) / 2);

                    stageList.get(clockChoice).setAlwaysOnTop(true);
                    primaryStage.setAlwaysOnTop(false);
                    listClocks.getSelectionModel().selectFirst();
                }

            }
        });

        //Checking to see if the clock name is less than 25 characters
        clockName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                String trueNum = clockName.getText();

                String newTextValue = "";
                if (trueNum.length() > 24){
                    newTextValue = trueNum.substring(0, 24);
                } else {
                    newTextValue = trueNum;
                }

                final String finalValue = newTextValue;

                Platform.runLater(() -> {
                    locked = true;
                    clockName.setText(finalValue);
                    clockName.positionCaret(finalValue.length());
                    locked = false;
                });
            }
        });

        //Checking to see if the user entered an integer less than 13 for the hour
        manualHour.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                String trueNum = "";

                for (char character : newValue.toCharArray()) {
                    if (String.valueOf(character).matches("\\d")) {
                        trueNum += character;
                    }
                }

                String newTextValue = "";

                if (trueNum.length() > 0) {
                    if (Integer.parseInt(trueNum.substring(0, 1)) != 0) {
                        if (Integer.parseInt(trueNum) < 13) {
                            newTextValue = trueNum;
                        } else {
                            if (trueNum.length() > 2) {
                                newTextValue = trueNum.substring(0, 2);
                            } else {
                                newTextValue = trueNum.substring(0, 1);   
                            }
                        }
                    }
                }

                final String finalValue = newTextValue;

                Platform.runLater(() -> {
                    locked = true;
                    manualHour.setText(finalValue);
                    manualHour.positionCaret(finalValue.length());
                    locked = false;
                });
            }
        });

        //Checking to see if the user entered an integer less than 60 for the minute
        manualMinute.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                String trueNum = "";

                for (char character : newValue.toCharArray()) {
                    if (String.valueOf(character).matches("\\d")) {
                        trueNum += character;
                    }
                }

                String newTextValue = "";

                if (trueNum.length() > 0) {
                    if (Integer.parseInt(trueNum.substring(0, 1)) != 0) {
                        if (Integer.parseInt(trueNum) < 60) {
                            newTextValue = trueNum;
                        } else {
                            if (trueNum.length() > 2) {
                                newTextValue = trueNum.substring(0, 2);
                            } else {
                                newTextValue = trueNum.substring(0, 1);   
                            }
                        }
                    }
                }

                final String finalValue = newTextValue;

                Platform.runLater(() -> {
                    locked = true;
                    manualMinute.setText(finalValue);
                    manualMinute.positionCaret(finalValue.length());
                    locked = false;
                });
            }
        });

        //Checking to see if the user entered an integer less than 60 for the second
        manualSecond.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                String trueNum = "";

                for (char character : newValue.toCharArray()) {
                    if (String.valueOf(character).matches("\\d")) {
                        trueNum += character;
                    }
                }

                String newTextValue = "";

                if (trueNum.length() > 0) {
                    if (Integer.parseInt(trueNum.substring(0, 1)) != 0) {
                        if (Integer.parseInt(trueNum) < 60) {
                            newTextValue = trueNum;
                        } else {
                            if (trueNum.length() > 2) {
                                newTextValue = trueNum.substring(0, 2);
                            } else {
                                newTextValue = trueNum.substring(0, 1);   
                            }
                        }
                    }
                }

                final String finalValue = newTextValue;

                Platform.runLater(() -> {
                    locked = true;
                    manualSecond.setText(finalValue);
                    manualSecond.positionCaret(finalValue.length());
                    locked = false;
                });
            }
        });

        root.getChildren().addAll(clockFeatures, clockTypeLabel, clockType, showDClock, dclockType, showDate, showSecondHand, createClock, resizeWarning, 
                                  clockName, manual, amPMtime, manualHour, clockColon1, manualMinute, clockColon2, manualSecond, ampmSetting, 
                                  automatic, timeZoneLabel, timeZone,
                                  findClock, listClocks);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(false); //Preventing the window from resizing
	}
}