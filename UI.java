package WellbeingCounter;

import java.text.ParseException;
import java.util.Scanner;

public class UI {
    public void start() throws ParseException {

        FileReader read = new FileReader();
        String[] dates = range(new Scanner(System.in));
        read.createList(dates[0],dates[1]);
//        read.createList("01-01-2023", "10-26-2023");

        Database database = new Database();
        database.listReader(read.getList());
        database.displayTopApp();
    }

    public String[] range(Scanner reader) {
        // getting start and end date

        System.out.println("Input initial date in format MM-dd-yyyy");
        String startDate = reader.nextLine();
        System.out.println("Input end date in format MM-dd-yyyy");
        String endDate = reader.nextLine();

        return new String[]{startDate,endDate};
    }
}
