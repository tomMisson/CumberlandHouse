//Thomas Misson

///IMPORT modules
import java.util.*;
import java.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Appointment 
{
	//Creates global variables and attributes for appointments
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	//Creates an instance of calender to verify dates 
	Calendar cal = Calendar.getInstance();
	//Create instance of doctor to assign a doctor to an appointment
	Doctor doc = new Doctor(); 

	//attributes
	String date = dateFormat.format(cal.getTime());
	String patientID;
	String nameOfDoctor = "";
	String appointmentStartTime;
	String appointmentEndTime;
	
	String[] hoursTime = new String[13];
	String[] minTime = new String[5];
	String appointmentTime;

	int appointmentDuration;

	//A constructer to populate the arrays  
	public Appointment()
	{	
		populateHourTimsArray();
		populateMinTimsArray();
	}

	public void offsetDate(int offset)
	{
		cal.add(Calendar.DATE, offset);
		date = dateFormat.format(cal.getTime());
	}

	//Methods that are responsible for holding the data about the opening hours of the surgery and the interval between appointments
	public void populateHourTimsArray()
	{
		hoursTime[0]= "";
		hoursTime[1]= "8";
		hoursTime[2]= "9";
		hoursTime[3]= "10";
		hoursTime[4]= "11";
		hoursTime[5]= "12";
		hoursTime[6]= "13";
		hoursTime[7]= "14";
		hoursTime[8]= "15";
		hoursTime[9]= "16";
		hoursTime[10]= "17";
		hoursTime[11]= "18";
		hoursTime[12]= "19";
	}
	public void populateMinTimsArray()
	{
		minTime[0]="";
		minTime[1]="00";
		minTime[2]="15";
		minTime[3]="30";
		minTime[4]="45";
	}
	// A to string method that takes the attributes of a appointment object and converts that object to a string 
	public String toString()
	{
		String data = appointmentStartTime + " " + appointmentEndTime + " " + date + " " + patientID + " " + nameOfDoctor;
		return data;
	}
}