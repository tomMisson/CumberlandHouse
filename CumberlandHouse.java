//Thomas Misson

//Imports modules nessecary for GUI's and tools
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*-This acts as a wrapper for all the classes that make up the graphical user interface.*/
public class CumberlandHouse extends JFrame
{
	//Creates a constructor for this class so that when it is run it runs the main menu and splash screen
	JPanel splashPanel = new JPanel(null);
	ImageIcon load = new ImageIcon("Resources/load.gif");
	JLabel lblLoad = new JLabel(load);

	int[] res = getResolution();

	int width = res[0];
	int height = res[1];

	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);
	
	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.add(splashPanel);
		this.setTitle("Cumberland house");
		splashPanel.setBackground(Color.decode("#ffffff"));
		this.setResizable(false);
		this.setForeground( new Color(-16777216) );
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 

		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Runs the splash screen animation and then runs the main menu
	public void splashScreen()
	{
		lblLoad.setSize(1600,900);
		lblLoad.setLocation(widthMid-800,hightMid-450);
		splashPanel.add(lblLoad);
		try
		{
			Thread.sleep(1500);
			this.setVisible(false);
			MainMenuGUI menu = new MainMenuGUI();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Timeout with splash screen. Please restart the program");
		}
	}

	//This method gets the default monitor resolution and returns the resolution as a integer array in the form width,hight
	public int[] getResolution()
	{
		int[] res = new int[2];//makes a 2 term array
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();//gets the local graphics environment through an object of GraphicsDevice
		res[0] = gd.getDisplayMode().getWidth();//sets the first term of the array as the width
		res[1] = gd.getDisplayMode().getHeight();//sets the second term of the array as the hight
		return res;
	}

	//Main method tells the compiler that the class needs to be constructed first
	public static void main(String[] args)
	{
		CumberlandHouse ch = new CumberlandHouse();
		ch.startGUI();
		ch.splashScreen();
	}
}

/*-This class is responsible for the primary authentication of the user. It tells potential users 
what to do if they wish to register and is the first screen any user is met with in order to log in.*/
class MainMenuGUI extends JFrame implements ActionListener, MouseListener, KeyListener
{
	//Sets up components and objects aswell as global variables for use in this class
	Patient returnedPatient;
	User returnedUser;
	JPanel MainMenuPanel = new JPanel(null); //layout
	JLabel lblInstruct = new JLabel("<html><h1>Please enter a user login:<h1/><html>", SwingConstants.CENTER);
	ImageIcon icon = new ImageIcon("Resources/NHS_200x80.png");
	JLabel banner0 = new JLabel(icon, JLabel.CENTER);
	JLabel banner1 = new JLabel();
 	JButton btnLogin = new JButton();
	JButton btnRegister = new JButton();
	JTextField tfID = new JTextField();
	JPasswordField tfPassword = new JPasswordField();
	PatientList patList = new PatientList();
	StaffList stfList = new StaffList();
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	CaesarCipher cc = new CaesarCipher();

	//Constructor that runs the method that builds the GUI
	public MainMenuGUI()
	{
		startGUI();
	}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		CREATEMainMenuPanel();
		this.add(MainMenuPanel);
		this.setTitle("Cumberland house");
		this.setResizable(false);
		this.setForeground( new Color(-16777216) );
		MainMenuPanel.setBackground(Color.decode("#ffffff"));
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 

		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEMainMenuPanel()
	{
		int[] res = getResolution();

		int width = res[0];
		int height = res[1];

		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		banner0.setLocation(0,0);
		banner0.setSize(res[0],200);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		MainMenuPanel.add(banner0);

		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		MainMenuPanel.add(banner1);

		btnLogin.setLocation(widthMid+20,hightMid + 30);
		btnLogin.setSize(140,50);
		btnLogin.addActionListener(this);
		btnLogin.setFont(standardFont);
		btnLogin.setBorderPainted(true);		
		btnLogin.setContentAreaFilled(false);
		btnLogin.setText("Log in ");
		MainMenuPanel.add(btnLogin);

		btnRegister.setLocation(widthMid-170,hightMid + 30);
		btnRegister.setSize(140,50);
		btnRegister.setFont(standardFont);
		btnRegister.addActionListener(this);
		btnRegister.setContentAreaFilled(false);
		btnRegister.setBorderPainted(true);
		btnRegister.setText("Register");
		MainMenuPanel.add(btnRegister);

		tfID.setLocation(widthMid-200,hightMid-120);
		tfID.setSize(400,50);
		tfID.setHorizontalAlignment(JTextField.CENTER);
		tfID.setFont(standardFont);
		tfID.setText("Username");
		tfID.setColumns(10);
		tfID.addKeyListener(this);
		tfID.addMouseListener(this);
		MainMenuPanel.add(tfID);

		tfPassword.setLocation(widthMid-200,hightMid-60);
		tfPassword.setSize(400,50);
		tfPassword.setText("");
		tfPassword.setFont(standardFont);
		tfPassword.setHorizontalAlignment(JTextField.CENTER);
		tfPassword.setColumns(10);
		tfPassword.setEchoChar('*');
		tfPassword.addKeyListener(this);
		tfPassword.addMouseListener(this);
		MainMenuPanel.add(tfPassword);

		lblInstruct.setLocation(widthMid-200,hightMid-180);
		lblInstruct.setSize(400,50);
		lblInstruct.setOpaque(false);
		lblInstruct.setFont(standardFont);
		MainMenuPanel.add(lblInstruct);
	}
	
	//This method executes different actions depending on where the source of the event is from 
	public void actionPerformed(ActionEvent e)
	{	
		//This makes a pop up informing any users that wish to register what they should do
		if(e.getSource()==btnRegister)
		{
			String message = "<html><body><div width='200px' align='center'>Registration can only be done through an admin. <br/><br/>Please visit the Reception at the site to register for this sevice.</div></body></html>";
 			JLabel messageLabel = new JLabel(message);
 			JOptionPane.showMessageDialog(null, messageLabel);
		}
		//If the login button is pressed then it runsh the authenitcate menthod
		if(e.getSource()==btnLogin)
		{
			authenticate();
		}
	}

	//This method gets the default monitor resolution and returns the resolution as a integer array in the form width,hight
	public int[] getResolution()
	{
		int[] res = new int[2];//makes a 2 term array
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();//gets the local graphics environment through an object of GraphicsDevice
		res[0] = gd.getDisplayMode().getWidth();//sets the first term of the array as the width
		res[1] = gd.getDisplayMode().getHeight();//sets the second term of the array as the hight
		return res;
	}

	public void mouseClicked(MouseEvent e) 
	{
		//When the text areas are clicked the text in them is emptied
		if(e.getSource()==tfID)
		{
			tfID.setText("");
		}
		if(e.getSource()==tfPassword)
		{
			tfPassword.setText("");
		}
	}

	public void mouseEntered(MouseEvent e)
	{}
	

	public void mouseExited(MouseEvent e)
	{}
	

	public void mouseReleased(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}

	public void keyTyped(KeyEvent e)
	{}

	public void keyPressed(KeyEvent e)
	{
		//When the enter key is pressed it runs the authenitcate method
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			authenticate();
		}
	}

	public void keyReleased(KeyEvent e)
	{}

	//This method dertermines what kind of a user is logging in and validates that user depending on what access level they are
	public void authenticate()
	{
		//Creates variables and gets the text of the username and password feild
		boolean failed = false;
		String id = tfID.getText();
		String password = tfPassword.getText();
		password = cc.encrypt(password);//the password entered is encrypted as the password in the file is already encrypted so when they are tested for equality, they will be testing if the 2 encryped passwords are eqaul rather than the file data and the users input

		//If the length of the feild is 3 digits then it will be a patient as members of staff have a username which is greater than 3 characters
		if(id.length() == 3)
		{
			//tests to see if the data entered is valid for the form of a patient id
			int iD = 0;
			try
			{
				iD = Integer.parseInt(id);	
			}
			catch(NumberFormatException err)
			{
				failed = true;
			}

			if(password.equals("") == false)
			{
				if(id.equals("")|| id.equals("Username") == false)
				{
					returnedPatient = patList.login(id, password);//Authenticates the patient to see if details match the record

					if(returnedPatient != null)
					{
						InteractionMenu inter = new InteractionMenu(id, returnedPatient, getResolution());//passes the authenticated user to the interaction menu
						this.setVisible(false);
					}
					else
					{
						failed = true;
					}
				}

				else
				{
					failed = true;
				}
			}

			else
			{
				failed = true;
			}
		}
		else
		{
			//Member of staff
			//tests to see if data entered is valid
			if(password.equals("") == false)
			{
				if(id.equals("")|| id.equals("Username") == false)
				{
					returnedUser = stfList.login(id, password);//Authenitcates member of staf 

					if(returnedUser != null)
					{
						InteractionMenu inter = new InteractionMenu(returnedUser, getResolution());//Passes member of staff to Interaction menu
						this.setVisible(false);
					}
					else
					{
						failed = true;
					}
				}

				else
				{
					failed = true;
				}
			}
		}
		//If any data isnt correct or isnt in the correct format it will return this error
		if(failed)
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid log in ");
			tfPassword.setText("");
		}
	}
}  

/*-Responsible for direction of users throughout the system, this view changes depending on what you 
should have access to at your user level.*/
class InteractionMenu extends JFrame implements ActionListener 
{
	//Sets up components and objects aswell as global variables for use in this class
	PatientList patList = new PatientList();
	JPanel InteractionPanel = new JPanel(null); //layout

	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	ImageIcon userico = new ImageIcon("Resources/user.png");
	ImageIcon searchico = new ImageIcon("Resources/search.png");
	ImageIcon timetableico = new ImageIcon("Resources/timetable.png");
	ImageIcon notesico = new ImageIcon("Resources/notes.png");
	ImageIcon clockico = new ImageIcon("Resources/clock.png");
	ImageIcon pencilico = new ImageIcon("Resources/edit.png");
	ImageIcon noteico = new ImageIcon("Resources/notes.png");
	ImageIcon editico = new ImageIcon("Resources/edit.png");

 	JButton btnFN1 = new JButton();
	JLabel lblWelcome = new JLabel();
	JButton btnFN2 = new JButton();
	JButton btnFN3 = new JButton();
	JButton btnFN4 = new JButton();
	JButton btnFN5 = new JButton();
	JButton btnFN6 = new JButton();
	JButton btnLogout = new JButton();

	JLabel lblMyaccount = new JLabel("My Account", JLabel.CENTER);
	JLabel user = new JLabel(userico, JLabel.CENTER);
	JLabel lblPatientLookup = new JLabel("Patient Lookup", JLabel.CENTER);
	JLabel search = new JLabel(searchico, JLabel.CENTER);
	JLabel lblTimetable = new JLabel("Doctor Timetable", JLabel.CENTER);
	JLabel timetable = new JLabel(timetableico, JLabel.CENTER);
	JLabel lblPatientManagement = new JLabel("<html>User<br/>Management</html>", JLabel.CENTER);
	JLabel edit = new JLabel(editico,JLabel.CENTER);
	JLabel lblAppoitments = new JLabel("Appointments", JLabel.CENTER);
	JLabel clock = new JLabel(clockico, JLabel.CENTER);
	JLabel lblPatientNotes = new JLabel("Patient Notes", JLabel.CENTER);
	JLabel notes = new JLabel(notesico, JLabel.CENTER);

	static String mode = "";
	static String authedID = "";
	String name;
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 20);
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();

	Patient thisPatient = null;
	User thisUser = null;

	//Cretaes a blank constructer to access any methods or global variables such as authedID
	public InteractionMenu()
	{}

	//Creates a constructer that takes a string id, a patient and the resolution of the graphics device
	public InteractionMenu(String id, Patient patient, int[] res)
	{
		//Passes through data necessary to taylor a unique experience for that user, such as thier name as a welcome
		authedID = id;
		thisPatient = patient;
		mode = "Patient";
		name = patient.forename + " " + patient.surname;
		//Runs the nessecary methods to build the interface
		userMode(mode);
		startGUI(res);
	}

	//Creates a constructer for determining what access level members of staff are and the resoltion of the graphics device
	public InteractionMenu(User user, int[] res)
	{
		//This determines what access level that user is by reading the access moifier for the authenticated user
		thisUser = user;
		if(thisUser.mode.equals("SuperUser"))
		{
			mode = "Super user";
		}
		else if(thisUser.mode.equals("Receptionist"))
		{
			mode = "Receptionist";
		}
		else if(thisUser.mode.equals("Doctor"))
		{
			mode = "Doctor";
		}
		name = user.forename + " " + user.surname;
		//Runs the nessecary methods to build the interface
		userMode(mode);
		startGUI(res);
	}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI(int[] res)
	{

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		CREATEInteractionPanel(res);
		this.add(InteractionPanel);
		this.setTitle("Main Menu");
		this.setSize(556,403);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setVisible(true);
		this.setResizable(false);
		InteractionPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.isUndecorated();
		
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEInteractionPanel(int[] res)
	{
		int width = res[0];
		int height = res[1];

		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		banner0.setLocation(0,0);
		banner0.setSize(res[0],100);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		InteractionPanel.add(banner0);

		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		InteractionPanel.add(banner1);
		
		if(mode.equals("Receptionist"))
		{
			btnFN1.setLocation(widthMid-225,hightMid-200);
		}
		else
		{
			btnFN1.setLocation(widthMid-400,hightMid-200);
		}
		btnFN1.setSize(200,200);
		btnFN1.addActionListener(this);
		btnFN1.setLayout(new GridLayout(2,1));
		lblAppoitments.setFont(standardFont);
		lblAppoitments.setSize(200,18);
		clock.setHorizontalAlignment(JButton.CENTER);
		clock.setVerticalAlignment(JButton.BOTTOM);
		btnFN1.add(clock);btnFN1.add(lblAppoitments);
		btnFN1.setContentAreaFilled(false);
		btnFN1.setBorderPainted(true);
		btnFN1.setHorizontalAlignment(JButton.CENTER);
		InteractionPanel.add(btnFN1);

		lblWelcome.setLocation(widthMid-250,hightMid-300);
		lblWelcome.setSize(500,50);
		lblWelcome.setOpaque(false);
		lblWelcome.setHorizontalAlignment(JLabel.CENTER);
		lblWelcome.setFont(headingFont);
		lblWelcome.setText("Welcome back, " + name);
		InteractionPanel.add(lblWelcome);

		if(mode.equals("Doctor"))
		{
			btnFN2.setLocation(widthMid-225,hightMid-200);
		}
		else
		{
			btnFN2.setLocation(widthMid-100,hightMid-200);
		}
		btnFN2.setSize(200,200);
		btnFN2.addActionListener(this);
		btnFN2.setLayout(new GridLayout(2,1));
		lblPatientNotes.setFont(standardFont);
		lblPatientNotes.setSize(200,18);
		notes.setHorizontalAlignment(JButton.CENTER);
		notes.setVerticalAlignment(JButton.BOTTOM);
		btnFN2.add(notes);btnFN2.add(lblPatientNotes);
		btnFN2.setContentAreaFilled(false);
		btnFN2.setBorderPainted(true);
		btnFN2.setHorizontalAlignment(JButton.CENTER);
		InteractionPanel.add(btnFN2);

		btnFN3.setLocation(widthMid-400,hightMid+25);
		btnFN3.setSize(200,200);
		btnFN3.addActionListener(this);
		btnFN3.setLayout(new GridLayout(2,1));
		lblPatientManagement.setFont(standardFont);
		lblPatientManagement.setSize(200,18);
		edit.setHorizontalAlignment(JButton.CENTER);
		edit.setVerticalAlignment(JButton.BOTTOM);
		btnFN3.add(edit);btnFN3.add(lblPatientManagement);
		btnFN3.setContentAreaFilled(false);
		btnFN3.setBorderPainted(true);
		btnFN3.setHorizontalAlignment(JButton.CENTER);
		InteractionPanel.add(btnFN3);

		if(mode.equals("Doctor"))
		{
			btnFN4.setLocation(widthMid-225,hightMid+25);
		}
		else
		{
			btnFN4.setLocation(widthMid-100,hightMid+25);
		}
		btnFN4.setSize(200,200);
		btnFN4.addActionListener(this);
		btnFN4.setHorizontalAlignment(JButton.CENTER);
		btnFN4.setLayout(new GridLayout(2,1));
		lblPatientLookup.setFont(standardFont);
		lblPatientLookup.setSize(200,18);
		search.setHorizontalAlignment(JButton.CENTER);
		search.setVerticalAlignment(JButton.BOTTOM);
		btnFN4.add(search);btnFN4.add(lblPatientLookup);
		btnFN4.setContentAreaFilled(false);
		btnFN4.setBorderPainted(true);
		InteractionPanel.add(btnFN4);

		if(mode.equals("Receptionist"))
		{
			btnFN5.setLocation(widthMid+25,hightMid-200);
		}
		else if(mode.equals("Doctor"))
		{
			btnFN5.setLocation(widthMid+25,hightMid-200);
		}
		else
		{
			btnFN5.setLocation(widthMid+200,hightMid-200);
		}
		btnFN5.setSize(200,200);
		btnFN5.addActionListener(this);
		btnFN5.setLayout(new GridLayout(2,1));
		lblMyaccount.setFont(standardFont);
		lblMyaccount.setSize(200,18);
		user.setHorizontalAlignment(JButton.CENTER);
		user.setVerticalAlignment(JButton.BOTTOM);
		btnFN5.add(user);btnFN5.add(lblMyaccount);
		btnFN5.setContentAreaFilled(false);
		btnFN5.setBorderPainted(true);
		InteractionPanel.add(btnFN5);

		if(mode.equals("Doctor"))
		{
			btnFN6.setLocation(widthMid+25,hightMid+25);
		}
		else
		{
			btnFN6.setLocation(widthMid+200,hightMid+25);
		}
		btnFN6.setSize(200,200);
		btnFN6.addActionListener(this);
		btnFN6.setOpaque(false);
		btnFN6.setHorizontalAlignment(JButton.CENTER);
		btnFN6.setLayout(new GridLayout(2,1));
		lblTimetable.setFont(standardFont);
		lblTimetable.setSize(200,18);
		timetable.setHorizontalAlignment(JButton.CENTER);
		timetable.setVerticalAlignment(JButton.BOTTOM);
		btnFN6.add(timetable);btnFN6.add(lblTimetable);
		btnFN6.setContentAreaFilled(false);
		btnFN6.setBorderPainted(true);
		InteractionPanel.add(btnFN6);

		btnLogout.setLocation(widthMid+200,hightMid-300);
		btnLogout.setSize(200,50);
		btnLogout.addActionListener(this);
		btnLogout.setOpaque(false);
		btnLogout.setFont(standardFont);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setText("Log out");
		InteractionPanel.add(btnLogout);
	}

	//This is a method that allows the authenticated users mode to be accessed throughout the system
	public static String getMode()
	{
		return mode;
	}

	//This is a method that allows the authenticated users id to be accessed throughout the system
	public static String getAuthedID()
	{
		return authedID;
	}

	//Performs actions when the interface is ineracted with
	public void actionPerformed(ActionEvent e)
	{
		//This section of code determines what buttons do when they are pressed, this changes by usergroup so the same 6 buttons have 9 different functions connected to them
		if(e.getSource()==btnFN1)
		{
			if(mode.contains("Receptionist") == true||mode.contains("Super user")== true)
			{
				AppointmentCreationTool apptCreate = new AppointmentCreationTool();
			}
			if(mode.equals("Patient")== true )
			{
				AppointmentsView apptView = new AppointmentsView();
				apptView.startGUI();
			}
		}
		if(e.getSource()==btnFN2)
		{
			if(mode.contains("Doctor") == true|| mode.contains("Super user")== true)
			{
				PatientNotesViewerCreator noteCreate = new PatientNotesViewerCreator();
			}

			if(mode.contains("Patient")== true)
			{
				ViewNotesPatient viewNotePat = new ViewNotesPatient();
				viewNotePat.startGUI();
			}
		}
		if(e.getSource()==btnFN3)
		{
			UserCreationTool create = new UserCreationTool();
			create.startGUI();
		}
		if(e.getSource()==btnFN4)
		{
			PatientLookup patLook = new PatientLookup();
			patLook.startGUI();
		}
		if(e.getSource()==btnFN5)
		{
			if(mode.equals("Patient"))
			{
				PatientDetailsView patDetails = new PatientDetailsView(thisPatient);
			}
			else
			{
				PatientDetailsView patDetails = new PatientDetailsView(thisUser);
			}
		}
		if(e.getSource()==btnFN6)
		{
			DoctorTimetable docTime = new DoctorTimetable();
		}
		if(e.getSource()==btnLogout)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?\r\nAny unsaved work will be lost", "Log out", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				this.dispose();
				MainMenuGUI mm = new MainMenuGUI();
			}
		}
	}

	//This method etermines which buttons should be visible to the user depending on what access level they have, eg.Receptionists cant have access to doctor notes
	public void userMode(String mode)
	{
		if(mode.contains("Super user"))
		{
			btnFN1.setVisible(true);
			btnFN2.setVisible(true);
			btnFN3.setVisible(true);
			btnFN4.setVisible(true);
			btnFN5.setVisible(true);
			btnFN6.setVisible(true);
		}
		else if(mode.contains("Doctor"))
		{
			btnFN1.setVisible(false);
			btnFN4.setVisible(true);
			btnFN5.setVisible(true);
			btnFN6.setVisible(true);
			btnFN2.setVisible(true);
			btnFN3.setVisible(false);
		}
		else if(mode.contains("Patient"))
		{
			btnFN1.setVisible(true);
			btnFN2.setVisible(true);
			btnFN5.setVisible(true);
			btnFN3.setVisible(false);
			btnFN4.setVisible(false);
			btnFN6.setVisible(false);
		}
		else if(mode.contains("Receptionist"))
		{
			btnFN2.setVisible(false);
			btnFN3.setVisible(true);
			btnFN4.setVisible(true);
			btnFN5.setVisible(true);
			btnFN1.setVisible(true);
			btnFN6.setVisible(true);
		}
	}
}  

