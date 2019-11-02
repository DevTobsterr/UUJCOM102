package com102_java_assignment;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;

/*

@author B00758943
@author B00753973

*/

/*
TODO:

Why does the main menu print twice after running getStats()?

*/

public class Interface {
    
    // For text input from user
    private final Scanner scan = new Scanner(System.in);
    
    // For file inputs
    private final Scanner FILE;
    
    // For appending to given file
    private final FileWriter appendWriter;

    public static void main(String[] args) throws IOException {
    
        // Initialises interface instance, begins call chain.
        Interface frontend  = new Interface();
        frontend.Start();
        
    }

    // Constructor
    public Interface() throws FileNotFoundException, IOException {
        
        // Creates new FileWriter for appending a new student to the file
        this.appendWriter = new FileWriter(
                "src\\com102_java_assignment\\resources\\students.txt", true);
        
        // Creates new scanner that contains the students file
        this.FILE = new Scanner(new File(
                "src\\com102_java_assignment\\resources\\students.txt"));
    
    }
    
    private void Start() throws IOException {
        
        // Calls main menu, in while loop to facilitate continuous use
        while (true) {
            
            mainMenu();
            
        }
        
    }
    
    // Presents all available options to the user.
    private void mainMenu() throws IOException {
        
        String option;
        
        // Simple text-based UI
        System.out.println("|-----MAIN MENU-----|"
            + "\n1. Display student list"
            + "\n2. Add record"
            + "\n3. Edit record"
            + "\n4. Delete record"
            + "\n5. Stats"
            + "\n6. Exit");
        option = scan.nextLine();
        
        // Decide what function to run based on user's choice
        switch (option){
            
            case "1":
            
                getStudents();
                break;
            
            case "2":
            
                addRecord();
                break;
            
            case "3":
            
                editRecord();
                break;
            
            case "4":
            
                deleteRecord();
                break;
            
            case "5":
            
                getStats();
                break;
            
            case "6":
            
                System.exit(0);
        
        }
        
        return;
        
    }
    
    // Displays contents of CSV File
    private void getStudents() throws FileNotFoundException {

        int i = 0;
        
        // This might seem redundant, but it "resets" the following while
        // condition, allowing the user to read the file more than once.
        Scanner fileToRead = new Scanner(new File(
            "src\\com102_java_assignment\\resources\\students.txt"));
        
        // Displays each line in file successively
        while (fileToRead.hasNextLine()) {
            System.out.println(fileToRead.nextLine());
            i++;
        
        }

        System.out.println("There are " +i+ " Students in the records");
        
    }
    
    // Gets student details from user and adds them to CSV File
    private void addRecord() throws FileNotFoundException, IOException {
        
        Student student = new Student();
        
        // Get and validate student ID from user
        System.out.println("Enter student B-Code");
        String bcode = scan.nextLine().toUpperCase();
        
        while (validateId(bcode) == false) { // Validate ID
        
            System.out.println("Invalid B-Code, try again");
            bcode = scan.nextLine().toUpperCase();
        
        }
        
        while (student.doesStudentExist(bcode) == true) {
            
            System.out.println("Student already exists");
            bcode = scan.nextLine();
            
        }
        
        // Get and validate student module mark from user
        System.out.println("Enter module mark");
        String mark = scan.nextLine();
        
        while (validateMark(mark) == false) { // Validate mark
            
            System.out.println("Invalid mark, try again");
            mark = scan.nextLine();
            
        }
        
        student.setNewStudent(bcode, mark);
        
    }
    
