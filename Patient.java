//Thomas Misson

///IMPORT modules
import java.util.*;

///Creates a new object class of patient
public class Patient 
{
	//Creates global variables and instanciates a input scanner
	Scanner newScan = new Scanner(System.in);
	int patientID;
	String forename;
	String surname;
	String password;
	int age;
	String postcode;
	String houseNumber;
	String telephoneNumber;
	String bloodType;
	String nextOfKin;
	String dob; 


	public Patient()
	{
		//System.out.println("New instance of Patient created");
		age = 0;
		//patientDetails();
	}

	public static void main(String[] args) 
	{
		Patient pat = new Patient();
	}

///Used to test if the global variables and the toString method work
/*
	public void patientDetails()
	{
		System.out.println("ID, forename, surname, password, age, postcode, telephoneNumber, bloodType, nextOfKin, dob");
		patientID = newScan.nextInt();
		forename = newScan.nextLine();
		surname = newScan.nextLine();
		password = newScan.nextLine();
		password = newScan.nextLine();
		age = newScan.nextInt();
		postcode = newScan.nextLine();
		telephoneNumber = newScan.nextLine();
		bloodType = newScan.nextLine();
		nextOfKin = newScan.nextLine();
		dob = newScan.nextLine();
		dob = newScan.nextLine();
	}
*/
	public String toString()
	{
		String data = (patientID + " " + forename + " " + surname + " " + password + " " + age + " " +  houseNumber + " " + postcode + " " + telephoneNumber + " " + bloodType + " " + nextOfKin + " " + dob);
		return data;
	}


}