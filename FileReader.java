package WellbeingCounter;

import java.text.*;
import java.util.*;
import java.io.File;

//this class main goal is to create a list containing all the files which fall inside the dates specified by the user in the directory
public class FileReader {
    private ArrayList<File> list;

    public FileReader() { 
        this.list = new ArrayList<>();
    }

    //creating a list of all the dates in the directory. parameters like start and end are taken.
    public void createList(String initialFile, String endFile) throws ParseException {// initialFile and endFile are initial and end dates

        //change your file here to use it yourself
        File directory = new File("C:/Users/sheik/AppData/Local/digital-wellbeing/dailylogs");

        File[] dirList = directory.listFiles();

        if (initialFile.isEmpty() && endFile.isEmpty()) {

            initialFile = "01-01-2023";
            endFile = today();
            System.out.println(initialFile);
            System.out.println(endFile);

        } else if (initialFile.isEmpty()) {
            initialFile = "01-01-2023";
            System.out.println(initialFile);
            System.out.println(endFile);

        } else if (endFile.isEmpty()) {
            endFile = today();
            System.out.println(initialFile);
            System.out.println(endFile);

        }
        //after the list with the filename is created filter() is called.

        filter(dirList, initialFile, endFile);

    }


    //filter() takes as argument the list containing file names, start and end date and inserts them to the filtered "list"
    public void filter(File[] array, String start, String end) {
        //array contains all the files in it
        ArrayList<File> directoryList = new ArrayList<>();
        for (File name : array) {
            Date tempDate = getDate(name.getName());
            if ((tempDate.after(getDate(start))) && (tempDate.before(getDate(end))) || tempDate.equals(getDate(start)) || tempDate.equals(getDate(end))) {
                list.add(name);
            }
        }
    }

    //this function returns today's date in a formatted way
    public String today() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String formattedMonth = month < 10 ? "0" + month : String.valueOf(month);
        String formattedDay = day < 10 ? "0" + day : String.valueOf(day);

        return formattedMonth + "-" + formattedDay + "-" + year;

    }
    // these methods convert the filename into a date object for easier use.
    public static Date getDate(String stringDate) {
        stringDate = stringDate.substring(0, 10);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);

        } catch (Exception e) {
            System.out.println("Error--" + e.getMessage());
        }
        return date;

    }


    //returns the list containing all the files of the directory
    public ArrayList<File> getList() {
        return this.list;
    }
}