    // Gets B-code of existing student from user, allows user to then edit
    // the student's details
    private void editRecord() throws FileNotFoundException, IOException {
        
        // Create student instance
        Student student = new Student();
        
        // Initialise some vars for below
        String oldId;
        String newId;
        String newMark;
        String option;
        
        // Get / validate B-Code of student the user wants to edit
        System.out.println("Enter B-Code of student you wish to edit");
        oldId = scan.nextLine();
        oldId = oldId.toUpperCase();
        
        while (validateId(oldId) == false) { // Validate ID
        
            System.out.println("Invalid B-Code, try again");
            oldId = scan.nextLine();
        
        }
        
        // What value does the user want to edit?
        System.out.println("Edit B-Code, module mark, or both? (To cancel, "
                + "enter 4) (1/2/3/4)");
        option = scan.nextLine();
        
        switch (option) {
            
            case "1": // Edit B-Code
                
                System.out.println("Enter new B-Code");
                newId = scan.nextLine();
                
                while (validateId(newId) == false) { // Validate ID
                
                    System.out.println("Invalid B-Code, try again");
                    newId = scan.nextLine();
                
                }
                
                student.editStudent(oldId, newId, "");
                
                break;
            
            case "2": // Edit module mark
                
                System.out.println("Enter new module mark");
                newMark = scan.nextLine();
                
                while (validateMark(newMark) == false) { // Validate mark
                    
                    System.out.println("Invalid mark, try again");
                    newMark = scan.nextLine();
                    
                }
                
                student.editStudent(oldId, "", newMark);
                
                break;
            
            case "3": // Edit both
                
                System.out.println("Enter new B-Code");
                newId = scan.nextLine();
                
                while (validateId(newId) == false) { // Validate ID
                
                    System.out.println("Invalid B-Code, try again");
                    newId = scan.nextLine();
                
                } 
                
                System.out.println("Enter new module mark");
                newMark = scan.nextLine();
                
                while (validateMark(newMark) == false) { // Validate mark
                    
                    System.out.println("Invalid mark, try again");
                    newMark = scan.nextLine();
                    
                }
                
                student.editStudent(oldId, newId, newMark);
                
                break; 
                
            default: // Cancel
                
                break;
            
        } 
        
    }
    
    // Gets B-code of existing student from user then deletes relevant
    // record
    private void deleteRecord() throws FileNotFoundException, IOException {
        
        Student student = new Student();
        
        System.out.println("Enter ID of student you wish to delete");
        String studentId = scan.nextLine();
        
        while (validateId(studentId) == false) { // Validate ID
        
            System.out.println("Invalid B-Code, try again");
            studentId = scan.nextLine();
        
        }
        
        student.deleteStudent(studentId);
        
    }
    
    // Generates basic statistics based on CSV File and displays them to
    // user. Stats include average score, max mark, and min mark
    private void getStats() throws FileNotFoundException, IOException {
        
        Student student = new Student();
        String option;
        System.out.println("What stat to get?" +
                "\n1. Average" +
                "\n2. Min grade" +
                "\n3. Max grade");
        option = scan.next();
        
        switch (option) {
            
            case "1":
                
                student.getStats("average");
                break;
            
            case "2":
                
                student.getStats("min");
                break;
            
            case "3":
                
                student.getStats("max");
                break;
            
        }
        
    }
    
    // Validates id, returns true if valid
    private boolean validateId(String id) {
        
        char[] idArray;
        idArray = id.toCharArray();
        char[] numArray;
        
        try {
            
            numArray = Arrays.copyOfRange(idArray, 1, 7);
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            
            return false;
            
        }
            
        boolean valid = false;
        
        if (idArray[0] == 'b' || idArray[0] == 'B') {
            
            for (int i = 0; i < 5; i = i + 1) {
                
                if (Character.isDigit(numArray[i])) {
                    
                    valid = true;
                    
                } else {
                    
                    valid = false;
                
                }
                
            }
            
        } else {
            
            valid = false;
            
        }
        
        if (id.length() != 6) {
            
            valid = false;
            
        } else {
            
            valid = true;
            
        }
        
        return valid;
        
    }

    //Validates module mark, returns true if valid
    private boolean validateMark(String mark) { 
        
        boolean valid = false;
        int markInt;
        
        for (int i = 0; i < mark.length(); i++) {

            if(Character.isDigit(mark.charAt(i))) { // Type check

                valid = true;

            } else { // Current character not a digit

                valid = false;

            }

        }
        
        try {
            
            markInt = Integer.parseInt(mark);
        
        } catch (NumberFormatException ex) {
            
            return false;
            
        }
        
        
        if (markInt > 100 || markInt < 0) {
            
            valid = false;
             
        } else {
            
            valid = true;
            
        }
        
        return valid;
        
    }
    
}