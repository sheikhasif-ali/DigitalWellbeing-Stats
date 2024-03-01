package WellbeingCounter;

import java.text.ParseException;
import java.util.Scanner;

public class UI {
    public void start() throws ParseException {

        Scanner reader = new Scanner(System.in);

        FileReader read = new FileReader();
        // getting start and end date

        String startDate, endDate;
        System.out.println("Input initial date in format MM-dd-yyyy");
        startDate = reader.nextLine();
        System.out.println("Input end date in format MM-dd-yyyy");
        endDate = reader.nextLine();

        read.createList(startDate, endDate);

        Database database = new Database();
        database.listReader(read.getList());
    
        System.out.println("Enter the limit in hours...");
        database.getLimit(reader.nextLine());


        database.displayTopApp();
    }
}
