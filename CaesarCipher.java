import java.util.Scanner;

public class CaesarCipher
{
    //Sets the number of characters to shift
    static int shift = 13;

    public static void main(String[] args)
    {
        //Allows testing of the encryption algorithem
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        //Returns the encrypted form of the data and the decrypted form
        System.out.println(encrypt(str));
        System.out.println(decrypt(encrypt(str)));

        //Closes input stream
        in.close();
    }

    //Method responsible for encrypting that takes the string to be enrypted as a parameter
    public static String encrypt(String str)
    {
        //Instanciates sting builder
        StringBuilder strBuilder = new StringBuilder();
        char c;//sets up an empty character
        //Loops through the string for each letter
        for (int i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);//assignes that blank character as the letter of the string that its on
            
            if (Character.isLetter(c))//deduces if the letter is a character
            {
            c = (char) (str.charAt(i) + shift);//applies the shift to it
           
            if ((Character.isLowerCase(str.charAt(i)) && c > 'z')|| (Character.isUpperCase(str.charAt(i)) && c > 'Z'))

                c = (char) (str.charAt(i) - (26 - shift));//If the charater is a x it loops back through the alphabet
            }
            strBuilder.append(c);//It then adds it to the string builder
        }
        return strBuilder.toString();//returns the encrypted string
    }

     //Method responsible for decrypting that takes the string to be enrypted as a parameter, it is identical to the previous method as it shifts the characters 13 along to return to the original value
    public static String decrypt(String str)
    {
        StringBuilder strBuilder = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            
            if (Character.isLetter(c))
            {
            c = (char) (str.charAt(i) + shift);
           
            if ((Character.isLowerCase(str.charAt(i)) && c > 'z')|| (Character.isUpperCase(str.charAt(i)) && c > 'Z'))

                c = (char) (str.charAt(i) - (26 - shift));
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }
}