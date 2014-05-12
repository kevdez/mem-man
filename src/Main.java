import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		// if there are arguments, 2 are required, for input and output files
		if(args.length > 0)
		{
			Scanner scan;
			//scan = new Scanner(new File(args[0]));
			MemoryManager mm = null;
			Driver dr = new Driver(Double.valueOf(args[0]), Double.valueOf(args[1]), args[2], mm);
			dr.run();
			
			System.exit(0);
		}
		else
		{
			System.out.println("Insufficient arguments");
			System.exit(0);
			
		}
		
	}

}
