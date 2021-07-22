package Calendar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TextManipulate 
{
	/**
	 * Takes temp.txt and copies it to output.txt while ignoring events specified on delete.txt
	 */
	public void delete() throws IOException
	{
		PrintWriter pw = new PrintWriter("output.txt");
        BufferedReader br1 = new BufferedReader(new FileReader("temp.txt"));
        String line1 = br1.readLine();
        while(line1 != null)
        {
            boolean check = false;
            BufferedReader br2 = new BufferedReader(new FileReader("delete.txt"));
            String line2 = br2.readLine();
            while(line2 != null)
            {
                if(line1.equals(line2)) // Checking when delete.txt line matches temp.txt line (events which are deleted)
                {
                    check = true;
                    break;
                }               
                line2 = br2.readLine();
            }
            br2.close();
            if(!check)
            {
                pw.println(line1); // Prints out all the non deleted events into output.txt
            } 
            line1 = br1.readLine();
        }        
        pw.flush();
        br1.close();
        pw.close();
	}
	/**
	 * Takes events.txt and copies it onto temp.txt
	 */
	public void load() throws IOException
	{
		PrintWriter pw = new PrintWriter("temp.txt");
		BufferedReader br1 = new BufferedReader(new FileReader("events.txt"));
		String line1 = br1.readLine();
        while(line1 != null)
        {
        	pw.println(line1);
            line1 = br1.readLine();             
        }
        
        pw.flush();
        br1.close();
        pw.close();
	}
	/**
	 * Checks whether temp.txt and delete.txt exists. If temp.txt doesn't exist, events.txt is copied to output.txt
	 * If temp.txt exists but delete.txt doesn't exist, events.txt is copied to output.txt
	 */
	public void filecheck() throws IOException
	{
		File f = new File("temp.txt");
		File f1 = new File("delete.txt");
		if (!f.exists())
		{
			PrintWriter pw = new PrintWriter("output.txt");
			BufferedReader br1 = new BufferedReader(new FileReader("events.txt"));
			String line1 = br1.readLine();
	        while(line1 != null)
	        {
	        	pw.println(line1);
	            line1 = br1.readLine();             
	        }
	        
	        pw.flush();
	        br1.close();
	        pw.close();
		}
		else
		{
			if(!f1.exists())
			{
				PrintWriter pw = new PrintWriter("output.txt");
				BufferedReader br1 = new BufferedReader(new FileReader("temp.txt"));
				String line1 = br1.readLine();
		        while(line1 != null)
		        {
		        	pw.println(line1);
		            line1 = br1.readLine();             
		        }
		        
		        pw.flush();
		        br1.close();
		        pw.close();
			}
		}
	}
}
