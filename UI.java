package WellbeingCounter;

import java.util.Scanner;

public class UI {
    public void start() {

        FileReader read = new FileReader();

        // getting start and end date

        String startDate, endDate;
        Scanner reader = new Scanner(System.in);
        System.out.println("Input initial date in format MM-dd-yyyy");
        startDate = reader.nextLine();
        System.out.println("Input end date in format MM-dd-yyyy");
        endDate = reader.nextLine();

        read.createList(startDate, endDate);

        Database database = new Database();
        database.listReader(read.getList());
        database.displayTopApp();
    }
}
