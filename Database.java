package WellbeingCounter;

import java.io.File;
import java.util.*;

public class Database implements Comparator<String> {

    // database will store each day and all the apps used that day.
    //list stores all the days with an array having all the apps with their times
    private final HashMap<Date, ArrayList<AppData>> list;
    //appTime stores the total time an app was used
    private HashMap<String, Integer> appTime;
    private final HashMap<String, Integer> gameTime;
    private final HashMap<String, Integer> codeTime;

    public Database() {
        this.appTime = new HashMap<>();
        this.list = new HashMap<>();
        this.codeTime = new HashMap<>();
        this.gameTime = new HashMap<>();
    }

    public void add(Date date, ArrayList<AppData> appList) {
        list.put(date, appList);
    }


    //adds to the list hashmap the date and all the apps used that day
    public void listReader(ArrayList<File> list) {
        for (File loop : list) {
            this.add(FileReader.getDate(loop.getName()), fileContent(loop));

        }
    }

    //creates a arrayList of appData type and returns it.
    public ArrayList<AppData> fileContent(File file) {
        ArrayList<AppData> appList = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split("\t");
                appList.add(new AppData(split[0], Integer.valueOf(split[1])));
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        appList.sort(Comparator.comparing(AppData::getAppName, String.CASE_INSENSITIVE_ORDER));
        return appList;
    }

    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (Date loop : list.keySet()) {
            answer.append("\n").append(loop).append("--\n").append(list.get(loop)).append("\n");
        }

        return answer.toString();
    }

    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }

    // create a function which will display the top 5 or 10 or however much
    // specified apps used in that time period
    public void appNameList() {
        for (ArrayList<AppData> loop : list.values()) {
            for (AppData appLoop : loop) {
                add(appLoop);
            }
        }
    }

    public void add(AppData app) {
        if (appTime.containsKey(app.getAppName())) {
            Integer appTimeAdd = appTime.get(app.getAppName()) + app.getAppTime();
            appTime.put(app.getAppName(), appTimeAdd);

        } else {
            appTime.putIfAbsent(app.getAppName(), app.getAppTime());

        }
    }

    public void displayTopApp() {
        appNameList();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(this.appTime.entrySet());

        // Step 2: Sort the List based on the integer values
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Step 3: Create a new LinkedHashMap to maintain the order of sorted entries
        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();

        // Step 4: Populate the LinkedHashMap with sorted entries
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        this.appTime = sortedHashMap;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the limit in hours...");
        int timeLimit = 6;
        String input;
        input = scanner.nextLine();
        if (!(input.isEmpty())) {
            timeLimit = Integer.parseInt(input);
        }
        int totalHours = 0;
        for (String loop : sortedHashMap.keySet()) {
            if (sortedHashMap.get(loop) >= setLimit(timeLimit)) {
                System.out.print("\n" + loop + " = " + (sortedHashMap.get(loop) / 3600) + " hrs");
                totalHours += sortedHashMap.get(loop) / 3600;

                if (sortedHashMap.get(loop) > 86400) {
                    System.out.print(" / ");
                    System.out.printf("%.2f", sortedHashMap.get(loop) / 86400.00);
                    System.out.print(" Days");
                }

            }
        }
        System.out.println("\n Total Time was: " + totalHours + " Hours Or " + totalHours / 24 + " Days!");

        System.out.println(codeAndGameTime());

    }

    public String codeAndGameTime() {
        ArrayList<String> gameList = new ArrayList<>(Arrays.asList("Borderlands2", "JustCause3", "Dishonored", "ShippingPC-BmGame", "TombRaider", "MW2CR", "forzahorizon5", "Cities", "hl2", "BioshockHD"));
        ArrayList<String> codeList = new ArrayList<>(Arrays.asList("chrome", "Code", "studio64", "idea64"));
        String codeReturn = "";
        String gameReturn = "";
        int totalGameTime = 0;
        int totalCodeTime = 0;

        for (String loop : appTime.keySet()) {
            int time = appTime.get(loop) / 3600;
            if (gameList.contains(loop)) {
                this.gameTime.put(loop, time);
                gameReturn += (loop + " " + time + "\n");
                totalGameTime += time;


            } else if (codeList.contains(loop)) {
                this.codeTime.put(loop, time);
                codeReturn += (loop + " " + time + "\n");
                totalCodeTime += time;
            }
        }

        return "\nTotal Coding Time is ---> " + totalCodeTime + " hours OR " + totalCodeTime/24 + " Days" + "\n" + "\nTotal Gaming Time is ---> " + totalGameTime + " hours OR "  + totalGameTime/24 + " Days";
    }


    public int setLimit(int limit) {
        return limit * 3600;
    }


}
