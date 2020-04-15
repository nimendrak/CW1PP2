package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {

        //using a multi dimensional array to store data temporary
        String[][][][] passengersArray = new String[5][31][SEATING_CAPACITY][4];
        //this list will only hold the data of booked dates
        List<String> bookedDatesList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String userOption;

        System.out.println("\n***************************************************");
        System.out.println("*** " + "\033[1;93m" + "DENUWARA MANIKE TRAIN SEATS BOOKING PROGRAM" + "\033[0m" + " ***");
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
                    welcomeScreen(passengersArray, 1, bookedDatesList);
                    break;

                case "V":
                case "v":
                    welcomeScreen(passengersArray, 2, bookedDatesList);
                    break;

                case "E":
                case "e":
                    welcomeScreen(passengersArray, 3, bookedDatesList);
                    break;

                case "D":
                case "d":
                    deletePassenger(passengersArray, bookedDatesList);
                    break;

                case "F":
                case "f":
                    findSeat(passengersArray, bookedDatesList);
                    break;

                case "S":
                case "s":
                    storeData(passengersArray, bookedDatesList);
                    break;

                case "L":
                case "l":
                    loadData(passengersArray, bookedDatesList);
                    break;

                case "O":
                case "o":
                    alphabeticalOrder(passengersArray, bookedDatesList);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before, you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        storeData(passengersArray, bookedDatesList);
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

    private void welcomeScreen(String[][][][] passengersArray, int welcomeScreenType, List<String> bookedDatesList) {
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

        Label labePrimary = new Label("v3.0");
        labePrimary.setFont(new Font("Arial", 12));

        VBox desOne = new VBox();
        desOne.setSpacing(15);
        VBox desTwo = new VBox();
        desTwo.setSpacing(10);
        desTwo.setPadding(new Insets(0, 0, 0, 20));

        Label departureLabel = new Label("Departure Station");
        departureLabel.setStyle("-fx-underline: true");
        departureLabel.setFont(new Font("Arial Bold", 15));
        departureLabel.setPadding(new Insets(30, 0, 0, 0));

        Label arrivalLabel = new Label("Arrival Station");
        arrivalLabel.setStyle("-fx-underline: true");
        arrivalLabel.setFont(new Font("Arial Bold", 15));
        arrivalLabel.setPadding(new Insets(30, 0, 0, 0));

        ToggleButton fromColombo = new ToggleButton("Colombo");
        fromColombo.setCursor(Cursor.HAND);
        fromColombo.setStyle("-fx-background-color: null");
        fromColombo.setFont(new Font("Arial Bold", 18));
        fromColombo.setTextFill(Paint.valueOf("#00A69C"));
        String[] fromColomboStops = {"Hatton", "Ella", "Badulla"};

        ComboBox<String> comboBoxOne = new ComboBox<>(FXCollections.observableArrayList(fromColomboStops));
        comboBoxOne.setStyle("-fx-border-color:#00A69C; -fx-border-radius: 10; -fx-border-width: 3;-fx-background-color: null; -fx-background-radius: 2;");
        comboBoxOne.setPrefWidth(120);
        comboBoxOne.getSelectionModel().select(2);
        comboBoxOne.setDisable(true);

        ToggleButton fromBadulla = new ToggleButton("Badulla");
        fromBadulla.setCursor(Cursor.HAND);
        fromBadulla.setStyle("-fx-background-color: null");
        fromBadulla.setFont(new Font("Arial Bold", 18));
        fromBadulla.setTextFill(Paint.valueOf("#00A69C"));
        String[] fromBadullaStops = {"Hatton", "Ella", "Colombo"};

        ComboBox<String> comboBoxTwo = new ComboBox<>(FXCollections.observableArrayList(fromBadullaStops));
        comboBoxTwo.setStyle("-fx-border-color:#00A69C; -fx-border-radius: 10; -fx-border-width: 3;-fx-background-color: null; -fx-background-radius: 2;");
        comboBoxTwo.getSelectionModel().select(2);
        comboBoxTwo.setDisable(true);

        fromColombo.setOnMouseClicked(event -> {
            comboBoxOne.setDisable(false);
            comboBoxOne.setStyle("-fx-border-color:#00A69C; -fx-border-radius: 10; -fx-border-width: 3;-fx-background-color: #f4f4f4; -fx-background-radius: 2;");
        });

        fromBadulla.setOnMouseClicked(event -> {
            comboBoxTwo.setDisable(false);
            comboBoxTwo.setStyle("-fx-border-color:#00A69C; -fx-border-radius: 10; -fx-border-width: 3;-fx-background-color: #f4f4f4; -fx-background-radius: 2;");
        });

        desOne.getChildren().addAll(departureLabel, fromColombo, fromBadulla);
        desTwo.getChildren().addAll(arrivalLabel, comboBoxOne, comboBoxTwo);

        HBox destinationsBox = new HBox();
        destinationsBox.setSpacing(10);
        destinationsBox.getChildren().addAll(desOne, desTwo);

        DatePicker checkInDatePicker = new DatePicker();
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePickerr) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(
                                LocalDate.now().plusDays(0))
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                        if (item.isAfter(
                                LocalDate.now().plusDays(30))
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        checkInDatePicker.setDayCellFactory(dayCellFactory);

        Label dataLabel = new Label("Select your date");
        dataLabel.setFont(new Font("Arial Bold", 15));
        dataLabel.setPadding(new Insets(30, 0, 0, 0));

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(0, 15);

        Button continueBtn = new Button("Continue");
        new ButtonFX().addBtn(continueBtn);
        continueBtn.setPrefWidth(100);

        welcomeWindow.setOnCloseRequest(event -> {
            event.consume();
            alertBoxWindowTypeOne(welcomeWindow);
        });

        continueBtn.setOnAction(event -> {
            AtomicInteger station = new AtomicInteger();
            int pickedDate;
            String pickedDateFull;
            String date;

            try {
                pickedDate = Integer.parseInt(String.valueOf(checkInDatePicker.getValue()).substring(8, 10));
                pickedDateFull = String.valueOf(checkInDatePicker.getValue());
                date = String.valueOf(checkInDatePicker.getValue());
                if (!bookedDatesList.contains(pickedDateFull)) {
                    bookedDatesList.add(pickedDateFull);
                }

                if (welcomeScreenType == 1) {
                    //from colombo to other destinations
                    if (fromColombo.isSelected()) {
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(0);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(1);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Badulla")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Badulla" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(2);
                        }
                    } else if (fromBadulla.isSelected()) {
                        //from badulla to other destinations
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(3);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(4);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Colombo")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Colombo" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(5);
                        }
                    } else if (fromColombo.isSelected() && fromBadulla.isSelected()) {
                        alertBoxWindowTypeTwo("Alert!", "Select one of Departure Stations", "1");
                    }
                    addPassenger(passengersArray, station.get(), pickedDate, date);

                } else if (welcomeScreenType == 2) {
                    //from colombo to other destinations
                    if (fromColombo.isSelected()) {
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(0);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(1);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Badulla")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Badulla" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(2);
                        }
                    } else if (fromBadulla.isSelected()) {
                        //from badulla to other destinations
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(3);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(4);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Colombo")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Colombo" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(5);
                        }
                    } else if (fromColombo.isSelected() && fromBadulla.isSelected()) {
                        alertBoxWindowTypeTwo("Alert!", "Select one of Departure Stations", "1");
                    }
                    allSeatsDisplay(passengersArray, station.get(), pickedDate, date);

                } else if (welcomeScreenType == 3) {
                    //from colombo to other destinations
                    if (fromColombo.isSelected()) {
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(0);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(1);
                        }
                        if (fromColombo.getText().equals("Colombo") & comboBoxOne.getValue().equals("Badulla")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Colombo - Badulla" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(2);
                        }
                    } else if (fromBadulla.isSelected()) {
                        //from badulla to other destinations
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Hatton")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Hatton" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(3);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Ella")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Ella" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(4);
                        }
                        if (fromBadulla.getText().equals("Badulla") & comboBoxTwo.getValue().equals("Colombo")) {
                            System.out.println("\nYou selected to book seats =>" + "\033[1;31m" + " Badulla - Colombo" + "\033[0m" + " on " + "\033[1;31m" + checkInDatePicker.getValue() + "\033[0m");
                            station.set(5);
                        }
                    } else if (fromColombo.isSelected() && fromBadulla.isSelected()) {
                        alertBoxWindowTypeTwo("Alert!", "Select one of Departure Stations", "1");
                    }
                    emptySeatsDisplay(passengersArray, station.get(), pickedDate, date);
                }
                welcomeWindow.close();

            } catch (Exception notPickedDate) {
                alertBoxWindowTypeTwo("Alert!", "Please select a date to proceed!", "1");
            }
        });

        welcomeWindow.initModality(Modality.APPLICATION_MODAL);
        Scene mainScene = new Scene(pane, 550, 525);

        //background image
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

        } catch (
                Exception e) {
            //ignored
        }

        vBox.getChildren().

                addAll(labelMain, labePrimary, destinationsBox, dataLabel, checkInDatePicker, emptySpace, continueBtn);
        pane.getChildren().

                add(vBox);

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
        new ButtonFX().addBtn(okBtn);
        okBtn.setPrefWidth(50);

        Button cancelBtn = new Button("Cancel");
        new ButtonFX().closeBtn(cancelBtn);

        Label label = new Label("Do you want to exit?");
        label.setFont(new Font("Arial Bold", 16));

        okBtn.setOnAction(event -> {
            alertBoxWindow.close();
            window.close();
        });

        cancelBtn.setOnAction(event -> alertBoxWindow.hide());

        layout.getChildren().add(label);
        layout.getChildren().add(gridPane);

        gridPane.add(okBtn, 0, 0);
        gridPane.add(cancelBtn, 1, 0);

        gridPane.setPadding(new Insets(0, 0, 0, 75));
        gridPane.setHgap(5);

        alertBoxWindow.showAndWait();
    }

    //type two alert box popup a new window and show messages according to the the parameters
    private void alertBoxWindowTypeTwo(String title, String message, String iconType) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle(title);

        if (iconType.equals("1")) {
            Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        } else {
            Image windowIcon = new Image(getClass().getResourceAsStream("confirmIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        }

        alertBoxWindow.setMinWidth(350);
        alertBoxWindow.setMinHeight(150);

        Label label = new Label();
        label.setFont(new Font("Arial Bold", 16));
        label.setPadding(new Insets(0, 0, 5, 0));
        label.setText(message);

        Button closeButton = new Button("Close");
        new ButtonFX().closeBtn(closeButton);
        closeButton.setOnAction(e -> alertBoxWindow.close());

        VBox layout = new VBox(5);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);
        alertBoxWindow.showAndWait();
    }

    /* i've used 4 different for loops to print seats as vertical rows, so each row should contain a action
    that each seat can perform in a specific condition whether it could be book or display */
    private void seatDisplay(String[][][][] passengersArray, int station, int pickedDate, Button
            bookBtn, List<Integer> selectedSeats, Stage window, VBox leftSeatsRowOne, VBox leftSeatsRowTwo, VBox
                                     RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType, String date) {

        List<Integer> seatNumbers = new ArrayList<>();

        for (int j = 0; j < SEATING_CAPACITY; j++) {
            if (passengersArray[station][pickedDate - 1][j][3] != null) {
                seatNumbers.add(Integer.valueOf(passengersArray[station][pickedDate - 1][j][3]));
            }
        }

        class displayMain {
            private void rowDisplay(int j, int k, VBox row) {
                for (int i = j; i <= k; i++) {
                    ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
                    seat.setId(Integer.toString(i));
                    seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8;-fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
                    row.getChildren().add(seat);
                    row.setSpacing(5);
                    seat.setCursor(Cursor.HAND);

                    if (actionType.equals("seatAction")) {
                        if (seatNumbers.contains(i)) {
                            seat.setStyle("-fx-background-color: #E3236D; -fx-background-radius: 8;-fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                        } else {
                            seat.setOnAction((e) -> {
                                if (seat.isSelected()) {
                                    seat.setStyle("-fx-background-color: #E3236D; -fx-background-radius: 8;-fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                                    if (!selectedSeats.contains(Integer.valueOf(seat.getId()))) {
                                        selectedSeats.add(Integer.valueOf(seat.getId()));
                                    }
                                    bookBtn.setDisable(false);

                                } else {
                                    seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8;-fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
                                    selectedSeats.remove(Integer.valueOf(seat.getId()));
                                    bookBtn.setDisable(true);
                                }
                            });
                        }
                        seatBookingAction(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, date);
                    }
                    if (actionType.equals("emptySeats")) {
                        if (seatNumbers.contains(i)) {
                            seat.setStyle("-fx-background-color: rgba(0,0,0,0.1); -fx-background-radius: 8;-fx-border-color: rgba(0,0,0,0.1); -fx-border-width: 3; -fx-border-radius: 8");
                            seat.setDisable(true);
                        } else {
                            seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8;-fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
                        }
                    }
                    if (actionType.equals("allSeats")) {
                        if (seatNumbers.contains(i)) {
                            seat.setStyle("-fx-background-color: #E3236D; -fx-background-radius: 8;-fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                        } else {
                            seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8;-fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
                        }
                    }
                }
            }
        }

        //row one
        new displayMain().rowDisplay(1, 11, leftSeatsRowOne);
        //row two
        new displayMain().rowDisplay(12, 21, leftSeatsRowTwo);
        //row three
        new displayMain().rowDisplay(22, 31, RightSeatsRowOne);
        RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));
        //row four
        new displayMain().rowDisplay(32, SEATING_CAPACITY, RightSeatsRowTwo);

    }

    private void singlePassengerBooking(String[][][][] passengersArray, int station, int pickedDate, List<
            Integer> selectedSeats, Stage window, String date) {
        try {
            Stage confirmationBox = new Stage();
            confirmationBox.initModality(Modality.APPLICATION_MODAL);

            FlowPane flowPane2 = new FlowPane();
            flowPane2.setPadding(new Insets(30));

            Image windowIcon2 = new Image(getClass().getResourceAsStream("pendingIcon.png"));
            confirmationBox.getIcons().add(windowIcon2);

            confirmationBox.setTitle("Confirmation");

            VBox vBox = new VBox();
            HBox hBox2 = new HBox();

            Label headerConfirmationBox = new Label("Prompt Passenger's Details");
            headerConfirmationBox.setFont(new Font("Arial Bold", 22));
            headerConfirmationBox.setTextFill(Paint.valueOf("#414141"));
            headerConfirmationBox.setPadding(new Insets(0, 0, 30, 0));

            Button confirmUser = new Button("Confirm");
            new ButtonFX().addBtn(confirmUser);
            confirmUser.setPrefWidth(100);
            confirmUser.setDisable(true);

            Button cancelBtn = new Button("Cancel");
            new ButtonFX().closeBtn(cancelBtn);
            cancelBtn.setOnAction(event1 -> confirmationBox.hide());

            Text userNameTxt = new Text("Enter your Given Name");
            TextField userNameTxtField = new TextField();
            userNameTxtField.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");

            Text userSurnameTxt = new Text("Enter your Surname");
            TextField userSurnameTxtField = new TextField();
            userSurnameTxtField.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");

            Text userNicTxt = new Text("Enter your NIC");
            TextField userNicTxtField = new TextField();
            userNicTxtField.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");

            userNicTxtField.setOnMouseExited(event1 -> {
                while (!userNicTxtField.getText().equals("")) {
                    if (((userNicTxtField.getText().matches("[1234567890]+v")) && (userNicTxtField.getText().length() == 10))
                            || ((userNicTxtField.getText().matches("[1234567890]+")) && userNicTxtField.getText().length() == 11)) {
                        userNicTxtField.setStyle("-fx-border-color: rgba(0,166,156,0.8) ; -fx-border-width: 3px ;");
                        break;
                    } else {
                        alertBoxWindowTypeTwo("Invalid!", "Please enter a valid NIC", "1");
                        userNicTxtField.setStyle("-fx-border-color: rgba(227,35,109,0.8) ; -fx-border-width: 3px ;");
                        userNicTxtField.setText("");
                    }
                }
            });
            //user must enter at least two character as the name to confirm his/her booking
            userNicTxtField.setOnKeyTyped(event1 -> {
                if (userNicTxtField.getText().isEmpty() && userSurnameTxtField.getText().isEmpty() && userNameTxtField.getText().isEmpty()) {
                    confirmUser.setDisable(true);
                } else {
                    confirmUser.setDisable(false);
                }
            });

            hBox2.getChildren().addAll(confirmUser, cancelBtn);
            hBox2.setPadding(new Insets(40, 0, 0, 0));
            hBox2.setSpacing(10);

            vBox.getChildren().addAll(headerConfirmationBox, userNameTxt, userNameTxtField, userSurnameTxt, userSurnameTxtField, userNicTxt, userNicTxtField, hBox2);
            vBox.setSpacing(10);

            flowPane2.getChildren().addAll(vBox);

            confirmUser.setOnAction(event2 -> {
                System.out.println("------------------");
                System.out.println("Confirmed Bookings");
                System.out.println("------------------\n");

                for (int j : selectedSeats) {
                    passengersArray[station][pickedDate - 1][j - 1][0] = userNameTxtField.getText().toLowerCase();
                    passengersArray[station][pickedDate - 1][j - 1][1] = userSurnameTxtField.getText();
                    passengersArray[station][pickedDate - 1][j - 1][2] = userNicTxtField.getText();
                    passengersArray[station][pickedDate - 1][j - 1][3] = String.valueOf(j);

                    if (station == 0) {
                        System.out.println("Destination     - Badulla to Colombo");
                    } else {
                        System.out.println("Destination     - Colombo to Badulla");
                    }

                    System.out.println("Booked Date     - " + date);
                    System.out.println("Passenger name  - " + passengersArray[station][pickedDate - 1][j - 1][0] + " " + passengersArray[station][pickedDate - 1][j - 1][1]);
                    System.out.println("NIC             - " + passengersArray[station][pickedDate - 1][j - 1][2]);
                    System.out.println("Seat            - #" + passengersArray[station][pickedDate - 1][j - 1][3]);
                    System.out.println();

                    //popup alertBox
                    alertBoxWindowTypeTwo("Booked!", "You have successfully booked Seat #" + passengersArray[station][pickedDate - 1][j - 1][3], "2");
                }
                confirmationBox.close();
                window.close();
            });
            Scene confirmationBoxScene = new Scene(flowPane2, 350, 450);
            confirmationBox.setScene(confirmationBoxScene);
            confirmationBox.showAndWait();

        } catch (Exception ignored) {
            //ignoring the runtime error which occurs by JavaFX
        }
    }

    private void multiplePassengerBooking(String[][][][] passengersArray, int station, int pickedDate, List<
            Integer> selectedSeats, Stage window, String date) {
        try {
            Stage confirmationBoxMultiple = new Stage();
            confirmationBoxMultiple.initModality(Modality.APPLICATION_MODAL);

            FlowPane flowPane2 = new FlowPane();
            flowPane2.setPadding(new Insets(30));
            Scene confirmationBoxScene;
            if (selectedSeats.size() > 2) {
                confirmationBoxScene = new Scene(flowPane2, 975, 325 + (selectedSeats.size() * 25));
            } else {
                confirmationBoxScene = new Scene(flowPane2, 975, 325);
            }

            Image windowIcon2 = new Image(getClass().getResourceAsStream("pendingIcon.png"));
            confirmationBoxMultiple.getIcons().add(windowIcon2);

            confirmationBoxMultiple.setTitle("Confirmation");

            Label headerConfirmationBox = new Label("Prompt Passengers' Details");
            headerConfirmationBox.setFont(new Font("Arial Bold", 22));
            headerConfirmationBox.setTextFill(Paint.valueOf("#414141"));
            headerConfirmationBox.setPadding(new Insets(0, 999, 30, 0));
            flowPane2.getChildren().addAll(headerConfirmationBox);

            ArrayList<TextField> firstNameArray = new ArrayList<>();
            ArrayList<TextField> surNameArray = new ArrayList<>();
            ArrayList<TextField> nicArray = new ArrayList<>();
            ArrayList<ComboBox> comBoxArray = new ArrayList<>();

            TextField[] passengerFirstName = new TextField[selectedSeats.size()];
            TextField[] passengerSurName = new TextField[selectedSeats.size()];
            TextField[] passengerNic = new TextField[selectedSeats.size()];
            ComboBox[] seatsNum = new ComboBox[selectedSeats.size()];

            VBox seatBox = new VBox();
            seatBox.setSpacing(13);
            Label headerOne = new Label("Seat");
            headerOne.setFont(new Font("Arial Bold", 16));
            seatBox.getChildren().addAll(headerOne);

            VBox firstNameBox = new VBox();
            firstNameBox.setSpacing(10);
            Label headerTwo = new Label("First Name");
            headerTwo.setFont(new Font("Arial Bold", 16));
            firstNameBox.getChildren().addAll(headerTwo);

            VBox lastNameBox = new VBox();
            lastNameBox.setSpacing(10);
            Label headerThree = new Label("Surname");
            headerThree.setFont(new Font("Arial Bold", 16));
            lastNameBox.getChildren().addAll(headerThree);

            VBox nicBox = new VBox();
            nicBox.setSpacing(10);
            Label headerFour = new Label("NIC");
            headerFour.setFont(new Font("Arial Bold", 16));
            nicBox.getChildren().addAll(headerFour);

            Separator spOne = new Separator(Orientation.VERTICAL);
            spOne.setPadding(new Insets(0, 40, 0, 40));

            Separator spTwo = new Separator(Orientation.VERTICAL);
            spTwo.setPadding(new Insets(0, 40, 0, 40));

            Separator spThree = new Separator(Orientation.VERTICAL);
            spThree.setPadding(new Insets(0, 40, 0, 40));

            Button confirmUser = new Button("Confirm");
            new ButtonFX().addBtn(confirmUser);
            confirmUser.setPrefWidth(100);
            confirmUser.setDisable(true);

            for (int i = 0; i < selectedSeats.size(); i++) {
                ComboBox seats = new ComboBox();
                seats.setPrefWidth(75);
                seatBox.getChildren().addAll(seats);
                for (int j : selectedSeats) {
                    seats.getItems().addAll(String.valueOf(j));
                }
                comBoxArray.add(seats);
                seatsNum[i] = seats;

                TextField firstName = new TextField();
                firstName.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");
                firstNameBox.getChildren().addAll(firstName);
                firstNameArray.add(firstName);
                passengerFirstName[i] = firstName;

                TextField surName = new TextField();
                surName.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");
                lastNameBox.getChildren().addAll(surName);
                surNameArray.add(surName);
                passengerSurName[i] = surName;

                TextField nic = new TextField();
                nic.setStyle("-fx-border-color: #c3c3c3 ; -fx-border-width: 3px ;");
                nicBox.getChildren().addAll(nic);
                //check this
                nicArray.add(nic);
                passengerNic[i] = nic;


                nic.setOnKeyTyped(event1 -> {
                    if (nic.getText().isEmpty() && firstName.getText().isEmpty() && surName.getText().isEmpty()) {
                        confirmUser.setDisable(true);
                    } else {
                        confirmUser.setDisable(false);
                    }
                });
            }

            flowPane2.getChildren().addAll(seatBox, spOne, firstNameBox, spTwo, lastNameBox, spThree, nicBox);
            HBox hBox2 = new HBox();
            Button cancelBtn = new Button("Cancel");
            new ButtonFX().closeBtn(cancelBtn);
            cancelBtn.setOnAction(event1 -> confirmationBoxMultiple.hide());

            hBox2.getChildren().addAll(confirmUser, cancelBtn);
            hBox2.setPadding(new Insets(50, 0, 0, 0));
            hBox2.setSpacing(10);

            flowPane2.getChildren().addAll(hBox2);

            confirmUser.setOnAction(event1 -> {
                System.out.println("------------------");
                System.out.println("Confirmed Bookings");
                System.out.println("------------------\n");

                try {
                    String firstName = null;
                    String surName = null;
                    String correctedNic = null;
                    int seatNum = 0;

                    for (int i = 0; i < nicArray.size(); i++) {
                        while (!nicArray.get(i).getText().equals("")) {
                            if (((nicArray.get(i).getText().matches("[1234567890]+v")) && (nicArray.get(i).getText().length() == 10))
                                    || ((nicArray.get(i).getText().matches("[1234567890]+")) && nicArray.get(i).getText().length() == 11)) {
                                nicArray.get(i).setStyle("-fx-border-color: rgba(0,166,156,0.8) ; -fx-border-width: 3px ;");
                                correctedNic = nicArray.get(i).getText();
                                break;
                            } else {
                                alertBoxWindowTypeTwo("Invalid!", "Please enter a valid NIC", "1");
                                nicArray.get(i).setStyle("-fx-border-color: rgba(227,35,109,0.8) ; -fx-border-width: 3px ;");
                                nicArray.get(i).setText("");
                            }
                        }

                        seatNum = Integer.parseInt((String) comBoxArray.get(i).getValue());
                        firstName = firstNameArray.get(i).getText();
                        surName = surNameArray.get(i).getText();

                        passengersArray[station][pickedDate - 1][seatNum][0] = firstName;
                        passengersArray[station][pickedDate - 1][seatNum][1] = surName;
                        passengersArray[station][pickedDate - 1][seatNum][2] = correctedNic;
                        passengersArray[station][pickedDate - 1][seatNum][3] = String.valueOf(seatNum);

                        if (station == 0) {
                            System.out.println("Destination     - Colombo to Hatton");
                        } else if (station == 1) {
                            System.out.println("Destination     - Colombo to Ella");
                        } else if (station == 2) {
                            System.out.println("Destination     - Colombo to Badulla");
                        } else if (station == 3) {
                            System.out.println("Destination     - Badulla to Hatton");
                        } else if (station == 4) {
                            System.out.println("Destination     - Badulla to Ella");
                        } else if (station == 5) {
                            System.out.println("Destination     - Badulla to Colombo");
                        }

                        System.out.println("Booked Date     - " + date);
                        System.out.println("Passenger name  - " + passengersArray[station][pickedDate - 1][seatNum][0] + " " + passengersArray[station][pickedDate - 1][seatNum][1]);
                        System.out.println("NIC             - " + passengersArray[station][pickedDate - 1][seatNum][2]);
                        System.out.println("Seat            - #" + passengersArray[station][pickedDate - 1][seatNum][3]);
                        System.out.println();

                        //popup alertBox
                        alertBoxWindowTypeTwo("Booked!", "You have successfully booked Seat #" + passengersArray[station][pickedDate - 1][seatNum][3], "2");
                    }
                    //resetting arrays
                    comBoxArray.clear();
                    firstNameArray.clear();
                    surNameArray.clear();
                    nicArray.clear();

                    confirmationBoxMultiple.close();
                    window.close();
                } catch (Exception e) {
                    //invalid nic exception
                }
            });

            confirmationBoxMultiple.setScene(confirmationBoxScene);
            confirmationBoxMultiple.showAndWait();

        } catch (
                Exception ignored) {
            //ignoring the runtime error which occurs by JavaFX
        }
    }

    private void seatBookingAction(String[][][][] passengersArray, int station, int pickedDate, Button
            bookBtn, List<Integer> selectedSeats, Stage window, String date) {
        bookBtn.setOnAction(event -> {
            if (selectedSeats.size() == 1) {
                singlePassengerBooking(passengersArray, station, pickedDate, selectedSeats, window, date);
            } else {
                multiplePassengerBooking(passengersArray, station, pickedDate, selectedSeats, window, date);
            }
        });
    }

    private void addPassenger(String[][][][] passengersArray, int station, int pickedDate, String date) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("\033[1;93m" + "ADD A CUSTOMER TO A SEAT" + "\033[0m");
        System.out.println("************************\n");

        List<Integer> seatNumbers = new ArrayList<>();
        Stage window = new Stage();

        if (station == 0) {
            window.setTitle("Destination - Colombo to Hatton");
        } else if (station == 1) {
            window.setTitle("Destination - Colombo to Ella");
        } else if (station == 2) {
            window.setTitle("Destination - Colombo to Badulla");
        } else if (station == 3) {
            window.setTitle("Destination - Badulla to Hatton");
        } else if (station == 4) {
            window.setTitle("Destination - Badulla to Ella");
        } else if (station == 5) {
            window.setTitle("Destination - Badulla to Colombo");
        }

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

        Scene scene = new Scene(flowPane, 465, 675);

        Label header = new Label("Select a Seat/ Seats");
        header.setStyle("-fx-underline: true");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 200, 30, 100));

        flowPane.getChildren().add(header);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(40, 0, 0, 130));
        hBox.setSpacing(10);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        Button bookBtn = new Button("Book");
        new ButtonFX().addBtn(bookBtn);
        bookBtn.setDisable(true);

        seatDisplay(passengersArray, station, pickedDate, bookBtn, seatNumbers, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "seatAction", date);

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        //close button
        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(e -> {
            alertBoxWindowTypeOne(window);
        });

        hBox.getChildren().addAll(bookBtn, closeBtn);
        flowPane.getChildren().addAll(hBox);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void allSeatsDisplay(String[][][][] passengersArray, int station, int pickedDate, String date) {
        List<Integer> selectedSeats = new ArrayList<>();
        List<Integer> seatNumbers = new ArrayList<>();

        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("\033[1;93m" + "DISPLAY ALL SEATS" + "\033[0m");
        System.out.println("*****************\n");

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Colombo to Hatton");
        } else if (station == 1) {
            window.setTitle("Destination - Colombo to Ella");
        } else if (station == 2) {
            window.setTitle("Destination - Colombo to Badulla");
        } else if (station == 3) {
            window.setTitle("Destination - Badulla to Hatton");
        } else if (station == 4) {
            window.setTitle("Destination - Badulla to Ella");
        } else if (station == 5) {
            window.setTitle("Destination - Badulla to Colombo");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 465, 675);

        Label header = new Label("All Seats");
        header.setStyle("-fx-underline: true");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 165, 30, 145));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        Button bookBtn = new Button();

        seatDisplay(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "allSeats", date);

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

        Label colorTwoLabel = new Label("Booked Seats ");

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel, emptySpace2, closeBtn);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void emptySeatsDisplay(String[][][][] passengersArray, int station, int pickedDate, String date) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("\033[1;93m" + "DISPLAY EMPTY SEATS" + "\033[0m");
        System.out.println("*******************\n");

        List<Integer> selectedSeats = new ArrayList<>();

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Colombo to Hatton");
        } else if (station == 1) {
            window.setTitle("Destination - Colombo to Ella");
        } else if (station == 2) {
            window.setTitle("Destination - Colombo to Badulla");
        } else if (station == 3) {
            window.setTitle("Destination - Badulla to Hatton");
        } else if (station == 4) {
            window.setTitle("Destination - Badulla to Ella");
        } else if (station == 5) {
            window.setTitle("Destination - Badulla to Colombo");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 465, 675);

        Label header = new Label("Check Available Seats");
        header.setStyle("-fx-underline: true");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 150, 30, 80));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        Button bookBtn = new Button();

        seatDisplay(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "emptySeats", date);

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats ");

        Button emptySpace2 = new Button();
        emptySpace2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace2.setMinSize(163, 10);

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, emptySpace2, closeBtn);
        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void deletePassenger(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************");
        System.out.println("\033[1;93m" + "DELETE A SEAT" + "\033[0m");
        System.out.println("*************\n");

        Scanner sc = new Scanner(System.in);

        int userOption;
        String removedSeatName;
        String nic;

        List<String> passengerNic = new ArrayList<>();
        List<String> passengerNames = new ArrayList<>();

        HashMap<Integer, String> passengerSeatAndNicDesOne = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNicDesTwo = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNicDesThree = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNicDesFour = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNicDesFive = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNicDesSix = new HashMap<>();

        class findDetails {
            private void find(int des, HashMap<Integer, String> seatAndNicByDes) {
                try {
                    for (String o : bookedDatesList) {
                        for (int i = 1; i <= SEATING_CAPACITY; i++) {
                            if (passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                                passengerNic.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                                passengerNames.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                                seatAndNicByDes.put(i, passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                            }
                        }
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        new findDetails().find(0, passengerSeatAndNicDesOne);
        new findDetails().find(1, passengerSeatAndNicDesTwo);
        new findDetails().find(2, passengerSeatAndNicDesThree);
        new findDetails().find(3, passengerSeatAndNicDesFour);
        new findDetails().find(4, passengerSeatAndNicDesFive);
        new findDetails().find(5, passengerSeatAndNicDesSix);

        class deletePassenger {
            public void delete(int des, String s, HashMap<Integer, String> seatCheck) {
                String destination = null;
                if (des == 0) {
                    destination = "Colombo to Hatton";
                } else if (des == 1) {
                    destination = "Colombo to Ella";
                } else if (des == 2) {
                    destination = "Colombo to Badulla";
                } else if (des == 3) {
                    destination = "Badulla to Hatton";
                } else if (des == 4) {
                    destination = "Badulla to Ella";
                } else if (des == 5) {
                    destination = "Badulla to Colombo";
                }
                try {
                    int removedSeatNumber;
                    if (!passengerNames.isEmpty()) {
                        System.out.print("Which seat do you want to delete (Prompt a Seat Number) : ");
                        //loop till user enters a integer for seat number
                        while (!sc.hasNextInt()) {
                            System.out.println("Prompt Integers to proceed!!\n");
                            System.out.print("Which seat do you want to delete (Prompt a Seat Number) : ");
                            sc.next();
                        }
                        removedSeatNumber = sc.nextInt();

                        if (seatCheck.containsKey(removedSeatNumber)) {
                            passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][0] = null;
                            passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][1] = null;
                            passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][2] = null;
                            passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][3] = String.valueOf(0);
                            System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                        } else {
                            System.out.println("\nYou did not book seat #" + removedSeatNumber + " on " + destination);
                        }
                    } else {
                        System.out.println("\nThere are no bookings were made for this Destination");
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        if (bookedDatesList.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            System.out.print("What is the name that you prompted to book your seat/ seats (Prompt Your First Name) : ");
            removedSeatName = sc.nextLine();
            if ((!passengerNames.contains(removedSeatName))) {
                System.out.println("\nNo seat has been booked under " + removedSeatName);
            } else {
                System.out.print("What is the NIC that you prompted to book your seat/ seats : ");
                nic = sc.next();
                if ((!passengerNic.contains(nic))) {
                    System.out.println("\nNo seat has been booked under " + nic);
                } else {
                    if ((passengerNic.contains(nic) & passengerNames.contains(removedSeatName))) {
                        System.out.println("\033[4;37m" + "\nYou have booked following seats\n" + "\033[0m");
                        new displayDetails().display(0, removedSeatName, nic, bookedDatesList, passengersArray);
                        new displayDetails().display(1, removedSeatName, nic, bookedDatesList, passengersArray);
                        new displayDetails().display(2, removedSeatName, nic, bookedDatesList, passengersArray);
                        new displayDetails().display(3, removedSeatName, nic, bookedDatesList, passengersArray);
                        new displayDetails().display(4, removedSeatName, nic, bookedDatesList, passengersArray);
                    } else {
                        System.out.println("No seat has been booked under " + removedSeatName + " or " + nic);
                    }
                }

                if ((passengerNic.contains(nic) && passengerNames.contains(removedSeatName))) {
                    System.out.print("Select a date (yyyy-mm-yy) : ");
                    String selectedDate = sc.next();
                    if (bookedDatesList.contains(selectedDate)) {
                        for (String s : bookedDatesList) {
                            System.out.print("\nSelect a Destination to delete seats\n01 Colombo to Hatton\n02 Colombo to Ella\n03 Colombo to Badulla\n04 Badulla to Hatton\n05 Badulla to Ella\n06 Badulla to Colombo\n\nPrompt 1 - 6 to proceed : ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Prompt Integers to proceed!!");
                                System.out.print("\nSelect a Destination to delete seats\n01 Colombo to Hatton\n02 Colombo to Ella\n03 Colombo to Badulla\n04 Badulla to Hatton\n05 Badulla to Ella\n06 Badulla to Colombo\n\nPrompt 1 - 6 to proceed : ");
                                sc.next();
                            }
                            userOption = sc.nextInt();

                            if (userOption == 1) {
                                if (passengerSeatAndNicDesOne.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesOne);
                                    break;
                                }
                            } else if (userOption == 2) {
                                if (passengerSeatAndNicDesTwo.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesTwo);
                                    break;
                                }
                            } else if (userOption == 3) {
                                if (passengerSeatAndNicDesThree.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesThree);
                                    break;
                                }
                            } else if (userOption == 4) {
                                if (passengerSeatAndNicDesFour.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesFour);
                                    break;
                                }
                            } else if (userOption == 5) {
                                if (passengerSeatAndNicDesFive.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesFive);
                                    break;
                                }
                            } else if (userOption == 6) {
                                if (passengerSeatAndNicDesSix.containsValue(nic)) {
                                    new deletePassenger().delete(userOption - 1, s, passengerSeatAndNicDesSix);
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("\nYou did not book any seats on " + selectedDate);
                    }
                }
            }
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void findSeat(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**************");
        System.out.println("\033[1;93m" + "FIND USER SEAT" + "\033[0m");
        System.out.println("**************\n");

        List<String> passengerNic = new ArrayList<>();
        List<String> passengerNames = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        class findDetails {
            private void find(int des) {
                try {
                    for (String o : bookedDatesList) {
                        for (int i = 1; i <= SEATING_CAPACITY; i++) {
                            if (passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                                passengerNic.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                                passengerNames.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                            }
                        }
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        new findDetails().find(0);
        new findDetails().find(1);
        new findDetails().find(2);
        new findDetails().find(3);
        new findDetails().find(4);
        new findDetails().find(5);

        System.out.print("What is the name that you prompted to book your seat/ seats (Prompt Your First Name) : ");
        String findUserName = sc.nextLine();

        System.out.print("What is the NIC that you prompted to book your seat/ seats : ");
        String nic = sc.next();

        if (bookedDatesList.isEmpty()) {
            System.out.println("\nNo seats have been booked yet!");
        } else {
            if (passengerNic.contains(nic) && passengerNames.contains(findUserName)) {
                System.out.println();
                new displayDetails().display(0, findUserName, nic, bookedDatesList, passengersArray);
                new displayDetails().display(1, findUserName, nic, bookedDatesList, passengersArray);
                new displayDetails().display(2, findUserName, nic, bookedDatesList, passengersArray);
                new displayDetails().display(3, findUserName, nic, bookedDatesList, passengersArray);
                new displayDetails().display(4, findUserName, nic, bookedDatesList, passengersArray);
                new displayDetails().display(5, findUserName, nic, bookedDatesList, passengersArray);
            } else {
                System.out.println("\nNo seat has been booked under " + findUserName + " or " + nic);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    private void alphabeticalOrder(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************************************************");
        System.out.println("\033[1;93m" + "VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME" + "\033[0m");
        System.out.println("*************************************************\n");

        //add all names to the passengerNameList
        List<String> passengerNameList = new ArrayList<>();

        class findDetails {
            private void find(int des) {
                try {
                    for (String o : bookedDatesList) {
                        for (int i = 1; i <= SEATING_CAPACITY; i++) {
                            if (passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                                if (!passengerNameList.contains(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0])) {
                                    passengerNameList.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        new findDetails().find(0);
        new findDetails().find(1);
        new findDetails().find(2);
        new findDetails().find(3);
        new findDetails().find(4);
        new findDetails().find(5);

        class showDetails {
            private void show(int des) {
                String destination = null;
                if (des == 0) {
                    destination = "Colombo to Hatton";
                } else if (des == 1) {
                    destination = "Colombo to Ella";
                } else if (des == 2) {
                    destination = "Colombo to Badulla";
                } else if (des == 3) {
                    destination = "Badulla to Hatton";
                } else if (des == 4) {
                    destination = "Badulla to Ella";
                } else if (des == 5) {
                    destination = "Badulla to Colombo";
                }

                try {
                    String temp;
                    for (int i = 0; i < passengerNameList.size(); i++) {
                        for (int j = i + 1; j < passengerNameList.size(); j++) {
                            if (passengerNameList.get(i).compareTo(passengerNameList.get(j)) > 0) {
                                temp = passengerNameList.get(i);
                                passengerNameList.set(i, passengerNameList.get(j));
                                passengerNameList.set(j, temp);
                            }
                        }
                    }

                    for (String t : passengerNameList) {
                        for (String u : bookedDatesList) {
                            for (int i = 1; i <= SEATING_CAPACITY; i++) {
                                if (passengersArray[des][Integer.parseInt(u.substring(8, 10)) - 1][i - 1][0] != null) {
                                    if (passengersArray[des][Integer.parseInt(u.substring(8, 10)) - 1][i - 1][0].equalsIgnoreCase(t)) {
                                        System.out.println("Destination     - " + destination);
                                        System.out.println("Booked Date     - " + u);
                                        System.out.println("Passenger name  - " + "\033[1;95m" + t + "\033[0m");
                                        System.out.println("NIC             - " + passengersArray[des][Integer.parseInt(u.substring(8, 10)) - 1][i - 1][2]);
                                        System.out.println("Seat            - " + "\033[1;31m" + "#" + passengersArray[des][Integer.parseInt(u.substring(8, 10)) - 1][i - 1][3] + "\033[0m");
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        try {
            if (bookedDatesList.isEmpty()) {
                System.out.println("\nNo seats have been booked yet!\n");
            } else {
                new showDetails().show(0);
                new showDetails().show(1);
                new showDetails().show(2);
                new showDetails().show(3);
                new showDetails().show(4);
                new showDetails().show(5);
            }
        } catch (Exception ignored) {
            //ignored
        }
        System.out.println("--------------------------------------------------");
    }

    //redirecting to the storeDataMain according to the station that user selected
    private void storeData(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********");
        System.out.println("\033[1;93m" + "STORE DATA" + "\033[0m");
        System.out.println("**********\n");

        //add all names to the passengerNameList
        List<String> passengersNicDesOne = new ArrayList<>();
        List<String> passengersNicDesTwo = new ArrayList<>();
        List<String> passengersNicDesThree = new ArrayList<>();
        List<String> passengersNicDesFour = new ArrayList<>();
        List<String> passengersNicDesFive = new ArrayList<>();
        List<String> passengersNicDesSix = new ArrayList<>();

        class bookedDetails {
            public void bookings(int des, List listName) {
                for (String o : bookedDatesList) {
                    for (int i = 1; i <= SEATING_CAPACITY; i++) {
                        //if passengersNicDesOne already has the passenger nic, it wont added again
                        if (passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                            if (!listName.contains(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2])) {
                                listName.add(passengersArray[des][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                            }
                        }
                    }
                }
            }
        }

        new bookedDetails().bookings(0, passengersNicDesOne);
        new bookedDetails().bookings(1, passengersNicDesTwo);
        new bookedDetails().bookings(2, passengersNicDesThree);
        new bookedDetails().bookings(3, passengersNicDesFour);
        new bookedDetails().bookings(4, passengersNicDesFive);
        new bookedDetails().bookings(4, passengersNicDesSix);

        if (!passengersNicDesOne.isEmpty()) {
            storeBookingData(0, passengersArray, bookedDatesList);
        }
        if (!passengersNicDesTwo.isEmpty()) {
            storeBookingData(1, passengersArray, bookedDatesList);
        }
        if (!passengersNicDesThree.isEmpty()) {
            storeBookingData(2, passengersArray, bookedDatesList);
        }
        if (!passengersNicDesFour.isEmpty()) {
            storeBookingData(3, passengersArray, bookedDatesList);
        }
        if (!passengersNicDesFive.isEmpty()) {
            storeBookingData(4, passengersArray, bookedDatesList);
        }
        if (!passengersNicDesSix.isEmpty()) {
            storeBookingData(6, passengersArray, bookedDatesList);
        }

        if (!bookedDatesList.isEmpty()) {
            System.out.println("Data on the following dates have been successfully stored!");
            for (String s : bookedDatesList) {
                System.out.print("\033[1;31m" + s + "\033[0m" + " | ");
            }
            System.out.println();
        } else {
            System.out.println("No bookings have been made yet!");
        }

        System.out.println("\n--------------------------------------------------");
    }

    //redirecting to the loadBookingData according to the station that user selected
    private void loadData(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************");
        System.out.println("\033[1;93m" + "LOAD PROGRAM FROM DATA" + "\033[0m");
        System.out.println("**********************\n");

        loadBookingData(0, passengersArray, bookedDatesList);
        loadBookingData(1, passengersArray, bookedDatesList);
        loadBookingData(2, passengersArray, bookedDatesList);
        loadBookingData(3, passengersArray, bookedDatesList);
        loadBookingData(4, passengersArray, bookedDatesList);
        loadBookingData(5, passengersArray, bookedDatesList);

        if (!bookedDatesList.isEmpty()) {
            System.out.println("Data on the following dates have been successfully loaded!");
            for (String s : bookedDatesList) {
                System.out.print("\033[1;31m" + s + "\033[0m" + " | ");
            }
            System.out.println();
        } else {
            System.out.println("No data has been stored yet!");
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void storeBookingData(int des, String[][][][] passengersArray, List<String> bookedDatesList) {
        //new file object
        BufferedWriter bufferedWriter = null;

        //initializing text file
        File file = null;
        String destination = null;

        if (des == 0) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToHatton.txt");
            destination = "Destination - Colombo - Hatton";
        } else if (des == 1) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToElla.txt");
            destination = "Destination - Colombo - Ella";
        } else if (des == 2) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToBadulla.txt");
            destination = "Destination - Colombo - Badulla";
        } else if (des == 3) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToHatton.txt");
            destination = "Destination - Badulla - Hatton";
        } else if (des == 4) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToElla.txt");
            destination = "Destination - Badulla - Ella";
        } else {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToColombo.txt");
            destination = "Destination - Badulla - Colombo";
        }

        for (String s : bookedDatesList) {
            try {
                //create new BufferedWriter for the output file
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                //iterate through array
                for (int i = 0; i < SEATING_CAPACITY; i++) {
                    if (passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i][3] != null) {
                        bufferedWriter.write(destination + " | Booked date - " + s +
                                " | Passenger name - " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i][0] + " " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i][1] +
                                " | NIC - " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i][2] +
                                " | Seat #" + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i][3]);
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //always close the writer
                    assert bufferedWriter != null;
                    bufferedWriter.close();
                } catch (Exception e) {
                    System.out.println("File is corrupted!");
                }
            }
        }
    }

    private void loadBookingData(int des, String[][][][] passengersArray, List<String> bookedDatesList) {
        //initializing text file
        File file;

        if (des == 0) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToHatton.txt");
        } else if (des == 1) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToElla.txt");
        } else if (des == 2) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToBadulla.txt");
        } else if (des == 3) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToHatton.txt");
        } else if (des == 4) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToElla.txt");
        } else {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToColombo.txt");
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //create BufferedReader object from the File
            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {
                //split the line by | and put it to a string array
                String[] parts = line.split(" ");

                String bookedDate = parts[9];
                String passengerName = parts[14];
                String passengerSurname = parts[15];
                String nic = parts[19];
                String[] seatNumber = parts[22].split("#");

                //add booking data to the multi-dimensional array
                if (!passengerName.equals("null")) {
                    passengersArray[des][(Integer.parseInt(bookedDate.substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][0] = passengerName;
                    passengersArray[des][(Integer.parseInt(bookedDate.substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][1] = passengerSurname;
                    passengersArray[des][(Integer.parseInt(bookedDate.substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][2] = nic;
                    passengersArray[des][(Integer.parseInt(bookedDate.substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][3] = seatNumber[1];
                    //add data to the bookedDatesList because if your load program right away, it must has the booked dates as well
                    if (!bookedDatesList.contains(bookedDate)) {
                        bookedDatesList.add(bookedDate);
                    }
                }
            }
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //button styling
    static class ButtonFX {
        void closeBtn(Button closeBtn) {
            closeBtn.setPrefWidth(75);
            closeBtn.setPadding(new Insets(8));
            closeBtn.setFont(new Font("Arial Bold", 16));
            closeBtn.setTextFill(Paint.valueOf("f4f4f4"));
            closeBtn.setStyle("-fx-background-color: rgba(227,35,109,1); -fx-background-radius: 20;");
            closeBtn.setOnMouseEntered(event -> {
                closeBtn.setStyle("-fx-background-color: rgba(175,33,90,1); -fx-background-radius: 20;");
            });
            closeBtn.setOnMouseExited(event -> {
                closeBtn.setStyle("-fx-background-color: rgba(227,35,109,1); -fx-background-radius: 20;");
            });
            closeBtn.setCursor(Cursor.HAND);
        }

        void addBtn(Button addBtn) {
            addBtn.setPrefWidth(75);
            addBtn.setPadding(new Insets(8));
            addBtn.setFont(new Font("Arial Bold", 16));
            addBtn.setTextFill(Paint.valueOf("f4f4f4"));
            addBtn.setStyle("-fx-background-color: rgb(0,185,175); -fx-background-radius: 20;");
            addBtn.setOnMouseEntered(event -> {
                addBtn.setStyle("-fx-background-color: rgb(0,155,146); -fx-background-radius: 20;");
            });
            addBtn.setOnMouseExited(event -> {
                addBtn.setStyle("-fx-background-color: rgb(0,185,175); -fx-background-radius: 20;");
            });
            addBtn.setCursor(Cursor.HAND);
        }
    }

    //display passnger details according to the user inputs
    static class displayDetails {
        public void display(int des, String findUserName, String nic, List<String> bookedDatesList, String[][][][] passengersArray) {
            String destination = null;
            if (des == 0) {
                destination = "Colombo to Hatton";
            } else if (des == 1) {
                destination = "Colombo to Ella";
            } else if (des == 2) {
                destination = "Colombo to Badulla";
            } else if (des == 3) {
                destination = "Badulla to Hatton";
            } else if (des == 4) {
                destination = "Badulla to Ella";
            } else if (des == 5) {
                destination = "Badulla to Colombo";
            }

            try {
                for (String s : bookedDatesList) {
                    for (int i = 1; i <= SEATING_CAPACITY; i++) {
                        if (passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][0] != null) {
                            if (passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][0].equalsIgnoreCase(findUserName) && passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][2].equalsIgnoreCase(nic)) {
                                System.out.println("Destination     - " + "\033[1;96m" + destination + "\033[0m");
                                System.out.println("Booked Date     - " + "\033[1;95m" + s + "\033[0m");
                                System.out.println("Passenger name  - " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][0] + " " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][1]);
                                System.out.println("NIC             - " + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][2]);
                                System.out.println("Seat            - " + "\033[1;31m" + "#" + passengersArray[des][Integer.parseInt(s.substring(8, 10)) - 1][i - 1][3] + "\033[0m");
                                System.out.println();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //
            }
        }
    }
}



