package WellbeingCounter;

import java.text.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Date;

//this class main goal is to create a list containing all the files in the directory
public class FileReader {
    private ArrayList<File> list;

    public FileReader() { // you removed String file from the argument of the constructor
        this.list = new ArrayList<>();
    }

    //creating a list of all the dates in the directory. parameters like start and end are taken.
    public void createList(String initialFile, String endFile) throws ParseException {// initialFile and endFile are initial and end dates

        //change your file here to use it yourself
        File directory = new File("C:/Users/Asif/AppData/Local/digital-wellbeing/dailylogs");

        File[] dirList = directory.listFiles();
        assert dirList != null;
        String[] date = dates(dirList);


        if (initialFile.isEmpty() && endFile.isEmpty()) {

            initialFile = date[0];
            endFile = date[1];
            System.out.println(initialFile);
            System.out.println(endFile);

        } else if (initialFile.isEmpty()) {
            initialFile = date[0];
            System.out.println(initialFile);
            System.out.println(endFile);

        } else if (endFile.isEmpty()) {
            endFile = date[1];
            System.out.println(initialFile);
            System.out.println(endFile);

        }
        //after the list with the filename is created filter() is called.
        System.out.println("early file is" + initialFile);
        System.out.println("latest file is" + endFile);
        filter(dirList, initialFile, endFile);

    }

    //filter() takes as argument the list containing file names, start and end date
    public void filter(File[] array, String start, String end) {
        //array contains all the files in it
        ArrayList<File> directoryList = new ArrayList<>();
        for (File name : array) {
            Date tempDate = getDate(name.getName());
//            System.out.println("start in filter is" + getDate(start));
            if ((tempDate.after(getDate(start))) && (tempDate.before(getDate(end))) || tempDate.equals(getDate(start)) || tempDate.equals(getDate(end))) {
                directoryList.add(name);
            }
        }
        this.list = directoryList;
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

    //as windows has a weird sorting of files for dates, this functtion will find the earliest and latest date available
    public String[] dates(File[] list) {
        Date earliest;
        Date latest;
        earliest = getDate(list[0].getName());
        latest = getDate(list[0].getName());

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        for (int i = 0; i < list.length-1; i++) {
            try{
            Date tempDate = getDate(list[i].getName());

            if (earliest.after(tempDate)) {
                earliest = tempDate;
            }
            if (latest.before(tempDate)) {
                latest = tempDate;
            }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("lmaoe" + e.getMessage());
            }


        }
        return new String[]{formatter.format(earliest), formatter.format(latest)};

    }

    //returns the list containing all the files of the directory
    public ArrayList<File> getList() {
        return this.list;
    }
}