/*-This screen allows users and patients to view any details held about them, this is in accordance
with the data protection act that states that any user in a system has the right to view data held by 
an organisation.*/
class PatientDetailsView extends JFrame 
{
	//Sets up components and objects aswell as global variables for use in this class
	PatientList patList = new PatientList();

	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 18);
	Font headingFont2 = new Font("MonoSpaced", Font.BOLD, 20);

	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	JPanel PatientDetailsViewPanel = new JPanel(null); 
	JLabel lblSystemDetails = new JLabel();
	JLabel lblPersonalDetails = new JLabel();
	JLabel lblContactInfo = new JLabel();
	JLabel lblID = new JLabel();
	JLabel lblForename = new JLabel();
	JLabel lblSurname = new JLabel();
	JLabel lblDOB = new JLabel();
	JLabel lblTelephoneNumber = new JLabel();
	JLabel lblHouseNumber = new JLabel();
	JLabel lblPostcode = new JLabel();
	JLabel lblUsername = new JLabel();
	JLabel lblUserID = new JLabel();
	JLabel lblUserForename = new JLabel();
	JLabel lblUserSurname = new JLabel();
	JLabel lblUserDOB = new JLabel();
	JLabel lblUserTelephoneNumber = new JLabel();
	JLabel lblUserHouseNumber = new JLabel();
	JLabel lblUserPostcode = new JLabel();
	JLabel lblUserUsername = new JLabel();

	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();

	Patient signedInPatient = null;
	User signedInUser = null;

	CaesarCipher cc = new CaesarCipher();

	//Creates a constructor that takes a user
	public PatientDetailsView(User user)
	{
		//passes the signed in user to a global variable
		signedInUser = user;
		try
		{
			signedInPatient = (Patient) user;	//Tries to determine if the user is a patient or memeber of staff by casting it to a patient
		}
		catch(Exception ex)
		{
			//If the try fails then the user is a member of staff 
		}
		startGUI();//Starts the GUI
		if(user.mode.equals("Patient"))//If the user is a patient then 
		{
			populateDataPatient();//Create all the labels nessecary for patient data
		}
		else
		{
			populateDataUser();//Create all the labels nessecary for staff data
		}
	}

	//Creates a constructor tha takes a ID 
	public PatientDetailsView(String idOfSelection)
	{
		signedInPatient = patList.returnPatFromId(idOfSelection);//Gets the patient data from the Array using the ID
		startGUI();//Starts the GUI 
		populateDataPatient();//Populates the feilds with the patient data
	}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		int[] res = getResolution();
		int width = res[0];
		int height = res[1];
		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATEPatientDetailsViewPanel();
		this.add(PatientDetailsViewPanel);
		this.setTitle("My account");
		this.setSize(548,414);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setResizable(false);
		this.setVisible(true);
		PatientDetailsViewPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEPatientDetailsViewPanel()
	{
		int[] res = getResolution();

		int width = res[0];
		int height = res[1];

		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		banner0.setLocation(0,0);
		banner0.setSize(res[0],100);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		PatientDetailsViewPanel.add(banner0);

		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		PatientDetailsViewPanel.add(banner1);

		lblSystemDetails.setLocation(widthMid-300,hightMid-300);
		lblSystemDetails.setSize(600,50);
		lblSystemDetails.setText("System Details___________________________________");
		lblSystemDetails.setFont(headingFont2);
		PatientDetailsViewPanel.add(lblSystemDetails);

		lblPersonalDetails.setLocation(widthMid-300,hightMid-180);
		lblPersonalDetails.setSize(600,50);
		lblPersonalDetails.setText("Personal Details_________________________________");
		lblPersonalDetails.setFont(headingFont2);
		PatientDetailsViewPanel.add(lblPersonalDetails);

		lblContactInfo.setLocation(widthMid-300,hightMid+10);
		lblContactInfo.setSize(600,50);
		lblContactInfo.setText("Contact Details__________________________________");
		lblContactInfo.setFont(headingFont2);
		PatientDetailsViewPanel.add(lblContactInfo);		

		lblUsername.setLocation(widthMid-255,hightMid-240);
		lblUsername.setSize(100,50);
		lblUsername.setOpaque(false);
		lblUsername.setText("Username:");
		lblUsername.setFont(headingFont);
		PatientDetailsViewPanel.add(lblUsername);

		lblUserUsername.setLocation(widthMid-150,hightMid-240);
		lblUserUsername.setSize(200,50);
		lblUserUsername.setOpaque(false);
		lblUserUsername.setText("Dummy");
		lblUserUsername.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserUsername);

		lblID.setLocation(widthMid-255,hightMid-240);
		lblID.setSize(100,50);
		lblID.setOpaque(false);
		lblID.setText("ID:");
		lblID.setFont(headingFont);
		PatientDetailsViewPanel.add(lblID);

		lblUserID.setLocation(widthMid-190,hightMid-240);
		lblUserID.setSize(50,50);
		lblID.setOpaque(false);
		lblUserID.setText("Dummy");
		lblUserID.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserID);

		lblForename.setLocation(widthMid-255,hightMid-140);
		lblForename.setSize(100,50);
		lblForename.setOpaque(false);
		lblForename.setText("Forename:");
		lblForename.setFont(headingFont);
		PatientDetailsViewPanel.add(lblForename);

		lblUserForename.setLocation(widthMid-150,hightMid-140);
		lblUserForename.setSize(150,50);
		lblUserForename.setOpaque(false);
		lblUserForename.setText("Dummy");
		lblUserForename.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserForename);

		lblSurname.setLocation(widthMid+50,hightMid-140);
		lblSurname.setSize(100,50);
		lblSurname.setOpaque(false);
		lblSurname.setText("Surname:");
		lblSurname.setFont(headingFont);
		PatientDetailsViewPanel.add(lblSurname);

		lblUserSurname.setLocation(widthMid+150,hightMid-140);
		lblUserSurname.setSize(150,50);
		lblUserSurname.setOpaque(false);
		lblUserSurname.setText("Dummy");
		lblUserSurname.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserSurname);

		lblDOB.setLocation(widthMid-255,hightMid-80);
		lblDOB.setSize(100,50);
		lblDOB.setOpaque(false);
		lblDOB.setText("D.o.b:");
		lblDOB.setFont(headingFont);
		PatientDetailsViewPanel.add(lblDOB);

		lblUserDOB.setLocation(widthMid-170,hightMid-80);
		lblUserDOB.setSize(150,50);
		lblUserDOB.setOpaque(false);
		lblUserDOB.setText("Dummy");
		lblUserDOB.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserDOB);

		lblTelephoneNumber.setLocation(widthMid-255,hightMid+60);
		lblTelephoneNumber.setSize(200,50);
		lblTelephoneNumber.setOpaque(false);
		lblTelephoneNumber.setText("Telephone number:");
		lblTelephoneNumber.setFont(headingFont);
		PatientDetailsViewPanel.add(lblTelephoneNumber);

		lblUserTelephoneNumber.setLocation(widthMid-50,hightMid+60);
		lblUserTelephoneNumber.setSize(200,50);
		lblUserTelephoneNumber.setOpaque(false);
		lblUserTelephoneNumber.setText("Dummy");
		lblUserTelephoneNumber.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserTelephoneNumber);

		lblHouseNumber.setLocation(widthMid-255,hightMid+110);
		lblHouseNumber.setSize(150,50);
		lblHouseNumber.setOpaque(false);
		lblHouseNumber.setText("House number:");
		lblHouseNumber.setFont(headingFont);
		PatientDetailsViewPanel.add(lblHouseNumber);

		lblUserHouseNumber.setLocation(widthMid-90,hightMid+110);
		lblUserHouseNumber.setSize(89,50);
		lblUserHouseNumber.setOpaque(false);
		lblUserHouseNumber.setText("Dummy");
		lblUserHouseNumber.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserHouseNumber);

		lblPostcode.setLocation(widthMid+50,hightMid+110);
		lblPostcode.setSize(100,50);
		lblPostcode.setOpaque(false);
		lblPostcode.setText("Postcode:");
		lblPostcode.setFont(headingFont);
		PatientDetailsViewPanel.add(lblPostcode);

		lblUserPostcode.setLocation(widthMid+150,hightMid+110);
		lblUserPostcode.setSize(100,50);
		lblUserPostcode.setOpaque(false);
		lblUserPostcode.setText("Dummy");
		lblUserPostcode.setFont(standardFont);
		PatientDetailsViewPanel.add(lblUserPostcode);
	}

	//This method gets the default monitor resolution and returns the resolution as a integer array in the form width,hight
	public int[] getResolution()
	{
		int[] res = new int[2];//makes a 2 term array
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();//gets the local graphics environment through an object of GraphicsDevice
		res[0] = gd.getDisplayMode().getWidth();//sets the first term of the array as the width
		res[1] = gd.getDisplayMode().getHeight();//sets the second term of the array as the hight
		return res;
	}

	//Method that populates the fields with data from the signed in patient
	public void populateDataPatient()
	{
		lblUserID.setText(signedInPatient.patientID+"");
		lblUserForename.setText(signedInPatient.forename);
		lblUserSurname.setText(signedInPatient.surname);
		lblUserDOB.setText(signedInPatient.dob);
		lblUserPostcode.setText(signedInPatient.postcode);
		lblUserHouseNumber.setText(signedInPatient.houseNumber+"");
		lblUserTelephoneNumber.setText(signedInPatient.telephoneNumber);
		lblUserUsername.setVisible(false);
		lblUsername.setVisible(false);
	}

	//Method that populates the fields with data from the signed in member of staff
	public void populateDataUser()
	{
		lblUserID.setVisible(false);
		lblID.setVisible(false);
		lblUserForename.setText(signedInUser.forename);
		lblUserSurname.setText(signedInUser.surname);
		lblUserDOB.setText(signedInUser.dob);
		lblUserPostcode.setVisible(false);
		lblPostcode.setVisible(false);
		lblUserHouseNumber.setVisible(false);
		lblHouseNumber.setVisible(false);
		lblUserTelephoneNumber.setText(signedInUser.contactNumber);
		lblUserUsername.setText(signedInUser.username);
	}
}  

/*-With this feature, receptionists, super users and doctors can all search the name of any patient in
the system to create notes or appointments and also view thier details.*/
class PatientLookup extends JFrame implements ActionListener, KeyListener, MouseListener
{
	//Sets up components and objects aswell as global variables for use in this class
	PatientList patList = new PatientList();

