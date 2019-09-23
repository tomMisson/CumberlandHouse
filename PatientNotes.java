//Thomas Misson

///IMPORT modules
import java.util.*;
import java.text.*;

///Creates a new object class of PatientNotes
public class PatientNotes 
{
	//Sets up different formatting rules for different uses in the system
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	DateFormat fileDate = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat fileTime = new SimpleDateFormat("HH-mm");

	//Creates global variables 
	String date = fileDate.format(new Date());
	String time = fileTime.format(new Date());
	String timeReal = timeFormat.format(new Date());

	String timeStamp = (date+ " " + time);
	String notes;
	int patientID;
	String doctorName;

	//Creates a to string method for the note object so it can be converted to a string
	public String toString()
	{
		String data = (doctorName + "$" + patientID + "$" + timeStamp + "$" + notes);
		return data;
	}
}