//Thomas Misson

//imports modules
import java.util.*;

//This model uses inheritance to derive the user level and what attributes that user should have.  
///Creates a new class called user which is the base class for all user types 
public class User
{
	//Defines attributes that all users will inherit
	String forename;
	String surname;
	String password;
	String username;
	String dob;
	String title;
	String contactNumber;
	String mode;

	//Defines a to string method that takes the data of the user class and converts it to a string
	public String toString()
	{
		String data = (title + "~~" + forename + "~~" + surname + "~~" + username + "~~" + password + "~~" + contactNumber + "~~" + dob + "~~" + mode);
		return data;
	}

}

class Patient extends User //Defines a patient as a subclass of User 
{	
	//Adds additional attributes to the patient object
	String patientID;
	String postcode;
	String houseNumber;
	String telephoneNumber;

	//creates a to string method for patients
	public String toString()
	{
		String data = (patientID + "~~" + forename +"~~" + surname +"~~" + username +"~~"+ password + "~~" +  houseNumber + "~~" + postcode + "~~" + contactNumber + "~~" + dob + "~~" + mode);
		return data;
	}
}

class SuperUser extends User //Defines a super user as a subclass of User 
{
	//creates a to string method for super users
	public String toString()
	{
		String data = (title + "~~" + forename + "~~" + surname + "~~" + username + "~~" + password + "~~" + contactNumber + "~~" + dob + "~~" + mode);
		return data;
	}
}

class Receptionist extends User //Defines a receptionist as a subclass of User 
{
	//creates a to string method for receptionists
	public String toString()
	{
		String data = (title + "~~" +forename + "~~" + surname + "~~" + username + "~~" + password + "~~" + contactNumber + "~~" + dob+ "~~" + mode);
		return data;
	}
}

class Doctor extends User //Defines a doctor as a subclass of User 
{
	//Creates a list for the doctors in the file to be read in to the system
	List<String> lstOfDocs = new ArrayList<String>();

	//creates a to string method for doctors
	public String toString()
	{
		String data = (title + "~~" + forename + "~~" + surname + "~~" + username + "~~" + password + "~~" + contactNumber+ "~~" +dob+ "~~" + mode);
		return data;
	}

	//populates the list model with doctor names
	public String[] populate()
	{
		lstOfDocs.add("");//creates a blank term to not have data pre-populated in combo-boxes

		//Creates an instance of staff list and runs a method that returns a string array of doctor titles and last names
		StaffList stfList = new StaffList();
		String[] namesToAddToList = stfList.returnDoctors();

		//loops through the returned list and adds the arry terms to the list
		for(int i=0; namesToAddToList[i] != null; i++)
		{
			lstOfDocs.add(namesToAddToList[i]);
		}
		
		//converts the array to a string and returns the array to be used in combo-boxes 
		String[] doctors = new String[lstOfDocs.size()];
		doctors = lstOfDocs.toArray(doctors);

		return doctors;
	}
}