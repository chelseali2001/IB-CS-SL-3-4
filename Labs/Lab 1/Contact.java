import java.io.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;  

/**
 * Creating/Updating a contact class
 * @author Chelsea Li
 */
public class Contact {
    public static Stage stage1 = new Stage();

    private static Alert fileAlert = new Alert(AlertType.ERROR);
    private static Alert errorAlert = new Alert(AlertType.ERROR);

    public static final ObservableList<Person> data = FXCollections.observableArrayList();
    public static final TableView<Person> info = new TableView<Person>(data);

    public static ArrayList<Integer> lookuplist = new ArrayList<Integer>(); 

    private static ArrayList<ArrayList<Integer>> phones;
    private static ArrayList<ArrayList<Integer>> emails;
    static ArrayList<ArrayList<Integer>> addresses;

    static {
        phones = new ArrayList<ArrayList<Integer>>();
        emails = new ArrayList<ArrayList<Integer>>();
        addresses = new ArrayList<ArrayList<Integer>>();
    }

	private static boolean locked = false;

    private static String originalfn;
    private static String originalln;
    private static String originalc;
    private static String originaln;

	private static int prow;
    private static String originalp;
    private static String pucheck;

	private static int erow;
    private static String originale;
    private static String eucheck;

	private static int arow;
    private static String originala;
    private static String aucheck;

    //State abbreviations
    private static String[] stateabbrev = {
		"AL", 
		"AK", 
		"AZ",
        "AR",
        "CA",
        "CO",
        "CT",
        "DE",
        "FL",
        "GA",
        "HI",
        "ID",
        "IL",
        "IN",
        "IA",
        "KS",
        "KY",
        "LA",
        "ME",
        "MD",
        "MA",
        "MI",
        "MN",
        "MS",
        "MO",
        "MT",
        "NE",
        "NV",
        "NH",
        "NJ",
        "NM",
        "NY",
        "NC",
        "ND",
        "OH",
        "OK",
        "OR",
        "PA",
        "RI",
        "SC",
        "SD",
        "TN",
        "TX",
        "UT",
        "VT",
        "VA",
        "WA",
        "WV",
        "WI",
        "WY"
    };

    //State names
    private static String[] statefull = {
		"Alabama", 
		"Alaska", 
		"Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
		"Minnesota", 
		"Mississippi", 
		"Missouri", 
		"Montana",
		"Nebraska", 
		"Nevada", 
		"New Hampshire", 
		"New Jersey", 
		"New Mexico", 
		"New York", 
		"North Carolina", 
		"North Dakota", 
		"Ohio", 
		"Oklahoma", 
		"Oregon", 
		"Pennsylvania",
		"Rhode Island", 
		"South Carolina", 
		"South Dakota", 
		"Tennessee", 
		"Texas", 
		"Utah", 
		"Vermont", 
		"Virginia", 
		"Washington", 
		"West Virginia", 
		"Wisconsin", 
		"Wyoming"
    };

    /**
     * Creating/Updating a contact method
     * @param primaryStage 
     * @param index
     * @param search
     * @param lookup
     */
	public static void runMethod(Stage primaryStage, int index, boolean search, String lookup) {
		//Window Settings
        Group root1 = new Group();
		Scene scene1 = new Scene(root1, 1180, 720);

        fileAlert.setContentText("Cannot open file");
        errorAlert.setContentText("Cannot find file");

		//Setting up the window title
		if (index < Person.people.size()) {
			stage1.setTitle(Person.people.get(index).getFirstName() + " " + Person.people.get(index).getLastName());
		} else {
			stage1.setTitle("Create a new contact");
		}		

        //Page Features

        //Exiting the page
        Button pagecancel = new Button("Cancel");
        pagecancel.setTranslateX(5);
        pagecancel.setTranslateY(5);

        //Closing warning (if you're creating a new contact)
       	Label closewarning = new Label("Press 'Done' to save everything or press 'Cancel' to exit");
       	closewarning.setTranslateX(125);
       	closewarning.setTranslateY(9);
       	closewarning.setVisible(false);

       	//Closing warning (if you're updating a contact)
       	Label closewarning0 = new Label("Press 'Done' to save everything, press 'Cancel' to exit, or press 'Delete Contact' to delete the contact");
       	closewarning0.setTranslateX(125);
       	closewarning0.setTranslateY(9);
       	closewarning0.setVisible(false);

        //Deleting the contact
        Button delete = new Button("Delete Contact");
        delete.setTranslateX(1065);
        delete.setTranslateY(5);
 		delete.setVisible(false); 

        //Saving the contact
        Button done = new Button("Done");
        done.setTranslateX(70);
        done.setTranslateY(5); 
        done.setVisible(false);

        //Name features

		//Enter first name
		TextField fn = new TextField();
		fn.setTranslateX(5);
		fn.setTranslateY(40);
		fn.setPrefWidth(230);
		fn.setPromptText("First Name (max letters: 30)");

		//Enter last name
		TextField ln = new TextField();
		ln.setTranslateX(240);
		ln.setTranslateY(40);
		ln.setPrefWidth(230);
		ln.setPromptText("Last Name (max letters: 30)");

		//Enter company name
		TextField c = new TextField();
		c.setTranslateX(475);
		c.setTranslateY(40);
		c.setPrefWidth(255);
		c.setPromptText("Company (max letters: 30)");

		//Enter notes
        TextField n = new TextField();
        n.setTranslateX(735);
        n.setTranslateY(40);
        n.setPrefWidth(310);
        n.setPromptText("Notes");

        //Phone Settings

        //Adding a phone number

 		//Clearing the textfields
        //Enter Phone Type
        //The comboBox at the bottom of the phone number table
        ComboBox<String> phonetypeadd = new ComboBox<String>();
        phonetypeadd.setPromptText("home");
        phonetypeadd.getItems().addAll(
            "home",
	        "work",
    		"iPhone",
    		"mobile",
    		"main",
    		"home fax",
    		"work fax",
    		"pager",
    		"other"
        );
        phonetypeadd.setTranslateX(0);
        phonetypeadd.setTranslateY(480);

        //The textfield at the bottom of the phone number table
        TextField phonenumberadd = new TextField();
        phonenumberadd.setTranslateX(0);
        phonenumberadd.setTranslateY(510);
        phonenumberadd.setPromptText("Phone Number");

        //The textfield at the bottom of the phone number table
        TextField optionalextensionadd = new TextField();
        optionalextensionadd.setTranslateX(0);
        optionalextensionadd.setTranslateY(540);
       	optionalextensionadd.setPromptText("Optional Extension");

       	//Clearing the textfields at the bottom of the phone number table
 		Button phoneaddclear = new Button("Clear");
 		phoneaddclear.setTranslateX(0);
 		phoneaddclear.setTranslateY(570);

 		//Adding a new phone number
 		final Button phoneadd = new Button("Add");
 		phoneadd.setTranslateX(53);
 		phoneadd.setTranslateY(570);

		Label phoneaddwarning = new Label("Error: Invalid phone number");
		phoneaddwarning.setTranslateX(0);
		phoneaddwarning.setTranslateY(600);
		phoneaddwarning.setVisible(false);	

 		//Updating a contact
        //The comboBox to the right of the phone number table
        ComboBox<String> phonetypeupdate = new ComboBox<String>();
        phonetypeupdate.getItems().addAll(
            "home",
	        "work",
    		"iPhone",
    		"mobile",
    		"main",
    		"home fax",
    		"work fax",
    		"pager",
    		"other"
        );
        phonetypeupdate.setTranslateX(205);
        phonetypeupdate.setTranslateY(75); 
        phonetypeupdate.setVisible(false); 
  

        //The textfield to the right of the phone number table
        TextField phonenumberupdate = new TextField();
        phonenumberupdate.setTranslateX(205);
        phonenumberupdate.setTranslateY(105);
        phonenumberupdate.setPromptText("Phone Number");
        phonenumberupdate.setVisible(false);

       	//The textfield to the right of the phone number table
       	TextField optionalextensionupdate = new TextField();
        optionalextensionupdate.setTranslateX(205);
        optionalextensionupdate.setTranslateY(135);
       	optionalextensionupdate.setPromptText("Optional Extension");
       	optionalextensionupdate.setVisible(false);

 		//Clearing the textfields to the right of the phone number table
 		Button phoneupdateclear = new Button("Clear");
 		phoneupdateclear.setTranslateX(205);
 		phoneupdateclear.setTranslateY(165);
 		phoneupdateclear.setVisible(false);

 		//Deleting the phone number
 		Button phonedel = new Button("Delete");
 		phonedel.setTranslateX(257);
 		phonedel.setTranslateY(165);
 		phonedel.setVisible(false);

        //If you don't want to update the chosen phone information
        Button phoneupdatecancel = new Button("Cancel");
        phoneupdatecancel.setTranslateX(205);
        phoneupdatecancel.setTranslateY(195);
        phoneupdatecancel.setVisible(false);

 		//Updating the chosen phone number
 		Button phoneupdate = new Button("Update");
 		phoneupdate.setTranslateX(267);
 		phoneupdate.setTranslateY(195);
 		phoneupdate.setVisible(false);

 		//for phone numbers
    	final ObservableList<Phone> pdata = FXCollections.observableArrayList();
    	final TableView<Phone> pinfo = new TableView<Phone>(pdata);

       	//Phone type column
        TableColumn<Phone, String> phoneTypeCol = new TableColumn<Phone, String>("Phone Type");
        phoneTypeCol.setMinWidth(100);
        phoneTypeCol.setCellValueFactory(new PropertyValueFactory<Phone, String>("phoneType"));    

        //Phone number column
        TableColumn<Phone, String> phoneNumberCol = new TableColumn<Phone, String>("Phone Number");
        phoneNumberCol.setMinWidth(125);
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Phone, String>("phoneNumber"));      

