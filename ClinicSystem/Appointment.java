package ClinicSystem;
import java.io.*;
import java.util.*;



public class Appointment{
    Scanner scanner = new Scanner(System.in);
    
    //variables
    public int fee;
    private int billAmount;
    private long ID; 
    private int month;
    private String day;
    private String time;
    private boolean isBooked;
    private static ArrayList<Appointment> appointments = new ArrayList<>();
     
     
    //counstructer
    public Appointment ()
    {
       ID=0;
       month=0;
       day=null;
       time=null;
       billAmount=0;
       this.isBooked = false;
    }
    


    //setters and getters
    public void setID(long ID)
    {
        this.ID=ID;
    }
    
    public long getId()
    {
        return ID;
    }
    
    public void setMonth(int month)
    {
      this .month=month;
    }
            
    public int getMonth()
    {
        return month;
    }        
     
    public void setDay(String day)
    {
      this.day=day;
    }
    
    public String getDay()
    {
        return day;
    }
    
      public void setTime(String time)
    {
        this.time=time;
    }
    
      public String getTime()
    {
        return time;
    }
    public void setBillAmount(int billAmount)
    {
        this.billAmount=billAmount;
    }
    public double getFee() {
        return billAmount;
    }
      
      

    // the method books an appointment for the patient 
    public void book()
    {
        System.out.print("Enter ID: ");
        ID = scanner.nextLong();
        System.out.print("Enter day: ");
        day = scanner.next();
        System.out.print("Enter time: ");
        time = scanner.next();
        System.out.print("Enter month: ");
        month = scanner.nextInt();
        billAmount=60;
        System.out.println("Bill amount "+billAmount+ " please pay ");
        fee = scanner.nextInt();
        if (fee < billAmount) {
            System.out.println("fee paid is less than the amount, please pay the correct amount");
            fee = scanner.nextInt();
        } else {
            System.out.println("Appointment fee collected successfully.");
        }

        if ("wednesday".equals(day) && "Tuesday".equals(day)) {
            System.out.println("This day is full. Please choose another day.");
        } else {
            this.day = day;
            this.time = time;
            this.month = month;
            this.ID = ID;
            this.billAmount=billAmount;
            this.isBooked = true;
            appointments.add(this);
            System.out.println("The booking is successful.");
        }
    }
    
    
    
    // updates the appointment if the patient wants
     public void updateAppointment() {
        if (!isBooked) {
            System.out.println("No appointment booked yet.");
        } else {
            System.out.println("Enter the new date and time for the appointment:");
            System.out.print("Enter ID: ");
            ID = scanner.nextLong();
            System.out.print("Enter day: ");
            day = scanner.next();
            System.out.print("Enter time: ");
            time = scanner.next();
            System.out.print("Enter month: ");
            month = scanner.nextInt();
            this.time = time;
            this.month = month;
            this.ID = ID;
            this.billAmount=billAmount;
            System.out.println("The appointment has been updated.");
        }
    }

     
     
    // deletes the appointment if the patient wants
    public void deleteAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointment booked yet.");
        } else {
            appointments.remove(this);
            this.isBooked = false;
            System.out.println("The appointment has been deleted.");
        }
    }

    
    
    // shows the patient's appointment 
    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments booked yet.");
        } else {
            for (Appointment a : appointments) {
                System.out.println("- " + a.day + "/" + a.month  + " at " + a.time);
            }
        }
 
    }
  
    
}