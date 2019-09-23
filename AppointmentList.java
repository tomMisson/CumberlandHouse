//Thomas Misson

///IMPORT modules
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

///CODE
public class AppointmentList
{
	//Defines objects and variables used throughout the class
	Appointment appt = new Appointment();
	Appointment[] appointmentArray = new Appointment[999];
	int nextAppointmentLocation = 0;
	String filename = "Appointment Master.txt";
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	//Constructor that reads in every appointment to the array that is defined globally
	public AppointmentList()
	{
		readAppointmentsFromFile();
	}

	//responsible for reading in each appointment to the array
	public void readAppointmentsFromFile()
	{
		//Resets the next apppointment location pointer
		nextAppointmentLocation = 0;
		try
		{
			//Creates file reading objects
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);

			//gets the first of the file 
			String aReadLine = br.readLine();

			for(int i=0; aReadLine != null; i++)//itterates through each line of the file
			{
				String[] splitAppointment = aReadLine.split(" ");

				Appointment tempRead = new Appointment();

				//Assignes the broken up data in to the attributes of the appointment
				tempRead.appointmentStartTime = splitAppointment[0];
				tempRead.appointmentEndTime = splitAppointment[1];
				tempRead.date = splitAppointment[2];
				tempRead.patientID = splitAppointment[3];
				tempRead.nameOfDoctor = splitAppointment[4]+ " "+ splitAppointment[5];

				addToList(tempRead);

				aReadLine = br.readLine();
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error reading file");
		}
	}

	//Allows an appointment to be added to the array of appointments and updates the pointer for then next appointment to be saved
	public void addToList(Appointment tempAppt)
	{
		appointmentArray[nextAppointmentLocation] = tempAppt;
		nextAppointmentLocation++;
	}

	//Uses a bubble sort to order appointments by start time
	public void sortList()
	{
		Appointment temp;
		boolean swapped = true;
		int n = nextAppointmentLocation;
		while(swapped == true){
			swapped = false;
			for(int i=0;i<(n-1);i++)
			{
				//If the current appointment start is greater than the next appintment in the list then they are swapped
				if(Integer.parseInt(appointmentArray[i].appointmentStartTime) > Integer.parseInt(appointmentArray[i+1].appointmentStartTime))
				{
					temp = appointmentArray[i+1];
					appointmentArray[i+1] = appointmentArray[i];
					appointmentArray[i] = temp;
					swapped = true;
				}
			}
		}
	}

	//Tests to see if an appointment already exists to avoid conflicts
	public boolean searchForExistingAppt(String sv)
	{
		boolean found = false;
		for(int i=0; i<nextAppointmentLocation;i++)//loops through the array
		{
			if(appointmentArray[i].toString().equals(sv))//checks if the term of the array and the passed appointment exist
			{
				found=true;
			}
		}
		return found;//returns the result depending on if it did or didnt find the appointment
	}

	//this method finds the index of the string in the array 
	public int searchForExistingApptToReturnID(String sv)
	{
		AppointmentList apptList = new AppointmentList(); //updates the list with the most recent data
		int found = -1;
		for(int i=0; i<nextAppointmentLocation;i++)//loops through the array
		{
			if(appointmentArray[i].toString().equals(sv))//if it finds the desired appointment
			{
				found=i;//the index is saved
				break;
			}
		}
		return found;//then the index is returned
	}

	//this method finds the index of the appointment in the array, the code is identical except the desired appointment is converted to a string for comparison
	public int searchForExistingAppt(Appointment sv)
	{
		int found = -1;

		for(int i=0; appointmentArray[i] != null; i++)
		{
			if(appointmentArray[i].toString().equals(sv.toString()))
			{
				found=i;
			}
		}
		return found;
	}

	///Responsible for finding any identical appointments that are unbooked
	///This is to prevent the duplication of a booked and unbooked appointment at the same time
	public boolean searchForUnbookedConflicting(Appointment sv)
	{
		boolean found = false;
		for(int i=0; i<nextAppointmentLocation;i++)
		{
			if(appointmentArray[i].toString().equals(sv.toString())&&appointmentArray[i].patientID.equals("0"))
			{
				found=true;
			}
		}
		return found;
	}

	//This method extracts appointments that are in the array for todays date under the name of the doctor that is passed
	public Appointment[] appoitmentsForDay(String drName)
	{
		Appointment[] appts = new Appointment[999];//Creates an array of appointments for these appointments to be saved to 
		Date date = new Date();
		int count = 0;

		for(int i=0; i<nextAppointmentLocation;i++)//loops through the array
		{
			if(appointmentArray[i].nameOfDoctor.equals(drName) && appointmentArray[i].date.equals(dateFormat.format(date)))//If the apppointment matches that days date and the doctors name
			{
				appts[count] = appointmentArray[i];//then it is added to the array
				count++;
			}
		}

		return appts;//the array is then returned
	}

	//returns all appointments in the system 
	public Appointment[] appoitmentsForDay()
	{
		Appointment[] appts = new Appointment[999];
		Date date = new Date();
		int count = 0;

		for(int i=0; i<nextAppointmentLocation;i++)//loops through the array 
		{
			appts[count] = appointmentArray[i];//adds all appointment to an array 
			count++;
			
		}

		return appts;//returns the array
	}

	//Loops through the array to get the appointments that are assigned to the user that is logged in
	public Appointment[] myAppoitmentsForDay()
	{
		InteractionMenu inter = new InteractionMenu();//Creates an instance of interaction menu to be able to get the authenticated id
		Appointment[] appts = new Appointment[999];
		Date date = new Date();
		int count = 0;

		for(int i=0; i<nextAppointmentLocation;i++)//loops through the array
		{
			if(appointmentArray[i].patientID.equals(inter.getAuthedID()))//if the id assigned to the appointment matches that of the autenticated user 
			{
				appts[count] = appointmentArray[i];//then the appoimntment is added to an array
				count++;
			}
		}
		return appts;//That array is then returned
	}