	JPanel LookupPanel = new JPanel(null); //layout
 	JLabel lblInstructions = new JLabel();
	JTextField tfSearchTerm = new JTextField();
	JButton btnSearch = new JButton();
	JButton btnApptORNote = new JButton();
	JButton btnViewDetails = new JButton();
	JButton btnEdit = new JButton();
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 20);
	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList lstPats = new JList(listModel);
	JScrollPane resultsScroll = new JScrollPane(lstPats);
	String patientName;
	String idOfSelection;
	InteractionMenu inter = new InteractionMenu();

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATELookupPanel();
		this.add(LookupPanel);
		this.setTitle("Patient Lookup");
		this.setSize(504,254);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setResizable(false);
		this.setVisible(true);
		LookupPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
		String mode = inter.getMode();

		if(mode.equals("Super user")||mode.equals("Doctor"))
		{
			btnApptORNote.setText("New note");
		}	
		else
		{
			btnApptORNote.setText("New appointment");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATELookupPanel()
	{
		int[] res = getResolution();

		int width = res[0];
		int height = res[1];

		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		banner0.setLocation(0,0);
		banner0.setSize(res[0],100);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		LookupPanel.add(banner0);

		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		LookupPanel.add(banner1);

		lblInstructions.setLocation(widthMid-350,hightMid-200);
		lblInstructions.setSize(700,50);
		lblInstructions.setOpaque(false);
		lblInstructions.setFont(headingFont);
		lblInstructions.setVerticalAlignment(JLabel.CENTER);
		lblInstructions.setText("Enter the name of the patient that you wish to lookup:");
		LookupPanel.add(lblInstructions);

		tfSearchTerm.setLocation(widthMid-260,hightMid-140);
		tfSearchTerm.setSize(400,50);
		tfSearchTerm.setText("");
		tfSearchTerm.setColumns(10);
		tfSearchTerm.addKeyListener(this);
		tfSearchTerm.addMouseListener(this);
		tfSearchTerm.setFont(standardFont);
		LookupPanel.add(tfSearchTerm);

		btnSearch.setLocation(widthMid+200,hightMid-140);
		btnSearch.setSize(100,50);
		btnSearch.addActionListener(this);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(true);
		btnSearch.setText("Search");
		LookupPanel.add(btnSearch);

		btnApptORNote.setLocation(widthMid+175,hightMid-50);
		btnApptORNote.setSize(200,50);
		btnApptORNote.addActionListener(this);
		btnApptORNote.setContentAreaFilled(false);
		btnApptORNote.setFont(standardFont);
		btnApptORNote.setVisible(false);
		btnApptORNote.setBorderPainted(true);
		LookupPanel.add(btnApptORNote);

		btnViewDetails.setLocation(widthMid+175,hightMid+10);
		btnViewDetails.setSize(200,50);
		btnViewDetails.addActionListener(this);
		btnViewDetails.setContentAreaFilled(false);
		btnViewDetails.setFont(standardFont);
		btnViewDetails.setText("View details");
		btnViewDetails.setVisible(false);
		btnViewDetails.setBorderPainted(true);
		LookupPanel.add(btnViewDetails);

		btnEdit.setLocation(widthMid+175,hightMid+70);
		btnEdit.setSize(200,50);
		btnEdit.addActionListener(this);
		btnEdit.setContentAreaFilled(false);
		btnEdit.setFont(standardFont);
		btnEdit.setText("Edit details");
		btnEdit.setVisible(false);
		btnEdit.setBorderPainted(true);
		LookupPanel.add(btnEdit);
		
		lstPats.setFont(standardFont);
		lstPats.addMouseListener(this);

		resultsScroll.setLocation(widthMid-260,hightMid-70);
		resultsScroll.setSize(400,200);
		resultsScroll.setVisible(false);
		LookupPanel.add(resultsScroll);
	}

	//This is the algorithem for my linear search
	public void search()
	{
		//It gets a patient list 
		PatientList patList = new PatientList();
		String patDetail = tfSearchTerm.getText();//gets the data from the search term 
		Patient[] foundPatients = new Patient[999];//Creates a blank array for the number of patients

		if(patDetail.length()>=2)//Validation to make sure the user enters a search term that wont return a large number of users by being able to enter a single charater
		{
			foundPatients = patList.findID(patDetail);//Populates the patient array with data returned from the method findID

			if(foundPatients[0] == null)//If the array is blank
			{
				//Return an error message and empty the list
				listModel.removeAllElements();
				resultsScroll.setVisible(false);	
				JOptionPane.showMessageDialog(null,"Unable to find patients");
			}
			else
			{
				//Empty the list and populates the list with data of the found users
				listModel.removeAllElements();
				resultsScroll.setVisible(true);
				for(int i=0; foundPatients[i] != null; i++)
				{
					//formats the found  data in the forem: forename surname - id
					String data = foundPatients[i].forename + " " + foundPatients[i].surname + " - " + foundPatients[i].patientID;
					listModel.addElement(data);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Please enter a valid search term", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		//performs the search method when the search button is clicked
		if(e.getSource()==btnSearch)
		{
			try
			{
				search();	
			}
			catch(Exception ex)
			{

			}
		}
		//Allows the user to use the search data to book appointments, create patient notes or view and edit user details
		if(e.getSource()==btnApptORNote)
		{
			
			String mode = inter.getMode();
			if(mode.equals("Super user")||mode.equals("Doctor"))
			{
				btnApptORNote.setVisible(true);
				PatientNotesViewerCreator view = new PatientNotesViewerCreator(idOfSelection);
			}
			else
			{	
				btnApptORNote.setVisible(true);
				AppointmentCreationTool apptCreate = new AppointmentCreationTool(idOfSelection);
			}
		}
		if(e.getSource()==btnViewDetails)
		{
			PatientDetailsView patDetailsView = new PatientDetailsView(idOfSelection);
		}
		if(e.getSource()==btnEdit)
		{
			UserCreationTool ucTool = new UserCreationTool(idOfSelection);
		}
	}

	public void keyTyped(KeyEvent e)
	{}

	public void keyPressed(KeyEvent e)
	{
		//If the enter key is pressed then it will perform the search method
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			try
			{
				search();	
			}
			catch(Exception ex)
			{
				
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{}

	public void mouseClicked(MouseEvent e) 
	{
		//when the data is clicked in the list, this makes the different buttons for the different choices of data manipulation appear
		if(e.getSource()==lstPats)
		{
			btnApptORNote.setVisible(true);
			btnViewDetails.setVisible(true);
			String mode = inter.getMode();

			if(mode.equals("Super user")||mode.equals("Doctor"))
			{
				btnEdit.setVisible(false);
			}	
			else
			{
				btnEdit.setVisible(true);
			}
			
			String selectedItem = lstPats.getSelectedValue().toString();
			String[] brokenSelectedItem = selectedItem.split(" ");
			idOfSelection = brokenSelectedItem[3];
		}
		//Empties and hides the list when a different value is being searched
		if(e.getSource()==tfSearchTerm)
		{
			btnApptORNote.setVisible(false);
			btnViewDetails.setVisible(false);
			btnEdit.setVisible(false);
			resultsScroll.setVisible(false);
		}
	}

	public void mouseEntered(MouseEvent e)
	{}
	

	public void mouseExited(MouseEvent e)
	{}
	

	public void mouseReleased(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}

	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}
}  

/*-This utility allows the creation of single and multipul appointments for use by receptionists. They 
can also edit these appointments and delete appointments.*/
class AppointmentCreationTool extends JFrame implements ActionListener, MouseListener
{
	//Sets up components and objects aswell as global variables for use in this class
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 14);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 16);

	int[] res = getResolution();
	int width = res[0];
	int height = res[1];
	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	Appointment app = new Appointment();
	AppointmentList apptList = new AppointmentList();
	PatientList patList = new PatientList();
	Doctor doc = new Doctor();	
	String filename = "Appointment Master.txt";
	JPanel SingleAppointmentPanel = new JPanel(null); //layout
	JPanel MultipleAppoitmentPanel = new JPanel(null);
	JPanel EditPanel = new JPanel(null); 

	JTabbedPane tabView = new JTabbedPane();
	ImageIcon user = new ImageIcon("Resources/user_20x20.png");
	ImageIcon users = new ImageIcon("Resources/users_20x20.png");
	ImageIcon edit = new ImageIcon("Resources/edit_20x20.png");

	ImageIcon icon = new ImageIcon("Resources/NHS_200x80.png");
	JLabel banner0 = new JLabel(icon, JLabel.CENTER);
	JLabel banner1 = new JLabel(icon, JLabel.CENTER);
	JLabel banner2 = new JLabel(icon, JLabel.CENTER);
	JTextField tfPatientID = new JTextField();
	String[] cbHoursFrom_data= app.hoursTime;
	JComboBox cbHoursFrom = new JComboBox(cbHoursFrom_data);
	JLabel lblColon = new JLabel();
	JLabel lblTimefrom = new JLabel();
	String[] cbMinsFrom_data= app.minTime;
	JComboBox cbMinsFrom = new JComboBox(cbMinsFrom_data);
	String[] cbMinsTo_data= app.minTime;
	JComboBox cbMinsTo = new JComboBox(cbMinsTo_data);
	JLabel lblColon2 = new JLabel();
	String[] cbHoursTo_data= app.hoursTime;
	JComboBox cbHoursTo = new JComboBox(cbHoursTo_data);
	JLabel lblAppointmentEnd = new JLabel();
	JButton btnSave = new JButton();
	JLabel lblDate = new JLabel();
	JTextField tfDate = new JTextField();
	String[] lblDrName_data= doc.populate();
	JComboBox cbDrName = new JComboBox(lblDrName_data);
	JLabel lblDoctor = new JLabel();
	JRadioButton rbtnBookable  = new JRadioButton();
	JRadioButton rbtnAssigned = new JRadioButton();

	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DefaultListModel<Appointment> listModel = new DefaultListModel<>();

	String[] cbDuration_data={"","10","15"};
	JComboBox cbDuration = new JComboBox(cbDuration_data);
	JLabel lblDur = new JLabel();
	JLabel lblBetween = new JLabel();
	JLabel lblAnd = new JLabel();
	JLabel lblColon3 = new JLabel();
	String[] cbMultiHourFrom_data=app.hoursTime;
	JComboBox cbMultiHourFrom = new JComboBox(cbMultiHourFrom_data);
	JLabel lblMultiMinFrom = new JLabel("00");
	String[] cbMultiHourTo_data=app.hoursTime;
	JComboBox cbMultiHourTo = new JComboBox(cbMultiHourTo_data);
	JLabel lblColon4 = new JLabel();
	JLabel lblMultiMinTo = new JLabel("00");
	JComboBox cbMultiDr = new JComboBox(lblDrName_data);
	JLabel lblDoc = new JLabel();
	JList lstApptsGen = new JList(listModel);
	JButton btnGenerate = new JButton();
	JButton btnMultiSave = new JButton();

	JScrollPane generateScroll = new JScrollPane(lstApptsGen);

	String[] tbAppointmentColumns = {" Start time "," End time ", " Date ", " Status ", " Doctor name "};
	Object[][] tbData = {{}};
	DefaultTableModel model = new DefaultTableModel(tbData, tbAppointmentColumns){
		public boolean isCellEditable(int rowIndex, int mColIndex){
			return false;
		}
	};
	JTable tbAppointments = new JTable(model);
	JScrollPane scrollTimeTable = new JScrollPane(tbAppointments);
	JButton btnEdit = new JButton();
	JButton btnDelete = new JButton();
	String tableData="";

	//Creates an constructor that takes an id for appointment booking
	public AppointmentCreationTool(String idToBook)
	{
		startGUI();
		tabView.setSelectedIndex(0);
		rbtnBookable.setSelected(false);
		rbtnAssigned.setSelected(true);
		tfPatientID.setVisible(true);
		tfPatientID.setText(idToBook);
	}
	//Creates an constructor that builds the gui
	public AppointmentCreationTool()
	{
		startGUI();
	}
	
	//Creates an constructor that takes a boolean but is blank for when i want to access the class without running the start gui method
	public AppointmentCreationTool(boolean irrevelant)
	{

	}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATESingleAppointmentPanel();
		CREATEMultipleAppointmentPanel();
		CREATEEditPanel();
		this.add(tabView);
		tabView.addTab("Single", user, SingleAppointmentPanel, "Create a single appointment");
		tabView.addTab("Multiple", users, MultipleAppoitmentPanel, "Create many appointments");
		tabView.addTab("Edit Appoitments", edit, EditPanel,"Edit, modify and delete appointments");
		tabView.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if(tabView.getSelectedIndex() == 2)
            {
            	populateApptTable();
            }
        }
    	});
		this.setTitle("Appointment Creator");
		this.setSize(560,400);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setVisible(true);
		this.setResizable(false);
		SingleAppointmentPanel.setBackground(Color.decode("#ffffff"));
		MultipleAppoitmentPanel.setBackground(Color.decode("#ffffff"));
		EditPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.isUndecorated();
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATESingleAppointmentPanel()
	{
		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		SingleAppointmentPanel.add(banner1);

		tfPatientID.setLocation(widthMid-200,hightMid-300);
		tfPatientID.setSize(40,50);
		tfPatientID.setText("");
		tfPatientID.setFont(standardFont);
		tfPatientID.setColumns(10);
		tfPatientID.addMouseListener(this);
		tfPatientID.setVisible(false);
		SingleAppointmentPanel.add(tfPatientID);

		cbHoursFrom.setLocation(widthMid-180,hightMid-200);
		cbHoursFrom.setSize(70,50);
		cbHoursFrom.addActionListener(this);
		cbHoursFrom.setFont(standardFont);
		cbHoursFrom.setEditable(false );
		SingleAppointmentPanel.add(cbHoursFrom);

		lblColon.setLocation(widthMid-110,hightMid-200);
		lblColon.setSize(10,50);
		lblColon.setText(":");
		lblColon.setFont(headingFont);
		lblColon.setOpaque(false);
		SingleAppointmentPanel.add(lblColon);

		lblTimefrom.setLocation(widthMid-400,hightMid-200);
		lblTimefrom.setSize(250,50);
		lblTimefrom.setOpaque(false);
		lblTimefrom.setText("Appointment start:");
		lblTimefrom.setFont(headingFont);
		SingleAppointmentPanel.add(lblTimefrom);

		cbMinsFrom.setLocation(widthMid-100,hightMid-200);
		cbMinsFrom.setSize(70,50);
		cbMinsFrom.addActionListener(this);
		cbMinsFrom.setEditable(false);
		cbMinsFrom.setFont(standardFont);
		SingleAppointmentPanel.add(cbMinsFrom);

		cbMinsTo.setLocation(widthMid-100,hightMid-100);
		cbMinsTo.setSize(70,50);
		cbMinsTo.setEditable(false);
		cbMinsTo.setFont(standardFont);
		SingleAppointmentPanel.add(cbMinsTo);

		lblColon2.setLocation(widthMid-110,hightMid-100);
		lblColon2.setSize(10,50);
		lblColon2.setOpaque(false);
		lblColon2.setText(":");
		lblColon2.setFont(headingFont);
		SingleAppointmentPanel.add(lblColon2);

		cbHoursTo.setLocation(widthMid-180,hightMid-100);
		cbHoursTo.setSize(70,50);
		cbHoursTo.setEditable(false);
		cbHoursTo.setFont(standardFont);
		SingleAppointmentPanel.add(cbHoursTo);

		lblAppointmentEnd.setLocation(widthMid-400,hightMid-100);
		lblAppointmentEnd.setSize(250,50);
		lblAppointmentEnd.setOpaque(false);
		lblAppointmentEnd.setText("Appointment finish:");
		lblAppointmentEnd.setFont(headingFont);
		SingleAppointmentPanel.add(lblAppointmentEnd);

		btnSave.setLocation(widthMid-50,hightMid+100);
		btnSave.setSize(100,50);
		btnSave.addActionListener(this);
		btnSave.setContentAreaFilled(false);
		btnSave.setFont(standardFont);
		btnSave.setText("Save");
		SingleAppointmentPanel.add(btnSave);

		lblDate.setLocation(widthMid,hightMid);
		lblDate.setSize(250,50);
		lblDate.setOpaque(false);
		lblDate.setFont(headingFont);
		lblDate.setText("Appointment date:");
		SingleAppointmentPanel.add(lblDate);

		tfDate.setLocation(widthMid+200,hightMid);
		tfDate.setSize(120,50);
		tfDate.setFont(standardFont);
		tfDate.setText("DD-MM-YYYY");
		tfDate.addMouseListener(this);
		tfDate.setColumns(10);
		SingleAppointmentPanel.add(tfDate);

		cbDrName.setLocation(widthMid-300,hightMid);
		cbDrName.setSize(150,50);
		cbDrName.setFont(standardFont);
		cbDrName.setEditable(false);
		SingleAppointmentPanel.add(cbDrName);

		lblDoctor.setLocation(widthMid-400,hightMid);
		lblDoctor.setSize(100,50);
		lblDoctor.setOpaque(false);
		lblDoctor.setText("Doctor:");
		lblDoctor.setFont(headingFont);
		SingleAppointmentPanel.add(lblDoctor);

		rbtnBookable.setLocation(widthMid-400,hightMid-350);
		rbtnBookable.setSize(150,50);
		rbtnBookable.setText("Bookable");
		rbtnBookable.setSelected(false);
		rbtnBookable.setFont(headingFont);
		rbtnBookable.setOpaque(false);
		rbtnBookable.addMouseListener(this);
		SingleAppointmentPanel.add(rbtnBookable);

		rbtnAssigned.setLocation(widthMid-400,hightMid-300);
		rbtnAssigned.setSize(200,50);
		rbtnAssigned.setText("Assign to user");
		rbtnAssigned.setSelected(false);
		rbtnAssigned.setOpaque(false);
		rbtnAssigned.addMouseListener(this);
		rbtnAssigned.setFont(headingFont);
		SingleAppointmentPanel.add(rbtnAssigned);
	}

	public void CREATEMultipleAppointmentPanel()
	{
		banner0.setLocation(0,res[1]-200);
		banner0.setSize(res[0],200);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		MultipleAppoitmentPanel.add(banner0);

		cbDuration.setLocation(widthMid-200,hightMid-300);
		cbDuration.setSize(100,50);
		cbDuration.setEditable(false);
		cbDuration.setFont(standardFont);
		MultipleAppoitmentPanel.add(cbDuration);

		lblDur.setLocation(widthMid-300,hightMid-300);
		lblDur.setSize(100,50);
		lblDur.setOpaque(false);
		lblDur.setText("Duration:");
		lblDur.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblDur);

		lblBetween.setLocation(widthMid-300,hightMid-200);
		lblBetween.setSize(100,50);
		lblBetween.setOpaque(false);
		lblBetween.setText("Between:");
		lblBetween.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblBetween);

		lblAnd.setLocation(widthMid-300,hightMid-100);
		lblAnd.setSize(50,50);
		lblAnd.setOpaque(false);
		lblAnd.setText("And:");
		lblAnd.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblAnd);

		lblColon3.setLocation(widthMid-130,hightMid-200);
		lblColon3.setSize(10,50);
		lblColon3.setOpaque(false);
		lblColon3.setText(":");
		lblColon3.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblColon3);

		cbMultiHourFrom.setLocation(widthMid-200,hightMid-200);
		cbMultiHourFrom.setSize(70,50);
		cbMultiHourFrom.setFont(standardFont);
		MultipleAppoitmentPanel.add(cbMultiHourFrom);

		lblMultiMinFrom.setLocation(widthMid-120,hightMid-200);
		lblMultiMinFrom.setSize(70,50);
		lblMultiMinFrom.setFont(standardFont);
		MultipleAppoitmentPanel.add(lblMultiMinFrom);

		cbMultiHourTo.setLocation(widthMid-200,hightMid-100);
		cbMultiHourTo.setSize(70,50);
		cbMultiHourTo.setEditable(false );
		cbMultiHourTo.setFont(standardFont);
		MultipleAppoitmentPanel.add(cbMultiHourTo);

		lblColon4.setLocation(widthMid-130,hightMid-100);
		lblColon4.setSize(10,50);
		lblColon4.setOpaque(false);
		lblColon4.setText(":");
		lblColon4.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblColon4);

		lblMultiMinTo.setLocation(widthMid-120,hightMid-100);
		lblMultiMinTo.setSize(70,50);
		lblMultiMinTo.setFont(standardFont);
		MultipleAppoitmentPanel.add(lblMultiMinTo);

		cbMultiDr.setLocation(widthMid-150,hightMid+20);
		cbMultiDr.setSize(150,50);
		cbMultiDr.setEditable(false );
		cbMultiDr.setFont(standardFont);
		MultipleAppoitmentPanel.add(cbMultiDr);

		lblDoc.setLocation(widthMid-300,hightMid+20);
		lblDoc.setSize(150,50);
		lblDoc.setOpaque(false);
		lblDoc.setText("Doctor name:");
		lblDoc.setFont(headingFont);
		MultipleAppoitmentPanel.add(lblDoc);

		generateScroll.setLocation(widthMid+100,hightMid-300);
		generateScroll.setSize(300,300);
		lstApptsGen.setFont(standardFont);
		MultipleAppoitmentPanel.add(generateScroll);

		btnGenerate.setLocation(widthMid+100,hightMid+20);
		btnGenerate.setSize(100,50);
		btnGenerate.addActionListener(this);
		btnGenerate.setFont(standardFont);
		btnGenerate.setContentAreaFilled(false);
		btnGenerate.setText("Generate");
		MultipleAppoitmentPanel.add(btnGenerate);

		btnMultiSave.setLocation(widthMid+300,hightMid+20);
		btnMultiSave.setSize(100,50);
		btnMultiSave.addActionListener(this);
		btnMultiSave.setText("Save");
		btnMultiSave.setFont(standardFont);
		btnMultiSave.setContentAreaFilled(false);
		MultipleAppoitmentPanel.add(btnMultiSave);
	}

	public void CREATEEditPanel()
	{
		banner2.setLocation(0,res[1]-200);
		banner2.setSize(res[0],200);
		banner2.setBackground(Color.decode("#005EB8"));
		banner2.setOpaque(true);
		banner2.setVisible(true);
		EditPanel.add(banner2);

		tbAppointments.addMouseListener(this);
		tbAppointments.setFont(standardFont);

		scrollTimeTable.setLocation(widthMid-500,hightMid-350);
		scrollTimeTable.setSize(1000,400);
		scrollTimeTable.setVisible(true);
		EditPanel.add(scrollTimeTable);
		model.setRowCount(0);

		btnEdit.setLocation(widthMid-180,hightMid+100);
		btnEdit.setSize(100,50);
		btnEdit.setFont(standardFont);
		btnEdit.setText("Edit");
		btnEdit.addActionListener(this);
		btnEdit.setContentAreaFilled(false);
		EditPanel.add(btnEdit);

		btnDelete.setLocation(widthMid+80,hightMid+100);
		btnDelete.setSize(100,50);
		btnDelete.setFont(standardFont);
		btnDelete.setText("Delete");
		btnDelete.addActionListener(this);
		btnDelete.setContentAreaFilled(false);
		EditPanel.add(btnDelete);
	}

	//This method populates the table with booked and unbooked appointments
	public void populateApptTable()
	{
		AppointmentList apptList = new AppointmentList();//Creates an istance of patient list
		model.setRowCount(0);//Empties the table model
		Appointment[] arrayAppoitments = apptList.appoitmentsForDay();//Takes all the apppointments in the file

		for(int i=0; arrayAppoitments[i] != null; i++)//Loops through the returned array
		{
			String[] apptToAdd = arrayAppoitments[i].toString().split(" ");//Splits the string at the whitespace

			String[] newData = {apptToAdd[0],apptToAdd[1],apptToAdd[2], apptToAdd[3], apptToAdd[4] + " " + apptToAdd[5]};//Breakes the data by commas for data for the file

			model.addRow(newData);//adds the data to the table
		}
	}

	public void mouseClicked(MouseEvent e) 
	{
		//When the mose clicks the date feild it clears it
		if(e.getSource()==tfDate)
		{
			tfDate.setText("");
		}
		//When the mose clicks the patient id feild it clears it
		if(e.getSource()==tfPatientID)
		{
			tfPatientID.setText("");
		}
		//When the mose clicks the bookable radio feild it unselects the assigned radio button
		if(e.getSource()==rbtnBookable)
		{
           rbtnAssigned.setSelected(false);
           tfPatientID.setVisible(false);
		}
		//When the mose clicks the assigned radio feild it unselects the bookable radio button
		if(e.getSource()==rbtnAssigned)
		{
			rbtnBookable.setSelected(false);
			tfPatientID.setVisible(true);
		}
		//When the appointment table is clicked, it takes the data from that row and makes a string out of it
		if(e.getSource()==tbAppointments)
		{
			int indexRow = tbAppointments.getSelectedRow();

			tableData = tbAppointments.getValueAt(indexRow,0).toString() + " " + tbAppointments.getValueAt(indexRow,1).toString()+ " " + tbAppointments.getValueAt(indexRow,2).toString() + " " + tbAppointments.getValueAt(indexRow,3).toString()+ " " + tbAppointments.getValueAt(indexRow,4).toString();
		}
	}

	public void mouseEntered(MouseEvent e)
	{}
	

	public void mouseExited(MouseEvent e)
	{}
	

	public void mouseReleased(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}

	//Gets the resolution of the graphics device and stores the width and hight as a integer array
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}

	public void actionPerformed(ActionEvent e)
	{
		//When the save button is pressed
		if(e.getSource()==btnSave)
		{
			String id = "";
			String date = tfDate.getText();

			if(rbtnAssigned.isSelected())//And the assigned radio button is clicked
			{
				//Validates each feild
				try
				{
					id= tfPatientID.getText();

					if(id.length() != 3)
					{
						JOptionPane.showMessageDialog(null,"Please enter a valid patient ID");
					}
					else
					{
						if(cbDrName.getSelectedItem().toString() != "" )
						{
							if(cbMinsFrom.getSelectedItem().toString() != ""&& cbHoursFrom.getSelectedItem().toString() != "" && cbHoursTo.getSelectedItem().toString() != "" && cbMinsTo.getSelectedItem().toString() != "")
							{
							
								if(isValid(date))
								{
									if(patList.findPatientId(id))
									{
										//Creates a blank appointment
										Appointment tempAppt = new Appointment();

										//Adds a leading 0 to any single digit times
										if(cbHoursFrom.getSelectedItem().toString().charAt(0) == '8' || cbHoursFrom.getSelectedItem().toString().charAt(0) == '9')
										{
											tempAppt.appointmentStartTime = "0"+cbHoursFrom.getSelectedItem() + "" + cbMinsFrom.getSelectedItem();
										}
										else
										{
											tempAppt.appointmentStartTime = cbHoursFrom.getSelectedItem() + "" + cbMinsFrom.getSelectedItem();
										}
										if(cbHoursTo.getSelectedItem().toString().charAt(0) == '8'|| cbHoursTo.getSelectedItem().toString().charAt(0)== '9')
										{
											tempAppt.appointmentEndTime = "0"+cbHoursTo.getSelectedItem() + "" + cbMinsTo.getSelectedItem();
										}
										else
										{
											tempAppt.appointmentEndTime = cbHoursTo.getSelectedItem() + "" + cbMinsTo.getSelectedItem();
										}
										//adds attributes to the appointment
										tempAppt.date = date;
										tempAppt.patientID = id;
										tempAppt.nameOfDoctor = cbDrName.getSelectedItem().toString();
										
										//Checks if the appointment exists 
										if(apptList.searchForExistingAppt(tempAppt.toString()))
										{
											JOptionPane.showMessageDialog(null, "Appointment already exists, please select a new date or time.");
										}
										else
										{	
											//and if it doesnt then add the appointment to the list and write the list to the file
											apptList.addToList(tempAppt);
											apptList.writeAppointmentToFile();
											//Provides the user with a confirmation message
											JOptionPane.showMessageDialog(null, "Appointment has been created");
										}
									}
									else
									{
										JOptionPane.showMessageDialog(null, "Please enter a valid ID");
									}
								}
								else
								{
									JOptionPane.showMessageDialog(null,"Please select a valid date");
								}	
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Please select a valid time");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a doctor name");
						}
					}
				}
				catch(NumberFormatException exp)
				{
					JOptionPane.showMessageDialog(null,"Please enter a valid patient ID");
				}	
			}

			//Preforms the same process as the lines before exept this time instead of getting data from the text are for a id, it sets the id to 0 meaning that anyone can book it
			if(rbtnBookable.isSelected())
			{
				if(cbDrName.getSelectedItem().toString() != "" )
				{
					if(cbMinsFrom.getSelectedItem().toString() != ""&& cbHoursFrom.getSelectedItem().toString() != "" && cbHoursTo.getSelectedItem().toString() != "" && cbMinsTo.getSelectedItem().toString() != "")
					{
					
						if(isValid(date))
						{
							Appointment tempAppt = new Appointment();

							if(cbHoursFrom.getSelectedItem().toString().charAt(0) == '8' || cbHoursFrom.getSelectedItem().toString().charAt(0) == '9')
							{
								tempAppt.appointmentStartTime = "0"+cbHoursFrom.getSelectedItem().toString() + "" + cbMinsFrom.getSelectedItem().toString();
							}
							else
							{
								tempAppt.appointmentStartTime = cbHoursFrom.getSelectedItem().toString() + "" + cbMinsFrom.getSelectedItem().toString();
							}
							if(cbHoursTo.getSelectedItem().toString().charAt(0) == '8'|| cbHoursTo.getSelectedItem().toString().charAt(0)== '9')
							{
								tempAppt.appointmentEndTime = "0"+cbHoursTo.getSelectedItem().toString() + "" + cbMinsTo.getSelectedItem().toString();
							}
							else
							{
								tempAppt.appointmentEndTime = cbHoursTo.getSelectedItem().toString() + "" + cbMinsTo.getSelectedItem().toString();
							}
							tempAppt.date = date;
							tempAppt.patientID = "0";
							tempAppt.nameOfDoctor = cbDrName.getSelectedItem().toString();

							
							if(apptList.searchForExistingAppt(tempAppt.toString()))
							{
								JOptionPane.showMessageDialog(null, "Appointment already exists, please select a new date or time.");
							}
							else
							{
								apptList.addToList(tempAppt);
								apptList.writeAppointmentToFile();
								JOptionPane.showMessageDialog(null, "Appointment has been created");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a valid date");
						}	
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Please select a valid time");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please select a doctor name");
				}
			}
		}
		//If the hour from combo-box is pressed 
		if(e.getSource()==cbHoursFrom)
		{	
			//takes the index from the hours from and mins from combo-boxes
			int im = cbMinsFrom.getSelectedIndex();
			int ih = cbHoursFrom.getSelectedIndex();
			//sets the hours to index to the same index of the hours from combo-box
			cbHoursTo.setSelectedIndex(ih);

			boolean breaking;
			//If the selected index is within the times when the surgery isnt open
			if(ih==6||ih==12)
			{
				breaking = true;
			}
			else
			{
				breaking = false;
			}

			//Doesnt allow the time to be set during a time when the surgery is closed
			if(ih==5 && im == 4)
			{
				cbHoursTo.setSelectedIndex(6);
				cbMinsTo.setSelectedIndex(1);
				cbHoursTo.setEnabled(false);
				cbMinsTo.setEnabled(false);
			}
			else
			{
				cbHoursTo.setEnabled(true);
				cbMinsTo.setEnabled(true);
			}
			if(ih==12 && im ==4)
			{
				cbHoursTo.setSelectedIndex(6);
				cbMinsTo.setSelectedIndex(1);
				cbHoursTo.setEnabled(false);
				cbMinsTo.setEnabled(false);
			}
			else
			{
				cbHoursTo.setEnabled(true);
				cbMinsTo.setEnabled(true);
			}
			//If the mins from is at the last index it will roll over to the next hour
			if(im==4)
			{
				cbHoursTo.setSelectedIndex(ih+1);
			}
			//If during closing hours
			if(breaking)
			{
				cbHoursFrom.setSelectedIndex(cbMinsFrom.getSelectedIndex()-1);
				cbMinsTo.setSelectedIndex(1);
				cbHoursTo.setSelectedIndex(0);
				cbMinsTo.setSelectedIndex(0);
				JOptionPane.showMessageDialog(null,"Unable to book appooitment for this time.\r\nPlease select a different time.");
			}

		}

		//Takes the value of the index is the last in the combo-box then it rolls over the hour and if not it goes to the next set of minutes 
		if(e.getSource()==cbMinsFrom)
		{
			int im = cbMinsFrom.getSelectedIndex();
			int ih = cbHoursFrom.getSelectedIndex();
			if(im==4)
			{
				cbHoursTo.setSelectedIndex(ih+1);
				cbMinsTo.setSelectedIndex(1);
			}
			else
			{

				cbMinsTo.setSelectedIndex(im+1);
			}
		}

		//When the generate appointments button is pressed it checks each feild has data in it
		if(e.getSource()==btnGenerate)
		{
			if(cbMultiHourFrom.getSelectedItem().toString().equals("")== false&&cbMultiHourTo.getSelectedItem().toString().equals("")== false&&cbMultiDr.getSelectedItem().toString().equals("")== false&&cbDuration.getSelectedItem().toString().equals("")==false)
			{
				generateAppoitments();//runs the generate appointmens method
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please enter data in all feilds");//Returns an error if data isnt found one of the feilds
			}
		}

		//When the multi save button is pressed 
		if(e.getSource()==btnMultiSave)
		{
			boolean exists = false;//set a boolean of name exists to false

			for(int i=0; i<listModel.size();i++)//loop for as many appointments there are in the generated list
			{
				Appointment apptToWrite = listModel.get(i);//gets the appointment at the point i in the list
				

				if(apptList.searchForExistingAppt(apptToWrite.toString()))//validates it doesnt exist
				{
					JOptionPane.showMessageDialog(null, "Appointment already exists, at: \r\n" + apptToWrite.toString() + "\r\nConsequently your appointments haven't been created.\r\nPlease enter new details to avoid confilcts.");
					exists = true;
					break;
				}
				else
				{
					apptList.addToList(apptToWrite);// if it doesnt it writes it to the list
				}
			}
			if(exists==false)
			{
				apptList.writeAppointmentToFile();//writes the list to the file
				JOptionPane.showMessageDialog(null,"Appoitments have been created");//Returns a confirmation message
			}
		}
		//when the edit button is pressed
		if(e.getSource()==btnEdit)
		{
			//It checks a row has bee selected
			if(tableData.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Please select an appointment");
			}
			else
			{
				//Splits an appointment string up and reads it in to an appointment object and assigns the attributes of that appointment with the string data
				Appointment temp = new Appointment();

				String[] splitData = tableData.split(" ");

				temp.appointmentStartTime = splitData[0];
				temp.appointmentEndTime = splitData[1];
				temp.date = splitData[2];
				temp.patientID = splitData[3];
				temp.nameOfDoctor = splitData[4]+ " "+ splitData[5];

				int result = apptList.searchForExistingAppt(temp);//gets the index of the appointment to edit 

				EditAppointmentView edit = new EditAppointmentView(temp,result);//Passes the appointment and the index to the edit dialogue
			}
		}
		//If the delete button is pressed
		if(e.getSource()==btnDelete)
		{
			//sees if an appointment has been selected
			if(tableData.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Please select an appointment");
			}
			else
			{
				//Confirms the user wants to delete the appointment and warns of the implications 
				int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment.\r\nNo user will be able to book this appointment\r\nand current bookings will be canceled?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(answer==0)
				{
					//Takes the appointment string, splits it up and reads it in to an appointment object with the attributes being the split appointment string data
					Appointment temp = new Appointment();

					String[] splitData = tableData.split(" ");

					temp.appointmentStartTime = splitData[0];
					temp.appointmentEndTime = splitData[1];
					temp.date = splitData[2];
					temp.patientID = splitData[3];
					temp.nameOfDoctor = splitData[4]+ " "+ splitData[5];

					if(apptList.deleteAppointment(temp))//Sees if the appointment is deleted sucessfully
					{
						JOptionPane.showMessageDialog(null,"Sucessfully deleted appointment");
						fireChange();
					}
					else//Warns the user if unable to delete appointment
					{
						JOptionPane.showMessageDialog(null,"Unable to delete appointment. Appointment doesn't exist");
					}
				}
				else
				{

				}
			}
			
		}
	}
	//Prompts a change and update of the table data 
	public void fireChange()
	{
		populateApptTable();//Repopulates the data
		model.fireTableDataChanged();//updates the table model
		EditPanel.repaint();//repaints the panel
	}

	//checks if the date is given in a valid format
	public boolean isValid(String value) 
	{
   		try//tries to take the inputted string and parse it as a date in the format dd-mm-yyyy
    	{
            new SimpleDateFormat("dd-MM-yyyy").parse(value);
            return true;//returns if it was sucessful
        } 
        catch(Exception e) 
        {
            return false;//returns if unsucessful
        }
    }

    //creates the appointment to be added to the list for confirmation before being written to the file
    public void generateAppoitments()
    {
    	listModel.removeAllElements();//empties the list

    	//gets the data from each of the combo boxes
		int duration = Integer.parseInt(cbDuration.getSelectedItem().toString());
		int startTime = Integer.parseInt(cbMultiHourFrom.getSelectedItem().toString());
		int endTime = Integer.parseInt(cbMultiHourTo.getSelectedItem().toString());

		int numOfAppointments = ((endTime-startTime)*60)/duration;//performs a calculation to determine how many appointment to generate

		//creates a new date object and gets todays date
		Date dateToday = new Date();
		String newDate = (dateFormat.format(dateToday));

		//multiplies the start time by 100 to give the start time in a 4 digit manner
		int newStartTime= startTime*100;

		for(int i=1; i<numOfAppointments+1; i++)
		{
			//creates appointments
			Appointment tempAppt = new Appointment();
			
			String strNewStartTime = newStartTime+"";

			//if the start time is during the lunch hour it returns an error
			if(strNewStartTime.charAt(0) =='1' && strNewStartTime.charAt(1) =='3'){JOptionPane.showMessageDialog(null,"Can't create appointments during 1pm.\r\nPlease enter a new time period."); listModel.removeAllElements(); break;}
		
			//Responsble for rolling over the hour when the duration hits 60
			if(strNewStartTime.charAt(1) == '6'&& (strNewStartTime.charAt(0) == '8' || strNewStartTime.charAt(0) == '9'))
			{
				newStartTime = newStartTime + 40;
				strNewStartTime = newStartTime+"";
			}

			if(strNewStartTime.charAt(2) == '6'&&strNewStartTime.charAt(0)=='1')
			{
				newStartTime = newStartTime + 40;
				strNewStartTime = newStartTime+"";
			}

			//adds a leading zero to any 3 digit start times
			if(strNewStartTime.charAt(0) == '8' || strNewStartTime.charAt(0) == '9')
			{
				tempAppt.appointmentStartTime = "0"+newStartTime;
			}
			else
			{
				tempAppt.appointmentStartTime = ""+newStartTime;
			}

			newStartTime = newStartTime+duration;//Increses start time which then becomes the end time of the previous appoitment and the start time of the next appointment

			strNewStartTime = newStartTime+"";//Converts it to a string
			
			//Responsble for rolling over the hour when the duration hits 60		
			if(strNewStartTime.charAt(1) == '6'&& (strNewStartTime.charAt(0) == '8' || strNewStartTime.charAt(0) == '9'))
			{
				newStartTime = newStartTime + 40;
				strNewStartTime = newStartTime+"";
			}
			if(strNewStartTime.charAt(2) == '6'&&strNewStartTime.charAt(0)=='1')
			{
				newStartTime = newStartTime + 40;
				strNewStartTime = newStartTime+"";
			}

			//adds a leading zero to any 3 digit start times
			if(strNewStartTime.charAt(0) == '8' || strNewStartTime.charAt(0) == '9')
			{
				tempAppt.appointmentEndTime = "0"+newStartTime;
			}
			else
			{
				tempAppt.appointmentEndTime = newStartTime+"";
			}
			
			//gets the date for the appointment
			tempAppt.date = newDate;
			//sets the appointment up to be bookable
			tempAppt.patientID = "0";
			tempAppt.nameOfDoctor = cbMultiDr.getSelectedItem().toString();//assigns the doctors name to the selected name in the array
			
			listModel.addElement(tempAppt);//adds the appointment to the list model
		}
    }
}  