        //Optional Extension column
        TableColumn<Phone, String> optionalExtensionCol = new TableColumn<Phone, String>("Optional Extension");
        optionalExtensionCol.setMinWidth(150);
        optionalExtensionCol.setCellValueFactory(new PropertyValueFactory<Phone, String>("optionalExtension"));      

        pinfo.setLayoutX(0);
        pinfo.setLayoutY(75);
        pinfo.setPrefWidth(200);

        pinfo.getColumns().add(phoneTypeCol);
        pinfo.getColumns().add(phoneNumberCol);
        pinfo.getColumns().add(optionalExtensionCol);

        //Email Settings

        //Adding an email

 		//Clearing the textfields
        //Enter Email Type
        //The comboBox at the bottom of the email table
        ComboBox<String> emailtypeadd = new ComboBox<String>();
        emailtypeadd.setPromptText("home");
        emailtypeadd.getItems().addAll(
            "home",
	        "work",
    		"iCloud",
    		"other"
        );
        emailtypeadd.setTranslateX(400);
        emailtypeadd.setTranslateY(480);

        //The textfield at the bottom of the email table
        TextField emailaddressadd = new TextField();
        emailaddressadd.setTranslateX(400);
        emailaddressadd.setTranslateY(510);
        emailaddressadd.setPromptText("example@example.com");

       	//Clearing the textfields at the bottom of the email table
 		Button emailaddclear = new Button("Clear");
 		emailaddclear.setTranslateX(400);
 		emailaddclear.setTranslateY(540);

 		//Adding a new email
 		final Button emailadd = new Button("Add");
 		emailadd.setTranslateX(453);
 		emailadd.setTranslateY(540);

		Label emailaddwarning = new Label("Error: Invalid Email");
		emailaddwarning.setTranslateX(400);
		emailaddwarning.setTranslateY(570);
		emailaddwarning.setVisible(false);	

 		//Updating a contact
        //The comboBox to the right of the email table
        ComboBox<String> emailtypeupdate = new ComboBox<String>();
        emailtypeupdate.getItems().addAll(
            "home",
	        "work",
	        "iCloud",
    		"other"
        );
        emailtypeupdate.setTranslateX(605);
        emailtypeupdate.setTranslateY(75); 
        emailtypeupdate.setVisible(false); 
  

        //The textfield to the right of the email table
        TextField emailaddressupdate = new TextField();
        emailaddressupdate.setTranslateX(605);
        emailaddressupdate.setTranslateY(105);
        emailaddressupdate.setPromptText("Email Address");
        emailaddressupdate.setVisible(false);

 		//Clearing the textfields to the right of the email table
 		Button emailupdateclear = new Button("Clear");
 		emailupdateclear.setTranslateX(605);
 		emailupdateclear.setTranslateY(135);
 		emailupdateclear.setVisible(false);

 		//Deleting the email
 		Button emaildel = new Button("Delete");
 		emaildel.setTranslateX(657);
 		emaildel.setTranslateY(135);
 		emaildel.setVisible(false);

        //If you don't want to update the chosen email information
        Button emailupdatecancel = new Button("Cancel");
        emailupdatecancel.setTranslateX(605);
        emailupdatecancel.setTranslateY(165);
        emailupdatecancel.setVisible(false);

 		//Updating the chosen phone number
 		Button emailupdate = new Button("Update");
 		emailupdate.setTranslateX(667);
 		emailupdate.setTranslateY(165);
 		emailupdate.setVisible(false);

 		//for emails
    	final ObservableList<Email> edata = FXCollections.observableArrayList();
    	final TableView<Email> einfo = new TableView<Email>(edata);

       	//Email type column
        TableColumn<Email, String> emailTypeCol = new TableColumn<Email, String>("Email Type");
        emailTypeCol.setMinWidth(100);
        emailTypeCol.setCellValueFactory(new PropertyValueFactory<Email, String>("emailType"));    

