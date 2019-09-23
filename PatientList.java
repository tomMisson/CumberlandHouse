//Thomas Misson

///IMPORT modules
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

///CODE
public class PatientList
{
	//Creates global variables and objects to be used throughout the class 
	Patient[] arrayPatients = new Patient[999];
	int nextPatientLocation = 0;
	String patientMasterfile = "Patient master.txt";
	CaesarCipher cc = new CaesarCipher(); 

	//Creates a constructer so that when the class is run the array populates so the methods can manipulate data from 
	//the array rather than manipulating a file
	public PatientList()
	{
		readPatientListFromFile();
		//showPatientList();
	}

	//Adds a patient to the global array by passing a patient to the method and incrementing the next patient location pointer
	public void addPatientToList(Patient tempPatient)
	{
		arrayPatients[nextPatientLocation] = tempPatient;
		nextPatientLocation++;
	}

	public void showPatientList()
	{
		//Loops through the array to show the term in the array and the pupil objects attributes that have been added to it
		for(int i=0; i<nextPatientLocation; i++)
		{
			String currentPositionPatientData = arrayPatients[i].toString();
			System.out.println("Patient " + i + " is " + currentPositionPatientData);
		}
	}

	public void writePatientListToFile()
	{
		try
		{
			//Sets up file writer objects and passes the filename
			FileWriter fw = new FileWriter(patientMasterfile);
			BufferedWriter bw = new BufferedWriter(fw);

			//loop through the array
			for(int i = 0; i<nextPatientLocation; i++)
			{
				//takes the data from the array and converts it from a patient to a string and then writes it and gets the next line ready for writing
				String currentPositionPatientData = arrayPatients[i].toString();
				bw.write(currentPositionPatientData);
				bw.newLine();
			}
			bw.close();
		}
		//Catches any exeptions from the writing process
		catch(Exception e)
		{
			System.out.println("Error writing to file "+ e);
		}
	}

	public void readPatientListFromFile()
	{	
		//Resets the next data location pointer
		nextPatientLocation = 0;
		try
		{
			//Makes file reader objects and passes filename
			FileReader fr = new FileReader(patientMasterfile);
			BufferedReader br = new BufferedReader(fr);

			//reads first line and makes a blank patient 
			String aReadLine = br.readLine();
			Patient tempRead = new Patient();
			//loops through all lines in the file
			while(aReadLine != null)
			{
				//resets patient to null
				tempRead = new Patient();
				//Splits the read in data and assigns that data to the appropriate attribute 
				String[] splitPatientData = aReadLine.split("~~");

				tempRead.patientID = splitPatientData[0];
				tempRead.forename = splitPatientData[1];
				tempRead.surname = splitPatientData[2];
				tempRead.username = splitPatientData[3];
				tempRead.password = splitPatientData[4];
				tempRead.houseNumber = splitPatientData[5];
				tempRead.postcode = splitPatientData[6];
				tempRead.telephoneNumber = splitPatientData[7];
				tempRead.dob = splitPatientData[8];
				tempRead.mode = splitPatientData[9];
				//adds patient to list and gets the next line
				addPatientToList(tempRead);
				aReadLine = br.readLine();
			}	
		}
		//catches any exceptions trown by reading  
		catch(Exception e)
		{
			System.out.println("Error reading file, ");
			e.printStackTrace();
		}
	}

	//checks to see if patient exists. 
	public boolean findPatientId(String id)
	{
		//ID is tested to see if it appears in the array 
		nextPatientLocation = 0;
		boolean foundId = false;
		try
		{
			//loops through the array
			for(int i=0; arrayPatients[i] != null; i++)
			{
				if(id.equals(arrayPatients[i].patientID))
				{
					foundId = true;
					return foundId;
				}		
			}
			//returns if patient exists 
			return foundId;
		}
		//throws exception if errror occurs
		catch(Exception e)
		{
			return false;
		}
	}

	//returns the location in the array of the patient passed
	public int arrayLocationOfPatient(Patient passedPat)
	{
		int index = -1;
		for(int i=0; i<nextPatientLocation; i++)//Loops through the array 
		{
			if(arrayPatients[i].toString().equals(passedPat.toString()))//tests if user exists in array
			{
				index = i;//gets index 
				break;
			}
		}
		return index;//returns index
	}

	//Method that verifies that the edit process was sucessful
	public boolean editData(int index, Patient pat)
	{
		try
		{	
			//replaces the data at the current index with the patient data passed
			arrayPatients[index] = pat;
			writePatientListToFile();// rewrite the file
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	//This method finds patients whos names contains the search term
	public Patient[] findID(String sv)
	{
		Patient[] foundPatients = new Patient[999];//Creates a blank array
		int count = 0;

		for(int i=0; arrayPatients[i]!=null ;i++)//loops through the array
		{
			if(arrayPatients[i].forename.contains(sv)||arrayPatients[i].surname.contains(sv))//sees if either surname or firstname contain the search term allowing for partieal searching
			{
				foundPatients[count] = arrayPatients[i];//if found it adds the patient to an array
				count++;
			}
		}
		return foundPatients;//that array is then returned
	}

	//Responsible for the login of patatients
	public Patient login(String id, String password)
	{
		int foundPatientLocation = -1;

		Patient tempPatient = null;//Creates a blank patient
		for(int i=0; i<nextPatientLocation; i++)//Loops through the array
		{
			if(arrayPatients[i].patientID.equals(id))//if the id entered matches the id of that term 
			{
				if(arrayPatients[i].password.equals(password))// and the password entered matches that of the password attribute of the same term in the array
				{
					tempPatient = arrayPatients[i];// that term is saved as a patient 
					break;
				}
			}
		}
		return tempPatient;//that patient is then returned to the system
	}

	//returns the patient object of that id
	public Patient returnPatFromId(String id)
	{
		Patient foundPatient = null;// blank patient is made

		for(int i=0; arrayPatients[i]!=null ;i++)//loops through the array
		{
			if(arrayPatients[i].patientID.equals(id))//if the id passed matches the id of that term in the array it will save that array term 
			{
				foundPatient = arrayPatients[i];
			}
		}
		return foundPatient;// that saved array term is then returned
	}

	//This tests if the patient has any notes accosiated with that account
	public boolean patientHasNotes(int id)
	{
		//creates a directory 
		File aDirectory = new File("Logs\\"+(id+"")+"\\");
		//saves a string array of all files in that directory
	    String[] files = aDirectory.list();

	    //tests if the array is empty or not
	   	if(files.length>0)
	   	{
	   		return true;//If there is data in the array then the length will be >= 1
	   	}
	   	else
	   	{
	   		return false;//If it is empty it will return false
	   	}
	}

	//Deduces from the next avlible index pointer what the id of the patient being created should be 
	public String nextAvalibleID()
	{
		//creates a blank string as all Id's have to be 3 digits and if it was an itger it would remove the leading 0's
		String id = "";
		//If the pointer is 0 then the id needs to be 001
		if(nextPatientLocation == 0)
		{
			id = "001";
		}
		//otherwise it is the existing length + howver many 0's needed to make it 3 digits + the pointer data + 1 to make the first one not overlap any leaing id's
		else if(nextPatientLocation < 10)
		{
			id = "00"+(nextPatientLocation+1);
		}
		else if(nextPatientLocation < 100)
		{
			id = "0"+(nextPatientLocation+1);
		}
		else if(nextPatientLocation < 999)
		{
			id = ""+(nextPatientLocation+1);
		}
		return id;//Id is returned
	}
}