package WellbeingCounter;

import java.util.ArrayList;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//this class main goal is to create a list containing all the files in the directory
public class FileReader {
    private ArrayList<File> list;

    public FileReader() { // you removed String file from the argument of the constructor
        this.list = new ArrayList<>();
    }

    //creating a list of all the dates in the directory. parameters like start and end are taken.
    public void createList(String initialFile, String endFile) {// initialFile and endFile are initial and end dates

        //change your file here to use it yourself
        File directory = new File("C:/Users/Asif/AppData/Local/digital-wellbeing/dailylogs");

        File[] dirList = directory.listFiles();
        if (initialFile.isEmpty() && endFile.isEmpty()) {
            initialFile = dirList[0].getName();
            endFile = dirList[dirList.length - 1].getName();
            System.out.println(initialFile);
            System.out.println(endFile);

        } else if (initialFile.isEmpty()) {
            initialFile = dirList[0].getName();
            System.out.println(initialFile);

        } else if (endFile.isEmpty()) {
            endFile = dirList[dirList.length - 1].getName();
            System.out.println(endFile);

        }
        //after the list with the filename is created filter() is called.
        filter(dirList, initialFile, endFile);

    }

//filter() takes as argument the list containing file names, start and end date
    public void filter(File[] array, String start, String end) {
        ArrayList<File> directoryList = new ArrayList<>();
        for (File name : array) {
            Date tempDate = getDate(name.getName());
            if ((tempDate.after(getDate(start))) && (tempDate.before(getDate(end))) || tempDate.equals(getDate(start)) || tempDate.equals(getDate(end))) {
                directoryList.add(name);
            }
        }
        this.list = directoryList;
    }

    // these methods convert the filename into a date object for easier use.
    public static Date getDate(String stringDate) {
        stringDate = stringDate.substring(0, 10);
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return date;

    }

    //returns the list containing all the files of the directory
    public ArrayList<File> getList() {
        return this.list;
    }
}