	//This method loops through the array and take any appointments that match the offset date and saves them as a string array
	public String[] listAppointments(int offset)
	{
		String[] appointment = new String[999];
		appt.offsetDate(offset);//offsets the date to get the desired date for the appointment refinement. For example if i want to get tomorrows appoitments then the date is offset by 1 
		
		int count = 0;

		for(int i =0; i<nextAppointmentLocation; i++)//loops through the array
		{
			if(appointmentArray[i].date.equals(appt.date) && appointmentArray[i].patientID.equals("0"))//if the appointment in that term is unbooked, has the id of 0, and matches the date desired. 
			{
				appointment[count] = appointmentArray[i].toString();//the appointment is converted to a string and is added to an array
				count++;
			}
		}	
		return  appointment;//this string array is then returned
	}

	//Responsible for booking appointments, this method takes an appointment in string form and changes the id it is booked to to the id of that user that is authenticated
	public void bookAppointment(String appointmentToModify)
	{
		int result = searchForExistingApptToReturnID(appointmentToModify);//gets the index of the appointment before it is modified so it knows which data to overwrite

		InteractionMenu inter = new InteractionMenu();//InteractionMenu object instancicated to get authenitcated Id
		String[] splitAppointment = appointmentToModify.split(" ");//Splits the string by the whitespace 

		Appointment tempRead = new Appointment();
		//Creates a temporary appointment object for the data to be read in to and attributes to be assigned to by the previously split data

		tempRead.appointmentStartTime = splitAppointment[0];
		tempRead.appointmentEndTime = splitAppointment[1];
		tempRead.date = splitAppointment[2];
		tempRead.patientID = splitAppointment[3];
		tempRead.nameOfDoctor = splitAppointment[4]+ " " +splitAppointment[5];
		
		tempRead.patientID = inter.getAuthedID();//replaces the currently 0 id with the id of the authenticated user 

		appointmentArray[result] = tempRead;//overwrites the existing appointment in the array

		writeAppointmentToFile();//file is updated with new array data

		JOptionPane.showMessageDialog(null, "Appointment has been booked");//User confirmation recived
	}

	//Responsible for canceling appointments, this algorithem is identical to the prior method except instead of changing the id to that of the authenticated user, it reverts it back to 0, allowing the appointment to be rebooked
	public void cancelAppointment(String appointmentToModify)
	{

		int result = searchForExistingApptToReturnID(appointmentToModify);

		InteractionMenu inter = new InteractionMenu();
		String[] splitAppointment = appointmentToModify.split(" ");

		Appointment tempRead = new Appointment();

		tempRead.appointmentStartTime = splitAppointment[0];
		tempRead.appointmentEndTime = splitAppointment[1];
		tempRead.date = splitAppointment[2];
		tempRead.patientID = splitAppointment[3];
		tempRead.nameOfDoctor = splitAppointment[4]+ " " +splitAppointment[5];
		
		tempRead.patientID = "0";

		appointmentArray[result] = tempRead;

		writeAppointmentToFile();

		JOptionPane.showMessageDialog(null, "Appointment has been canceled");
	}

	//This method loops through the array and writes each term as a line in the masterfile
	public void writeAppointmentToFile()
	{
		sortList();//the data in the array is first sorted
		try
		{
			//Sets up file writer objects and passes 
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);

			for(int i = 0; i<nextAppointmentLocation; i++)//loops through the array
			{
				//For every term, it is converted to a string and then written to the file. 
				String currentPositionAppointmentData = appointmentArray[i].toString();
				bw.write(currentPositionAppointmentData);
				bw.newLine();
			}
			bw.close();//this closes the output stream
		}

		catch(Exception e)
		{
			System.out.println("Error writing to file "+ e);// Any exceptions are caught and an arror message is displayed to the user
		}
	}

	//For when an appointment cant be edited, this method will delete and appointment that is passed to it.
	public boolean deleteAppointment(Appointment apptToDelete)
	{
		int index=0;
		Appointment[] appointmentArrayTemp = new Appointment[999];//A new array is made to hold the data that doesnt have the appointment marked for deletion
		
		appointmentArrayTemp = appointmentArray;//All data in the current array is saved to this array, in effect, duplicating it
		int result = searchForExistingAppt(apptToDelete);// the index for the appointment for deletion is then saved

		if(result != -1)//if the appointment is found
		{
			index = result;
		
			if(index == -1)
			{
				return false;
			}
			else
			{
				appointmentArrayTemp[index] = null;//then set the appointment at that index to null

				for(int x=index+1; x<appointmentArrayTemp.length; x++)// then loop through the new array for every term after the deleted data
				{
					//swap the data in the next position with the null value in the lefthand position to that term, essentially moving the null to the end 
					Appointment tempAppt = new Appointment();
					tempAppt = appointmentArrayTemp[x];
					appointmentArrayTemp[x-1] = tempAppt;
				}
				//the global array is then updated with the data in the new array that no longer has the appointment marked for deletion in
				appointmentArray = appointmentArrayTemp;
				nextAppointmentLocation--;//pointer decrements
				writeAppointmentToFile();//file is updated
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	//This method updates appointments in the array with new data that is passed to it in the form of another appointment
	public void editAppt(int indexToEdit, Appointment temp)
	{
		//the data in the index passed is overwritten with data that is passed
		appointmentArray[indexToEdit] = temp;
		writeAppointmentToFile();//file is updated
	}
}