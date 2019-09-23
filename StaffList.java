//Thomas Misson

///IMPORT modules
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Creates stafflist class 
public class StaffList
{
	//Creates global variables and objects to be used throughout the class 
	User[] arrayUsers = new User[999];
	int nextUserLocation = 0;
	String staffMasterfile = "Staff master.txt";
	CaesarCipher cc = new CaesarCipher();

	//Creates a constructer so that when the class is run the array populates so the methods can manipulate data from 
	//the array rather than manipulating a file
	public StaffList()
	{
		readUserListFromFile();
		//showUserList();
	}

	//Adds a user to the global array by passing a user to the method and incrementing the next user location pointer
	public void addUserToList(User tempUser)
	{
		arrayUsers[nextUserLocation] = tempUser;
		nextUserLocation++;
	}

	//this method itterates through the terms of the array, converting them to a string and writing them to a file as a line
	public void writeUserListToFile()
	{
		try
		{
			//Sets up file writer objects and passes 
			FileWriter fw = new FileWriter(staffMasterfile);
			BufferedWriter bw = new BufferedWriter(fw);

			//loops through each term of the array
			for(int i = 0; i<nextUserLocation; i++)
			{
				//Converts the user object to a string 
				String currentPositionUserData = arrayUsers[i].toString();
				//Writes the line to the file and sets up the next line
				bw.write(currentPositionUserData);
				bw.newLine();
			}
			//closes the writer
			bw.close();
		}
		//Catches any exeptions thrown from the writer
		catch(Exception e)
		{
			System.out.println("Error writing to file "+ e);
		}
	}

	//reads lines from the file and coverts the strings to objects of Users
	public void readUserListFromFile()
	{
		//Sets up user loction so that for every new user created it can incrment and always point to the next avalible index
		nextUserLocation = 0;
		try
		{
			//Setus up a file reader object
			FileReader fr = new FileReader(staffMasterfile);
			BufferedReader br = new BufferedReader(fr);

			//Reads the first line and creates a user for the data to be read in to
			String aReadLine = br.readLine();
			User tempRead = new User();
			//Loops through the file until there is no data
			while(aReadLine != null)
			{
				//sets the user object to null
				tempRead = new User();
				//Splits the read line from the file
				String[] splitUserData = aReadLine.split("~~");

				//assigns all the sections of the line in the file to the correct attribute of the user object
				tempRead.title = splitUserData[0];
				tempRead.forename = splitUserData[1];
				tempRead.surname = splitUserData[2];
				tempRead.username = splitUserData[3];
				tempRead.password = splitUserData[4];
				tempRead.contactNumber = splitUserData[5];
				tempRead.dob = splitUserData[6];
				tempRead.mode = splitUserData[7];

				//adds the user to the aray
				addUserToList(tempRead);
				//reads the next line
				aReadLine = br.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println("Error reading file, ");
			e.printStackTrace();
		}
	}

	public void showUserList()
	{
		//Loops through the array to show the term in the array and the user objects attributes that have been added to it
		for(int i=0; i<nextUserLocation; i++)
		{
			String currentPositionUserData = arrayUsers[i].toString();
			System.out.println("User " + i + " is " + currentPositionUserData);
		}
	}

	//responsible for the login process of users, the method is passed 2 strings 
	public User login(String username, String password)
	{
		User tempUser = null;
		//loops through the array of users 
		for(int i=0; i<nextUserLocation; i++)
		{
			//compares the given username with that terms username
			if(arrayUsers[i].username.equals(username))
			{
				//If they match, it compares that terms password
				if(arrayUsers[i].password.equals(password))
				{
					//assigns the blank user to that term user in the users array and returns the user
					tempUser = arrayUsers[i];
					break;
				}
			}
		}
		return tempUser;
	}

	//Creates a method that returns a string array of doctor names comprised of title and surname 
	public String[] returnDoctors()
	{
		//Creates variables for making the array of names 
		String title = "";
		String surname = "";
		String[] names = new String[999];
		int count = 0;

		//loops through the user array to find doctors 
		for(int i=0; i<nextUserLocation; i++)
		{
			if(arrayUsers[i].mode.equals("Doctor"))
			{
				//takes thier title and surname and combines them in to string
				title = arrayUsers[i].title;
				surname = arrayUsers[i].surname;
				names[count] = title + " " + surname;
				count++; 
			}
		}
		//returns that array of doctor names
		return names;
	}
}