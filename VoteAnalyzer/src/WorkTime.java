import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

public class WorkTime {
    private HashSet<TimePeriod> periods;

    public WorkTime() {
        periods = new HashSet<>();
    }

    public void addTime(String time) {
        TimePeriod newPeriod = null;
        try {
            newPeriod = new TimePeriod(time);
            for (TimePeriod period : periods) {
                if (newPeriod.equalDay(period)) {
                    period.updatePeriod(newPeriod);
                    return;
                }
            }
            periods.add(newPeriod);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String line = "";
        for (TimePeriod period : periods) {
            if (!line.isEmpty()) {
                line += ", ";
            }
            line += period;
        }
        return line;
    }
}
