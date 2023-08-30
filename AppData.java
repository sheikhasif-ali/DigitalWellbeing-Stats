package WellbeingCounter;

public class AppData {

    private final String appName;
    private final Integer appTime;

    public AppData(String name, Integer time) {
        this.appName = name;
        this.appTime = time;
    }

    public String getAppName() {
        return appName;
    }

    public Integer getAppTime() {
        return appTime;
    }

    public String toString() {
        return "\n" + appName + ":" + appTime;
    }

}