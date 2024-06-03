package ClinicSystem;

import java.io.*;
import java.util.*;

public class MainClass {

    public static void main(String[] args) {
        
        Scanner input=new Scanner(System.in);
     
     //create ArrayList for Clinic Information
     ArrayList<String> ClinicInformation = new ArrayList<String>();
     
     ClinicInformation.add(0,"Life Clinic");
     ClinicInformation.add(1, "Al- Zaher Street in Makkah infront of Umm al Qura Universaty");
     ClinicInformation.add(2,"20 hours a day");
     ClinicInformation.add(3, "Sunday to Thursday");
     ClinicInformation.add(4, "0593939393");
      
     //print the Clinic Information
     System.out.println("Welcome to "+ClinicInformation.get(0));
     System.out.println("that is placed in:  "+ClinicInformation.get(1));
     System.out.println(ClinicInformation.get(0)+" welcomes you: "+ClinicInformation.get(2));
     System.out.println("it is open from: "+ClinicInformation.get(3));
     System.out.println("To communicate and enquire through call: "+ClinicInformation.get(4));
     System.out.println();
     System.out.println();
     
     
     System.out.println("The Patient:");
     
    // Create patients 1    
     Patient patient1= new Patient();

    System.out.println("What is your name? ");
    patient1.setName();
    System.out.println("What is your age? ");
    patient1.setAge();
    patient1.medicalHistory();
    System.out.println();
    
    
    
    
    //make an Appointment
    Appointment booking=new Appointment();
    System.out.println("Hello " + ""+patient1.getName()+" Do you want a booking? ");
    input.next();
    System.out.println();
    
    booking.book();
    System.out.println();
    
    System.out.println("Will this is your appointment: ");
    booking.viewAppointments();
     System.out.println();
     
     
     
    System.out.println("Do you want to edit it?");
    String a=input.next();
    
    if(a.equals("yes")){
         booking.updateAppointment();
         booking.viewAppointments();
   
    }
  
    
    
    System.out.println("Do you want to delete it?");
    a=input.next();
    
    if(a.equals("yes")){
         booking.deleteAppointment();
    }
    //booking.collectFees(60.0);
    System.out.println("Ok ,now go to the doctor");
    System.out.println();   
    
    
    
   //the doctor's info 
   System.out.println("The Doctor:");
  
  Doctor doc = new Doctor();
  doc.drLogin();
  System.out.println(); 
  
  doc.docTasks();
  System.out.println(); 
  
  patient1.showToDoctor();;
  System.out.println(); 
  
  doc.labResults();
  System.out.println(); 
  
  doc.prescribeMedication();
 
  
   // File 
        try {
            File file = new File("patient_Info.txt");
            PrintWriter write= new PrintWriter(file);
            write.println("This the patient's Information:");
            write.println(patient1.getName()+", "+patient1.getAge());
            write.println("This the patient's Appointment:");
            write.println(booking.getDay()+" / "+booking.getMonth()+" at "+booking.getTime());
            write.close();

        }catch (IOException ex) {
            System.out.println(ex);
        }
       }
   }