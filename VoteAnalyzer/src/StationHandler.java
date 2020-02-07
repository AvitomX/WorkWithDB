import java.util.HashMap;

public class StationHandler {
    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();

    public static void defineStationWorkTime(Integer station, String time) {
        WorkTime tempWorkTime = voteStationWorkTimes.get(station);
        if (tempWorkTime == null) {
            tempWorkTime = new WorkTime();
            tempWorkTime.addTime(time);
            voteStationWorkTimes.put(station, tempWorkTime);
        } else {
            tempWorkTime.addTime(time);
        }
    }

    public static void printStationTime() {
        System.out.println("Voting station work times: ");
        for (Integer votingStation : voteStationWorkTimes.keySet()) {
            WorkTime workTime = voteStationWorkTimes.get(votingStation);
            System.out.println("\t" + votingStation + " - " + workTime);
        }
    }
}