/*-This is a basic dialouge that allows the receptionist to enter new data to change the current data held
by an appointment.*/
class EditAppointmentView extends JFrame implements ActionListener
{
	//Sets up components and objects aswell as global variables for use in this class
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 14);
	AppointmentCreationTool apptCreate = new AppointmentCreationTool(true);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 16);
	JPanel EditPanel = new JPanel(null); //layout
 	JTextField tfStartTime = new JTextField();
	JTextField tfEndTime = new JTextField();
	JLabel lblStartTime = new JLabel();
	JLabel lblEndTime = new JLabel();
	JButton btnSubmit = new JButton();
	JButton btnCancel = new JButton();
	JLabel lblDate = new JLabel();
	JTextField tfDate = new JTextField();
	JLabel lblPatient = new JLabel();
	JTextField tfPatientID = new JTextField();
	Appointment passedAppt = null;
	int indexToReplace=0;
	static boolean hasBeenChanged = false;
	AppointmentList apptList = new AppointmentList();

	int[] res = getResolution();
	int width = res[0];
	int height = res[1];
	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	//This method gets the default monitor resolution and returns the resolution as a integer array in the form width,hight
	public int[] getResolution()
	{
		int[] res = new int[2];//makes a 2 term array
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();//gets the local graphics environment through an object of GraphicsDevice
		res[0] = gd.getDisplayMode().getWidth();//sets the first term of the array as the width
		res[1] = gd.getDisplayMode().getHeight();//sets the second term of the array as the hight
		return res;
	}

	//Creates a constructor that takes an index and an appointment and sets the relevant feilds to the passed appointment data
	public EditAppointmentView(Appointment appt, int indexOfModifier)
	{
		startGUI();
		passedAppt = appt;
		indexToReplace= indexOfModifier;
		tfStartTime.setText(appt.appointmentStartTime);
		tfEndTime.setText(appt.appointmentEndTime);
		tfDate.setText(appt.date);
		tfPatientID.setText(appt.patientID+"");
	}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(0);	
		CREATEEDITPANE();
		this.add(EditPanel);
		this.setTitle("Edit details");
		this.setSize(542,387);
		this.setLocation(widthMid-271,hightMid-193);
		this.setForeground( new Color(-16777216) );
		EditPanel.setBackground(Color.decode("#ffffff"));
		this.setVisible(true);
		this.setResizable(false);
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEEDITPANE()
	{
		tfStartTime.setLocation(38,110);
		tfStartTime.setSize(100,50);
		tfStartTime.setText("");
		tfStartTime.setColumns(10);
		tfStartTime.setOpaque(false);
		tfStartTime.setFont(standardFont);
		EditPanel.add(tfStartTime);

		tfEndTime.setLocation(165,110);
		tfEndTime.setSize(100,50);
		tfEndTime.setText("");
		tfEndTime.setColumns(10);
		tfEndTime.setOpaque(false);
		tfEndTime.setFont(standardFont);
		EditPanel.add(tfEndTime);

		lblStartTime.setLocation(38,65);
		lblStartTime.setSize(140,50);
		lblStartTime.setOpaque(false);
		lblStartTime.setFont(headingFont);
		lblStartTime.setText("Start time:");
		EditPanel.add(lblStartTime);

		lblEndTime.setLocation(165,65);
		lblEndTime.setSize(100,50);
		lblEndTime.setOpaque(false);
		lblEndTime.setFont(headingFont);
		lblEndTime.setText("End time:");
		EditPanel.add(lblEndTime);

		btnSubmit.setLocation(376,217);
		btnSubmit.setSize(100,50);
		btnSubmit.addActionListener(this);
		btnSubmit.setFont(standardFont);
		btnSubmit.setContentAreaFilled(false);
		btnSubmit.setText("Submit");
		EditPanel.add(btnSubmit);

		btnCancel.setLocation(376,277);
		btnCancel.setSize(100,50);
		btnCancel.addActionListener(this);
		btnCancel.setFont(standardFont);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setText("Cancel");
		EditPanel.add(btnCancel);

		lblDate.setLocation(376,65);
		lblDate.setSize(100,50);
		lblDate.setOpaque(false);
		lblDate.setFont(headingFont);
		lblDate.setText("Date:");
		EditPanel.add(lblDate);

		tfDate.setLocation(376,110);
		tfDate.setSize(100,50);
		tfDate.setText("");
		tfDate.setOpaque(false);
		tfDate.setFont(standardFont);
		tfDate.setColumns(10);
		EditPanel.add(tfDate);

		lblPatient.setLocation(38,217);
		lblPatient.setSize(250,50);
		lblPatient.setOpaque(false);
		lblPatient.setFont(headingFont);
		lblPatient.setText("Patient booked to:");
		EditPanel.add(lblPatient);

		tfPatientID.setLocation(240,217);
		tfPatientID.setSize(100,50);
		tfPatientID.setText("");
		tfPatientID.setColumns(10);
		tfPatientID.setOpaque(false);
		tfPatientID.setFont(standardFont);
		EditPanel.add(tfPatientID);
	}

	public void actionPerformed(ActionEvent e)
	{
		//If the submit button is pressed then it will perform the edit process
		if(e.getSource()==btnSubmit)
		{
			hasBeenChanged = true;
			performEdit();
		}
		//Backs out of the edit and writes the old data to the list and updates the file
		if(e.getSource()==btnCancel)
		{
			this.setVisible(false);
			apptList.addToList(passedAppt);

			apptList.writeAppointmentToFile();
			apptCreate.fireChange();
		}
	}

	//A method that gets the current value of hasBeenChanged, a global variable 
	public boolean getHasBeenChanged()
	{
		return hasBeenChanged;
	}

	//A method that updates the data that is changed in the dialogue
	public void performEdit()
	{
		//Creates a new instance of patient list
		PatientList patList = new PatientList();
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);//confirmation of edit
		if(answer==0)
		{	
			//validates to make sure there is data in all feilds 
			if((tfStartTime.getText().toString().equals("")||tfEndTime.getText().toString().equals("")||tfPatientID.getText().toString().equals("")||tfDate.getText().toString().equals("")) != true)
			{
				if(patList.findPatientId(tfPatientID.getText()))//Makes sure the patient exists
				{
					//Creates a new instance of appointment and gets all data for the relevant feilds of the appointment objects
					Appointment temp = new Appointment();

					temp.nameOfDoctor = passedAppt.nameOfDoctor;//Doctors name cant be edited
					temp.appointmentStartTime = tfStartTime.getText();
					temp.appointmentEndTime = tfEndTime.getText();
					temp.patientID = tfPatientID.getText();
					temp.date = tfDate.getText();

					if(apptList.searchForUnbookedConflicting(temp))//Checks that there is no unbooked appointment that the patient could book instead 
					{
						JOptionPane.showMessageDialog(null, "Can't save appointment as appointment already exists, \r\nplease enter valid data.");
					}
					else
					{
						//Edits the list 
						apptList.editAppt(indexToReplace ,temp);
						apptCreate.fireChange();//Updates the appointment table
						apptCreate.populateApptTable();	//populates appointment table 
						this.setVisible(false);//Closes the dialogue
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid patient ID");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter data in all feilds");
			}
		}
		else
		{
			//Writes original data to list and file and closes dialogue
			this.setVisible(false);
			apptList.addToList(passedAppt);

			apptList.writeAppointmentToFile();
			apptCreate.fireChange();//Updates table
		}
		if(getHasBeenChanged())
		{
			apptCreate.fireChange();//updates appoitment table
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Unable to change table, please refresh view of table");
		}
	}
}

