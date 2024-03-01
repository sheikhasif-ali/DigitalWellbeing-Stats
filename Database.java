package WellbeingCounter;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/* database will store each day and all the apps used that day.
it also contains methods to display topApps according to the time criteria.*/
public class Database implements Comparator<String> {

    //list stores all the days with an array having all the apps with their times
    private final HashMap<Date, ArrayList<AppData>> list;
    //totalAppTime stores the name of the app along with total time an app was used
    private HashMap<String, Integer> totalAppTime;
    private int timeLimit = 0;


    public Database() {
        this.totalAppTime = new HashMap<>();
        this.list = new HashMap<>();

    }

    public void add(Date date, ArrayList<AppData> appList) {
        list.put(date, appList);
    }


    //adds to the list hashmap the date and all the apps used that day in order to populate it
    public void listReader(ArrayList<File> list) {
        //this used (File loop : list) but it caused a IndexOutOfBound error so replaced it
//        for (int i = 0; i < list.size() - 2; i++) {
//            this.add(FileReader.getDate(list.get(i).getName()), fileContent(list.get(i)));
//
//        }
        for (File loop : list) {
            this.add(FileReader.getDate(loop.getName()), fileContent(loop));

        }
        //this will populate the list hashmap
//        appNameList();
    }

    //creates a arrayList of appData type of an particular date and returns it. So it contains all the apps used in a single day and how much it was used.
    public ArrayList<AppData> fileContent(File file) {
        ArrayList<AppData> appList = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split("\t");
                appList.add(new AppData(split[0], Integer.valueOf(split[1])));
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage() + "\n " + Arrays.toString(e.getStackTrace()) + "\n Error In: " + file.getName());
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

    // todo: create a function which will display the top 5 or 10 or however much


    //this funtion will add the total time an app was used for every app
    public void appNameList() {
        for (ArrayList<AppData> loop : list.values()) {
            for (AppData app : loop) {
                if (totalAppTime.containsKey(app.getAppName())) {
                    totalAppTime.put(app.getAppName(), totalAppTime.get(app.getAppName()) + app.getAppTime());

                } else {
                    totalAppTime.putIfAbsent(app.getAppName(), app.getAppTime());

                }
            }
        }
    }

    public void displayTopApp() {
        appNameList();
//        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(this.totalAppTime.entrySet());
//
//        // Step 2: Sort the List based on the integer values
//        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
//
//        // Step 3: Create a new LinkedHashMap to maintain the order of sorted entries
//        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
//
//        // Step 4: Populate the LinkedHashMap with sorted entries
//        for (Map.Entry<String, Integer> entry : entryList) {
//            sortedHashMap.put(entry.getKey(), entry.getValue());
//        }

        LinkedHashMap<String, Integer> sortedHashMap = totalAppTime.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));


        this.totalAppTime = sortedHashMap;

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
        //totalHours means the Total Time for all the apps fitting in the date range
        System.out.println("\n\nApps were used for: " + totalHours + " Hours Or " + totalHours / 24 + " Days!");
        System.out.println(codeAndGameTime());

    }

    public String codeAndGameTime() {
        ArrayList<String> gameList = new ArrayList<>(Arrays.asList("RDR2", "sekiro", "ride5-Win64-Shipping", "Borderlands2", "JustCause3", "Dishonored", "ShippingPC-BmGame", "TombRaider", "MW2CR", "forzahorizon5", "Cities", "hl2", "BioshockHD"));
        ArrayList<String> codeList = new ArrayList<>(Arrays.asList("chrome", "Code", "studio64", "idea64"));
        String codeReturn = "";
        String gameReturn = "";
        int totalGameTime = 0;
        int totalCodeTime = 0;

        for (String loop : totalAppTime.keySet()) {
            int time = totalAppTime.get(loop) / 3600;
            if (gameList.contains(loop)) {
//                this.gameTime.put(loop, time);
                gameReturn += (loop + " " + time + "\n");
                totalGameTime += time;


            } else if (codeList.contains(loop)) {
//                this.codeTime.put(loop, time);
                codeReturn += (loop + " " + time + "\n");
                totalCodeTime += time;
            }
        }

        //these times will ouput total time used and doesn't see for any date ranges
        return "\nTotal Coding Time is ---> " + totalCodeTime + " hours OR " + totalCodeTime / 24 + " Days" + "\n" + "\nTotal Gaming Time is ---> " + totalGameTime + " hours OR " + totalGameTime / 24 + " Days";
    }

    public void getLimit(String limit) {
        if (limit.isBlank()) {
            this.timeLimit = 0;

        } else {
            this.timeLimit = Integer.parseInt(limit);
        }

    }

    public int setLimit(int limit) {
        return limit * 3600;
    }


}