        //Email address column
        TableColumn<Email, String> emailAddressCol = new TableColumn<Email, String>("Email Address");
        emailAddressCol.setMinWidth(200);
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<Email, String>("emailAddress"));     

        einfo.setLayoutX(400);
        einfo.setLayoutY(75);
        einfo.setPrefWidth(200);

        einfo.getColumns().add(emailTypeCol);
        einfo.getColumns().add(emailAddressCol);

        //Address Settings

        //Adding an address

 		//Clearing the textfields
        //Enter Address Type
        //The comboBox at the bottom of the address table
        ComboBox<String> addresstypeadd = new ComboBox<String>();
        addresstypeadd.setPromptText("home");
        addresstypeadd.getItems().addAll(
            "home",
	        "work",
    		"other"
        );
        addresstypeadd.setTranslateX(800);
        addresstypeadd.setTranslateY(480);

        //The textfield at the bottom of the address table
        TextField street1add = new TextField();
        street1add.setTranslateX(800);
        street1add.setTranslateY(510);
        street1add.setPromptText("Street 1");

        //The textfield at the bottom of the address table
        TextField street2add = new TextField();
        street2add.setTranslateX(800);
        street2add.setTranslateY(540);
       	street2add.setPromptText("Street 2");

        //The textfield at the bottom of the address table
        TextField cityadd = new TextField();
        cityadd.setTranslateX(800);
        cityadd.setTranslateY(570);
       	cityadd.setPromptText("City");

        //The comboBox at the bottom of the address table
        final ComboBox<String> stateadd = new ComboBox<String>();
        stateadd.setPromptText("State");
        stateadd.getItems().addAll(
        	stateabbrev
        );
        stateadd.setTranslateX(800);
        stateadd.setTranslateY(600);
        stateadd.setPrefWidth(100);
        stateadd.setEditable(true);

        //The textfield at the bottom of the address table
        TextField zipcodeadd = new TextField();
        zipcodeadd.setTranslateX(800);
        zipcodeadd.setTranslateY(630);
       	zipcodeadd.setPromptText("Zip Code");

       	//Clearing the textfields at the bottom of the address table
 		Button addressaddclear = new Button("Clear");
 		addressaddclear.setTranslateX(800);
 		addressaddclear.setTranslateY(660);

 		//Adding a new phone number
 		final Button addressadd = new Button("Add");
 		addressadd.setTranslateX(853);
 		addressadd.setTranslateY(660);

		Label addressaddwarning = new Label("Error: Invalid address");
		addressaddwarning.setTranslateX(800);
		addressaddwarning.setTranslateY(690);
		addressaddwarning.setVisible(false);	

 		//Updating a contact
        //The comboBox to the right of the address table
        ComboBox<String> addresstypeupdate = new ComboBox<String>();
        addresstypeupdate.getItems().addAll(
            "home",
	        "work",
    		"other"
        );
        addresstypeupdate.setTranslateX(1005);
        addresstypeupdate.setTranslateY(75); 
        addresstypeupdate.setVisible(false); 
  

        //The textfield to the right of the address table
        TextField street1update = new TextField();
        street1update.setTranslateX(1005);
        street1update.setTranslateY(105);
        street1update.setPromptText("Street 1");
        street1update.setVisible(false);

       	//The textfield to the right of the address table
       	TextField street2update = new TextField();
        street2update.setTranslateX(1005);
        street2update.setTranslateY(135);
       	street2update.setPromptText("Street 2");
       	street2update.setVisible(false);

        //The textfield at the right of the address table
        TextField cityupdate = new TextField();
        cityupdate.setTranslateX(1005);
        cityupdate.setTranslateY(165);
       	cityupdate.setPromptText("City");
       	cityupdate.setVisible(false);

        //The comboBox at the right of the address table
        ComboBox<String> stateupdate = new ComboBox<String>();
        stateupdate.getItems().addAll(
        	stateabbrev
        );
        stateupdate.setTranslateX(1005);
        stateupdate.setTranslateY(195);
        stateupdate.setVisible(false);
        stateupdate.setPrefWidth(100);
        stateupdate.setEditable(true);

        //The textfield at the right of the address table
        TextField zipcodeupdate = new TextField();
        zipcodeupdate.setTranslateX(1005);
        zipcodeupdate.setTranslateY(225);
       	zipcodeupdate.setPromptText("Zip Code");
       	zipcodeupdate.setVisible(false);

 		//Clearing the textfields to the right of the address table
 		Button addressupdateclear = new Button("Clear");
 		addressupdateclear.setTranslateX(1005);
 		addressupdateclear.setTranslateY(255);
 		addressupdateclear.setVisible(false);

 		//Deleting the address
 		Button addressdel = new Button("Delete");
 		addressdel.setTranslateX(1057);
 		addressdel.setTranslateY(255);
 		addressdel.setVisible(false);

        //If you don't want to update the chosen address information
        Button addressupdatecancel = new Button("Cancel");
        addressupdatecancel.setTranslateX(1005);
        addressupdatecancel.setTranslateY(285);
        addressupdatecancel.setVisible(false);

 		//Updating the chosen address
 		Button addressupdate = new Button("Update");
 		addressupdate.setTranslateX(1067);
 		addressupdate.setTranslateY(285);
 		addressupdate.setVisible(false);

 		//for address
    	final ObservableList<Address> adata = FXCollections.observableArrayList();
    	final TableView<Address> ainfo = new TableView<Address>(adata);

       	//Address type column
        TableColumn<Address, String> addressTypeCol = new TableColumn<Address, String>("Address Type");
        addressTypeCol.setMinWidth(100);
        addressTypeCol.setCellValueFactory(new PropertyValueFactory<Address, String>("addressType"));    

        //Street 1 column
        TableColumn<Address, String> street1Col = new TableColumn<Address, String>("Street 1");
        street1Col.setMinWidth(200);
        street1Col.setCellValueFactory(new PropertyValueFactory<Address, String>("street1"));      

        //Street 2 column
        TableColumn<Address, String> street2Col = new TableColumn<Address, String>("Street 2");
        street2Col.setMinWidth(200);
        street2Col.setCellValueFactory(new PropertyValueFactory<Address, String>("street2"));    

        //City column
        TableColumn<Address, String> cityCol = new TableColumn<Address, String>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(new PropertyValueFactory<Address, String>("city"));   

        //State column
        TableColumn<Address, String> stateCol = new TableColumn<Address, String>("State");
        stateCol.setMinWidth(25);
        stateCol.setCellValueFactory(new PropertyValueFactory<Address, String>("state"));  

        //Zip Code column
        TableColumn<Address, String> zipCodeCol = new TableColumn<Address, String>("Zip Code");
        zipCodeCol.setMinWidth(50);
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<Address, String>("zipCode"));    

        ainfo.setLayoutX(800);
        ainfo.setLayoutY(75);
        ainfo.setPrefWidth(200);

        ainfo.getColumns().add(addressTypeCol);
        ainfo.getColumns().add(street1Col);
        ainfo.getColumns().add(street2Col);
        ainfo.getColumns().add(cityCol);
        ainfo.getColumns().add(stateCol);
        ainfo.getColumns().add(zipCodeCol);

        //Setting up the page
        clear(pinfo, einfo, ainfo);

        if (index < Person.people.size()) { //If the user is editing a contact
        	fn.setText(Person.people.get(index).getFirstName());
        	ln.setText(Person.people.get(index).getLastName());
        	c.setText(Person.people.get(index).getCompany());
        	n.setText(Notes.notes.get(index).getNotes());

        	delete.setVisible(true);

        	//Getting the values of the contact
	        originalfn = fn.getText();
	        originalln = ln.getText();
	        originalc = c.getText();
	        originaln = n.getText();

	        for (int x = 0; x < phones.get(index).size(); x++) {
	        	originalp += Phone.phone.get(phones.get(index).get(x)).getPhoneType() + Phone.phone.get(phones.get(index).get(x)).getPhoneNumber() + Phone.phone.get(phones.get(index).get(x)).getOptionalExtension();
	        	
	        	pdata.add(Phone.phone.get(phones.get(index).get(x)));
	        }

	        for (int x = 0; x < emails.get(index).size(); x++) {
	        	originale += Email.email.get(emails.get(index).get(x)).getEmailType() + Email.email.get(emails.get(index).get(x)).getEmailAddress();
	        	
	        	edata.add(Email.email.get(emails.get(index).get(x)));
	        }

	        for (int x = 0; x < addresses.get(index).size(); x++) {
	        	originala += Address.address.get(addresses.get(index).get(x)).getAddressType() + Address.address.get(addresses.get(index).get(x)).getStreet1() + Address.address.get(addresses.get(index).get(x)).getStreet2() + Address.address.get(addresses.get(index).get(x)).getCity() + Address.address.get(addresses.get(index).get(x)).getState() + Address.address.get(addresses.get(index).get(x)).getZipCode();
	        	
	        	adata.add(Address.address.get(addresses.get(index).get(x)));
	        }
        } else {
        	phones.add(new ArrayList<Integer>());
        	emails.add(new ArrayList<Integer>());
        	addresses.add(new ArrayList<Integer>());
        }

		//Closing this window when the main window is closed
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        stage1.close();
		    }
		});	

		//Preventing the page from closing       
		stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        event.consume();

		        if (index < Person.people.size()) { //Warning label if this is going to be a new contact
		        	closewarning0.setVisible(true);
			    } else { //Warning label if the user is updating a contact
			       	closewarning.setVisible(true);   	
			    }
		    }
		});

        //Closing the window
    	pagecancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				infoList(search, lookup);
				stage1.close();
			}
		});

    	//Deleting the contact
    	delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				//Updating the addressbook values
				Person.people.remove(index);
				Notes.notes.remove(index);
				phones.remove(index);		
				emails.remove(index);
				addresses.remove(index);
				saveContact(); 

				infoList(search, lookup); //Updating the table on the main page

				stage1.close();
			}
		});

        /*
          Done button
          If you want to make sure that the contacts list is updated, you have to press
          the view existing contacts button
        */
        //Save the responses into a file
    	done.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String fnvalue = fn.getText();
				String lnvalue = ln.getText();
				String cvalue = c.getText();
				String nvalue = n.getText();

				if (index < Person.people.size()) { //If the user is updating a contact
					FirstName.firstNames.get(index).setFirstName(fnvalue);
					LastName.lastNames.get(index).setLastName(lnvalue);
					Company.companies.get(index).setCompany(cvalue);
					Notes.notes.get(index).setNotes(nvalue);
				} else { //If the user is creating a new contact
					Person np = new Person(fnvalue, lnvalue, cvalue);
					Notes nn = new Notes(nvalue);
				}

				saveContact();

				infoList(search, lookup);

				stage1.close();
			}
		});

		/*
		  Making sure the user only enters a first name, checks to see if the first name entered
		  are all letters and is less than 30 characters
		*/
        fn.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(fn, oldValue, newValue, 0);
        	setSaveState(done, index, fn, ln, c, n);
		});

		/*
		  Making sure the user only enters a last name, checks to see if the first name entered
		  are all letters and is less than 30 characters
		*/
        ln.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(ln, oldValue, newValue, 0);
			setSaveState(done, index, fn, ln, c, n);
		});

		/*
		  Making sure the user only enters a company name, checks to see if the first name entered
		  are all letters and is less than 30 characters
		*/
        c.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(c, oldValue, newValue, 1);
        	setSaveState(done, index, fn, ln, c, n);
		});


        n.textProperty().addListener((observable, oldValue, newValue) -> {
        	setSaveState(done, index, fn, ln, c, n);
		});

        //Formating the phone number
        phonenumberadd.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                //Checks if user is deleting numbers (new < old)
                //Checks if the Textfield is empty
                //Checks to see if the user deleted one of the numbers next to a character
				if (!newValue.isEmpty() && newValue.length() < oldValue.length() && (newValue.substring(newValue.length() - 1).equals("-") || newValue.trim().substring(newValue.length() - 1).equals(")") || newValue.substring(newValue.length() - 1).equals("("))) {
					newValue = newValue.trim().substring(0, newValue.length() - 1);
				}

                //Checks for non-digit numbers
                if (!newValue.isEmpty() && !newValue.substring(newValue.length() - 1).matches("\\d")) {
					locked = true;
                    phonenumberadd.setText(oldValue);
					locked = false;
                } else {
                    String trueNum = "";

                    for (char character : newValue.toCharArray()) {
                        if (String.valueOf(character).matches("\\d")) {
                            trueNum += character;
                        } 
                    }
					
					String newTextValue = "";

					//Formatting the phone number
                    if (trueNum.length() > 10) {
                        newTextValue = trueNum;
                    } else if (trueNum.length() > 6) {
                        newTextValue = "(" + trueNum.substring(0,3) + ") " + trueNum.substring(3,6) + "-" + trueNum.substring(6);
                    } else if (trueNum.length() > 3) {
                        newTextValue = trueNum.substring(0,3) + "-" + trueNum.substring(3);
                    } else {
                        newTextValue = trueNum;
                    }
					
					final String finalValue = newTextValue;
					
					Platform.runLater(() -> {
						locked = true;
						phonenumberadd.setText(finalValue);
                        //Moves the mouse caret to the end of the value
						phonenumberadd.positionCaret(finalValue.length());
						locked = false;
					});
                }
            }
        });

        //Making sure the user only enters numbers
        optionalextensionadd.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(optionalextensionadd, oldValue, newValue, 3);
        });

        //Checks to see if the phone type changed
        phonetypeupdate.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				phoneState(phoneupdate, index, prow, phonetypeupdate, phonenumberupdate, optionalextensionupdate);
			}
        });

        //Formating the phone number and seeing if the phone number changed
        phonenumberupdate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!locked) {
                //Checks if user is deleting numbers (new < old)
                //Checks if the Textfield is empty
                //Checks to see if the user deleted one of the numbers next to a character
				if (!newValue.isEmpty() && newValue.length() < oldValue.length() && (newValue.substring(newValue.length() - 1).equals("-") || newValue.trim().substring(newValue.length() - 1).equals(")") || newValue.substring(newValue.length() - 1).equals("("))) {
					newValue = newValue.trim().substring(0, newValue.length() - 1);
				}

                //Checks for non-digit numbers
                if (!newValue.isEmpty() && !newValue.substring(newValue.length() - 1).matches("\\d")) {
					locked = true;
                    phonenumberupdate.setText(oldValue);
					locked = false;
                } else {
                    String trueNum = "";

                    for (char character : newValue.toCharArray()) {
                        if (String.valueOf(character).matches("\\d")) {
                            trueNum += character;
                        } 
                    }
					
					String newTextValue = "";

                    if (trueNum.length() > 10) {
                        newTextValue = trueNum;
                    } else if (trueNum.length() > 6) {
                        newTextValue = "(" + trueNum.substring(0,3) + ") " + trueNum.substring(3,6) + "-" + trueNum.substring(6);
                    } else if (trueNum.length() > 3) {
                        newTextValue = trueNum.substring(0,3) + "-" + trueNum.substring(3);
                    } else {
                        newTextValue = trueNum;
                    }
					
					final String finalValue = newTextValue;
					
					Platform.runLater(() -> {
						locked = true;
						phonenumberupdate.setText(finalValue);
                        //Moves the mouse caret to the end of the value
						phonenumberupdate.positionCaret(finalValue.length());
						locked = false;
					});
                }
            }

        	phoneState(phoneupdate, index, prow, phonetypeupdate, phonenumberupdate, optionalextensionupdate);
        });

        //Making sure the user only enters numbers and seeing if the optional extension changed
        optionalextensionupdate.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(optionalextensionupdate, oldValue, newValue, 3);
        	phoneState(phoneupdate, index, prow, phonetypeupdate, phonenumberupdate, optionalextensionupdate);
        });

    	//Clearing all of the textfields at the bottom of the page
		phoneaddclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				phonetypeadd.setValue("home");
				phonenumberadd.clear();
				optionalextensionadd.clear();
			}
		});

		//Adding a new phone number
    	phoneadd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newphonetype = phonetypeadd.getValue();
				String newphonenumber = phonenumberadd.getText();
				String newoptionalextension = optionalextensionadd.getText();

				phoneaddwarning.setVisible(true);

				if (!phonenumberadd.getText().isEmpty()) { //If the optional extension is only entered
					if (newphonetype == null) {
						newphonetype = "home";
					}

					pdata.add(new Phone(newphonetype, newphonenumber, newoptionalextension));

					phones.get(index).add(Phone.phone.size()-1);

					phonetypeadd.setValue("home");
					phonenumberadd.clear();
					optionalextensionadd.clear();

					phoneaddwarning.setVisible(false);

					setSaveState(done, index, fn, ln, c, n);
				}
			}
		});

    	//Clearing all of the textfields at the right of the page
		phoneupdateclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				phonetypeupdate.setValue("home");
				phonenumberupdate.clear();
				optionalextensionupdate.clear();
			}
		});

    	//Deleting the chosen phone information
    	phonedel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				phones.get(index).remove(prow);

				phoneGet(pdata, pinfo, index);

				phoneClose(true, phonetypeupdate, phonenumberupdate, optionalextensionupdate, phoneupdateclear, phonedel, phoneupdatecancel, phoneupdate);

				setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//If you don't want to update the chosen phone information
    	phoneupdatecancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				phoneClose(true, phonetypeupdate, phonenumberupdate, optionalextensionupdate, phoneupdateclear, phonedel, phoneupdatecancel, phoneupdate);
			}
		});


    	//Updating the phone information
    	phoneupdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newphonetype = phonetypeupdate.getValue();
				String newphonenumber = phonenumberupdate.getText();
				String newoptionalextension = optionalextensionupdate.getText();

				PhoneType.phoneType.get(prow).setPhoneType(newphonetype);
				PhoneNumber.phoneNumber.get(prow).setPhoneNumber(newphonenumber);
				OptionalExtension.optionalExtension.get(prow).setOptionalExtension(newoptionalextension);

				phoneGet(pdata, pinfo, index);

		        phoneClose(true, phonetypeupdate, phonenumberupdate, optionalextensionupdate, phoneupdateclear, phonedel, phoneupdatecancel, phoneupdate);

		        setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//When a phone number is chosen
        pinfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2){ //You have to double click to get the data
                    prow = pinfo.getSelectionModel().getSelectedIndex(); //Row number

                    if (prow != -1) {
                    	phoneClose(false, phonetypeupdate, phonenumberupdate, optionalextensionupdate, phoneupdateclear, phonedel, phoneupdatecancel, phoneupdate);

                    	pucheck = Phone.phone.get(phones.get(index).get(prow)).getPhoneType() + Phone.phone.get(phones.get(index).get(prow)).getPhoneNumber() + Phone.phone.get(phones.get(index).get(prow)).getOptionalExtension();
	                    phonetypeupdate.setValue(Phone.phone.get(phones.get(index).get(prow)).getPhoneType());
	                    phonenumberupdate.setText(Phone.phone.get(phones.get(index).get(prow)).getPhoneNumber());
	                    optionalextensionupdate.setText(Phone.phone.get(phones.get(index).get(prow)).getOptionalExtension());
                    }
                }
            }
        });

        //Checking to see if the email type changed
        emailtypeupdate.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				emailState(emailupdate, index, erow, emailtypeupdate, emailaddressupdate);
			}
        });

        //Checking to see if the email address changed
        emailaddressupdate.textProperty().addListener((observable, oldValue, newValue) -> {
        	emailState(emailupdate, index, erow, emailtypeupdate, emailaddressupdate);
        });

    	//Clearing all of the textfields at the bottom of the page
		emailaddclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				emailtypeadd.setValue("home");
				emailaddressadd.clear();
			}
		});

		//Adding a new email
    	emailadd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newemailtype = emailtypeadd.getValue();
				String newemailaddress = emailaddressadd.getText();

				emailaddwarning.setVisible(true);

				if (!emailaddressadd.getText().isEmpty()) { //If an email is entered
					String[] eadd = newemailaddress.split("@");

					if (eadd.length == 2) { //If there's a value before and after the "@"
						if (eadd[1].length() > 4) { //If there's possibly a domain at the end
							String[] eadd0 = eadd[1].split("\\.");

							if (eadd0.length == 2) { //Making sure that there's only 1 domain
								if (eadd0[1].equals("com") || eadd0[1].equals("net") || eadd0[1].equals("edu") || eadd0[1].equals("org")) { //Checking to see if the user used a valid domain
									emailaddwarning.setVisible(false);

									//If the user didn't pick a phone type
									if (newemailtype == null) {
										newemailtype = "home";
									}
									
									edata.add(new Email(newemailtype, newemailaddress));

									emails.get(index).add(Email.email.size()-1);

									emailtypeadd.setValue("home");
									emailaddressadd.clear();

									setSaveState(done, index, fn, ln, c, n);
								}
							}
						}
					} 
				}
			}
		});

    	//Clearing all of the textfields at the right of the page
		emailupdateclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				emailtypeupdate.setValue("home");
				emailaddressupdate.clear();
			}
		});

    	//Deleting the chosen email information
    	emaildel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				emails.get(index).remove(erow);

		        emailGet(edata, einfo, index);

				emailClose(true, emailtypeupdate, emailaddressupdate, emailupdateclear, emaildel, emailupdatecancel, emailupdate);

				setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//If you don't want to update the chosen email information
    	emailupdatecancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				emailClose(true, emailtypeupdate, emailaddressupdate, emailupdateclear, emaildel, emailupdatecancel, emailupdate);
			}
		});


    	//Updating the email information
    	emailupdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newemailtype = emailtypeupdate.getValue();
				String newemailaddress = emailaddressupdate.getText();

				EmailType.emailType.get(erow).setEmailType(newemailtype);
				EmailAddress.emailAddress.get(erow).setEmailAddress(newemailaddress);

		        emailGet(edata, einfo, index);

		        emailClose(true, emailtypeupdate, emailaddressupdate, emailupdateclear, emaildel, emailupdatecancel, emailupdate);

		        setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//When an email is chosen
        einfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2){ //You have to double click to get the data
                    erow = einfo.getSelectionModel().getSelectedIndex(); //Row number

                    if (erow != -1) {
                    	emailClose(false, emailtypeupdate, emailaddressupdate, emailupdateclear, emaildel, emailupdatecancel, emailupdate);

                    	eucheck = Email.email.get(emails.get(index).get(erow)).getEmailType() + Email.email.get(emails.get(index).get(erow)).getEmailAddress();
	                    emailtypeupdate.setValue(Email.email.get(emails.get(index).get(erow)).getEmailType());
	                    emailaddressupdate.setText(Email.email.get(emails.get(index).get(erow)).getEmailAddress());
                    }
                }
            }
        });

        //Making sure the user only enters characters
        cityadd.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(cityadd, oldValue, newValue, 4);
        });

        //Making sure the user only enters 5 numbers for the zip code
        zipcodeadd.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(zipcodeadd, oldValue, newValue, 2);
        });

        //Checking to see if the address type changed
        addresstypeupdate.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
			}
        });

        //Checking to see if the street 1 name changed
        street1update.textProperty().addListener((observable, oldValue, newValue) -> {
        	addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
        });

        //Checking to see if the street 2 name changed
        street2update.textProperty().addListener((observable, oldValue, newValue) -> {
        	addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
        });

        //Making sure the user only enters characters and if the city name changed
        cityupdate.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(cityupdate, oldValue, newValue, 4);
        	addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
        });

        //Checking to see if the state named changed
        stateupdate.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
			}
        });

        //Making sure the user only enters 5 numbers and to see if the zipcode changed
        zipcodeupdate.textProperty().addListener((observable, oldValue, newValue) -> {
        	check(zipcodeupdate, oldValue, newValue, 2);
        	addressState(addressupdate, index, arow, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate);
        });

    	//Clearing all of the textfields at the bottom of the page
		addressaddclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				addresstypeadd.setValue("home");
				street1add.clear();
				street2add.clear();
				cityadd.clear();
				stateadd.setValue(null);
				zipcodeadd.clear();
			}
		});

		//Adding a new address
    	addressadd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newaddresstype = addresstypeadd.getValue();
				String newstreet1 = street1add.getText();
				String newstreet2 = street2add.getText();
				String newcity = cityadd.getText();
				String newstate = stateadd.getValue();
				String newzipcode = zipcodeadd.getText();

				addressaddwarning.setVisible(true);

				if (!street1add.getText().isEmpty() && !cityadd.getText().isEmpty() && (stateadd.getValue() != null) && !zipcodeadd.getText().isEmpty()) { //If the required textfields are filled
					if (newzipcode.length() == 5) { //If the zipcode has 5 digits
						boolean statebool = false;

						if (newstate.length() > 2) { //If the full state name is entered
							//Making sure the state name is correct
							for (int x = 0; x < 50; x++) {
								if (statefull[x].equalsIgnoreCase(newstate)) {
									statebool = true;
									newstate = stateabbrev[x];
								}
							}
						} else { //If the state initial is entered
							//Making sure the state abbreviation is correct
							for (int x = 0; x < 50; x++) {
								if (stateabbrev[x].equalsIgnoreCase(newstate)) {
									statebool = true;
									newstate = stateabbrev[x];
								}
							}
						}

						if (statebool == true) { //If the state entered is correct
							//If an address type wasn't selected
							if (newaddresstype == null) {
								newaddresstype = "home";
							}

							addressaddwarning.setVisible(false);

							newcity = newcity.substring(0, 1).toUpperCase() + newcity.substring(1).toLowerCase();

							adata.add(new Address(newaddresstype, newstreet1, newstreet2, newcity, newstate, newzipcode));

							addresses.get(index).add(Address.address.size()-1);

							addresstypeadd.setValue("home");
							street1add.clear();
							street2add.clear();
							stateadd.setValue(null);
							cityadd.clear();

							stateadd.setValue(null);

							zipcodeadd.clear();

							setSaveState(done, index, fn, ln, c, n);
						}
					}
				} 
			}
		});

    	//Clearing all of the textfields at the right of the page
		addressupdateclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				addresstypeupdate.setValue("home");
				street1update.clear();
				street2update.clear();
				cityupdate.clear();
				stateupdate.setValue(null);
				zipcodeupdate.clear();
			}
		});

    	//Deleting the chosen address information
    	addressdel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				addresses.get(index).remove(arow);

		        addressGet(adata, ainfo, index);

				addressClose(true, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate, addressupdateclear, addressdel, addressupdatecancel, addressupdate);

				setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//If you don't want to update the chosen address information
    	addressupdatecancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				addressClose(true, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate, addressupdateclear, addressdel, addressupdatecancel, addressupdate);
			}
		});


    	//Updating the address information
    	addressupdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				String newaddresstype = addresstypeupdate.getValue();
				String newstreet1 = street1update.getText();
				String newstreet2 = street2update.getText();
				String newcity = cityupdate.getText();
				String newstate = stateupdate.getValue();
				String newzipcode = zipcodeupdate.getText();

				newcity = newcity.substring(0, 1).toUpperCase() + newcity.substring(1).toLowerCase();

				AddressType.addressType.get(arow).setAddressType(newaddresstype);
				Street1.street1.get(arow).setStreet1(newstreet1);
				Street2.street2.get(arow).setStreet2(newstreet2);
				City.city.get(arow).setCity(newcity);
				State.state.get(arow).setState(newstate);
				ZipCode.zipCode.get(arow).setZipCode(newzipcode);

		        addressGet(adata, ainfo, index);

		        addressClose(true, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate, addressupdateclear, addressdel, addressupdatecancel, addressupdate);

		        setSaveState(done, index, fn, ln, c, n);
			}
		});

    	//When an address is chosen
        ainfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2){ //You have to double click to get the data
                    arow = ainfo.getSelectionModel().getSelectedIndex(); //Row number

                    if (arow != -1) {
                    	addressClose(false, addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate, addressupdateclear, addressdel, addressupdatecancel, addressupdate);

                    	aucheck = Address.address.get(addresses.get(index).get(arow)).getAddressType() + Address.address.get(addresses.get(index).get(arow)).getStreet1() + Address.address.get(addresses.get(index).get(arow)).getStreet2() + Address.address.get(addresses.get(index).get(arow)).getCity() + Address.address.get(addresses.get(index).get(arow)).getState() + Address.address.get(addresses.get(index).get(arow)).getZipCode();

	                    addresstypeupdate.setValue(Address.address.get(addresses.get(index).get(arow)).getAddressType());
	                    street1update.setText(Address.address.get(addresses.get(index).get(arow)).getStreet1());
	                    street2update.setText(Address.address.get(addresses.get(index).get(arow)).getStreet2());
	                    cityupdate.setText(Address.address.get(addresses.get(index).get(arow)).getCity());
	                    stateupdate.setValue(Address.address.get(addresses.get(index).get(arow)).getState());
	                    zipcodeupdate.setText(Address.address.get(addresses.get(index).get(arow)).getZipCode());
                    }
                }
            }
        });

		root1.getChildren().addAll(pagecancel, delete, done, closewarning, closewarning0, 
								   fn, ln, c, n,
								   pinfo, einfo, ainfo, 
								   phonetypeadd, phonenumberadd, optionalextensionadd, phoneaddclear, phoneadd, phoneaddwarning, 
								   phonetypeupdate,  phonenumberupdate,  optionalextensionupdate, phoneupdateclear, phonedel, phoneupdatecancel, phoneupdate,
								   emailtypeadd, emailaddressadd, emailaddclear, emailadd, emailaddwarning,
								   emailtypeupdate, emailaddressupdate, emailupdateclear, emaildel, emailupdatecancel, emailupdate,
								   addresstypeadd, street1add, street2add, cityadd, stateadd, zipcodeadd, addressaddclear, addressadd, addressaddwarning,
								   addresstypeupdate, street1update, street2update, cityupdate, stateupdate, zipcodeupdate, addressupdateclear, addressdel, addressupdatecancel, addressupdate);			
		stage1.setScene(scene1);
		stage1.show();
	}

    /** 
     * Setting up the table on the main page
     * @param root
     */
    public static void runTable(Group root) {
        //Table
        info.setLayoutX(0);
        info.setLayoutY(35);

        //First Name Column
        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
        firstNameCol.setMinWidth(240);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        //Last Name Column
        TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>("Last Name");
        lastNameCol.setMinWidth(240);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        //Company Column
        TableColumn<Person, String> companyCol = new TableColumn<Person, String>("Company");
        companyCol.setMinWidth(240);
        companyCol.setCellValueFactory(new PropertyValueFactory<Person, String>("company"));        

        info.getColumns().add(firstNameCol);
        info.getColumns().add(lastNameCol);
        info.getColumns().add(companyCol);

        root.getChildren().add(info);
    }

    /**
     * Getting the contacts from a text file
     * @param search
     * @param lookup
     */
    public static void infoList(boolean search, String lookup) {
        String line = null;
        String fileName = "contacts.txt";

        //Getting rid of all of the current contacts
        lookuplist.removeAll(lookuplist);

        Person.people.removeAll(Person.people);
        FirstName.firstNames.removeAll(FirstName.firstNames);
        LastName.lastNames.removeAll(LastName.lastNames);
        Company.companies.removeAll(Company.companies);

        Notes.notes.removeAll(Notes.notes);

        phones.removeAll(phones);
        Phone.phone.removeAll(Phone.phone);
        PhoneType.phoneType.removeAll(PhoneType.phoneType);
        PhoneNumber.phoneNumber.removeAll(PhoneNumber.phoneNumber);
        OptionalExtension.optionalExtension.removeAll(OptionalExtension.optionalExtension);

        emails.removeAll(emails);
        Email.email.removeAll(Email.email);
        EmailType.emailType.removeAll(EmailType.emailType);
        EmailAddress.emailAddress.removeAll(EmailAddress.emailAddress);    

        addresses.removeAll(addresses);
        Address.address.removeAll(Address.address);
        AddressType.addressType.removeAll(AddressType.addressType);
        Street1.street1.removeAll(Street1.street1);
        Street2.street2.removeAll(Street2.street2);
        City.city.removeAll(City.city);
        State.state.removeAll(State.state);
        ZipCode.zipCode.removeAll(ZipCode.zipCode);    

        //Pulling up a contact
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int pcounter = 0;
            int phonecount = 0;

            int ecounter = 0;
            int emailcount = 0;

            int acounter = 0;
            int addresscount = 0;

            //Finding the right contact to change in the text file
            while ((line = bufferedReader.readLine()) != null) {
                phones.add(new ArrayList<Integer>());
                emails.add(new ArrayList<Integer>());
                addresses.add(new ArrayList<Integer>());

                //"|" splits up the contact info into first name, last name, company, notes, phone number, email, and address
                String[] infoArray = line.split("\\|", -1);

                //If there's no first name
                if (infoArray[0].equals("null")) {
                    infoArray[0] = "";
                }

                //If there's no last name
                if (infoArray[1].equals("null")) {
                    infoArray[1] = "";
                }                
                
                //If there's no company name
                if (infoArray[2].equals("null")) {
                    infoArray[2] = "";
                }

                Person p = new Person(infoArray[0], infoArray[1], infoArray[2]);

                //If there's no notes
                if (infoArray[3].equals("null")) {
                    infoArray[3] = "";
                }

                Notes ns = new Notes(infoArray[3]);

                //"/" splits up the types of phone numbers, emails, and addresses
                String[] phoneNums = infoArray[4].split("/", -1);

                String[][] pn = new String[phoneNums.length][];

                //"," splits up the phone number info into: phone number type (home, mobile, etc.), phone number, and optional extension
                int comma = 0;

                for (String pns : phoneNums) {
                    pn[comma++] = pns.split(",");
                }

                //Organizing the phone number info
                for (int x = 0; x < phoneNums.length; x++) {
                    if (pn[x][0].equals("null") && pn[x][1].equals("null") && pn[x][2].equals("null")) { //If there's no phone number
                        continue;
                    } else {
                        //If there's no phone type
                        if (pn[x][0].equals("null")) {
                            pn[x][0] = "";
                        }

                        //If there's no phone number
                        if (pn[x][1].equals("null")) {
                            pn[x][1] = "";
                        }

                        //If there's no optional extension
                        if (pn[x][2].equals("null")) {
                            pn[x][2] = "";
                        }

                        Phone npe = new Phone(pn[x][0], pn[x][1], pn[x][2]);
                        phones.get(pcounter).add(phonecount);
                        phonecount++;
                    }
                }

                pcounter++;

                //"/" splits up the types of phone numbers, emails, and addresses
                String[] emailAdds = infoArray[5].split("/", -1);

                String[][] ea = new String[emailAdds.length][];

                //"," splits up the email info into: email type, email address
                comma = 0;
                for (String eas : emailAdds) {
                    ea[comma++] = eas.split(",");
                }

                //Organizing the email info
                for (int x = 0; x < emailAdds.length; x++) {
                    if (ea[x][0].equals("null") && ea[x][1].equals("null")) { //If there's no email
                        continue;
                    } else {
                        //IF there's no email type
                        if (ea[x][0].equals("null")) {
                            ea[x][0] = "";
                        }

                        //If there's no email
                        if (ea[x][1].equals("null")) {
                            ea[x][1] = "";
                        }

                        Email nel = new Email(ea[x][0], ea[x][1]);
                        emails.get(ecounter).add(emailcount);
                        emailcount++;
                    }
                }

                ecounter++;

                //"/" splits up the types of phone numbers, emails, and addresses
                String[] addressAdds = infoArray[6].split("/", -1);

                String[][] a = new String[addressAdds.length][];

                //"," splits up the email info into: email type, email address
                comma = 0;

                for (String as : addressAdds) {
                    a[comma++] = as.split(",");
                }

                //Organizing the email info
                for (int x = 0; x < addressAdds.length; x++) { //doesn't work if one of the values is ""
                    if (a[x][0].equals("null") && a[x][1].equals("null") && a[x][2].equals("null") && a[x][3].equals("null") && a[x][4].equals("null") && a[x][5].equals("null")) { //If there's no address
                        continue;
                    } else {
                        //If there's no address type
                        if (a[x][0].equals("null")) {
                            a[x][0] = "";
                        }

                        //If there's no street 1 name
                        if (a[x][1].equals("null")) {
                            a[x][1] = "";
                        }

                        //If there's no street 2 name
                        if (a[x][2].equals("null")) {
                            a[x][2] = "";
                        }

                        //If there's no city name
                        if (a[x][3].equals("null")) {
                            a[x][3] = "";
                        }

                        //If there's no state name
                        if (a[x][4].equals("null")) {
                            a[x][4] = "";
                        }

                        //If there's no zipcode
                        if (a[x][5].equals("null")) {
                            a[x][5] = "";
                        }

                        Address nas = new Address(a[x][0], a[x][1], a[x][2], a[x][3], a[x][4], a[x][5]);
                        addresses.get(acounter).add(addresscount);
                        addresscount++;               
                    }
                }

                acounter++;
            }

            bufferedReader.close();

            for (int x = 0; x < info.getItems().size(); x++) {
                info.getItems().clear();
            }

            //Collecting and displaying the desired contacts
            if (search == true) { //If the user is searching up a value
                if (!lookup.equals("")) {
                    for (int x = 0; x < Person.people.size(); x++) {
                        boolean lookupcheck = false;

                        //Checking to see if the search value matches a first name
                        if (lookup.length() <= Person.people.get(x).getFirstName().length()) {
                            if (Person.people.get(x).getFirstName().substring(0, lookup.length()).toUpperCase().equals(lookup.toUpperCase())) {
                                lookupcheck = true;
                            }
                        }

                        //Checking to see if the search value matches a last name
                        if (lookup.length() <= Person.people.get(x).getLastName().length()) {
                            if (Person.people.get(x).getLastName().substring(0, lookup.length()).toUpperCase().equals(lookup.toUpperCase())) {
                                lookupcheck = true;
                            }
                        } 

                        //Checking to see if the search value matches a company name
                        if (lookup.length() <= Person.people.get(x).getCompany().length()) {
                            if (Person.people.get(x).getCompany().substring(0, lookup.length()).toUpperCase().equals(lookup.toUpperCase())) {
                                lookupcheck = true;
                            }
                        } 

                        if (lookupcheck == true) { //If there's a match
                            data.add(Person.people.get(x));
                            lookuplist.add(x);
                        }
                    }
                }
            } else {
                for (int x = 0; x < Person.people.size(); x++) {
                    data.add(Person.people.get(x));
                    lookuplist.add(x);
                }
            }
        } catch (FileNotFoundException ex) {
            fileAlert.show(); 
        } catch (IOException ex) { 
            errorAlert.show();  
        }
    }

	/**
     * Clearing the tables
     * @param pinfo
     * @param einfo
     * @param ainfo
     */
	private static void clear(TableView<Phone> pinfo, TableView<Email> einfo, TableView<Address> ainfo) {
        for (int x = 0; x < pinfo.getItems().size(); x++) {
            pinfo.getItems().clear();
        }

        for (int x = 0; x < einfo.getItems().size(); x++) {
            einfo.getItems().clear();
        }

        for (int x = 0; x < ainfo.getItems().size(); x++) {
            ainfo.getItems().clear();
        }
	}

	/**
     * Checking to see if something changed or if a first name, last name, or company name is entered
     * @param done
     * @param index
     * @param fn
     * @param ln
     * @param c
     * @param n
     */
	private static void setSaveState(Button done, int index, TextField fn, TextField ln, TextField c, TextField n) {
		String fnval = fn.getText();
		String lnval = ln.getText();
		String cval = c.getText();
        String nval = n.getText();
        String pval = "";
        String eval = "";
        String aval = "";

        //Checking to see if any of the values changed
        for (int x = 0; x < phones.get(index).size(); x++) {
        	pval += Phone.phone.get(phones.get(index).get(x)).getPhoneType() + Phone.phone.get(phones.get(index).get(x)).getPhoneNumber() + Phone.phone.get(phones.get(index).get(x)).getOptionalExtension();
        }

        for (int x = 0; x < emails.get(index).size(); x++) {
        	eval += Email.email.get(emails.get(index).get(x)).getEmailType() + Email.email.get(emails.get(index).get(x)).getEmailAddress();
        }

        for (int x = 0; x < addresses.get(index).size(); x++) {
        	aval += Address.address.get(addresses.get(index).get(x)).getAddressType() + Address.address.get(addresses.get(index).get(x)).getStreet1() + Address.address.get(addresses.get(index).get(x)).getStreet2() + Address.address.get(addresses.get(index).get(x)).getCity() + Address.address.get(addresses.get(index).get(x)).getState() + Address.address.get(addresses.get(index).get(x)).getZipCode();
        }

		if (fnval.equals("") && lnval.equals("") && cval.equals("")) { //If there's no identity
			done.setVisible(false);
		} else {
			if (fnval.equals(originalfn) && lnval.equals(originalln) && cval.equals(originalc) && nval.equals(originaln) && pval.equals(originalp) && eval.equals(originale) && aval.equals(originala)) {
				done.setVisible(false);
			} else {
				done.setVisible(true);
			}
		}
	}

	/**
     * Checking to see if chosen contact has changed
     * @param phoneupdate
     * @param index
     * @param prow
     * @param phonetypeupdate
     * @param phonenumberupdate
     * @param optionalextensionupdate
     */
	private static void phoneState(Button phoneupdate, int index, int prow, ComboBox<String> phonetypeupdate, TextField phonenumberupdate, TextField optionalextensionupdate) {
		String puval = String.valueOf(phonetypeupdate.getValue()) + String.valueOf(phonenumberupdate.getText()) + String.valueOf(optionalextensionupdate.getText());

		phoneupdate.setVisible(false);

		if (!phonenumberupdate.getText().isEmpty()) { //If the optional extension is only entered
			if (!pucheck.equals(puval)) {
				phoneupdate.setVisible(true);
			}
		}
	}

	/**
     * Pulling up or closing the phone update section
     * @param phoneClose
     * @param phonetypeupdate
     * @param phonenumberupdate
     * @param optionalextensionupdate
     * @param phoneupdateclear
     * @param phonedel
     * @param phoneupdatecancel
     * @param phoneupdate
     */
	private static void phoneClose(boolean phoneClose, ComboBox<String> phonetypeupdate, TextField phonenumberupdate, TextField optionalextensionupdate, Button phoneupdateclear, Button phonedel, Button phoneupdatecancel, Button phoneupdate) {
		if (phoneClose == true) {
	        phonetypeupdate.setVisible(false);
	        phonenumberupdate.setVisible(false);
	        optionalextensionupdate.setVisible(false);

	        phoneupdateclear.setVisible(false);
	        phonedel.setVisible(false);
	        phoneupdatecancel.setVisible(false);
	        phoneupdate.setVisible(false);
		} else {
            phonetypeupdate.setVisible(true);
            phonenumberupdate.setVisible(true);
            optionalextensionupdate.setVisible(true);

            phoneupdateclear.setVisible(true);
            phonedel.setVisible(true);
            phoneupdatecancel.setVisible(true);
		}
	}

	/**
     * Setting up the phone number table
     * @param pdata
     * @param pinfo
     * @param index
     */
	private static void phoneGet(ObservableList<Phone> pdata, TableView<Phone> pinfo, int index) {
        for (int x = 0; x < pinfo.getItems().size(); x++) {
            pinfo.getItems().clear();
        }

        for (int x = 0; x < phones.get(index).size(); x++) {
        	pdata.add(Phone.phone.get(phones.get(index).get(x)));
        }
	}

	/**
     * Checking to see if chosen contact has changed
     * @param emailupdate
     * @param index
     * @param erow
     * @param emailtypeupdate
     * @param emailaddressupdate
     */
	private static void emailState(Button emailupdate, int index, int erow, ComboBox<String> emailtypeupdate, TextField emailaddressupdate) {
		String euval = String.valueOf(emailtypeupdate.getValue()) + String.valueOf(emailaddressupdate.getText());
		String evalue = emailaddressupdate.getText();

		emailupdate.setVisible(false);

		if (!emailaddressupdate.getText().isEmpty()) {  //If the email textbox isn't empty
			String[] eup = evalue.split("@");

			if (eup.length == 2) { //If there's a value before the "@" sign and after it
				if (eup[1].length() > 4) { //If the value after the "@" sign is possbily a valid domain
					String[] eup0 = eup[1].split("\\.");

					if (eup0.length == 2) { //If there's 1 domain
						if (eup0[1].equals("com") || eup0[1].equals("net") || eup0[1].equals("edu") || eup0[1].equals("org")) { //Checking to see if the domain is valid
							if (!eucheck.equals(euval)) {
								emailupdate.setVisible(true);
							}
						}
					}
				}
			}
		}
	}

	/**
     * Pulling up or closing the email update section
     * @param emailClose
     * @param emailtypeupdate
     * @param emailaddressupdate
     * @param emailupdateclear
     * @param emaildel
     * @param emailupdatecancel
     * @param emailupdate
     */
	private static void emailClose(boolean emailClose, ComboBox<String> emailtypeupdate, TextField emailaddressupdate, Button emailupdateclear, Button emaildel, Button emailupdatecancel, Button emailupdate) {
		if (emailClose == true) { //If the user is done updating the email
	        emailtypeupdate.setVisible(false);
	        emailaddressupdate.setVisible(false);

	        emailupdateclear.setVisible(false);
	        emaildel.setVisible(false);
	        emailupdatecancel.setVisible(false);
	        emailupdate.setVisible(false);
		} else {
            emailtypeupdate.setVisible(true);
            emailaddressupdate.setVisible(true);

            emailupdateclear.setVisible(true);
            emaildel.setVisible(true);
            emailupdatecancel.setVisible(true);
		}
	}

	/**
     * Setting the email table
     * @param edata
     * @param einfo
     * @param index
     */
	private static void emailGet(ObservableList<Email> edata, TableView<Email> einfo, int index) {
       	//Clearing the table
        for (int x = 0; x < einfo.getItems().size(); x++) {
            einfo.getItems().clear();
        }

        //Adding values to the table
        for (int x = 0; x < emails.get(index).size(); x++) {
        	edata.add(Email.email.get(emails.get(index).get(x)));
        }
	}

	/**
     * Checking to see if chosen contact has changed
     * @param addressupdate
     * @param index
     * @param arow
     * @param addresstypeupdate
     * @param street1update
     * @param street2update
     * @param cityupdate
     * @param stateupdate
     * @param zipcodeupdate
     */
	private static void addressState(Button addressupdate, int index, int arow, ComboBox<String> addresstypeupdate, TextField street1update, TextField street2update, TextField cityupdate, ComboBox<String> stateupdate, TextField zipcodeupdate) {
		String auval = String.valueOf(addresstypeupdate.getValue()) + String.valueOf(street1update.getText()) + String.valueOf(street2update.getText()) + String.valueOf(cityupdate.getText()) + String.valueOf(stateupdate.getValue()) + String.valueOf(zipcodeupdate.getText());
		String avalue = zipcodeupdate.getText();
		String stateup = stateupdate.getValue();

		addressupdate.setVisible(false);

		if (!street1update.getText().isEmpty() && !cityupdate.getText().isEmpty() && (stateupdate.getValue() != null) && !zipcodeupdate.getText().isEmpty()){ //Checking to see if none of the textfields (except for street 2) are empty
			if (avalue.length() == 5) { //If the zipcode is 5 digits long
				boolean statebool = false;

				//Checking to see if a correct state is entered
				if (stateup.length() > 2) { //If the user entered the state name
					for (int x = 0; x < 50; x++) {
						if (statefull[x].equalsIgnoreCase(stateup)) {
							statebool = true;
						}
					}
				} else { //If the user entered the state abbreviation
					for (int x = 0; x < 50; x++) {
						if (stateabbrev[x].equalsIgnoreCase(stateup)) {
							statebool = true;
						}
					}
				}

				if (statebool == true) {
					if (!aucheck.equals(auval)) {
						addressupdate.setVisible(true);
					}
				}
			}
		}
	}

	/**
     * Pulling up or closing the phone update section
     * @param addressClose
     * @param addresstypeupdate
     * @param street1update
     * @param street2update
     * @param cityupdate
     * @param stateupdate
     * @param zipcodeupdate
     * @param addressupdateclear
     * @param addressdel
     * @param addressupdatecancel
     * @param addressupdate
     */
	private static void addressClose(boolean addressClose, ComboBox<String> addresstypeupdate, TextField street1update, TextField street2update, TextField cityupdate, ComboBox<String> stateupdate, TextField zipcodeupdate, Button addressupdateclear, Button addressdel, Button addressupdatecancel, Button addressupdate) {
		if (addressClose == true) { //If the user is done updating the address
	        addresstypeupdate.setVisible(false);
	        street1update.setVisible(false);
	        street2update.setVisible(false);
	        cityupdate.setVisible(false);
	        stateupdate.setVisible(false);
	        zipcodeupdate.setVisible(false);

	        addressupdateclear.setVisible(false);
	        addressdel.setVisible(false);
	        addressupdatecancel.setVisible(false);
	        addressupdate.setVisible(false);
		} else {
	        addresstypeupdate.setVisible(true);
	        street1update.setVisible(true);
	        street2update.setVisible(true);
	        cityupdate.setVisible(true);
	        stateupdate.setVisible(true);
	        zipcodeupdate.setVisible(true);

	        addressupdateclear.setVisible(true);
	        addressdel.setVisible(true);
	        addressupdatecancel.setVisible(true);
		}
	}

	/**
     * Setting up the address table
     * @param adata
     * @param ainfo
     * @param index
     */
	private static void addressGet(ObservableList<Address> adata, TableView<Address> ainfo, int index) {
        //Clearing the table
        for (int x = 0; x < ainfo.getItems().size(); x++) {
            ainfo.getItems().clear();
        }

        //Adding values to the table
        for (int x = 0; x < addresses.get(index).size(); x++) {
        	adata.add(Address.address.get(addresses.get(index).get(x)));
        }
	}

	/**
     * Formatting the textfields
     * @param text
     * @param oldValue
     * @param newValue
     * @param limit
     */
	private static void check(TextField text, String oldValue, String newValue, int limit) {
		String trueNum = "";

		if (limit == 0) {
			//User can only enter letters
			for (char character : newValue.toCharArray()) {
				if (String.valueOf(character).matches("[A-Za-z]")) {
					trueNum += character;
				}
			}

			/*
			  The user can only enter a max of 30 letters
			  I wanted to limit the number of letters for the first and last name 
			  because people working with database would normally limit the 
			  number of characters that users can enter for their first and last 
			  name to avoid creating a mess of large values
			*/
			if (trueNum.length() > 30) {
				text.setText(trueNum.substring(0,30));
			} else {
				text.setText(trueNum);
			}
		} else if (limit == 1) {
			trueNum = newValue;

			//User can only enter a max of 30 letters
			if (trueNum.length() > 30) {
				text.setText(trueNum.substring(0,30));
			} else {
				text.setText(trueNum);
			}
		} else if (limit == 2) {
			//User can only enter numbers
			for (char character : newValue.toCharArray()) {
				if (String.valueOf(character).matches("\\d")) {
					trueNum += character;
				}
			}

			//User can only enter 5 numbers
			if (trueNum.length() > 5) {
				text.setText(trueNum.substring(0,5));
			} else {
				text.setText(trueNum);
			}			           
		} else if (limit == 3) {
			//User can only enter numbers
			for (char character : newValue.toCharArray()) {
				if (String.valueOf(character).matches("\\d")) {
					trueNum += character;
				}
			}

			text.setText(trueNum);
		} else if (limit == 4) {
			//User can only enter letters
			for (char character : newValue.toCharArray()) {
				if (String.valueOf(character).matches("[A-Za-z]")) {
					trueNum += character;
				}
			}

			text.setText(trueNum);
		}
	}

	/**
     * Saving the new/updated contact
     */
	private static void saveContact() {
	    String fileName = "contacts.txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			PrintWriter writer = new PrintWriter(fileName);
			writer.print("");
			writer.close();

			for (int x = 0; x < Person.people.size(); x++) {
				String fcheck = Person.people.get(x).getFirstName();
				String lcheck = Person.people.get(x).getLastName();
				String ccheck = Person.people.get(x).getCompany();
				String ncheck = Notes.notes.get(x).getNotes();

				//If a first name wasn't entered
				if (fcheck.equals("")) {
					fcheck = null;
				}

				//If a last name wasn't entered
				if (lcheck.equals("")) {
					lcheck = null;
				}

				//If a company name wasn't entered
				if (ccheck.equals("")) {
					ccheck = null;
				}

				//If there aren't any notes
				if (ncheck.equals("")) {
					ncheck = null;
				}

				bufferedWriter.write(fcheck + "|" + lcheck + "|" + ccheck + "|" + ncheck + "|");
				
				if (phones.get(x).size() == 0) { //If there aren't any phone numbers
					bufferedWriter.write("null,null,null");
				} else {
					for (int i = 0; i < phones.get(x).size(); i++) {
						String ptcheck = Phone.phone.get(phones.get(x).get(i)).getPhoneType();
						String pncheck = Phone.phone.get(phones.get(x).get(i)).getPhoneNumber();
						String oecheck = Phone.phone.get(phones.get(x).get(i)).getOptionalExtension();

						//If a phone type wasn't entered
						if (ptcheck.equals("")) {
							ptcheck = null;
						}

						//If a phone number wasn't entered
						if (pncheck.equals("")) {
							pncheck = null;
						}

						//If an optional extension wasn't entered
						if (oecheck.equals("")) {
							oecheck = null;
						}

					    bufferedWriter.write(ptcheck + "," + pncheck + "," + oecheck);
			        	
			        	if (i != phones.get(x).size() - 1) {
			        		if (phones.get(x).size() != 1) { //If there's only 1 phone number or this is the last one on the list of phone numbers
			        			bufferedWriter.write("/");
			        		}
					    }
					}
				}

				bufferedWriter.write("|");

				if (emails.get(x).size() == 0) { //If there aren't any email addresses
					bufferedWriter.write("null,null");
				} else {
					for (int i = 0; i < emails.get(x).size(); i++) {
						String etcheck = Email.email.get(emails.get(x).get(i)).getEmailType();
						String eacheck = Email.email.get(emails.get(x).get(i)).getEmailAddress();

						//If an email type wasn't entered
						if (etcheck.equals("")) {
							etcheck = null;
						}

						//If an email address wasn't entered
						if (eacheck.equals("")) {
							eacheck = null;
						}

					    bufferedWriter.write(etcheck + "," + eacheck);
			        	
			        	if (i != emails.get(x).size() - 1) {
			        		if (emails.get(x).size() != 1) { //If there's only 1 email or this is the last one on the list of emails 
			        			bufferedWriter.write("/");
			        		}
					    }
					}
				}

				bufferedWriter.write("|");

				if (addresses.get(x).size() == 0) { //If there aren't any addresses
					bufferedWriter.write("null,null,null,null,null,null");
				} else {
					for (int i = 0; i < addresses.get(x).size(); i++) {
						String atcheck = Address.address.get(addresses.get(x).get(i)).getAddressType();
						String s1check = Address.address.get(addresses.get(x).get(i)).getStreet1();
						String s2check = Address.address.get(addresses.get(x).get(i)).getStreet2();
						String cicheck = Address.address.get(addresses.get(x).get(i)).getCity();
						String scheck = Address.address.get(addresses.get(x).get(i)).getState();
						String zccheck = Address.address.get(addresses.get(x).get(i)).getZipCode();

						//If an address type wasn't entered
						if (atcheck.equals("")) {
							atcheck = null;
						}

						//If a street 1 name wasn't entered
						if (s1check.equals("")) {
							s1check = null;
						}

						//If a street 2 name wasn't entered
						if (s2check.equals("")) {
							s2check = null;
						}

						//If a city name wasn't entered
						if (cicheck.equals("")) {
							cicheck = null;
						}

						//If a state name wasn't entered
						if (scheck.equals("")) {
							scheck = null;
						}

						//If a zipcode wasn't entered
						if (zccheck.equals("")) {
							zccheck = null;
						}

					    bufferedWriter.write(atcheck + "," + s1check + "," + s2check + "," + cicheck + "," + scheck + "," + zccheck);
			        	
			        	if (i != addresses.get(x).size() - 1) {
			        		if (addresses.get(x).size() != 1) { //If there's only 1 address or this is the last one on the list of addresses
			        			bufferedWriter.write("/");
			        		}
					    }
					}
				}

				bufferedWriter.write("|");

				bufferedWriter.write("\n");
			}

            bufferedWriter.close();
        } catch (FileNotFoundException ex) {
            fileAlert.show(); 
        } catch (IOException ex) { 
            errorAlert.show(); 
        }
	}
}