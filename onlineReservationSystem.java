import java.util.*;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Reservation {
    static int counter = 1000;
    String pnr;
    String name;
    int trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;

    Reservation(String name, int trainNumber, String classType, String dateOfJourney, String from, String to) {
        this.pnr = "PNR" + counter++;
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = getTrainName(trainNumber);
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    static String getTrainName(int trainNumber) {
        Map<Integer, String> trainMap = Map.of(
            123, "Express A",
            456, "Express B",
            789, "Local C",
            111, "Local D",
            666, "Service E"
        );
        return trainMap.getOrDefault(trainNumber, "Unknown Train");
    }

    public String toString() {
        return "PNR: " + pnr + "\nName: " + name + "\nTrain: " + trainNumber + " - " + trainName + 
               "\nClass: " + classType + "\nDate: " + dateOfJourney + "\nFrom: " + from + "\nTo: " + to;
    }
}

public class onlineReservationSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static Map<String, Reservation> reservations = new HashMap<>();

    static boolean login() {
        System.out.println("\n\t------------------------------");
        System.out.println("\t           Login User");
        System.out.println("\t------------------------------");
        while(true) {
            System.out.print("\nEnter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            for(User user : users) {
                if(user.username.equalsIgnoreCase(username) && user.password.equals(password)) {
                    System.out.println("\n----Login successful.----");
                    return true;
                }
            }
            System.out.println("\n----Invalid credentials. Try again.----");
        }
    }

    static void registerUser() {
        System.out.println("\n\t------------------------------------");
        System.out.println("\t           Register new User");
        System.out.println("\t------------------------------------");
        System.out.print("\nEnter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("\n----User registered successfully.----");
    }

    static void makeReservation() {
        System.out.println("\n\t--------------------------------------");
        System.out.println("\t           Reservation Form");
        System.out.println("\t--------------------------------------");
        System.out.print("\nEnter Your Name: ");
        String name = scanner.nextLine();
        int trainNumber;
        while(true) {
            System.out.print("Enter Train Number (123/456/789/111/666): ");
            trainNumber = scanner.nextInt();
            scanner.nextLine();
            if (!Reservation.getTrainName(trainNumber).equals("Unknown Train")) break;
            System.out.println("Invalid train number. Please try again.");
        }
        System.out.print("Enter Class Type (e.g., Sleeper, 1st AC, 2nd AC): ");
        String classType = scanner.nextLine();
        System.out.print("Enter Date of Journey (DD-MM-YYYY): ");
        String date = scanner.nextLine();
        System.out.print("From: ");
        String from = scanner.nextLine();
        System.out.print("To: ");
        String to = scanner.nextLine();

        Reservation res = new Reservation(name, trainNumber, classType, date, from, to);
        reservations.put(res.pnr, res);
        System.out.println("\n----Reservation Successful!----\nYour PNR is: " + res.pnr);
    }

    static void cancelReservation() {
        System.out.println("\n\t--------------------------------------");
        System.out.println("\t           Cancelation Form");
        System.out.println("\t--------------------------------------");
        System.out.print("\nEnter your PNR Number: ");
        String pnr = scanner.nextLine();

        if(reservations.containsKey(pnr)) {
            Reservation res = reservations.get(pnr);
            System.out.println("\nReservation Details:");
            System.out.println(res);
            System.out.print("\nDo you want to cancel this reservation? (yes/no): ");
            String confirm = scanner.nextLine();

            if(confirm.equalsIgnoreCase("yes")) {
                reservations.remove(pnr);
                System.out.println("\n----Reservation cancelled successfully.----");
            } else {
                System.out.println("\n----Cancellation aborted.----");
            }
        } else {
            System.out.println("\n----PNR not found.----");
        }
    }

    public static void main(String[] args) {
        users.add(new User("admin", "admin123"));

        System.out.println("-----------------------------------------------------------------");
        System.out.println("\tWelcome to the Online Reservation System");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("\nDo you want to (1)Login or (2)Register\nEnter 1 or 2: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if(option == 2) {
            registerUser();
        }

        if(login()) {
            int choice;
            do {
                System.out.println("\n\t------------------------------");
                System.out.println("\t           Main Menu");
                System.out.println("\t------------------------------");
                System.out.println("\n1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. Exit");
                System.out.print("\nEnter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 -> makeReservation();
                    case 2 -> cancelReservation();
                    case 3 -> System.out.println("\n-------Thank you for using the Online Reservation System.-------");
                    default -> System.out.println("\n----Invalid choice. Please Try again.----");
                }
            } while(choice != 3);
        }
    }
}
