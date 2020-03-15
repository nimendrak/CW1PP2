import java.util.Arrays;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {

        String[][][] studentspassengerDetails = new String[30][42][2];

        studentspassengerDetails[0][0][0] = "nimendra";
        studentspassengerDetails[0][0][1] = "981871871V";

        studentspassengerDetails[0][1][0] = "thavindu";
        studentspassengerDetails[0][1][1] = "123456789V";

        for (int day = 0; day < studentspassengerDetails.length; day++) {
            System.out.println("Day " + (day + 1));
            System.out.println();
            for (int seatNum = 0; seatNum < studentspassengerDetails[day].length; seatNum++) {
                System.out.println("Seat #" + (seatNum + 1));
                System.out.println("Passenger Details");
                for (int passengerDetails = 0; passengerDetails < studentspassengerDetails[day][seatNum].length; passengerDetails++) {
                    System.out.print(studentspassengerDetails[day][seatNum][passengerDetails] + "\t");
                    System.out.println();
                }
                System.out.println("------------------------");
            }
        }
    }

}