/*-This is the interface that allows the patients to book and cancel appointments aswell as view thier 
currently booked appointments and view appointments for that day and in the future.*/
class AppointmentsView extends JFrame implements ActionListener, MouseListener
{
	//Sets up components and objects aswell as global variables for use in this class
	ImageIcon icon = new ImageIcon("Resources/NHS_200x80.png");
	JLabel banner0 = new JLabel(icon, JLabel.CENTER);
	JLabel banner1 = new JLabel(icon, JLabel.CENTER);
	InteractionMenu inter = new InteractionMenu();
	JPanel ViewPanel = new JPanel(null); //layout
	JPanel AppointmentPanel = new JPanel(null); 
	JTabbedPane tabView = new JTabbedPane();
 	JButton btnAppt1 = new JButton();
	JButton btnAppt2 = new JButton();
	JButton btnAppt3 = new JButton();
	JButton btnAppt4 = new JButton();
	JButton btnBackADay = new JButton();
	JButton btnBook = new JButton();
	JButton btnForwardADay = new JButton();
	JLabel lblDate = new JLabel();
	JLabel lblIntstruct1 = new JLabel("Click to book",JLabel.CENTER);
	JLabel lblIntstruct2 = new JLabel("Or look at others",JLabel.LEFT);
	JLabel lblIntstruct3 = new JLabel("Click an appointment to cancel",JLabel.CENTER);
	JTextArea taAllAppointments = new JTextArea();
	Appointment appt = new Appointment();
	String patientID = inter.getAuthedID();
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Calendar cal = Calendar.getInstance();
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 16);
	ImageIcon editico = new ImageIcon("Resources/edit_20x20.png");
	ImageIcon clockico = new ImageIcon("Resources/clock_20x20.png");
	String[] tbAppointmentColumns = {" Start time "," End time ", " Date ", " Status ", " Doctor name "};
	Object[][] tbAvalibleAppsData = {{}};
	DefaultTableModel modelAvalible = new DefaultTableModel(tbAvalibleAppsData, tbAppointmentColumns){
		public boolean isCellEditable(int rowIndex, int mColIndex){
			return false;
		}
	};
	JTable tbAvalibleAppointments = new JTable(modelAvalible);
	JScrollPane scrollApptTable = new JScrollPane(tbAvalibleAppointments);

	String tableData="";
	Object[][] tbData = {{}};
	DefaultTableModel model = new DefaultTableModel(tbData, tbAppointmentColumns){
		public boolean isCellEditable(int rowIndex, int mColIndex){
			return false;
		}
	};
	JTable tbAppointments = new JTable(model);
	JScrollPane scrollTimeTable = new JScrollPane(tbAppointments);
	JButton btnCancel = new JButton();

	int[] res = getResolution();
	int width = res[0];
	int height = res[1];
	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	AppointmentList apptList = new AppointmentList();
	String[] listOfAppointments = apptList.listAppointments(0);

	int offset = 0;

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATEViewPanel();
		CREATEAppointmentPanel();
		this.add(tabView);
		tabView.addTab("Book", editico, ViewPanel, "Book appointments");
		tabView.addTab("My appointments", clockico, AppointmentPanel, "View and cancel appointments");
		tabView.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if(tabView.getSelectedIndex() == 1)
            {
            	populateMyApptTable();
            }
            if(tabView.getSelectedIndex() == 0)
            {
            	listOfAppointments = apptList.listAppointments(0);
            	populateBtnAppt();
            	populateApptTable();
            }
        }
    	});
		this.setTitle("Appointments");
		this.setSize(660,424);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setVisible(true);
		this.setResizable(false);
		populateBtnAppt();
		ViewPanel.setBackground(Color.decode("#ffffff"));
		AppointmentPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.isUndecorated();
		populateMyApptTable();
		populateApptTable();
		
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEViewPanel()
	{
		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		ViewPanel.add(banner1);

		lblIntstruct1.setLocation(widthMid-800,hightMid-300);
		lblIntstruct1.setSize(150,50);
		lblIntstruct1.setFont(headingFont);
		ViewPanel.add(lblIntstruct1);

		lblIntstruct2.setLocation(widthMid+100,hightMid-300);
		lblIntstruct2.setSize(250,50);
		lblIntstruct2.setFont(headingFont);
		ViewPanel.add(lblIntstruct2);

		btnAppt1.setLocation(widthMid-800,hightMid-250);
		btnAppt1.setSize(700,70);
		btnAppt1.addActionListener(this);
		btnAppt1.setContentAreaFilled(false);
		btnAppt1.setFont(standardFont);
		btnAppt1.setText("1");
		ViewPanel.add(btnAppt1);

		btnAppt2.setLocation(widthMid-800,hightMid-170);
		btnAppt2.setSize(700,70);
		btnAppt2.addActionListener(this);
		btnAppt2.setText("2");
		btnAppt2.setContentAreaFilled(false);
		btnAppt2.setFont(standardFont);
		ViewPanel.add(btnAppt2);

		btnAppt3.setLocation(widthMid-800,hightMid-90);
		btnAppt3.setSize(700,70);
		btnAppt3.addActionListener(this);
		btnAppt3.setText("3");
		btnAppt3.setContentAreaFilled(false);
		btnAppt3.setFont(standardFont);
		ViewPanel.add(btnAppt3);

		btnAppt4.setLocation(widthMid-800,hightMid-10);
		btnAppt4.setSize(700,70);
		btnAppt4.addActionListener(this);
		btnAppt4.setText("4");
		btnAppt4.setContentAreaFilled(false);
		btnAppt4.setFont(standardFont);
		ViewPanel.add(btnAppt4);

		btnBackADay.setLocation(widthMid-150,hightMid-450);
		btnBackADay.setSize(75,50);
		btnBackADay.addActionListener(this);
		btnBackADay.setText("<-");
		btnBackADay.setFont(headingFont);
		btnBackADay.setVisible(false);
		btnBackADay.setContentAreaFilled(false);
		ViewPanel.add(btnBackADay);

		btnForwardADay.setLocation(widthMid+60,hightMid-450);
		btnForwardADay.setSize(75,50);
		btnForwardADay.addActionListener(this);
		btnForwardADay.setText("->");
		btnForwardADay.setFont(headingFont);
		btnForwardADay.setContentAreaFilled(false);
		ViewPanel.add(btnForwardADay);

		btnBook.setLocation(widthMid+550,hightMid+170);
		btnBook.setSize(100,50);
		btnBook.addActionListener(this);
		btnBook.setText("Book");
		btnBook.setFont(standardFont);
		btnBook.setVisible(false);
		btnBook.setContentAreaFilled(false);
		ViewPanel.add(btnBook);

		lblDate.setLocation(widthMid-60,hightMid-450);
		lblDate.setSize(120,50);
		lblDate.setOpaque(false);
		lblDate.setFont(headingFont);
		lblDate.setText(dateFormat.format(cal.getTime()));
		ViewPanel.add(lblDate);

		tbAvalibleAppointments.addMouseListener(this);
		tbAvalibleAppointments.setFont(standardFont);

		scrollApptTable.setLocation(widthMid+100,hightMid-250);
		scrollApptTable.setSize(800,400);
		scrollApptTable.setVisible(true);
		ViewPanel.add(scrollApptTable);
		modelAvalible.setRowCount(0);
	}

	public void CREATEAppointmentPanel()
	{
		banner0.setLocation(0,res[1]-200);
		banner0.setSize(res[0],200);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		AppointmentPanel.add(banner0);

		tbAppointments.addMouseListener(this);
		tbAppointments.setFont(standardFont);

		lblIntstruct3.setLocation(widthMid-500,hightMid-450);
		lblIntstruct3.setSize(350,50);
		lblIntstruct3.setFont(headingFont);
		AppointmentPanel.add(lblIntstruct3);

		scrollTimeTable.setLocation(widthMid-500,hightMid-400);
		scrollTimeTable.setSize(1000,400);
		scrollTimeTable.setVisible(true);
		AppointmentPanel.add(scrollTimeTable);
		model.setRowCount(0);

		btnCancel.setLocation(widthMid-50,hightMid+100);
		btnCancel.setSize(100,50);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setText("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setFont(standardFont);
		btnCancel.setVisible(false);
		AppointmentPanel.add(btnCancel);
	}

	//gets the resolution of the graphics device that the system is running on
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}

	//Takes the data from the list of appointments and populates the booking table
	public void populateApptTable()
	{
		modelAvalible.setRowCount(0);//Empty table model

		for(int i=0; listOfAppointments[i] != null; i++)//Loop  through the array till you hit null
		{
			String[] apptToAdd = listOfAppointments[i].toString().split(" ");//Split the array term to strings
			String value = "";
			//Masks any booked appointments ids with a string rather than showing either an id or a 0

			if(apptToAdd[3].equals(0))
			{
				value = "Booked";
			}
			else
			{	
				value = "Unbooked";
			}

			String[] newData = {apptToAdd[0],apptToAdd[1],apptToAdd[2], value , apptToAdd[4] + " " + apptToAdd[5]};//Creates a string array for the row in the table

			modelAvalible.addRow(newData);//Adds the data to the model
		}
	}

	public void populateMyApptTable()
	{
		AppointmentList apptList = new AppointmentList();//Creates an instance of appointment list
		model.setRowCount(0);//Empties the model
		Appointment[] arrayAppoitments = apptList.myAppoitmentsForDay();//gets an appointment array of that authenticated user

		for(int i=0; arrayAppoitments[i] != null; i++)//loops through that array
		{
			String[] apptToAdd = arrayAppoitments[i].toString().split(" ");//Splits it to a string
			String value = "";
			if(apptToAdd[3].equals(0))//Masks ID's with booked and unbooked
			{
				value = "Unbooked";
			}
			else
			{	
				value = "Booked";
			}

			String[] newData = {apptToAdd[0],apptToAdd[1],apptToAdd[2], value, apptToAdd[4] + " " + apptToAdd[5]};//creates a string array to hold the row of data to be added

			model.addRow(newData);//Adds the data to the model
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		//When the table of personal appointments is clicked, the data from that row is saved in to table data
		if(e.getSource()==tbAppointments)
		{
			InteractionMenu inter = new InteractionMenu();

			int indexRow = tbAppointments.getSelectedRow();

			tableData = tbAppointments.getValueAt(indexRow,0).toString() + " " + tbAppointments.getValueAt(indexRow,1).toString()+ " " + tbAppointments.getValueAt(indexRow,2).toString() + " " + tbAppointments.getValueAt(indexRow,3).toString()+ " " + tbAppointments.getValueAt(indexRow,4).toString();

			String[] splitData = tableData.split(" ");
			if(splitData[3].equals("Booked"))
			{
				tableData = tbAppointments.getValueAt(indexRow,0).toString() + " " + tbAppointments.getValueAt(indexRow,1).toString()+ " " + tbAppointments.getValueAt(indexRow,2).toString() + " " + inter.getAuthedID() + " " + tbAppointments.getValueAt(indexRow,4).toString();
			}
			else
			{
				tableData = tbAppointments.getValueAt(indexRow,0).toString() + " " + tbAppointments.getValueAt(indexRow,1).toString()+ " " + tbAppointments.getValueAt(indexRow,2).toString() + " " + "0" + " " + tbAppointments.getValueAt(indexRow,4).toString();
			}

			btnCancel.setVisible(true);
		}
		//When the table of avalible appointments is clicked, the data from that row is saved in to table data
		if(e.getSource()==tbAvalibleAppointments)
		{
			InteractionMenu inter = new InteractionMenu();

			int indexRow = tbAvalibleAppointments.getSelectedRow();

			tableData = tbAvalibleAppointments.getValueAt(indexRow,0).toString() + " " + tbAvalibleAppointments.getValueAt(indexRow,1).toString()+ " " + tbAvalibleAppointments.getValueAt(indexRow,2).toString() + " " + tbAvalibleAppointments.getValueAt(indexRow,3).toString()+ " " + tbAvalibleAppointments.getValueAt(indexRow,4).toString();

			String[] splitData = tableData.split(" ");
			if(splitData[3].equals("Booked"))
			{
				tableData = tbAvalibleAppointments.getValueAt(indexRow,0).toString() + " " + tbAvalibleAppointments.getValueAt(indexRow,1).toString()+ " " + tbAvalibleAppointments.getValueAt(indexRow,2).toString() + " " + inter.getAuthedID() + " " + tbAvalibleAppointments.getValueAt(indexRow,4).toString();
			}
			else
			{
				tableData = tbAvalibleAppointments.getValueAt(indexRow,0).toString() + " " + tbAvalibleAppointments.getValueAt(indexRow,1).toString()+ " " + tbAvalibleAppointments.getValueAt(indexRow,2).toString() + " " + "0" + " " + tbAvalibleAppointments.getValueAt(indexRow,4).toString();
			}

			btnBook.setVisible(true);
		}
	}

	public void mouseEntered(MouseEvent e)
	{}
	

	public void mouseExited(MouseEvent e)
	{}
	

	public void mouseReleased(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}


	public void actionPerformed(ActionEvent e)
	{
		//when an appointment button is pressed, the user gets a confirmation, if they select yes then the book method is run and the buttons and table are updated
		if(e.getSource()==btnAppt1)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you book: \r\n" + btnAppt1.getText()+"\r\nDo you wish to book this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.bookAppointment(listOfAppointments[0]);
				listOfAppointments = apptList.listAppointments(0);
				populateBtnAppt();
				populateApptTable();
			}
		}

		if(e.getSource()==btnAppt2)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you book: \r\n" + btnAppt2.getText()+"\r\nDo you wish to book this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.bookAppointment(listOfAppointments[1]);
				listOfAppointments = apptList.listAppointments(0);
				populateBtnAppt();
				populateApptTable();
			}
		}

		if(e.getSource()==btnAppt3)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you book: \r\n" + btnAppt3.getText()+"\r\nDo you wish to book this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.bookAppointment(listOfAppointments[2]);
				listOfAppointments = apptList.listAppointments(0);
				populateBtnAppt();
				populateApptTable();
			}		
		}

		if(e.getSource()==btnAppt4)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you book: \r\n" + btnAppt4.getText()+"\r\nDo you wish to book this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.bookAppointment(listOfAppointments[3]);
				listOfAppointments = apptList.listAppointments(0);
				populateBtnAppt();
				populateApptTable();
			}
		}

		if(e.getSource()==btnBook)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you book: \r\n" + btnAppt4.getText()+"\r\nDo you wish to book this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.bookAppointment(tableData);
				listOfAppointments = apptList.listAppointments(0);
				populateBtnAppt();
				populateApptTable();
			}
		}

		//When pressed, this updates the label with the previous days date unless it reaches the current date. This changes the calender and offset by -1 day and shifts the date in the instance of calender in appointment
		if(e.getSource()==btnBackADay)
		{
			cal.add(Calendar.DATE, -1);
			lblDate.setText(dateFormat.format(cal.getTime()));
			if(dateFormat.format(cal.getTime()).contains(appt.date) == true)
			{
				btnBackADay.setVisible(false);
			}
			offset = -1;
			listOfAppointments = apptList.listAppointments(offset);
			populateBtnAppt();
			populateApptTable();
		}
		//When pressed, this updates the label with the next days date, you can keep going and it will rollover the month. This changes the calender and offset by 1 day and shifts the date in the instance of calender in appointment
		if(e.getSource()==btnForwardADay)
		{
			cal.add(Calendar.DATE, 1);
			lblDate.setText(dateFormat.format(cal.getTime()));
			if(dateFormat.format(cal.getTime()).contains(appt.date) != true)
			{
				btnBackADay.setVisible(true);
			}
			offset = 1;
			listOfAppointments = apptList.listAppointments(offset);
			populateBtnAppt();
			populateApptTable();
		}

		//If the cancel button is pressed, the cancel method is run after being asked to confirm the choice to cancel
		if(e.getSource()==btnCancel)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Select 'Yes' if you wish you cancel: \r\n" + tableData +"\r\nDo you wish to cancel this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if(answer==0)
			{
				apptList.cancelAppointment(tableData);
				populateMyApptTable();//updates table
			}
		}
	}

	//This method determines if there is data in the term of the array and consequently only shows the appointments with data in them
	public void populateBtnAppt()
	{
		if(isAppointment(listOfAppointments[0]) != true){lblIntstruct1.setVisible(false);}
		btnAppt1.setVisible(isAppointment(listOfAppointments[0]));
		btnAppt2.setVisible(isAppointment(listOfAppointments[1]));
		btnAppt3.setVisible(isAppointment(listOfAppointments[2]));
		btnAppt4.setVisible(isAppointment(listOfAppointments[3]));
		
		//Formats the data to a readable form
		btnAppt1.setText(formatAppointment(listOfAppointments[0]));
		btnAppt2.setText(formatAppointment(listOfAppointments[1]));
		btnAppt3.setText(formatAppointment(listOfAppointments[2]));
		btnAppt4.setText(formatAppointment(listOfAppointments[3]));
	}

	//determines if an appointment from the array contails null data 
	private boolean isAppointment(String appt)
	{
		if (appt == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//This method adds strings before terms like the start time to explain to the user what the data means, aiding the readability
	public String formatAppointment(String appt)
	{
		if(appt != null)
		{
			String[] splitData = appt.split(" ");

			String status;
			String start = "Starts: "+splitData[0];
			String end = "Ends: "+splitData[1];
			String date = "On: "+splitData[2];
			if(splitData[3].equals("0"))
			{
				status = "Avalible";
			}
			else
			{
				status = "Booked";
			}

			String doc = splitData[4] + " " + splitData[5];
			String data = (start + " " + end + " " + doc + " " + status + " " + date);
			return data;
		}
		else
		{
			return null;
		}
	}
}  

/*-This screen allows doctors to create and view patient notes that relate to the appointments
that the patients have*/
class PatientNotesViewerCreator extends JFrame implements ActionListener, KeyListener
{
	//Sets up components and objects aswell as global variables for use in this class
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 18);

	Doctor doc = new Doctor();
	PatientNotes note = new PatientNotes();
	Border border = BorderFactory.createLineBorder(Color.BLACK);

	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel(icon, JLabel.RIGHT);

	int[] res = getResolution();

	ImageIcon noteico = new ImageIcon("Resources/newNote.png");
	ImageIcon viewico = new ImageIcon("Resources/search_20x20.png");

	JPanel ViewPanel = new JPanel(null); //layout
	JPanel CreatePanel = new JPanel(null); //layout
 	JLabel lblDate = new JLabel();
	JLabel lblTime = new JLabel();
	JLabel lblLiveTime = new JLabel();
	JLabel lblLiveDate = new JLabel();
	JLabel lblDoctorName = new JLabel();
	String[] cbxDoctorSelector_data= doc.populate();
	JComboBox cbxDoctorSelector = new JComboBox(cbxDoctorSelector_data);
	JTextArea taNoteBody = new JTextArea();
	JLabel lblNoteBody = new JLabel();
	JButton btnSave = new JButton();
	JLabel lblPatientID = new JLabel();
	JTextField tfID = new JTextField();
	JLabel search = new JLabel(viewico);
	JLabel readLabel = new JLabel("Read");
	JLabel lblSelect = new JLabel();

	JLabel lblDate2 = new JLabel();
	JLabel lblTime2 = new JLabel();
	JLabel lblLiveTime2 = new JLabel();
	JLabel lblLiveDate2 = new JLabel();
	JLabel lblDoctorName2 = new JLabel();
	JLabel lblDoctor = new JLabel();
	JTextArea taNoteBody2 = new JTextArea();
	JLabel lblNoteBody2 = new JLabel();
	JLabel lblPatientID2 = new JLabel();
	JButton btnRead = new JButton();
	String temp;
	JTextField tfID2 = new JTextField();

	int width = res[0];
	int height = res[1];
	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	int count = 0;

	String[] contenseOfDir;
	DefaultComboBoxModel model = new DefaultComboBoxModel();
	JComboBox cbAppointmentSelecter = new JComboBox(model);

	PatientList patList = new PatientList();
	String id = tfID.getText();
	File aDirectory = new File("Logs\\"+ id+"\\");

	String foldername = "Logs\\";
	JTabbedPane tabView = new JTabbedPane();

	JScrollPane scrollRead = new JScrollPane(taNoteBody);
	JScrollPane scrollWrite = new JScrollPane(taNoteBody2);

	CaesarCipher cc = new CaesarCipher();

	//Creates a constructor that runs the gui
	public PatientNotesViewerCreator()
	{
		startGUI();
	}

	//Creates a constructor that takes an id and pre-populates the id feild for the writing of a note 
	public PatientNotesViewerCreator(String idOfSelection)
	{
		startGUI();
		tabView.setSelectedIndex(0);
		tfID2.setText(idOfSelection);
	}
	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		tabView.addTab("Create", noteico, CreatePanel, "Create a patient notes");
		tabView.addTab("View", viewico, ViewPanel, "View existing notes");
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		runTabbs();
		this.add(tabView);
		ViewPanel.setBackground(Color.decode("#ffffff"));
		CreatePanel.setBackground(Color.decode("#ffffff"));
		this.setTitle("Patient note creator");
		this.setSize(586,517);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setVisible(true);
		this.setResizable(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.isUndecorated();
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void runTabbs()
	{
		banner0.setLocation(0,res[1]-200);
		banner0.setSize(res[0],200);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		banner1.setLocation(0,res[1]-200);
		banner1.setSize(res[0],200);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		ViewPanel.add(banner0);
		CreatePanel.add(banner1);

		cbAppointmentSelecter.setLocation(widthMid+100,hightMid-340);
		cbAppointmentSelecter.setSize(250,40);
		cbAppointmentSelecter.setEditable(false);
		cbAppointmentSelecter.setVisible(false);
		cbAppointmentSelecter.setOpaque(false);
		cbAppointmentSelecter.addActionListener(this);
		cbAppointmentSelecter.setFont(standardFont);
		ViewPanel.add(cbAppointmentSelecter);

		lblDate.setLocation(widthMid-350,hightMid-210);
		lblDate.setSize(150,50);
		lblDate.setOpaque(false);
		lblDate.setFont(headingFont);
		lblDate.setText("Timestamp:");
		ViewPanel.add(lblDate);

		tfID.setLocation(widthMid-220,hightMid-340);
		tfID.setSize(50,50);
		tfID.setText("");
		tfID.addKeyListener(this);
		tfID.setColumns(10);
		tfID.setFont(standardFont);
		ViewPanel.add(tfID);

		lblPatientID.setLocation(widthMid-350,hightMid-340);
		lblPatientID.setSize(150,50);
		lblPatientID.setOpaque(false);
		lblPatientID.setText("Patient ID:");
		lblPatientID.setFont(headingFont);
		ViewPanel.add(lblPatientID);

		lblLiveDate.setLocation(widthMid-220,hightMid-210);
		lblLiveDate.setSize(200,50);
		lblLiveDate.setOpaque(false);
		lblLiveDate.setText("");
		lblLiveDate.setFont(standardFont);
		ViewPanel.add(lblLiveDate);

		lblDoctorName.setLocation(widthMid+120,hightMid-210);
		lblDoctorName.setSize(90,50);
		lblDoctorName.setOpaque(false);
		lblDoctorName.setText("Doctor:");
		lblDoctorName.setFont(headingFont);
		ViewPanel.add(lblDoctorName);

		lblDoctor.setLocation(widthMid+200,hightMid-210);
		lblDoctor.setSize(150,50);
		lblDoctor.setText("");
		lblDoctor.setFont(standardFont);
		ViewPanel.add(lblDoctor);

		lblSelect.setLocation(widthMid-35,hightMid-340);
		lblSelect.setSize(150,50);
		lblSelect.setText("Select note:");
		lblSelect.setFont(headingFont);
		lblSelect.setVisible(false);
		ViewPanel.add(lblSelect);

		taNoteBody.setLocation(widthMid-350,hightMid-100);
   		taNoteBody.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		taNoteBody.setSize(700,250);
		taNoteBody.setText("");
		taNoteBody.setRows(5);
		taNoteBody.setColumns(5);
		taNoteBody.setVisible(true);
		taNoteBody.setLineWrap(true);
		taNoteBody.setFont(standardFont);
		taNoteBody.setWrapStyleWord(true);
		taNoteBody.setEditable(false);
		scrollRead.setLocation(widthMid-350,hightMid-100);
		scrollRead.setSize(700,250);
		ViewPanel.add(scrollRead);

		lblNoteBody.setLocation(widthMid-350,hightMid-150);
		lblNoteBody.setSize(100,50);
		lblNoteBody.setOpaque(false);
		lblNoteBody.setText("Note:");
		lblNoteBody.setFont(headingFont);
		ViewPanel.add(lblNoteBody);

		btnRead.setLocation(widthMid-350,hightMid-280);
		btnRead.setSize(100,40);
		btnRead.setLayout(new GridLayout(1,2));
		btnRead.addActionListener(this);
		btnRead.add(search);btnRead.add(readLabel);
		btnRead.setFont(standardFont);
		btnRead.setContentAreaFilled(false);
		ViewPanel.add(btnRead);

		lblDate2.setLocation(widthMid-350,hightMid-240);
		lblDate2.setSize(70,50);
		lblDate2.setOpaque(false);
		lblDate2.setText("Date:");
		lblDate2.setFont(headingFont);
		CreatePanel.add(lblDate2);

		lblPatientID2.setLocation(widthMid+150,hightMid-300);
		lblPatientID2.setSize(150,50);
		lblPatientID2.setOpaque(false);
		lblPatientID2.setText("Patient ID:");
		lblPatientID2.setFont(headingFont);
		CreatePanel.add(lblPatientID2);

		tfID2.setLocation(widthMid+310,hightMid-300);
		tfID2.setSize(40,50);
		tfID2.setText("");
		tfID2.setFont(standardFont);
		tfID2.setColumns(10);
		CreatePanel.add(tfID2);

		lblTime2.setLocation(widthMid-350,hightMid-300);
		lblTime2.setSize(70,50);
		lblTime2.setOpaque(false);
		lblTime2.setFont(headingFont);
		lblTime2.setText("Time:");
		CreatePanel.add(lblTime2);

		lblLiveTime2.setLocation(widthMid-280,hightMid-300);
		lblLiveTime2.setSize(100,50);
		lblLiveTime2.setOpaque(false);
		lblLiveTime2.setText(note.timeReal);
		lblLiveTime2.setFont(standardFont);
		CreatePanel.add(lblLiveTime2);

		lblLiveDate2.setLocation(widthMid-280,hightMid-240);
		lblLiveDate2.setSize(150,50);
		lblLiveDate2.setOpaque(false);
		lblLiveDate2.setText(note.date);
		lblLiveDate2.setFont(standardFont);
		CreatePanel.add(lblLiveDate2);

		lblDoctorName2.setLocation(widthMid+100,hightMid-240);
		lblDoctorName2.setSize(100,50);
		lblDoctorName.setOpaque(false);
		lblDoctorName2.setText("Doctor:");
		lblDoctorName2.setFont(headingFont);
		CreatePanel.add(lblDoctorName2);

		cbxDoctorSelector.setLocation(widthMid+200,hightMid-240);
		cbxDoctorSelector.setSize(150,50);
		cbxDoctorSelector.setEditable(false );
		cbxDoctorSelector.setOpaque(false);
		cbxDoctorSelector.setFont(standardFont);
		CreatePanel.add(cbxDoctorSelector);

		taNoteBody2.setLocation(widthMid-350,hightMid-100);
		taNoteBody2.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		taNoteBody2.setSize(700,250);
		taNoteBody2.setText("");
		taNoteBody2.setRows(5);
		taNoteBody2.setColumns(5);
		taNoteBody2.setVisible(true);
		taNoteBody2.setLineWrap(true);
		taNoteBody2.setWrapStyleWord(true);
		taNoteBody2.setFont(standardFont);
		scrollWrite.setLocation(widthMid-350,hightMid-100);
		scrollWrite.setSize(700,250);
		CreatePanel.add(scrollWrite);

		lblNoteBody2.setLocation(widthMid-350,hightMid-150);
		lblNoteBody2.setSize(100,50);
		lblNoteBody2.setOpaque(false);
		lblNoteBody2.setFont(headingFont);
		lblNoteBody2.setText("Note:");
		CreatePanel.add(lblNoteBody2);

		btnSave.setLocation(widthMid-50,hightMid+160);
		btnSave.setSize(100,50);
		btnSave.addActionListener(this);
		btnSave.setText("Save");
		btnSave.setFont(standardFont);
		btnSave.setContentAreaFilled(false);
		CreatePanel.add(btnSave);
	}

	//Gets the resolution of the graphics display 
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}

	public void keyTyped(KeyEvent e)
	{}

	public void keyPressed(KeyEvent e)
	{
		//When the enter key is pressed when searching for an id for that patients notes
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			readNotes();
		}
	}

	public void keyReleased(KeyEvent e)
	{}

	//This method finds the notes for the doctor to view
	public void readNotes()
	{
		id = tfID.getText();//gets the id from the text feild

		//Sets labels to nothing
		taNoteBody.setText("");
		lblLiveDate.setText("");
		lblDoctor.setText("");
		
		if(patList.findPatientId(id))//Determines if the user exisits
		{
			//makes the combo-box visible
			cbAppointmentSelecter.setVisible(true);
			lblSelect.setVisible(true);

			try
			{
				File aDirectory = new File("Logs\\"+id+"\\");//Creates a directory where the notes will be stored if they exist
				
				//Empties combo-box model 
				model.removeAllElements();
				model.addElement("");
				contenseOfDir = aDirectory.list();//Lists all the files in that directory

		   		for(int i=0; i<contenseOfDir.length; i++)//Loops therough the length of the array 
		   		{
		   			model.addElement(contenseOfDir[i]);//adds the file name to the combo-box
		   		}	
		   		//If there is no data in the array then return the error message that there are no notes found in the directory
				if(contenseOfDir[0]==null)
				{
					JOptionPane.showMessageDialog(null,"No notes found");
				}
			}
			catch(Exception ex)
			{

			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Patient not found");
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnSave)
		{
			if(taNoteBody2.getText().equals("")||cbxDoctorSelector.getSelectedItem().toString().equals("")||tfID2.getText().equals("") || tfID2.getText().length() <3)//Validates that there is data in the feilds 
			{
				JOptionPane.showMessageDialog(null,"Please enter data in all feilds.");
			}
			else
			{
				if(patList.findPatientId(tfID2.getText()))//validates the patient entered exists
				{
					try
					{
						//Determines if the user has a subdirectory in Logs for thier notes
						File dir = new File(foldername+ tfID2.getText());

						if (dir.exists() == false)//If they dont
						{
							dir.mkdirs();//Make a subdirectory
						}

						String id = tfID2.getText();

						String filename = "Logs\\"+ tfID2.getText()+"\\"+ note.timeStamp +".txt";//Create a text file for the data to be written to 
						
						PatientNotes tempNote = new PatientNotes();//Creates a new instance of patient notes
						
						//takes the data from the fields and saves the data in to the note object
						tempNote.doctorName = cbxDoctorSelector.getSelectedItem().toString();
						tempNote.patientID = Integer.parseInt(id);
						tempNote.time = note.time;
						tempNote.date = note.date; 
						tempNote.notes = cc.encrypt(taNoteBody2.getText());//Encrypt the note body

						//Sets up file writer objects and passes 
						FileWriter fw = new FileWriter(filename, true);
						BufferedWriter bw = new BufferedWriter(fw);

						bw.write(tempNote.toString());//Write the note to the subdirectory
						bw.close();

						JOptionPane.showMessageDialog(null, "Note has been saved");//Shows a confirmation

						//Resets the feilds for new data
						cbxDoctorSelector.setSelectedIndex(0);
						tfID2.setText("");
						taNoteBody2.setText("");
					}

					catch(Exception ex)
					{
						System.out.println("Error writing to file "+ ex);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"User doesn't exist so note hasn't been saved");
				}
			}
		}

		//If the read button is pressed then read notes method is executed
		if(e.getSource()==btnRead)
		{
			readNotes();
		}

		//When the appointments combo-box has been populated this loads the data in to the viewer
		if(e.getSource()==cbAppointmentSelecter)
		{
			try
			{
				String newDir = aDirectory+"\\"+id+"\\"+cbAppointmentSelecter.getSelectedItem().toString();//Takes the directory of the file

				//Loads new reader components
				FileReader fr = new FileReader(newDir);
				BufferedReader br = new BufferedReader(fr);

				//Reads in the line 
				String aReadLine = br.readLine();

				//Splits the data
				String[] splitNoteData = aReadLine.split("\\$");
				//Parses the data in to the relevant labels
				lblDoctor.setText(splitNoteData[0]);
				lblLiveDate.setText(splitNoteData[2]);
				taNoteBody.setText(cc.decrypt(splitNoteData[3]));
			}
			catch(Exception ex)
			{
			}
		}
	}
}  

