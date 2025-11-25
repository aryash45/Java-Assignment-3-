import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}
class Student {

    private long rollNumber;     
    private String Name;
    private int[] marks = new int[3];

    public Student(long rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.Name = studentName;
        this.marks = marks;
    }

    public void validateMarks() throws InvalidMarksException {
        for (int m : marks) {
            if (m < 0 || m > 100) {
                throw new InvalidMarksException("Marks must be between 0 and 100.");
            }
        }
    }

    public double calculateAverage() {
        int sum = 0;
        for (int m : marks) sum += m;
        return sum / 3.0;
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + Name);

        System.out.print("Marks: ");
        for (int m : marks) 
        System.out.print(m + " ");
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);
        System.out.println("Result: " + (avg >= 40 ? "Pass" : "Fail"));
    }

    public long getRollNumber() {   
        return rollNumber;
    }
}

 class ResultManager {

    private Student[] students = new Student[100];
    private int count = 0;
    private Scanner sc = new Scanner(System.in);

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            long roll = sc.nextLong();   
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, marks);
            s.validateMarks();

            students[count++] = s;
            System.out.println("Student added successfully.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Numbers only.");
            sc.nextLine();
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            long roll = sc.nextLong();   

            Student found = null;
            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    found = students[i];
                    break;
                }
            }

            if (found == null) {
                System.out.println("Student not found.");
            } else {
                found.displayResult();
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid roll number!");
            sc.nextLine();
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    public void mainMenu() {
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> showStudentDetails();
                    case 3 -> System.out.println("Exiting system...");
                    default -> System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number!");
                sc.nextLine();
            }
        }

        sc.close();
        System.out.println("Thank You!.");
    }

    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.mainMenu();
    }
}
