import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import javafx.util.Duration;

/**
 * ClockWindow class
 * This extends from the JavaFX Application
 * @author Chelsea Li
 */
public class ClockWindow {

	static int createdNum = 0;

	//List of the clock features
	private static ArrayList<ImageView> imageList;
    private static ArrayList<Group> rootList;
    private static ArrayList<Button> buttonList;
    private static ArrayList<ImageView> hourHandList;
    private static ArrayList<ImageView> minuteHandList;
    private static ArrayList<ImageView> secondHandList;
    private static ArrayList<Label> digitalLabelList;
    private static ArrayList<Label> dateLabelList;
    private static ArrayList<RotateTransition> hourTransitionList;
    private static ArrayList<RotateTransition> minuteTransitionList;
    private static ArrayList<RotateTransition> secondTransitionList;
    private static ArrayList<Timeline> digitalTimelineList;
    private static ArrayList<Integer> tracker;

    private static int timeCounter;
    private static int dayCounter;
    private static int monthCounter;
    private static int yearCounter;

    static {
    	imageList = new ArrayList<ImageView>();
        rootList = new ArrayList<Group>();
        buttonList = new ArrayList<Button>();
        hourHandList = new ArrayList<ImageView>();
        minuteHandList = new ArrayList<ImageView>();
        secondHandList = new ArrayList<ImageView>();
        digitalLabelList = new ArrayList<Label>();        
        dateLabelList = new ArrayList<Label>();
        hourTransitionList = new ArrayList<RotateTransition>();
        minuteTransitionList = new ArrayList<RotateTransition>();
        secondTransitionList = new ArrayList<RotateTransition>();
        digitalTimelineList = new ArrayList<Timeline>();
        tracker = new ArrayList<Integer>();
    }

