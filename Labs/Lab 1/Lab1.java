import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class
 * This extends from the JavaFX Application
 * @author Chelsea Li
 */
public class Lab1 extends Application {

    private static boolean searched = false; //Checks to see if the user is searching a value

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
    	Scene primaryScene = new Scene(root, 722, 435);

        //Setting up the table on the main menu
        Contact.runTable(root);
        Contact.infoList(false, "");

        //Search bar
        TextField sbar = new TextField();
        sbar.setTranslateX(5);
        sbar.setTranslateY(5);
        sbar.setPromptText("Search");

        //Clear search bar
        Button clear = new Button("X");
        clear.setTranslateX(175);
        clear.setTranslateY(5);
        clear.setVisible(false);

        //Cancel (get out of the search bar funtion)
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(205);
        cancel.setTranslateY(5);
        cancel.setVisible(false);

        //Add a new contact
        Button newcontact = new Button("+");
        newcontact.setTranslateX(690);
        newcontact.setTranslateY(5);

        //Checking what you are typing into the search bar
        sbar.textProperty().addListener((observable, oldValue, newValue) -> {
            clear.setVisible(true);
            cancel.setVisible(true);
            newcontact.setVisible(false);

            String lookup = sbar.getText(); //The variable "lookup" represents the value in the search bar

            Contact.infoList(true, lookup);
        }); 

        //Clearing the search bar
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                sbar.clear();
            }
        });

        //Getting out of the search bar button (and show all of the contacts)
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                Platform.runLater(() -> {
                    clear.setVisible(false);
                    cancel.setVisible(false);
                    newcontact.setVisible(true);
                });

                sbar.clear();

                Contact.infoList(false, "");
            }
        });

        //Creating a new contact
        newcontact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                windowCheck(primaryStage, -1, sbar);
            }
        });

        //When a value on the table is double clicked
        Contact.info.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2){ //You have to double click to get the data
                    int row = Contact.info.getSelectionModel().getSelectedIndex(); //Row number
                    
                    windowCheck(primaryStage, row, sbar);
                }
            }
        });

        root.getChildren().addAll(newcontact, clear, cancel, sbar);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
	}

    /**
     * Checks if the contact window is opended
     * @param primaryStage
     * @param row
     * @param sbar
     */
    private static void windowCheck(Stage primaryStage, int row, TextField sbar) {
        if (!Contact.stage1.isShowing()) {
            if (row != -1) {//If the user didn't click on an empty cell
                row = Contact.lookuplist.get(row); //Getting the data value

                String lookup = sbar.getText();

                if (sbar.getText().isEmpty()) { //If the user looked up a value
                    Contact.runMethod(primaryStage, row, false, lookup);
                } else {
                    Contact.runMethod(primaryStage, row, true, lookup);
                }
            } else {
                Contact.runMethod(primaryStage, Person.people.size(), false, "");
            }
        }
    }
}