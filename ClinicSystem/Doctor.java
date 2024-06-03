package ClinicSystem;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class Doctor extends Patient {
     Scanner input = new Scanner(System.in); 

//private instance variables 
  private String medication; 
  private String results; 
  private String drName; 
  private int drId; 
 
 
//setters and getters 
public void setMedication(){ 
  this.medication = input.next(); 
} 
 public String getMedication(){ 
  return medication; 
} 

public void setResults(){ 
  this.results = input.next(); 
} 
 public String getResults(){ 
  return results; 
} 

public void setDrName() { 
  try { 
    this.drName = input.next(); 
  } 
  catch (InputMismatchException e) { 
    System.out.println("Invalid doctor name"); 
  } 
} 
 public String getDrName(){ 
  return drName; 
} 

public void setDrId(){ 
  try { 
    this.drId = input.nextInt(); 
  } 
  catch (InputMismatchException e) { 
    System.out.println("Invalid doctor ID"); 
  } 
} 
 public int getDrId(){ 
  return drId; 
} 
 
 
//methods 
  public void drLogin(){ 
    Doctor doc = new Doctor(); 
    System.out.println("hello, please enter your name"); 
    doc.setDrName(); 
    System.out.println("please enter your medical ID"); 
    doc.setDrId(); 
    System.out.println("hello Dr."+doc.getDrName()); 
} 
 

//ArrayList to display the doctor's tasks 
  public void docTasks(){ 
    ArrayList <String> tasks = new ArrayList <String>(); 
    System.out.println("Welcome back, please enter your first task for today"); 
    boolean done = false; 
    Scanner input = new Scanner(System.in); 
     
  while (!done){ 
     String tsk = input.nextLine(); 
     tasks.add(tsk);  
     System.out.println("would like to add another task?"); 
     String ans = input.nextLine(); 
     if(!ans.equalsIgnoreCase("yes"))
     done = true; 
  } 

    System.out.println("here is the list of tasks for today"); 
    int listSize = tasks.size(); 
 
  for(int position = 0; position<listSize; position++){ 
     System.out.println(tasks.get(position)); 
  } 
} 
 
//inherited method from the patient's class
 @Override
 public void showToDoctor(){
  super.showToDoctor();
}

//method to enter lab results of a patient
 public void labResults(){ 
    Doctor doc = new Doctor(); 
    System.out.println("please enter the lab results for the patient"); 
    doc.setResults();
 if(getResults() == "positive"){ 
  System.out.println("results have been entered in the system");
    doc.prescribeMedication(); 
  } 
} 

//method to prescribe medication for a patient based on their lab results
     @Override
 public void prescribeMedication(){ 
  Doctor doc = new Doctor(); 
  System.out.println("prescribe medication for the patient"); 
  doc.setMedication();
  System.out.println("medication have been prescribed to patient, thank you.");
  } 
}