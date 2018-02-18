import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String file = "";
		
		Scanner sn = new Scanner(System.in);
		boolean exit = false;
		int option;
		CsvImporter importer = null;
		String currentPath = new File(".").getAbsolutePath();

		while(!exit){
			System.out.println("1. Load initial bulk");
			System.out.println("2. Process updatefile");
	        System.out.println("3. Quit");
	        
	        try{
		        System.out.println("Choose an option");
		        option = sn.nextInt();
		        sn.nextLine();
		        switch(option){
	               case 1:
	            	   System.out.println("Enter filename here : ");
	            	   String initialfilename = sn.nextLine();
	            	   System.out.println("Importing initial load...");
	                   importer = createCsvImporter();
	                   file =  new File(new File(currentPath).getPath(), "data\\" + initialfilename).toString();
	                   importer.InitialBulk(file);
	                   System.out.println("Imported!");
	                   System.out.println("Fertig!");
	                   break;
	               case 2:
	            	   System.out.println("Enter filename here : ");
	            	   String updatefilename = sn.nextLine();
	            	   System.out.println("Importing update file...");
	                   importer = createCsvImporter();
	                   file =  new File(new File(currentPath).getPath(), "data\\" + updatefilename).toString();
	                   importer.ProcessUpdateFile(file);
	                   System.out.println("Imported!");
	                   System.out.println("Fertig!");
	                   break;
	                case 3:
	                   exit=true;
	                   break;
	                default:
	                   System.out.println("Only options between 1 and 3");
		        }
	        } catch (InputMismatchException e) {
                System.out.println("You should input a digit!");
                sn.next();
            }
           }
	}
	
	private static CsvImporter createCsvImporter()
	{
		CustomerDao customerDao = new CustomerDao();
		PaymentformDao paymentformDao = new PaymentformDao();
		PaymentplanDao paymentplanDao = new PaymentplanDao();
		return new CsvImporter(customerDao, paymentformDao, paymentplanDao);
	}
	
}