/*-With this part of the system, receptionists and doctors can view doctors timetables and print physical 
copies of these timetables.*/
class DoctorTimetable extends JFrame implements ActionListener 
{
	//Sets up components and objects aswell as global variables for use in this class
	Doctor doc = new Doctor();
	Appointment appt = new Appointment();

	JPanel DoctorPanel = new JPanel(null); //layout
	JButton btnPrint = new JButton();
	String[] cbDoctorName_data= doc.populate();
	JComboBox cbDoctorName = new JComboBox(cbDoctorName_data);
	String[] tbAppointmentColumns = {" Start time "," End time ", " Date ", " Status ", " Doctor name "};
	Object[][] tbData = {{}};
	DefaultTableModel model = new DefaultTableModel(tbData, tbAppointmentColumns);
	JTable tbAppointments = new JTable(model);
	JScrollPane scrollTimeTable = new JScrollPane(tbAppointments);
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 20);
	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();
	JLabel instructions = new JLabel("Click on the doctors name that you want to see the timetable of", JLabel.CENTER);

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{

		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATEDoctorPanel();
		this.add(DoctorPanel);
		this.setTitle("Doctor day timetable");
		this.setSize(611,516);
		this.setForeground( new Color(-16777216) );
		this.setBackground( new Color(-2696737) );
		this.setResizable(false);
		this.setVisible(true);
		DoctorPanel.setBackground(Color.decode("#ffffff"));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Creates a constuctor that runs the GUI
	public DoctorTimetable()
	{
		startGUI();
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEDoctorPanel()
	{
		int[] res = getResolution();

		int width = res[0];
		int height = res[1];

		int hightMid = (res[1]/2);
		int widthMid = (res[0]/2);

		banner0.setLocation(0,0);
		banner0.setSize(res[0],100);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		DoctorPanel.add(banner0);

		banner1.setLocation(0,res[1]-100);
		banner1.setSize(res[0],100);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		DoctorPanel.add(banner1);

		instructions.setLocation(widthMid-400,hightMid-250);
		instructions.setSize(800,50);
		instructions.setFont(standardFont);
		DoctorPanel.add(instructions);

		tbAppointments.setEnabled(false);

		scrollTimeTable.setLocation(widthMid-500,hightMid-130);
		scrollTimeTable.setSize(1000,400);
		scrollTimeTable.setVisible(true);
		scrollTimeTable.setOpaque(false);
		DoctorPanel.add(scrollTimeTable);

		btnPrint.setLocation(widthMid-280,hightMid-200);
		btnPrint.setSize(100,50);
		btnPrint.addActionListener(this);
		btnPrint.setText("Print");
		btnPrint.setVisible(false);
		btnPrint.setContentAreaFilled(false);
		DoctorPanel.add(btnPrint);

		cbDoctorName.setLocation(widthMid-500,hightMid-200);
		cbDoctorName.setSize(200,50);
		cbDoctorName.addActionListener(this);
		DoctorPanel.add(cbDoctorName);
	}

	//Gets the resolution of the default graphics device 
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}


	public void actionPerformed(ActionEvent e)
	{
		//When the combo-box of doctor names is clicked
		if(e.getSource()==cbDoctorName)
		{
			model.setRowCount(0);//Empties the table model
			AppointmentList apptList = new AppointmentList();//creates a new instance of appointment list
			String value="";

			String drName = cbDoctorName.getSelectedItem().toString();//takes the doctor name

			Appointment[] arrayAppoitments = apptList.appoitmentsForDay(drName);//gets appoitments for that day for that doctor

			if(arrayAppoitments[0] == null || cbDoctorName.getSelectedItem().toString().equals("")) //If there are no appointments
			{
				btnPrint.setVisible(false);//hide the print button
			}

			else
			{
				btnPrint.setVisible(true);//otherwise make it visable
				for(int i=0; arrayAppoitments[i] != null; i++)//For the length of the array
				{
					String[] apptToAdd = arrayAppoitments[i].toString().split(" ");

					//Read appointment in to the table
					if(apptToAdd[3].equals("0"))
					{value = "Unbooked";} 
					else
					{value = "Booked to: " + apptToAdd[3];}

					String[] newData = {apptToAdd[0],apptToAdd[1],apptToAdd[2], value, apptToAdd[4] + " " + apptToAdd[5]};

					model.addRow(newData);//add appointment to table
				}
			}
		}

		if(e.getSource()==btnPrint)
		{
			boolean sucessful = false;
			try//Try and print the table
			{
				tbAppointments.print();//opens print dialogue
				sucessful = true;
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Error printing");
			}

			if(sucessful)
			{
				JOptionPane.showMessageDialog(null,"Printed timetable");
			}
		}
	}
}  

