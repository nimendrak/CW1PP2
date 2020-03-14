package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Create a HashMap object called badullaToColombo to store userName and seatNumber and array to store passengers' names
        HashMap<Integer, String> badullaToColombo = new HashMap<>();
        List<String> toColomboPassengersNameList = new ArrayList<>();

        // Create a HashMap object called colomboToBadulla to store userName and seatNumber and array to store passengers' names
        HashMap<Integer, String> colomboToBadulla = new HashMap<>();
        List<String> toBadullaPassengersNameList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        int welcomeScreenType;
        String userOption;

        System.out.println("\n***************************************************");
        System.out.println("*** DENUWARA MANIKE TRAIN SEATS BOOKING PROGRAM ***");
        System.out.println("***************************************************\n");

        do {
            System.out.println("Choose a option, which mentioned below\n");
            System.out.println("Prompt \"A\" to add a customer to a seat");
            System.out.println("Prompt \"V\" to display all seats");
            System.out.println("Prompt \"E\" to display available seats");
            System.out.println("Prompt \"D\" to delete customer from a seat");
            System.out.println("Prompt \"F\" to find the seat for a given customer");
            System.out.println("Prompt \"S\" to store program data into a file");
            System.out.println("Prompt \"L\" to load program data from a file");
            System.out.println("Prompt \"O\" to view seats in ordered alphabetically by name");
            System.out.println("Prompt \"Q\" to exit from the program");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "A":
                case "a":
                    welcomeScreenType = 1;
                    welcomeScreen(badullaToColombo, colomboToBadulla, toColomboPassengersNameList, toBadullaPassengersNameList, welcomeScreenType);
                    break;

                case "V":
                case "v":
                    welcomeScreenType = 2;
                    welcomeScreen(badullaToColombo, colomboToBadulla, toColomboPassengersNameList, toBadullaPassengersNameList, welcomeScreenType);
                    break;

                case "E":
                case "e":
                    welcomeScreenType = 3;
                    welcomeScreen(badullaToColombo, colomboToBadulla, toColomboPassengersNameList, toBadullaPassengersNameList, welcomeScreenType);
                    break;

                case "D":
                case "d":
                    deleteCustomer(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "F":
                case "f":
                    findSeat(badullaToColombo, colomboToBadulla, sc, toColomboPassengersNameList, toBadullaPassengersNameList);
                    break;

                case "S":
                case "s":
                    storeData(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "L":
                case "l":
                    loadProgramFile(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "O":
                case "o":
                    alphabeticalOrder(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        storeData(badullaToColombo, colomboToBadulla, sc);
                    } else {
                        System.out.println("\nProgram is now Exiting..");
                    }
                    break;

                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------\n");
            }
        } while (!userOption.equals("q"));
    }

    private void welcomeScreen(HashMap badullaToColombo, HashMap colomboToBadulla, List toColomboPassengersNameList, List toBadullaPassengersNameList, int welcomeScreenType) {
        Stage welcomeWindow = new Stage();
        welcomeWindow.setTitle("Welcome!");

        Image windowIcon = new Image(getClass().getResourceAsStream("mainIcon.png"));
        welcomeWindow.getIcons().add(windowIcon);

        Pane pane = new Pane();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(5);

        Label labelMain = new Label("Denuwara Manike Train Seats Booking Program");
        labelMain.setFont(new Font("Arial Bold", 18));

        Label labePrimary = new Label("v2.0");
        labePrimary.setFont(new Font("Arial", 12));

        Label destinationsLabel = new Label("Select your Destination");
        destinationsLabel.setFont(new Font("Arial", 15));
        destinationsLabel.setPadding(new Insets(30, 0, 0, 0));

        String[] destinations = {"Badulla to Colombo", "Colombo to Badulla"};
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(destinations));
        comboBox.getSelectionModel().select(0);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(0, 15);

        Button continueBtn = new Button("Continue");

        continueBtn.setOnAction(event -> {
            if (welcomeScreenType == 1) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou have selected Badulla to Colombo\n");
                    addCustomer(badullaToColombo, toBadullaPassengersNameList);
                } else {
                    System.out.println("\nYou have selected Colombo to Badulla\n");
                    addCustomer(colomboToBadulla, toColomboPassengersNameList);
                }

            } else if (welcomeScreenType == 2) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou have selected Badulla to Colombo\n");
                    displayAllSeats(badullaToColombo, toBadullaPassengersNameList);
                } else {
                    System.out.println("\nYou have selected Colombo to Badulla\n");
                    displayAllSeats(colomboToBadulla, toColomboPassengersNameList);
                }

            } else if (welcomeScreenType == 3) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou have selected Badulla to Colombo\n");
                    displayAvailableSeats(badullaToColombo, toBadullaPassengersNameList);
                } else {
                    System.out.println("\nYou have selected Colombo to Badulla\n");
                    displayAvailableSeats(colomboToBadulla, toColomboPassengersNameList);
                }
            }
            welcomeWindow.close();
        });

        welcomeWindow.initModality(Modality.APPLICATION_MODAL);

        Scene mainScene = new Scene(pane, 550, 525);

        try {
            // create a input stream
            FileInputStream input = new FileInputStream("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\mainBg.jpg");

            // create a image
            Image image = new Image(input);

            // create a background image
            BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            // create Background
            Background background = new Background(backgroundimage);

            // set background
            pane.setBackground(background);

        } catch (Exception e) {
            //ignored
        }

        vBox.getChildren().addAll(labelMain, labePrimary, destinationsLabel, comboBox, emptySpace, continueBtn);
        pane.getChildren().add(vBox);

        welcomeWindow.setScene(mainScene);
        welcomeWindow.showAndWait();
    }

    //type one alert box act as a confirmation box for the quit the current stage
    private void alertBoxWindowTypeOne(Stage window) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Alert!");

        Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
        alertBoxWindow.getIcons().add(windowIcon);

        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(150);

        VBox layout = new VBox(10);
        GridPane gridPane = new GridPane();
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);

        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");

        Label label = new Label("Do you want to exit?");

        okBtn.setOnAction(event -> {
            alertBoxWindow.close();
            window.close();
        });

        cancelBtn.setOnAction(event -> alertBoxWindow.hide());

        layout.getChildren().add(label);
        layout.getChildren().add(gridPane);

        gridPane.add(okBtn, 0, 0);
        gridPane.add(cancelBtn, 1, 0);

        gridPane.setPadding(new Insets(0, 0, 0, 83));
        gridPane.setHgap(10);

        alertBoxWindow.showAndWait();
    }

    //type two alert box popup a new window and show messages according to the the parameters
    private void alertBoxWindowTypeTwo(String message) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Booked!");

        Image windowIcon = new Image(getClass().getResourceAsStream("confirmIcon.png"));
        alertBoxWindow.getIcons().add(windowIcon);

        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(150);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alertBoxWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);
        alertBoxWindow.showAndWait();
    }

    private void loadProgramFile(HashMap badullaToColombo, HashMap colomboToBadulla, Scanner sc) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************");
        System.out.println("LOAD PROGRAM FROM DATA");
        System.out.println("**********************\n");

        System.out.print("Select you destination\n1. To Badulla\n2. To Colombo\n\nPrompt 1 or 2 to continue : ");
        String option = sc.next();

        switch (option) {
            case "1":
                File file1 = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\colomboToBadullaPassengers.txt");
                List<String> toBadullaPassengersNameList = new ArrayList<>(colomboToBadulla.values());
                loadProgramFileMain(colomboToBadulla, toBadullaPassengersNameList, file1);
                break;
            case "2":
                File file2 = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\badullaToColomboPassengers.txt");
                List<String> toColomboPassengersNameList = new ArrayList<>(badullaToColombo.values());
                loadProgramFileMain(badullaToColombo, toColomboPassengersNameList, file2);
                break;
            default:
                System.out.println("You have entered a invalid input");
                break;
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void loadProgramFileMain(HashMap<Integer, String> passengers, List<String> userNameList, File file) {
        BufferedReader bufferedReader = null;

        try {
            //create BufferedReader object from the File
            bufferedReader = new BufferedReader(new FileReader(file));

            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {

                //split the line by :
                String[] parts = line.split(" has booked seat #");

                //first part is name, second is age
                String passengerName = parts[0].trim();
                Integer passengerSeat = Integer.parseInt(parts[1].trim());

                //put name, age in HashMap if they are not empty
                if (!passengerName.equals("") && !passengerSeat.equals(""))
                    passengers.put(passengerSeat, passengerName);
                userNameList.add(passengerName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Always close the BufferedReader
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    System.out.println("\nStored data has been successfully loaded to the program!\nTip - Prompt \"V\" to check available seats");
                } catch (Exception e) {
                    //ignored
                }
            }
        }
    }

    private void storeData(HashMap badullaToColombo, HashMap colomboToBadulla, Scanner sc) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********");
        System.out.println("STORE DATA");
        System.out.println("**********\n");

        System.out.print("Select you destination\n1. To Badulla\n2. To Colombo\n\nPrompt 1 or 2 to continue : ");
        String option = sc.next();

        switch (option) {
            case "1":
                File file1 = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\colomboToBadullaPassengers.txt");
                storeDataMain(colomboToBadulla, file1);
                break;
            case "2":
                File file2 = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\badullaToColomboPassengers.txt");
                storeDataMain(badullaToColombo, file2);
                break;
            default:
                System.out.println("You have entered a invalid input");
                break;
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void storeDataMain(HashMap<Integer, String> passengers, File file) {
        //new file object
        BufferedWriter bufferedWriter = null;

        if (passengers.isEmpty()) {
            System.out.println("\nNo seats have been booked yet!");
        } else {
            try {
                //create new BufferedWriter for the output file
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                //iterate map entries
                for (Map.Entry<Integer, String> entry : passengers.entrySet()) {
                    //put key and value
                    bufferedWriter.write(entry.getValue() + " has booked seat #" + entry.getKey());
                    //new line
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("\nData has been successfully stored!");
                    //always close the writer
                    bufferedWriter.close();
                } catch (Exception e) {
                    //error ignored
                }
            }
        }
    }

    private void allSeatsDisplay(HashMap<Integer, String> passengerDestination, int i, Button seat) {
        if (passengerDestination.containsKey(i)) {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void emptySeatsDisplay(HashMap<Integer, String> passengerDestination, int i, Button seat) {
        if (passengerDestination.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void seatBookingAction(Button seat, HashMap<Integer, String> passengerDestination, int i, List<String> passengersNameList) {
        seat.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        });

        seat.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        });


        if (passengerDestination.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setOnAction(event -> {
                if (!passengerDestination.containsKey(seat.getId())) {
                    try {
                        Stage confirmationBox = new Stage();
                        confirmationBox.initModality(Modality.APPLICATION_MODAL);

                        FlowPane flowPane = new FlowPane();
                        flowPane.setPadding(new Insets(30));

                        Image windowIcon = new Image(getClass().getResourceAsStream("pendingIcon.png"));
                        confirmationBox.getIcons().add(windowIcon);

                        confirmationBox.setTitle("Confirmation");

                        VBox vBox = new VBox();
                        HBox hBox = new HBox();
                        vBox.setAlignment(Pos.CENTER);
                        hBox.setAlignment(Pos.CENTER);

                        Text userNameTxt = new Text("Enter your name");
                        TextField userNameTxtField = new TextField();

                        Button confirmUser = new Button("Confirm");
                        confirmUser.setDisable(true);

                        Button cancelBtn = new Button("Cancel");
                        cancelBtn.setOnAction(event1 -> confirmationBox.hide());

                        hBox.getChildren().addAll(confirmUser, cancelBtn);
                        hBox.setPadding(new Insets(0, 0, 0, 45));
                        hBox.setSpacing(10);

                        vBox.getChildren().addAll(userNameTxt, userNameTxtField);
                        vBox.setPadding(new Insets(0, 0, 10, 25));
                        vBox.setSpacing(10);

                        flowPane.getChildren().addAll(vBox, hBox);

                        //user must enter at least two character as the name to confirm his/her booking
                        userNameTxtField.setOnKeyTyped(event1 -> {
                            if (userNameTxtField.getText().isEmpty()) {
                                confirmUser.setDisable(true);
                            } else {
                                confirmUser.setDisable(false);
                            }
                        });

                        confirmUser.setOnAction(event2 -> {
                            //print the current action in console
                            System.out.println(userNameTxtField.getText().toLowerCase() + " has booked Seat #" + seat.getId());

                            //if user name already added to the userNameList it won't added again
                            if (passengersNameList.contains(userNameTxtField.getText().toLowerCase())) {
                                //put data to the hashMap
                                passengerDestination.put(Integer.valueOf(seat.getId()), userNameTxtField.getText().toLowerCase());
                            } else {
                                passengerDestination.put(Integer.valueOf(seat.getId()), userNameTxtField.getText().toLowerCase());
                                //add names to the userNameList
                                passengersNameList.add(userNameTxtField.getText().toLowerCase());
                            }

                            //popup alertBox
                            alertBoxWindowTypeTwo("You have successfully booked Seat #" + seat.getId());
                            //change color of the booked seat and disable it
                            seat.setStyle(null);
                            seat.setDisable(true);
                            confirmationBox.close();
                        });

                        Scene confirmationBoxScene = new Scene(flowPane, 300, 175);
                        confirmationBox.setScene(confirmationBoxScene);
                        confirmationBox.showAndWait();

                    } catch (Exception ignored) {
                        //ignoring the runtime error which occurs by JavaFX which I dont know exactly
                    }
                }

            });
        }
    }

    /* i've used 4 different for loops to print seats as vertical rows, so each row should contain a action
    that each seat can perform in a specific condition whether it could be select only or display only */
    private void seatDisplay(HashMap<Integer, String> passengerDestination, List<String> passengersNameList, VBox
            leftSeatsRowOne, VBox leftSeatsRowTwo, VBox RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType) {

        for (int i = 1; i <= 11; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowOne.getChildren().add(seat);
            leftSeatsRowOne.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatBookingAction(seat, passengerDestination, i, passengersNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengerDestination, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengerDestination, i, seat);
            }
        }

        for (int i = 12; i <= 21; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowTwo.getChildren().add(seat);
            leftSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatBookingAction(seat, passengerDestination, i, passengersNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengerDestination, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengerDestination, i, seat);
            }
        }

        for (int i = 22; i <= 31; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowOne.getChildren().add(seat);
            RightSeatsRowOne.setSpacing(5);
            RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatBookingAction(seat, passengerDestination, i, passengersNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengerDestination, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengerDestination, i, seat);
            }
        }

        for (int i = 32; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowTwo.getChildren().add(seat);
            RightSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatBookingAction(seat, passengerDestination, i, passengersNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengerDestination, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengerDestination, i, seat);
            }
        }
    }

    private void addCustomer(HashMap passengerDestination, List<String> passengersNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("************************\n");

        Stage window = new Stage();
        window.setTitle("Train Seats Booking Program");
        window.initModality(Modality.APPLICATION_MODAL);

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        window.setOnCloseRequest(event -> {
            event.consume();
            alertBoxWindowTypeOne(window);
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("Select a Seat");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 200, 25, 120));

        flowPane.getChildren().add(header);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(41, 0, 0, 162));
        hBox.setSpacing(10);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(passengerDestination, passengersNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "seatAction");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        //close button
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            alertBoxWindowTypeOne(window);
        });

        hBox.getChildren().addAll(closeBtn);
        flowPane.getChildren().addAll(hBox);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("\n--------------------------------------------------");
    }

    private void displayAvailableSeats(HashMap passengerDestination, List<String> passengersNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("DISPLAY EMPTY SEATS");
        System.out.println("*******************\n");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("Check Available Seats");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 150, 25, 80));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(passengerDestination, passengersNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "emptySeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button emptySpace2 = new Button();
        emptySpace2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace2.setMinSize(163, 10);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, emptySpace2, closeBtn);
        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void displayAllSeats(HashMap passengerDestination, List<String> passengersNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("DISPLAY ALL SEATS");
        System.out.println("*****************\n");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("All Seats");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 165, 25, 145));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(passengerDestination, passengersNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "allSeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button emptySpace2 = new Button();
        emptySpace2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace2.setMinSize(20, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button colorTwoButton = new Button();
        colorTwoButton.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        colorTwoButton.setMinSize(33, 10);

        Label colorTwoLabel = new Label("Booked Seats");

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel, emptySpace2, closeBtn);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void findSeat(HashMap badullaToColombo, HashMap colomboToBadulla, Scanner sc, List toColomboPassengersNameList, List toBadullaPassengersNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**************");
        System.out.println("FIND USER SEAT");
        System.out.println("**************\n");

        System.out.print("Select you destination\n1. To Badulla\n2. To Colombo\n\nPrompt 1 or 2 to continue : ");
        String option = sc.next();

        switch (option) {
            case "1":
                findSeatMain(colomboToBadulla, sc, toColomboPassengersNameList);
                break;
            case "2":
                findSeatMain(badullaToColombo, sc, toBadullaPassengersNameList);
                break;
            default:
                System.out.println("You have entered a invalid input");
                break;
        }
        System.out.println("--------------------------------------------------");
    }

    private void findSeatMain(HashMap passengerDestination, Scanner sc, List<String> passengersNameList) {
        if (passengerDestination.isEmpty()) {
            System.out.println("\nNo seats have been booked yet!");
        } else {
            System.out.print("\nPrompt your name to find the seat : ");
            String findUserName = sc.next();
            System.out.println();
            if (passengerDestination.containsValue(findUserName)) {
                for (String s : passengersNameList) {
                    if (s.equalsIgnoreCase(findUserName)) {
                        for (Object o : passengerDestination.keySet()) {
                            if (passengerDestination.get(o).equals(findUserName)) {
                                System.out.println(s + " has booked Seat #" + o);
                            }
                        }
                    }
                }
            } else {
                System.out.println("\nNo seat has been booked under " + findUserName);
            }
        }
    }

    private void deleteCustomer(HashMap badullaToColombo, HashMap colomboToBadulla, Scanner sc) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************");
        System.out.println("DELETE A SEAT");
        System.out.println("*************\n");

        System.out.print("Select you destination\n1. To Badulla\n2. To Colombo\n\nPrompt 1 or 2 to continue : ");
        String option = sc.next();

        switch (option) {
            case "1":
                deleteCustomerMain(colomboToBadulla, sc);
                break;
            case "2":
                deleteCustomerMain(badullaToColombo, sc);
                break;
            default:
                System.out.println("You have entered a invalid input");
                break;
        }
        System.out.println("\n--------------------------------------------------");
    }

    public void deleteCustomerMain(HashMap<Integer, String> passengerDestination, Scanner sc) {
        int removedSeatNumber;
        String removedSeatName;

        if (passengerDestination.isEmpty()) {
            System.out.println("\nNo seats have been booked yet!");
        } else {
            System.out.print("\nWhat is the name that you prompted to book your seat (Prompt Username) : ");
            removedSeatName = sc.next();

            if (!passengerDestination.containsValue(removedSeatName)) {
                System.out.println("\nNo seat has been booked under " + removedSeatName);
            } else {
                System.out.println("\nYou have booked these seats;");
                if (passengerDestination.containsValue(removedSeatName)) {
                    for (HashMap.Entry<Integer, String> entry : passengerDestination.entrySet()) {
                        if (removedSeatName.equals(entry.getValue())) {
                            System.out.print(entry.getKey() + " ");
                        }
                    }

                    System.out.print("\nWhich seat do you want to delete (Prompt Seat Number) : ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Prompt Integers!!");
                        System.out.print("\nWhich seat do you want to delete (Prompt Seat Number) : ");
                        sc.next();
                    }
                    removedSeatNumber = sc.nextInt();

                    if (passengerDestination.containsKey(removedSeatNumber)) {
                        passengerDestination.remove(removedSeatNumber);
                        System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                    } else {
                        System.out.println("\nYou did not book any seats under this seat #" + removedSeatNumber);
                    }
                }
            }
        }
    }

    private void alphabeticalOrder(HashMap badullaToColombo, HashMap colomboToBadulla, Scanner sc) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************************************************");
        System.out.println("VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME");
        System.out.println("*************************************************\n");

        System.out.print("Select you destination\n1. To Badulla\n2. To Colombo\n\nPrompt 1 or 2 to continue : ");
        String option = sc.next();

        switch (option) {
            case "1":
                // Converting HashMap values into ArrayList
                List<String> toBadullaPassengersNameList = new ArrayList<>(colomboToBadulla.values());
                alphabeticalOrderMain(colomboToBadulla, toBadullaPassengersNameList);
                break;
            case "2":
                // Converting HashMap values into ArrayList
                List<String> toColomboPassengersNameList = new ArrayList<>(badullaToColombo.values());
                alphabeticalOrderMain(badullaToColombo, toColomboPassengersNameList);
                break;
            default:
                System.out.println("You have entered a invalid input");
                break;
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void alphabeticalOrderMain(HashMap passengerDestination, List<String> passengersNameList) {
        String temp;
        try {
            if (passengerDestination.isEmpty()) {
                System.out.println("\nNo seats have been booked yet!");
            } else {
                System.out.println();
                for (int i = 0; i < passengersNameList.size(); i++) {
                    for (int j = i + 1; j < passengersNameList.size(); j++) {
                        if (passengersNameList.get(i).compareTo(passengersNameList.get(j)) > 0) {
                            temp = passengersNameList.get(i);
                            passengersNameList.set(i, passengersNameList.get(j));
                            passengersNameList.set(j, temp);
                        }
                    }
                }

                for (String s : passengersNameList) {
                    for (Object o : passengerDestination.keySet()) {
                        if (passengerDestination.get(o).equals(s)) {
                            System.out.println(s + " has booked seat #" + o);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            //ignored
        }
    }
}


