import java.util.Scanner;

// Smart Banking App
public class CLI_Assignment2 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J"; // To clear the terminal
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String RESET = "\033[0m";
        final String COLOR_RED_BOLD = "\033[31;1m";

        final String DASHBOARD = "Welcome to Smart Banking App";
        final String OPEN_ACCOUNT = "Open New Account";
        final String ERROR_MSG = String.format("%s%s%s\n",COLOR_RED_BOLD,"%s",RESET);
        

        String[][] accountInfo = new String[0][0]; // [Account][0: Name, 1: Number, 2: Deposit]
        String screen = DASHBOARD;

        do {
            // Print dashboard
            final String APP_TITLE = String.format("%s%s%s\n", COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("-".repeat(50));
            System.out.println(" ".repeat((50 - APP_TITLE.length() + 7) / 2).concat(APP_TITLE));
            System.out.println("-".repeat(50));

            switch (screen) {
                case DASHBOARD:
                    System.out.println("[1]. Open New Account\n[2]. Exit\n");
                    System.out.println("Enter an option to continue > ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        case 1:
                            screen = OPEN_ACCOUNT;
                            break;
                        case 2: System.exit(0); break;
                        default:
                            continue;
                    }
                    break;

                // To open account
                case OPEN_ACCOUNT:
                    // Generate the account number
                    String newAccountNumber = String.format("SDB-S%05d", accountInfo.length + 1);
                    System.out.println("New Account Number: " + newAccountNumber);

                    boolean valid;
                    String name;
                    
                    // Get account holder name and validate it
                    do {
                        valid = true;
                        System.out.println("Enter Account Holder Name: ");
                        name = scanner.nextLine().strip();

                        if (name.isBlank()) {
                            System.out.printf(ERROR_MSG,"Name can't be empty");
                            valid = false;
                            continue;
                        }

                        for (int i = 0; i < name.length(); i++) {
                            if (!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))) {
                                System.out.printf(ERROR_MSG,"Invalid Names");
                                valid = false;
                                break;
                            }
                        }
                    } while (!valid);
                    

                    // Get initial deposit amount and validate it
                    String initialDeposit;
                    double initialDepositDouble = 0.0; // Declare the variable outside the loop
                   
                    do {
                        valid = true;
                        System.out.println("Enter Initial Deposit (minimum 5000): ");
                        initialDeposit = scanner.nextLine();
                        if (!initialDeposit.startsWith("0")) {
                            boolean depositValid = true;
                            for (int i = 0; i < initialDeposit.length(); i++) {
                                if (!Character.isDigit(initialDeposit.charAt(i))) {
                                    depositValid = false;
                                    break;
                                }
                            }

                            if (depositValid) {
                                initialDepositDouble = Double.parseDouble(initialDeposit);
                                if (initialDepositDouble < 5000) {
                                    System.out.printf(ERROR_MSG,"Initial deposit must be at least 5000");
                                    valid = false;
                                }
                            } else {
                                System.out.printf(ERROR_MSG,"Invalid input. Please enter a valid amount");
                                valid = false;
                            }
                        } else {
                            System.out.printf(ERROR_MSG,"Invalid input. Please enter a valid amount");
                            valid = false;
                        }
                    } while (!valid);

                    // Create new account entry in the 3D array
                    String[][] newAccountInfo = new String[accountInfo.length + 1][3];
                    newAccountInfo[accountInfo.length][0] = name;
                    newAccountInfo[accountInfo.length][1] = newAccountNumber;
                    newAccountInfo[accountInfo.length][2] = String.valueOf(initialDepositDouble);
/* 

                    // Get initial deposit amount and validate it: By using try and catch method. 
                    double initialDeposit = 0.0;
                    do {
                        valid = true;
                        System.out.println("Enter Initial Deposit (minimum 5000): ");
                        try {
                            initialDeposit = Double.parseDouble(scanner.nextLine());
                            if (initialDeposit < 5000) {
                                System.out.printf("Initial deposit must be at least 5000\n",ERROR_MSG);
                                valid = false;
                            }
                        } catch (NumberFormatException e) {
                            System.out.printf("Invalid input. Please enter a valid amount\n",ERROR_MSG);
                            valid = false;
                        }
                    } while (!valid);

                    // Create new account entry in the 3D array
                    String[][] newAccountInfo = new String[accountInfo.length + 1][3];
                    newAccountInfo[accountInfo.length][0] = name;
                    newAccountInfo[accountInfo.length][1] = newAccountNumber;
                    newAccountInfo[accountInfo.length][2] = String.valueOf(initialDeposit);
    
*/
                    //ask for another entry.
                    System.out.println(name + " added successfully. Do you want to open a new account (Y/n)? ");
                    if (scanner.nextLine().toUpperCase().strip().equals("Y")) {
                        continue;
                    }

                    //assign temporary newAccountInfo array to the accountInfo array. 
                    accountInfo = newAccountInfo;
                    screen = DASHBOARD;
                    break;

                default:
                    System.exit(0);
            }

        } while (true);
    }
}