/*-This is the screen responsible for the viewing of patient notes on the patient side. This means that 
unlike doctors, the authenticated user only sees thier notes*/
class ViewNotesPatient extends JFrame implements ActionListener
{
	//Sets up components and objects aswell as global variables for use in this class
	InteractionMenu inter = new InteractionMenu();
	JPanel NOTEPANEL = new JPanel(null); //layout
 	JLabel lblDate = new JLabel();
	JLabel lblLiveDate = new JLabel();
	JLabel lblDoctorName = new JLabel();
	JLabel lblDoctor = new JLabel();
	JTextArea taNoteBody = new JTextArea();
	JLabel lblNoteBody = new JLabel();
	String[] contenseOfDir;
	JComboBox cbAppointmentSelecter;
	Border border = BorderFactory.createLineBorder(Color.BLACK);

	PatientNotes note = new PatientNotes();
	PatientList patList = new PatientList();
	String id = inter.getAuthedID()+"";
	File aDirectory = new File("Logs\\"+ id+"\\");

	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 16);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 18);

	ImageIcon icon = new ImageIcon("Resources/NHS_250x100.png");
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();
	
	int[] res = getResolution();

	int width = res[0];
	int height = res[1];

	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	CaesarCipher cc = new CaesarCipher();

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATENOTEPANEL();
		this.add(NOTEPANEL);
		this.setTitle("Note viewer");
		this.setSize(586,540);
		this.setForeground( new Color(-16777216) );
		NOTEPANEL.setBackground(Color.decode("#ffffff"));
		this.setVisible(true);
		this.setResizable(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.isUndecorated();
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
		findAppointmentDates();
	}

	//gets the local display resolution
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATENOTEPANEL()
	{
		banner0.setLocation(0,0);
		banner0.setSize(res[0],100);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		NOTEPANEL.add(banner0);

		banner1.setLocation(0,res[1]-100);
		banner1.setSize(res[0],100);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		NOTEPANEL.add(banner1);

		lblDate.setLocation(widthMid-350,hightMid-70);
		lblDate.setSize(150,50);
		lblDate.setOpaque(false);
		lblDate.setText("Time stamp:");
		lblDate.setFont(headingFont);
		NOTEPANEL.add(lblDate);

		lblLiveDate.setLocation(widthMid-200,hightMid-70);
		lblLiveDate.setSize(200,50);
		lblLiveDate.setOpaque(false);
		lblLiveDate.setText("");
		lblLiveDate.setFont(standardFont);
		NOTEPANEL.add(lblLiveDate);

		lblDoctorName.setLocation(widthMid+100,hightMid-70);
		lblDoctorName.setSize(100,50);
		lblDoctorName.setOpaque(false);
		lblDoctorName.setText("Doctor:");
		lblDoctorName.setFont(headingFont);
		NOTEPANEL.add(lblDoctorName);

		lblDoctor.setLocation(widthMid+200,hightMid-70);
		lblDoctor.setSize(150,50);
		lblDoctor.setText("");
		lblDoctor.setOpaque(false);
		lblDoctor.setFont(standardFont);
		NOTEPANEL.add(lblDoctor);

		taNoteBody.setLocation(widthMid-350,hightMid+50);
		taNoteBody.setSize(700,250);
		taNoteBody.setText("");
		taNoteBody.setRows(5);
		taNoteBody.setColumns(5);
		taNoteBody.setVisible(true);
		taNoteBody.setLineWrap(true);
		taNoteBody.setEditable(false);
		taNoteBody.setFont(standardFont);
		taNoteBody.setWrapStyleWord(true);
		taNoteBody.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		NOTEPANEL.add(taNoteBody);

		lblNoteBody.setLocation(widthMid-350,hightMid);
		lblNoteBody.setSize(100,50);
		lblNoteBody.setOpaque(false);
		lblNoteBody.setFont(headingFont);
		lblNoteBody.setText("Note:");
		NOTEPANEL.add(lblNoteBody);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==cbAppointmentSelecter)
		{
			try
			{
				//get appointment from directry of file
				String newDir = aDirectory+"\\"+cbAppointmentSelecter.getSelectedItem().toString();

				//opens new reader objects
				FileReader fr = new FileReader(newDir);
				BufferedReader br = new BufferedReader(fr);

				String aReadLine = br.readLine();
				//reads data in

				String[] splitNoteData = aReadLine.split("\\$");

				//parses data in to the correct label
				lblDoctor.setText(splitNoteData[0]);
				lblLiveDate.setText(splitNoteData[2]);
				taNoteBody.setText(cc.decrypt(splitNoteData[3]));
			}
			catch(Exception ex)
			{
			}
		}
	}

	//Lists the appointments that have notes
	public void findAppointmentDates()
	{		
		File aDirectory = new File("Logs\\"+(id+"")+"\\");//Creates a directory

	   	String[] files = aDirectory.list();//Creats a string array of the note files

	    if(files != null)//If there are notes in the directory then 
	    {
	    	//Create the combo-box
			cbAppointmentSelecter = new JComboBox(files);
			cbAppointmentSelecter.setLocation(widthMid-150,hightMid-150);
			cbAppointmentSelecter.setSize(250,40);
			cbAppointmentSelecter.setEditable(false);
			cbAppointmentSelecter.setVisible(true);
			cbAppointmentSelecter.setFont(standardFont);
			cbAppointmentSelecter.addActionListener(this);
			NOTEPANEL.add(cbAppointmentSelecter);

			NOTEPANEL.repaint();//updates the panel
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(null, "Unable to find notes");
	    	this.setVisible(false);
	    }
	}

}  

/*-This tool allows the receptionists to create patients, in effect, registering them to the GP's surgery.
For super users, they can add doctors and receptionists. This is to prevent misuse of this system and protect
of patients as receptionists shouldn't be able to read patient notes.*/
class UserCreationTool extends JFrame implements ActionListener, MouseListener
{
	//Sets up components and objects aswell as global variables for use in this class
	Font standardFont = new Font("MonoSpaced", Font.PLAIN, 14);
	Font headingFont = new Font("MonoSpaced", Font.BOLD, 16);

	int[] res = getResolution();
	int width = res[0];
	int height = res[1];
	int hightMid = (res[1]/2);
	int widthMid = (res[0]/2);

	JPanel UserCreationPanel = new JPanel(null);
	JButton btnSave = new JButton();

	JRadioButton rbtnPatient = new JRadioButton();
	JRadioButton rbtnReceptionist = new JRadioButton();
	JRadioButton rbtnDoctor = new JRadioButton();

	ImageIcon icon = new ImageIcon("Resources/NHS_125x50.png");
	JLabel banner0 = new JLabel(icon, JLabel.RIGHT);
	JLabel banner1 = new JLabel();

	JLabel divider0 = new JLabel();
	JLabel divider1 = new JLabel();

	JLabel lblTitle = new JLabel();
	String[] cbTitle_data = {"","Mr","Mrs","Miss","Dr"};
	JComboBox cbTitle = new JComboBox(cbTitle_data);
	JLabel lblName = new JLabel();
	JTextField tfForename = new JTextField();
	JTextField tfSurname = new JTextField();
	JLabel lblUsername = new JLabel();
	JTextField tfUsername = new JTextField();
	JLabel lblPassword = new JLabel();
	JPasswordField tfPasswordInitial = new JPasswordField();
	JLabel lblPasswordConfirm = new JLabel();
	JPasswordField tfPasswordConfirmation = new JPasswordField();
	JLabel lblContactNuber = new JLabel();
	JTextField tfContactNumber = new JTextField();
	JLabel lblDOB = new JLabel();
	JTextField tfDOB = new JTextField();
	JLabel lblHouseNumber = new JLabel();
	JTextField tfHouseNumber = new JTextField();
	JLabel lblPostcode = new JLabel();
	JTextField tfPostcode = new JTextField();
	boolean editMode = false;
	Patient searchedPatient;
	int index = 0;
	CaesarCipher cc = new CaesarCipher();

	//Creates a constructer that takes an id 
	public UserCreationTool(String idOfSelection)
	{
		PatientList patList = new PatientList();
		//Creates an instance of patient list and returns the patient from the id
		searchedPatient = patList.returnPatFromId(idOfSelection);
		index = patList.arrayLocationOfPatient(searchedPatient);//returns the index of the user in the array
		editMode = true;

		//builds the gui
		startGUI();

		//Sets visibility of components and assigns data to those labels
		rbtnPatient.setSelected(true);
	    lblTitle.setVisible(false);
	    cbTitle.setVisible(false);
	    lblName.setVisible(true);
	    tfForename.setVisible(true);
	    tfSurname.setVisible(true);
	    lblUsername.setVisible(false);
	    tfUsername.setVisible(false);
	    lblPassword.setVisible(true);
	    tfPasswordInitial.setVisible(true);
	    lblPasswordConfirm.setVisible(true);
	    tfPasswordConfirmation.setVisible(true);
	    lblContactNuber.setVisible(true);
	    tfContactNumber.setVisible(true);
	    lblDOB.setVisible(true);
	    tfDOB.setVisible(true);
	    lblHouseNumber.setVisible(true);
	    tfHouseNumber.setVisible(true);
	    lblPostcode.setVisible(true);
	    tfPostcode.setVisible(true);
	    lblDOB.setText("* Date of birth:");
	    lblContactNuber.setText("* Contact number:");

		tfForename.setText(searchedPatient.forename);
		tfSurname.setText(searchedPatient.surname);
		tfPasswordInitial.setText(searchedPatient.password);
		tfPasswordConfirmation.setText(searchedPatient.password);
		tfContactNumber.setText(searchedPatient.contactNumber);
		tfDOB.setText(searchedPatient.dob);
		tfHouseNumber.setText(searchedPatient.houseNumber);
		tfPostcode.setText(searchedPatient.postcode);
	}

	//Creates a blank constructer
	public UserCreationTool()
	{}

	//This method builds the Jframe and adds the nessecary atrributes as well as runs the method for the component attribute assignment
	public void startGUI()
	{
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		CREATEUserCreationPanel();
		this.add(UserCreationPanel);
		this.setTitle("User Creator");
		this.setSize(width,height);
		this.setForeground( new Color(-16777216));
		UserCreationPanel.setBackground(Color.decode("#ffffff"));
		this.setVisible(true);
		this.setResizable(false);
		this.isUndecorated();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		try
		{
			this.setIconImage(ImageIO.read(new File("Resources/Logo.png")));
		}
		catch(IOException exc)
		{
			JOptionPane.showMessageDialog(null,"Unable to find icon please restart system.");
		}
	}