    /**
     * runMethod method
     * @param primaryStage
     * @param comboList
     * @param comboListCheck
     */
	public static void runMethod(Stage primaryStage, ObservableList<String> comboList, ObservableList<String> comboListCheck) {
        //Window settings
        Group root0 = new Group();
        Scene secondaryScene = new Scene(root0, 600, 600);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle(Clock.clockList.get(createdNum).getName());

        //Getting the clock image name
        String[] clockPicName = Clock.clockList.get(createdNum).getClockType().split(" ");
        String newclockPicName = clockPicName[0].substring(0).toLowerCase() + clockPicName[1].substring(0, 1).toUpperCase() + clockPicName[1].substring(1);

        //Menu button
        Button menuButton = new Button("Menu");
        menuButton.setTranslateX(545);
        menuButton.setTranslateY(5);

        //Getting the image of the clock
        ImageView clockPic = new ImageView(newclockPicName + ".jpg");
        clockPic.setPreserveRatio(true);
        clockPic.fitWidthProperty().bind(secondaryStage.widthProperty());
        clockPic.fitHeightProperty().bind(secondaryStage.heightProperty());

        //Getting the image of the hour hand
        ImageView hourHand = new ImageView("hourHand.jpg");
        hourHand.setTranslateX(Hands.handsList.get(createdNum).getHourX());
        hourHand.setTranslateY(Hands.handsList.get(createdNum).getHourY());
        hourHand.setFitWidth(Hands.handsList.get(createdNum).getHourW());
       	hourHand.setFitHeight(Hands.handsList.get(createdNum).getHourH());

       	//Getting the image of the minute hand
        ImageView minuteHand = new ImageView("minuteHand.jpg");
        minuteHand.setTranslateX(Hands.handsList.get(createdNum).getMinX());
        minuteHand.setTranslateY(Hands.handsList.get(createdNum).getMinY());
        minuteHand.setFitWidth(Hands.handsList.get(createdNum).getMinW());
        minuteHand.setFitHeight(Hands.handsList.get(createdNum).getMinH());

        //Getting the image of the second hand
        ImageView secondHand = new ImageView("secondHand.jpg");
        secondHand.setTranslateX(Hands.handsList.get(createdNum).getSecX());
        secondHand.setTranslateY(Hands.handsList.get(createdNum).getSecY());
        secondHand.setFitWidth(Hands.handsList.get(createdNum).getSecW());
        secondHand.setFitHeight(Hands.handsList.get(createdNum).getSecH());

        //Digital time label
        Label digitalLabel = new Label();
        if (Clock.clockList.get(createdNum).getDigitalClockType().equals("12-Hour AM-PM")) {
        	digitalLabel.setTranslateX(DateTime.datetimeList.get(createdNum).getTimeSetX());
        } else {
        	digitalLabel.setTranslateX(DateTime.datetimeList.get(createdNum).getTimeSetX24());
        }
        digitalLabel.setTranslateY(DateTime.datetimeList.get(createdNum).getTimeSetY());
        digitalLabel.setFont(new Font(DateTime.datetimeList.get(createdNum).getFont()));

        //Date label
        Label dateLabel = new Label();
        dateLabel.setTranslateX(DateTime.datetimeList.get(createdNum).getDateSetX());
        dateLabel.setTranslateY(DateTime.datetimeList.get(createdNum).getDateSetY());
        dateLabel.setFont(new Font(DateTime.datetimeList.get(createdNum).getFont()));

        //Setting up the clock hands, digital clock, and date
        Lab2.calendar = GregorianCalendar.getInstance();

        double secondDegrees;
        double minuteDegrees;
        double hourDegrees;

        //Getting the position of the clock hands
        final String[] selectedTime = Clock.clockList.get(createdNum).getTimeZone().split(":");;

        if (Clock.clockList.get(createdNum).getSettingType()) {
        	if (Clock.clockList.get(createdNum).getTimeZone().equals("Your location")) {
	            Lab2.calendar.setTimeZone(TimeZone.getDefault());
	        } else {
	            Lab2.calendar.setTimeZone(TimeZone.getTimeZone(Clock.clockList.get(createdNum).getTimeZone()));
	        }

        	secondDegrees = Lab2.calendar.get(Calendar.SECOND) * 6;
        	minuteDegrees = (Lab2.calendar.get(Calendar.MINUTE) + secondDegrees / 360) * 6;
        	hourDegrees = (Lab2.calendar.get(Calendar.HOUR) + minuteDegrees / 360) * 30;
        } else {
        	secondDegrees = Double.parseDouble(selectedTime[2]) * 6;
        	minuteDegrees = (Double.parseDouble(selectedTime[1]) + secondDegrees / 360) * 6;
        	hourDegrees = (Double.parseDouble(selectedTime[0]) + minuteDegrees / 360) * 30; 
        }

        hourHand.setRotate(360 + hourDegrees);
        minuteHand.setRotate(360 + minuteDegrees);
        secondHand.setRotate(360 + secondDegrees);

        //The hour hand will make a full rotation every 12 hours
		RotateTransition hourTransition = new RotateTransition(Duration.hours(12), hourHand);
		hourTransition.setByAngle(360);
		hourTransition.setCycleCount(Animation.INDEFINITE);
		hourTransition.setInterpolator(Interpolator.LINEAR);
		hourTransition.play();

		//The minute hand will make a full rotation every 60 minutes
		RotateTransition minuteTransition = new RotateTransition(Duration.minutes(60), minuteHand);
		minuteTransition.setByAngle(360);
		minuteTransition.setCycleCount(Animation.INDEFINITE);
		minuteTransition.setInterpolator(Interpolator.LINEAR);
		minuteTransition.play();

		//The second hand will make a full rotation every 60 seconds
		RotateTransition secondTransition = new RotateTransition(Duration.seconds(60), secondHand);
		secondTransition.setByAngle(360);
		secondTransition.setCycleCount(Animation.INDEFINITE);
		secondTransition.setInterpolator(Interpolator.LINEAR);
		secondTransition.play();

		timeCounter = 1;
		dayCounter = 0;
		monthCounter = 0;
		yearCounter = 0;

		final boolean settingType = Clock.clockList.get(createdNum).getSettingType();
		final String timeZone = Clock.clockList.get(createdNum).getTimeZone();
		final String digitalClockType = Clock.clockList.get(createdNum).getDigitalClockType();
		final int currentMonth = Lab2.calendar.get(Calendar.MONTH) + 1;
		final int currentDate = Lab2.calendar.get(Calendar.DATE);
		final int currentYear = Lab2.calendar.get(Calendar.YEAR);
	
		/*
		  The digital time will change every second
		  The date will change every day (if the user entered the time themselves, the date will change when the time reaches 12:00AM or 24:00)
		*/
		Timeline digitalTimeline = new Timeline(
			new KeyFrame(Duration.seconds(1), 
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                    	Lab2.calendar = GregorianCalendar.getInstance();

                    	String hourDigital = "";
                    	String minuteDigital = "";
                    	String secondDigital = "";
                    	String ampm = "";

                    	String monthVal = "";
                    	String dayVal = "";
                    	String yearVal = "";

				        if (settingType) { //Digital time for a given timezone
				        	if (timeZone.equals("Your location")) {
					            Lab2.calendar.setTimeZone(TimeZone.getDefault());
					        } else {
					            Lab2.calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
					        }

	                        hourDigital = String.valueOf(Lab2.calendar.get(Calendar.HOUR));
	                        if (digitalClockType.equals("24-Hour")) {
	                            hourDigital = String.valueOf(Lab2.calendar.get(Calendar.HOUR_OF_DAY));

	                            if (Lab2.calendar.get(Calendar.HOUR_OF_DAY) < 10) {
	                                hourDigital = "0" + String.valueOf(Lab2.calendar.get(Calendar.HOUR_OF_DAY));
	                            } 
	                        } else {
	                            if (Lab2.calendar.get(Calendar.HOUR) < 10) {
	                                hourDigital = "0" + String.valueOf(Lab2.calendar.get(Calendar.HOUR));
	                            }
	                        } 

	                        minuteDigital = String.valueOf(Lab2.calendar.get(Calendar.MINUTE));
	                        if (Lab2.calendar.get(Calendar.MINUTE) < 10) {
	                            minuteDigital = "0" + String.valueOf(Lab2.calendar.get(Calendar.MINUTE));
	                        }

	                        secondDigital = String.valueOf(Lab2.calendar.get(Calendar.SECOND));
	                        if (Lab2.calendar.get(Calendar.SECOND) < 10) {
	                            secondDigital = "0" + String.valueOf(Lab2.calendar.get(Calendar.SECOND));
	                        }

	                        ampm = String.valueOf(Lab2.calendar.get(Calendar.HOUR_OF_DAY));
	                        if (Lab2.calendar.get(Calendar.HOUR_OF_DAY) <= 12) {
	                            ampm = "AM";
	                        } else {
	                            ampm = "PM";
	                        }

				        	monthVal = String.valueOf(Lab2.calendar.get(Calendar.MONTH) + 1);
				        	dayVal = String.valueOf(Lab2.calendar.get(Calendar.DATE));
				        	yearVal = String.valueOf(Lab2.calendar.get(Calendar.YEAR));
				        } else { //Date and time for a set time
				        	int totalTime = (Integer.valueOf(selectedTime[0]) * 3600) + (Integer.valueOf(selectedTime[1]) * 60) + Integer.valueOf(selectedTime[2]) + timeCounter;
				        	int oppositeTime = -1 * ((Integer.valueOf(selectedTime[0]) * 3600) + (Integer.valueOf(selectedTime[1]) * 60) + Integer.valueOf(selectedTime[2]));
				        	if ((selectedTime[3].equals("PM")) && (Integer.valueOf(selectedTime[0]) < 12)) {
				        		totalTime = totalTime + 43200;
				        		oppositeTime = oppositeTime - 43200;
				        	}
				        	
				        	int timeHour = totalTime / 3600;
				        	totalTime = totalTime - (timeHour * 3600);

				        	int timeMinute = totalTime / 60;
				        	totalTime = totalTime - (timeMinute * 60);

				    		if (totalTime == 0) {
				    			secondDigital = "00";
				    		} else if (totalTime < 10) {
				    			secondDigital = "0" + String.valueOf(totalTime);
				    		} else {
				    			secondDigital = String.valueOf(totalTime);
				    		}

				    		if (timeMinute == 0) {
				    			minuteDigital = "00";
				    		} else if (timeMinute < 10) {
				    			minuteDigital = "0" + String.valueOf(timeMinute);
				    		} else {
				    			minuteDigital = String.valueOf(timeMinute);
				    		}
				    		
				    		ampm = "AM";
				    		if (timeHour == 0) {
				    			if (digitalClockType.equals("12-Hour AM-PM")) {
				    				hourDigital = "12";
				    			} else {
				    				hourDigital = "24";
				    			}
				    		} else if (timeHour == 24) {
				    			hourDigital = "12";

				    			timeCounter = oppositeTime;

				    			dayCounter++;
				    		} else if ((timeHour == 12) && digitalClockType.equals("12-Hour AM-PM")) {
				    			hourDigital = "12";
				    			ampm = "PM";
				    		} else if (timeHour > 12) {
				    			if (digitalClockType.equals("12-Hour AM-PM")) {
				    				ampm = "PM";
				    				
				    				timeHour = timeHour - 12;

				    				if (timeHour == 0) {
				    					hourDigital = "12";
				    				} else if (timeHour < 10) {
				    					hourDigital = "0" + String.valueOf(timeHour); 
				    				} else {
				    					hourDigital = String.valueOf(timeHour);
				    				}
				    			} else {
				    				hourDigital = String.valueOf(timeHour);
				    			}
				    		} else if (timeHour < 10) {
				    			hourDigital = "0" + String.valueOf(timeHour);
				    		} else {
				    			hourDigital = String.valueOf(timeHour);
				    		}

				    		timeCounter++;

				    		int dayCheck = currentDate + dayCounter;
				    		int monthCheck = currentMonth + monthCounter;
				    		int yearCheck = (currentYear + yearCounter) % 4;
				    		int maxDays;

				    		if ((monthCheck == 4) || (monthCheck == 6) || (monthCheck == 9) || (monthCheck == 11)) {
				    			maxDays = 30;
				    		} else if ((yearCheck == 0) && (monthCheck == 2)) {
				    			maxDays = 29;
				    		} else if ((yearCheck != 0) && (monthCheck == 2)) {
				    			maxDays = 28;
				    		} else {
				    			maxDays = 31;
				    		}

				    		if (dayCheck == maxDays) {
				    			dayCounter = (-1 * currentDate) + 1;
				    			monthCounter++;
				    		}

				    		if (monthCheck == 12) {
				    			monthCounter = (-1 * currentMonth) + 1;
				    			yearCounter++;
				    		}

				    		monthVal = String.valueOf(monthCheck);
				    		dayVal = String.valueOf(dayCheck);
				    		yearVal = String.valueOf(currentYear + yearCounter);
				        } 

                        if (digitalClockType.equals("12-Hour AM-PM")) {
                        	digitalLabel.setText(hourDigital + ":" + minuteDigital + ":" + secondDigital + ampm);
                        } else {
                            digitalLabel.setText(hourDigital + ":" + minuteDigital + ":" + secondDigital);
                        }

                        if (monthVal.length() < 2) {
                        	monthVal = "0" + monthVal;
                        }

                        if (dayVal.length() < 2) {
                        	dayVal = "0" + dayVal;
                        }

                        dateLabel.setText(monthVal + "/" + dayVal + "/" + yearVal);
                    }
                }
            )
        );

		digitalTimeline.setCycleCount(Animation.INDEFINITE);
        digitalTimeline.play();

        //If the user doesn't want to show the digital time
		if (!Clock.clockList.get(createdNum).getShowDigitalClock()) {
			digitalLabel.setVisible(false);
		}

		//If the user doesn't want to show the date
		if (!Clock.clockList.get(createdNum).getShowDate()) {
			dateLabel.setVisible(false);
		}

		//If the user doesn't want to show the second hand
		if (!Clock.clockList.get(createdNum).getShowSecondHand()) {
			secondHand.setVisible(false);
		}

        root0.getChildren().addAll(clockPic, menuButton, dateLabel, digitalLabel, hourHand, minuteHand, secondHand);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.show();

        //The window can't resize if the digital clock or date is showing
        if (Clock.clockList.get(createdNum).getShowDigitalClock() || Clock.clockList.get(createdNum).getShowDate()) {
        	secondaryStage.setResizable(false);
        }

        //Storing the features
        imageList.add(clockPic);
        rootList.add(root0); 
        buttonList.add(menuButton);
        hourHandList.add(hourHand);
        minuteHandList.add(minuteHand);
        secondHandList.add(secondHand);
        digitalLabelList.add(digitalLabel);
        dateLabelList.add(dateLabel);
        hourTransitionList.add(hourTransition);
        minuteTransitionList.add(minuteTransition);
        secondTransitionList.add(secondTransition);
        digitalTimelineList.add(digitalTimeline);
        Lab2.stageList.add(secondaryStage);

		final int index = createdNum;

        createdNum++;

        //If the user wants to see the menu
        buttonList.get(index).setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
            	//The menu window will be on top and centered
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

                primaryStage.setAlwaysOnTop(true); 

                for (int x = 0; x < Lab2.stageList.size(); x++) {
                    Lab2.stageList.get(x).setAlwaysOnTop(false);
                }
            } 
        });

        //If the image changes width
		imageList.get(index).fitWidthProperty().addListener((obs, oldVal, newVal) -> {
	        Bounds boundsInScene = imageList.get(index).localToScene(imageList.get(index).getBoundsInLocal());
	        double xInScene = boundsInScene.getMaxX() / 600;
	        double yInScene = boundsInScene.getMaxY() / 600;
	        double xChange = boundsInScene.getMaxX() - 600;

	        /*
			  The button will change positions. The clock hands will change size and positions.
	        */
	        buttonList.get(index).setTranslateX(545 + xChange);

	        hourHandList.get(index).setTranslateX(Hands.handsList.get(index).getHourX() * xInScene);
	        hourHandList.get(index).setTranslateY(Hands.handsList.get(index).getHourY() * yInScene);
	        hourHandList.get(index).setFitWidth(Hands.handsList.get(index).getHourW() * xInScene);
	        hourHandList.get(index).setFitHeight(Hands.handsList.get(index).getHourH() * yInScene);

	        minuteHandList.get(index).setTranslateX(Hands.handsList.get(index).getMinX() * xInScene);
	        minuteHandList.get(index).setTranslateY(Hands.handsList.get(index).getMinY() * xInScene);
	        minuteHandList.get(index).setFitWidth(Hands.handsList.get(index).getMinW() * xInScene);
	        minuteHandList.get(index).setFitHeight(Hands.handsList.get(index).getMinH() * yInScene);

	        secondHandList.get(index).setTranslateX(Hands.handsList.get(index).getSecX() * xInScene);
	        secondHandList.get(index).setTranslateY(Hands.handsList.get(index).getSecY() * xInScene);
	        secondHandList.get(index).setFitWidth(Hands.handsList.get(index).getSecW() * xInScene);
	        secondHandList.get(index).setFitHeight(Hands.handsList.get(index).getSecH() * yInScene);
		});

		//If the image height changes
		imageList.get(index).fitHeightProperty().addListener((obs, oldVal, newVal) -> {
	        Bounds boundsInScene = imageList.get(index).localToScene(imageList.get(index).getBoundsInLocal());
	        double xInScene = boundsInScene.getMaxX() / 600;
	        double yInScene = boundsInScene.getMaxY() / 600;
	        double xChange = boundsInScene.getMaxX() - 600;

	        /*
			  The button will change positions. The clock hands will change size and positions.
	        */
	        buttonList.get(index).setTranslateX(545 + xChange);

	        hourHandList.get(index).setTranslateX(Hands.handsList.get(index).getHourX() * xInScene);
	        hourHandList.get(index).setTranslateY(Hands.handsList.get(index).getHourY() * yInScene);
	        hourHandList.get(index).setFitWidth(Hands.handsList.get(index).getHourW() * xInScene);
	        hourHandList.get(index).setFitHeight(Hands.handsList.get(index).getHourH() * yInScene);

	        minuteHandList.get(index).setTranslateX(Hands.handsList.get(index).getMinX() * xInScene);
	        minuteHandList.get(index).setTranslateY(Hands.handsList.get(index).getMinY() * xInScene);
	        minuteHandList.get(index).setFitWidth(Hands.handsList.get(index).getMinW() * xInScene);
	        minuteHandList.get(index).setFitHeight(Hands.handsList.get(index).getMinH() * yInScene);

	        secondHandList.get(index).setTranslateX(Hands.handsList.get(index).getSecX() * xInScene);
	        secondHandList.get(index).setTranslateY(Hands.handsList.get(index).getSecY() * xInScene);
	        secondHandList.get(index).setFitWidth(Hands.handsList.get(index).getSecW() * xInScene);
	        secondHandList.get(index).setFitHeight(Hands.handsList.get(index).getSecH() * yInScene);
		});

		//If a window is closed
        Lab2.stageList.get(index).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            	int clockTrack = 0;

            	boolean deleted = false;

            	//The clock will be removed from the list of existing clocks
            	for (int x = 0; x < comboListCheck.size() && !deleted; x++) {
            		if (comboListCheck.get(x).equals(Clock.clockList.get(index).getIndex())) {
            			clockTrack = x;
            			comboListCheck.remove(x);
            			deleted = true;
            		}
            	}

            	comboList.remove(clockTrack);
            }
        });
	}
}