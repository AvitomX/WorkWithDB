import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePeriod
{
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private long from;
    private long to;
    private String time;
    private Date dayDate;

    public TimePeriod(String time) throws ParseException {
        this.time = time;
        this.dayDate = dayFormat.parse(time);
        this.from = dateFormat.parse(time).getTime();
        this.to = this.from;
    }


    public boolean equalDay(TimePeriod period) {
        return this.dayDate.equals(period.getDayDate());
    }

    public void updatePeriod(TimePeriod period) {
        if (this.from > period.getFrom())
            this.from = period.getFrom();
        if (this.to < period.getTo())
            this.to = period.getTo();
    }

    public Date getDayDate() {
        return dayDate;
    }

    public String getTime() {
        return time;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public String toString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String from = dateFormat.format(this.from);
        String to = timeFormat.format(this.to);
        return from + "-" + to;
    }
}
