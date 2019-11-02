package com102_java_assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*

@author b00758943
@author b00753973

*/

public class Student {
    
    // For text input from user, subject to removal once I determine whether or
    // not we actually need it in this file
    private final Scanner scan = new Scanner(System.in);
    
    // For appending to file
    private final FileWriter appendWriter;
    
    // Student's unique ID, format is as follows "[b00][IIIIII]"
    private String studentId;
    
    // Each student's module mark, stored as a string to allow for writing to
    // file
    private String mark;
    
    // Constructors
    Student() throws IOException {
        
        // Creates new FileWriter for appending to file
        this.appendWriter = new FileWriter(
                "src\\com102_java_assignment\\resources\\students.txt", true);
    
    }

    // Takes the student ID and mark of a new student, appending them to the 
    // file
    public void setNewStudent(String studentId, String mark) {

        // Collate unique id and mark into single string to append
        String newRecord;
        newRecord = ("\n" + studentId.toUpperCase() + "," + mark);
        
        try (PrintWriter printWriter = new PrintWriter(appendWriter)) {
            
            printWriter.println(newRecord.trim());
            printWriter.flush();
            printWriter.close();
        
        }
        
    }
    
    // Takes a student ID, finds it in the file, overwrites it with new data
    public void editStudent(String oldID, String newID, String
            newMark) throws FileNotFoundException, IOException {
        
        // Some file-handling related objects
        File fileToEdit = new File(
            "src\\com102_java_assignment\\resources\\students.txt");
        
        BufferedReader reader;
        FileWriter writer;
         
        // Initialise some vars we'll need
        String oldData = "";  
        String oldString = "";
        String newString = "";
        String[] splitString;

        reader = new BufferedReader(new FileReader(fileToEdit));

        // This big while loop iterates through the file line by line, checking
        // for oldID, if it's there, it will decide what data in the line to
        // update, then get us oldString and newString (with the old and new 
        // data respectively). It also collates the old file lines into oldData
        String line = reader.readLine();

        while (line != null) { // While not EoF

            if (line.indexOf(oldID) == 0) { // String found in line

                oldString = line;
                splitString = oldString.split(",");

                if (newID.length() < 1) { // Update mark only

                    splitString[1] = newMark;

                } else if (newMark.length() < 1) { // Update ID only

                    splitString[0] = newID;

                } else { // Update both

                    splitString[0] = newID;
                    splitString[1] = newMark;

                }

                for (int i = 0; i <= 1; i+=1) {

                    newString += splitString[i] + ",";

                }

            }

            oldData = oldData + line + System.lineSeparator(); 
            line = reader.readLine();

        }

        // Replace oldString with newString in oldData
        String newData = oldData.replaceAll(oldString, newString);

        // Rewrite file
        writer = new FileWriter(fileToEdit);
        writer.write(newData);
                
        // Close up shop
        reader.close(); 
        writer.close();
        
    }
    
    // Largely the same process as editStudent, but only one ID is needed, 
    // and we just delete the line (as well as any newlines that would muck
    // up our formatting) instead of editing it with user-defined values.
    public void deleteStudent(String studentId) throws FileNotFoundException, 
            IOException {
        
        Scanner file = new Scanner(new File(
                "src\\com102_java_assignment\\resources\\students.txt"));
        PrintWriter writer = new PrintWriter(
                "src\\com102_java_assignment\\resources\\temp.txt");

        String line;
        
        while (file.hasNext()) { // While not EoF

            line = file.next();
            
            if (line.indexOf(studentId.toUpperCase()) != 0) { // Match not found
                
                writer.println(line);
            
            }
            
        }
        
        file.close();
        writer.close();
        
        Scanner newFile = new Scanner(new File(
                "src\\com102_java_assignment\\resources\\temp.txt"));
        PrintWriter oldFile = new PrintWriter(
                "src\\com102_java_assignment\\resources\\students.txt");
        
        String line2;
        
        while (newFile.hasNext()) {
            
            line2 = newFile.next();
            oldFile.println(line2);
            
        }
        
        newFile.close();
        oldFile.close();
        
    }
    
    public void getStats(String option) throws FileNotFoundException, 
            IOException{
        
        File file = new File(
            "src\\com102_java_assignment\\resources\\students.txt");
        
        BufferedReader reader;
        
        String line;
        String[] splitLine;
        reader = new BufferedReader(new FileReader(file));
        line = reader.readLine();
        List gradesList = new ArrayList();
        int i = 0;
        int grade;
        int total = 0;
        float average;
        int max = 0;
        int min = 110;
        int x;
        
        // Get int array of grades
        while (line != null) {
            
            splitLine = line.split(",");
            grade = Integer.parseInt(splitLine[1]);
            gradesList.add(grade);
            line = reader.readLine();
            
        }
        
        Integer[] grades = new Integer[gradesList.size()];
        gradesList.toArray(grades);
        
        if (option == "average") {
            
            for (x = 0; x < grades.length; x += 1) {
                
                total += grades[x];
                
            }
            
            average = total / (grades.length);
            System.out.println("Average mark is: " + average);
            
        } else if (option == "min") { // Simple bubble sort
            
            for (x = 0; x < grades.length; x += 1) {
                
                if (grades[x] < min) {
                    
                    min = grades[x];
                    
                }
                
            }
            
            System.out.println("Lowest mark is: " + min);
            
        } else { // Simple bubble sort
            
            for (x = 0; x < grades.length; x += 1) {
                
                if (grades[x] > max) {
                    
                    max = grades[x];
                    
                }
                
            }
            
            System.out.println("Highest mark is: " + max);
            
        }
        
        return;
        
    }
    
    
    // Checks if given student ID already exists, returns false if not found
    public boolean doesStudentExist(String id) throws FileNotFoundException {
        
        Scanner file = new Scanner(new File(
            "src\\com102_java_assignment\\resources\\students.txt"));
        
        String line;
        
        while (file.hasNext()) {
            
            line = file.next();
            
            if (line.indexOf(id) == 0) {
                
                return true;
                
            } 
            
        }
        
        return false;
        
    }
    
}
    