	//Initialises components and places them where they need to go as well as assigning further attributes such as font
	public void CREATEUserCreationPanel()
	{
		banner0.setLocation(0,0);
		banner0.setSize(res[0],50);
		banner0.setBackground(Color.decode("#005EB8"));
		banner0.setOpaque(true);
		banner0.setVisible(true);
		UserCreationPanel.add(banner0);

		banner1.setLocation(0,res[1]-100);
		banner1.setSize(res[0],100);
		banner1.setBackground(Color.decode("#005EB8"));
		banner1.setOpaque(true);
		banner1.setVisible(true);
		UserCreationPanel.add(banner1);

		divider0.setLocation(widthMid-400,hightMid-400);
		divider0.setSize(800,50);
		divider0.setText("________________________________________________________________________");
		divider0.setFont(headingFont);
		UserCreationPanel.add(divider0);

		divider1.setLocation(widthMid-400,hightMid-350);
		divider1.setSize(800,50);
		divider1.setText("________________________________________________________________________");
		divider1.setFont(headingFont);
		UserCreationPanel.add(divider1);

		rbtnPatient.setLocation(widthMid-200,hightMid-365);
		rbtnPatient.setSize(100,50);
		rbtnPatient.setText("Patient");
		rbtnPatient.setSelected(false);
		rbtnPatient.addMouseListener(this);
		rbtnPatient.setOpaque(false);
		rbtnPatient.setFont(standardFont);
		rbtnPatient.setContentAreaFilled(false);
		UserCreationPanel.add(rbtnPatient);

		rbtnDoctor.setLocation(widthMid-100,hightMid-365);
		rbtnDoctor.setSize(100,50);
		rbtnDoctor.setText("Doctor");
		rbtnDoctor.setSelected(false);
		rbtnDoctor.addMouseListener(this);
		rbtnDoctor.setOpaque(false);
		rbtnDoctor.setContentAreaFilled(false);
		rbtnDoctor.setFont(standardFont);
		UserCreationPanel.add(rbtnDoctor);

		rbtnReceptionist.setLocation(widthMid,hightMid-365);
		rbtnReceptionist.setSize(140,50);
		rbtnReceptionist.setText("Receptionist");
		rbtnReceptionist.setSelected(false);
		rbtnReceptionist.addMouseListener(this);
		rbtnReceptionist.setOpaque(false);
		rbtnReceptionist.setFont(standardFont);
		rbtnReceptionist.setContentAreaFilled(false);
		UserCreationPanel.add(rbtnReceptionist);

		lblTitle.setLocation(widthMid-400,hightMid-300);
		lblTitle.setSize(100,50);
		lblTitle.setText("* Title:");
		lblTitle.setFont(headingFont);
		lblTitle.setVisible(false);
		UserCreationPanel.add(lblTitle);

		cbTitle.setLocation(widthMid-310,hightMid-300);
		cbTitle.setSize(150,50);
		cbTitle.setFont(standardFont);
		cbTitle.setVisible(false);
		UserCreationPanel.add(cbTitle);

		lblName.setLocation(widthMid-400,hightMid-240);
		lblName.setSize(100,50);
		lblName.setFont(headingFont);
		lblName.setText("* Name:");
		lblName.setVisible(false);
		UserCreationPanel.add(lblName);

		tfForename.setLocation(widthMid-380,hightMid-190);
		tfForename.setSize(150,50);
		tfForename.setFont(standardFont);
		tfForename.setColumns(10);
		tfForename.addMouseListener(this);
		tfForename.setVisible(false);
		tfForename.setText("First");
		UserCreationPanel.add(tfForename);

		tfSurname.setLocation(widthMid-220,hightMid-190);
		tfSurname.setSize(150,50);
		tfSurname.setFont(standardFont);
		tfSurname.setColumns(10);
		tfSurname.addMouseListener(this);
		tfSurname.setVisible(false);
		tfSurname.setText("Last");
		UserCreationPanel.add(tfSurname);

		lblUsername.setLocation(widthMid,hightMid-240);
		lblUsername.setSize(150,50);
		lblUsername.setFont(headingFont);
		lblUsername.setText("* Username:");
		lblUsername.setVisible(false);
		UserCreationPanel.add(lblUsername);

		tfUsername.setLocation(widthMid+20,hightMid-190);
		tfUsername.setSize(200,50);
		tfUsername.setFont(standardFont);
		tfUsername.setColumns(10);
		tfUsername.setVisible(false);
		tfUsername.setText("");
		UserCreationPanel.add(tfUsername);

		lblPassword.setLocation(widthMid-400,hightMid-140);
		lblPassword.setSize(150,50);
		lblPassword.setFont(headingFont);
		lblPassword.setText("* Password:");
		lblPassword.setVisible(false);
		UserCreationPanel.add(lblPassword);

		tfPasswordInitial.setLocation(widthMid-380,hightMid-90);
		tfPasswordInitial.setSize(200,50);
		tfPasswordInitial.setFont(standardFont);
		tfPasswordInitial.setColumns(10);
		tfPasswordInitial.setVisible(false);
		tfPasswordInitial.setText("");
		tfPasswordInitial.setEchoChar('*');
		UserCreationPanel.add(tfPasswordInitial);

		lblPasswordConfirm.setLocation(widthMid-400,hightMid-40);
		lblPasswordConfirm.setSize(200,50);
		lblPasswordConfirm.setFont(headingFont);
		lblPasswordConfirm.setText("* Confirm password:");
		lblPasswordConfirm.setVisible(false);
		UserCreationPanel.add(lblPasswordConfirm);

		tfPasswordConfirmation.setLocation(widthMid-380,hightMid+10);
		tfPasswordConfirmation.setSize(200,50);
		tfPasswordConfirmation.setFont(standardFont);
		tfPasswordConfirmation.setColumns(10);
		tfPasswordConfirmation.setVisible(false);
		tfPasswordConfirmation.setText("");
		tfPasswordConfirmation.setEchoChar('*');
		UserCreationPanel.add(tfPasswordConfirmation);

		lblContactNuber.setLocation(widthMid,hightMid+60);
		lblContactNuber.setSize(200,50);
		lblContactNuber.setFont(headingFont);
		lblContactNuber.setVisible(false);
		UserCreationPanel.add(lblContactNuber);

		tfContactNumber.setLocation(widthMid+20,hightMid+110);
		tfContactNumber.setSize(200,50);
		tfContactNumber.setFont(standardFont);
		tfContactNumber.setColumns(10);
		tfContactNumber.setVisible(false);
		tfContactNumber.setText("");
		UserCreationPanel.add(tfContactNumber);

		lblDOB.setLocation(widthMid-400,hightMid+60);
		lblDOB.setSize(250,50);
		lblDOB.setFont(headingFont);
		lblDOB.setVisible(false);
		UserCreationPanel.add(lblDOB);

		tfDOB.setLocation(widthMid-380,hightMid+110);
		tfDOB.setSize(200,50);
		tfDOB.setFont(standardFont);
		tfDOB.setColumns(10);
		tfDOB.setVisible(false);
		tfDOB.setText("dd-mm-yyyy");
		tfDOB.addMouseListener(this);
		UserCreationPanel.add(tfDOB);

		lblHouseNumber.setLocation(widthMid,hightMid-140);
		lblHouseNumber.setSize(250,50);
		lblHouseNumber.setFont(headingFont);
		lblHouseNumber.setText("* House number:");
		lblHouseNumber.setVisible(false);
		UserCreationPanel.add(lblHouseNumber);

		tfHouseNumber.setLocation(widthMid+20,hightMid-90);
		tfHouseNumber.setSize(80,50);
		tfHouseNumber.setFont(standardFont);
		tfHouseNumber.setColumns(10);
		tfHouseNumber.setVisible(false);
		tfHouseNumber.setText("");
		UserCreationPanel.add(tfHouseNumber);

		lblPostcode.setLocation(widthMid,hightMid-40);
		lblPostcode.setSize(250,50);
		lblPostcode.setFont(headingFont);
		lblPostcode.setText("* Postcode:");
		lblPostcode.setVisible(false);
		UserCreationPanel.add(lblPostcode);

		tfPostcode.setLocation(widthMid+20,hightMid+10);
		tfPostcode.setSize(150,50);
		tfPostcode.setFont(standardFont);
		tfPostcode.setColumns(10);
		tfPostcode.setVisible(false);
		tfPostcode.setText("");
		UserCreationPanel.add(tfPostcode);

		btnSave.setLocation(widthMid-100,hightMid+200);
		btnSave.setSize(100,50);
		btnSave.addActionListener(this);
		btnSave.setText("Save");
		btnSave.setFont(standardFont);
		btnSave.setContentAreaFilled(false);
		btnSave.setOpaque(false);
		UserCreationPanel.add(btnSave);

		InteractionMenu inter = new InteractionMenu();
		if(inter.getMode().equals("Receptionist"))
		{
			rbtnReceptionist.setVisible(false);
			rbtnDoctor.setVisible(false);
		}
		else
		{
			rbtnReceptionist.setVisible(true);
			rbtnDoctor.setVisible(true);
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		//If the patient radio button is pressed then show only relevant components and edit the mandatory feilds
		if(e.getSource()==rbtnPatient)
		{
           rbtnReceptionist.setSelected(false);
           rbtnDoctor.setSelected(false);
           lblTitle.setVisible(false);
           cbTitle.setVisible(false);
           lblName.setVisible(true);
           tfForename.setVisible(true);
           tfSurname.setVisible(true);
           lblUsername.setVisible(false);
           tfUsername.setVisible(false);
           lblPassword.setVisible(true);
           tfPasswordInitial.setVisible(true);
           lblPasswordConfirm.setVisible(true);
           tfPasswordConfirmation.setVisible(true);
           lblContactNuber.setVisible(true);
           tfContactNumber.setVisible(true);
           lblDOB.setVisible(true);
           tfDOB.setVisible(true);
           lblHouseNumber.setVisible(true);
           tfHouseNumber.setVisible(true);
           lblPostcode.setVisible(true);
           tfPostcode.setVisible(true);
           lblDOB.setText("* Date of birth:");
           lblContactNuber.setText("* Contact number:");
		}		
		//If the receptionist radio button is pressed then show only relevant components and edit the mandatory feilds
		if(e.getSource()==rbtnReceptionist)
		{
           rbtnPatient.setSelected(false);
           rbtnDoctor.setSelected(false);
           lblTitle.setVisible(true);
           cbTitle.setVisible(true);
           lblName.setVisible(true);
           tfForename.setVisible(true);
           tfSurname.setVisible(true);
           lblUsername.setVisible(true);
           tfUsername.setVisible(true);
           lblPassword.setVisible(true);
           tfPasswordInitial.setVisible(true);
           lblPasswordConfirm.setVisible(true);
           tfPasswordConfirmation.setVisible(true);
           lblContactNuber.setVisible(true);
           tfContactNumber.setVisible(true);
           lblDOB.setVisible(true);
           tfDOB.setVisible(true);
           lblHouseNumber.setVisible(false);
           tfHouseNumber.setVisible(false);
           lblPostcode.setVisible(false);
           tfPostcode.setVisible(false);
           lblDOB.setText("  Date of birth:");
           lblContactNuber.setText("  Contact number:");
		}	
		//If the doctor radio button is pressed then show only relevant components and edit the mandatory feilds
		if(e.getSource()==rbtnDoctor)
		{
           rbtnReceptionist.setSelected(false);
           rbtnPatient.setSelected(false);
           lblTitle.setVisible(true);
           cbTitle.setVisible(true);
           lblName.setVisible(true);
           tfForename.setVisible(true);
           tfSurname.setVisible(true);
           lblUsername.setVisible(true);
           tfUsername.setVisible(true);
           lblPassword.setVisible(true);
           tfPasswordInitial.setVisible(true);
           lblPasswordConfirm.setVisible(true);	
           tfPasswordConfirmation.setVisible(true);
           lblContactNuber.setVisible(true);
           tfContactNumber.setVisible(true);
           lblDOB.setVisible(true);
           tfDOB.setVisible(true);
           lblHouseNumber.setVisible(false);
           tfHouseNumber.setVisible(false);
           lblPostcode.setVisible(false);
           tfPostcode.setVisible(false);
           lblDOB.setText("  Date of birth:");
           lblContactNuber.setText("  Contact number:");
		}	

		//If the forename, surname or date of birth feilds are clicked then the data in that field is emptied
		if(e.getSource()==tfForename)
		{
			tfForename.setText("");
		}

		if(e.getSource()==tfSurname)
		{
			tfSurname.setText("");
		}

		if(e.getSource()==tfDOB)
		{
			tfDOB.setText("");
		}
	}

	public void mouseEntered(MouseEvent e)
	{}
	

	public void mouseExited(MouseEvent e)
	{}
	

	public void mouseReleased(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}

	//Parses the date given to see if its in a valid format 
	public boolean isValid(String value) 
	{
   		try
    	{
    		//tries to convert the passed date to the form of dd-mm-yyyy
            new SimpleDateFormat("dd-MM-yyyy").parse(value);
            return false;
        } 
        catch(Exception e) 
        {
            return true;
        }
    }

    //get resolution of the graphics device 
	public int[] getResolution()
	{
		int[] res = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		res[0] = gd.getDisplayMode().getWidth();
		res[1] = gd.getDisplayMode().getHeight();
		return res;
	}

	//Tests to see if the postcode given matches the standard format for the uk
	public boolean postcodeIsValid(String postcode)
	{
		//Converts the data to uppercase and removes the spaces
		postcode = postcode.toUpperCase();
		postcode = postcode.replaceAll("\\s+",""); 

		char[] letter = postcode.toCharArray();//breaks the string in to a character array
		int length = letter.length;
		boolean valid = true;

		if(length == 6)
		{
			//Checks to see if it follows the format AA00AA
			if(Character.isLetter(letter[0])&&Character.isLetter(letter[1])&&Character.isDigit(letter[2])&&Character.isDigit(letter[3])&&Character.isLetter(letter[4])&&Character.isLetter(letter[5]))
			{
				valid = false;
			}
		}
		else if(length == 7)
		{
			//Checks to see if it follows the format AA000AA
			if(Character.isLetter(letter[0])&&Character.isLetter(letter[1])&&Character.isDigit(letter[2])&&Character.isDigit(letter[3])&&Character.isDigit(letter[4])&&Character.isLetter(letter[5])&&Character.isLetter(letter[6]))
			{
				valid = false;
			}
		}
		return valid;
	}

	public void actionPerformed(ActionEvent e)
	{
		//creates new instamces of patient notes
		StaffList stfList = new StaffList();
		PatientList patList = new PatientList();

		if(e.getSource()==btnSave)
		{
			if(editMode != true)
			{
				//stfList.showUserList();
				//patList.showPatientList();

				//If doctor is being created
				if(rbtnDoctor.isSelected())
				{
					//Validates inputted data for each field 
					if(cbTitle.getSelectedItem().toString().equals("")){JOptionPane.showMessageDialog(null, "Error with title entered, please check feild.");}
					else if(tfUsername.toString().equals("")||tfUsername.getText().length()<2||tfUsername.getText().length()>20){JOptionPane.showMessageDialog(null, "Error with username entered, please check feild.");}
					else if(tfForename.getText().equals("First")||tfForename.getText().equals("")||tfForename.getText().length() > 20 || tfForename.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with forename entered, please check feild.");}
					else if(tfSurname.getText().equals("Last")||tfSurname.getText().equals("")||tfSurname.getText().length() > 20 || tfSurname.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with surname entered, please check feild.");}
					else if(tfPasswordInitial.getText().length()<2||tfPasswordInitial.getText().length()>20||tfPasswordInitial.getText().equals(tfPasswordConfirmation.getText())==false){JOptionPane.showMessageDialog(null, "Error with password entered, please check feilds match.");}
					else if(tfDOB.getText().equals("")&&isValid(tfDOB.getText())){JOptionPane.showMessageDialog(null, "Error with date of birth entered, please check feild.");}
					else if(tfContactNumber.getText().length()<12&&tfContactNumber.getText().length()>1){JOptionPane.showMessageDialog(null, "Error with contact number entered, please check feild.");}
					else
					{
						//Creates a new doctor
						Doctor doc = new Doctor();
						//Passes the data from the feilds to the docotr object
						doc.title = cbTitle.getSelectedItem().toString();
						doc.forename = tfForename.getText(); 
						doc.surname = tfSurname.getText();
						doc.username = tfUsername.getText();
						doc.password = cc.encrypt(tfPasswordConfirmation.getText());
						doc.contactNumber = tfContactNumber.getText();
						if(tfDOB.getText().equals("dd-mm-yyyy"))
						{
							doc.dob = "";	
						}
						else
						{
							doc.dob = tfDOB.getText();
						}
						doc.mode = "Doctor";

						//adss the doctor to the list and updates the file
						stfList.addUserToList(doc);
						stfList.writeUserListToFile();

						JOptionPane.showMessageDialog(null, "Successfully created doctor");//Confirms doctor creation	
						this.setVisible(false);
					}
				}
				else if(rbtnPatient.isSelected())
				{	//Validates inputted data for each field 
					if(tfForename.getText().equals("First")||tfForename.getText().equals("")||tfForename.getText().length() > 20 || tfForename.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with forename entered, please check feild.");}
					else if(tfSurname.getText().equals("Last")||tfSurname.getText().equals("")||tfSurname.getText().length() > 20 || tfSurname.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with surname entered, please check feild.");}
					else if(tfPasswordInitial.getText().length()<2||tfPasswordInitial.getText().length()>20||tfPasswordInitial.getText().equals(tfPasswordConfirmation.getText())==false){JOptionPane.showMessageDialog(null, "Error with password entered, please check feilds match.");}
					else if(tfDOB.getText().equals("dd-mm-yyyy")||tfDOB.getText().equals("")||isValid(tfDOB.getText())){JOptionPane.showMessageDialog(null, "Error with date of birth entered, please check feild.");}
					else if(tfHouseNumber.getText().length()>5||tfHouseNumber.getText().equals("")){JOptionPane.showMessageDialog(null, "Error with house number entered, please check feild.");}
					else if(tfContactNumber.getText().length()!=11||tfContactNumber.getText().equals("")){JOptionPane.showMessageDialog(null, "Error with contact number entered, please check feild.");}
					else if(tfPostcode.getText().equals("")||postcodeIsValid(tfPostcode.getText())){JOptionPane.showMessageDialog(null, "Error with postcode entered, please check feild.");}
					else
					{
						//Creates a new patient
						Patient pat = new Patient();

						//takes data from the feilds and reads it in to the patient object
						pat.patientID = patList.nextAvalibleID();//Runs algorithem to get next avalible ID
						pat.forename = tfForename.getText(); 
						pat.surname = tfSurname.getText();
						pat.username = null;
						pat.password = cc.encrypt(tfPasswordConfirmation.getText());
						pat.houseNumber = tfHouseNumber.getText();
						pat.postcode = tfPostcode.getText();
						pat.contactNumber = tfContactNumber.getText();
						pat.dob = tfDOB.getText();
						pat.mode = "Patient";

						//Patient is written to list and file
						patList.addPatientToList(pat);
						patList.writePatientListToFile();

						//Returns message saying the user login for the patient
						JOptionPane.showMessageDialog(null, "Successfully created patient, ID is: " + pat.patientID);
						this.setVisible(false);
					}
				}
				else if(rbtnReceptionist.isSelected())
				{
					//Validates inputted data for each field 
					if(cbTitle.getSelectedItem().toString().equals("")){JOptionPane.showMessageDialog(null, "Error with title entered, please check feild.");}
					else if(tfUsername.toString().equals("")||tfUsername.getText().length()<2||tfUsername.getText().length()>20){JOptionPane.showMessageDialog(null, "Error with username entered, please check feild.");}
					else if(tfForename.getText().equals("First")||tfForename.getText().equals("")||tfForename.getText().length() > 20 || tfForename.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with forename entered, please check feild.");}
					else if(tfSurname.getText().equals("Last")||tfSurname.getText().equals("")||tfSurname.getText().length() > 20 || tfSurname.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with surname entered, please check feild.");}
					else if(tfPasswordInitial.getText().length()<2||tfPasswordInitial.getText().length()>20||tfPasswordInitial.getText().equals(tfPasswordConfirmation.getText())==false){JOptionPane.showMessageDialog(null, "Error with password entered, please check feilds match.");}
					else if(tfDOB.getText().equals("")&&isValid(tfDOB.getText())){JOptionPane.showMessageDialog(null, "Error with date of birth entered, please check feild.");}
					else if(tfContactNumber.getText().length()<12&&tfContactNumber.getText().length()>1){JOptionPane.showMessageDialog(null, "Error with contact number entered, please check feild.");}
					else
					{
						//creates a new receptionist
						Receptionist rec = new Receptionist();

						//reads data in to receptionist object from the feilds
						rec.title = cbTitle.getSelectedItem().toString();
						rec.forename = tfForename.getText(); 
						rec.surname = tfSurname.getText();
						rec.username = tfUsername.getText();
						rec.password = cc.encrypt(tfPasswordConfirmation.getText());
						rec.contactNumber = tfContactNumber.getText();
						if(tfDOB.getText().equals("dd-mm-yyyy"))
						{
							rec.dob = "";	
						}
						else
						{
							rec.dob = tfDOB.getText();
						}
						rec.mode = "Receptionist";

						//adds receptionist to the list and updates the file
						stfList.addUserToList(rec);
						stfList.writeUserListToFile();

						//confirms receptioist generation
						JOptionPane.showMessageDialog(null, "Successfully created receptionist");
						this.setVisible(false);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please select a user group");
				}
			}
			else
			{
				//Edits the data if the users data is to be edited
				int answer = JOptionPane.showConfirmDialog(null, "Saving now will replace the current user data! \r\n Are you sure you want to edit user details?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(answer==0)
				{
					//validates feilds 
					if(tfForename.getText().equals("First")||tfForename.getText().equals("")||tfForename.getText().length() > 20 || tfForename.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with forename entered, please check feild.");}
					else if(tfSurname.getText().equals("Last")||tfSurname.getText().equals("")||tfSurname.getText().length() > 20 || tfSurname.getText().length() < 2){JOptionPane.showMessageDialog(null, "Error with surname entered, please check feild.");}
					else if(tfPasswordInitial.getText().length()<2||tfPasswordInitial.getText().length()>20||tfPasswordInitial.getText().equals(tfPasswordConfirmation.getText())==false){JOptionPane.showMessageDialog(null, "Error with password entered, please check feilds match.");}
					else if(tfDOB.getText().equals("dd-mm-yyyy")||tfDOB.getText().equals("")||isValid(tfDOB.getText())){JOptionPane.showMessageDialog(null, "Error with date of birth entered, please check feild.");}
					else if(tfHouseNumber.getText().length()>5||tfHouseNumber.getText().equals("")){JOptionPane.showMessageDialog(null, "Error with house number entered, please check feild.");}
					else if(tfContactNumber.getText().length()!=11||tfContactNumber.getText().equals("")){JOptionPane.showMessageDialog(null, "Error with contact number entered, please check feild.");}
					else if(tfPostcode.getText().equals("")||postcodeIsValid(tfPostcode.getText())){JOptionPane.showMessageDialog(null, "Error with postcode entered, please check feild.");}
					else
					{
						//Creates new patient object
						Patient pat = new Patient();

						//Reads data in to the patient object from each feild 
						pat.patientID = searchedPatient.patientID;
						pat.forename = tfForename.getText(); 
						pat.surname = tfSurname.getText();
						pat.username = null;
						pat.password = tfPasswordConfirmation.getText();
						pat.houseNumber = tfHouseNumber.getText();
						pat.postcode = tfPostcode.getText();
						pat.contactNumber = tfContactNumber.getText();
						pat.dob = tfDOB.getText();
						pat.mode = "Patient";

						//performs edit process and checks if sucessful
						if(patList.editData(index,pat))
						{
							JOptionPane.showMessageDialog(null, "Successfully edited data");//returns confirmation of edit
							this.setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error editing data");
						}
					}
				}
			}
		}
	}
}  