package ClinicSystem;

import java.io.*;
import java.util.*;

public class Patient {
    Scanner a=new Scanner(System.in);
     
    //variables
    private String name;
    private int age;
    private String medicalHistory;
    String m;

    
    //setters and getters 
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setName(){
     this.name=a.next();
    }
 
    public void setAge(){
     this.age=a.nextInt();
    }
  
     public void setMedicalHistory(){
     this.medicalHistory=a.next();
    }
     
    
    //shows the patient's info to the doctor
    public void showToDoctor() {
        System.out.println("This is information for the Patient: ");
        System.out.println("Patient Name: " + name);
        System.out.println("Patient Age: " + age);
        System.out.println("Patient Medical History: " + m);
    }
    

    //the methode asks the patient if they have a chronic illness. if the patient have it then asks what is it?
    public String medicalHistory(){
     System.out.println("Do you have a chronic illness? ");
     m=a.next();
     if("yes".equals(m)){
        System.out.println("what it is ?");
         m=a.next(); 
         return m;
    }else
         return "Ok, good";

    }

   
    public void prescribeMedication(){ 
    Patient patient = new Patient(); 
    System.out.println("prescribe medication for the patient"); 
  } 
   
   
    
    
}