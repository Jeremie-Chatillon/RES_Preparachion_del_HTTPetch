import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Horloge {
    static private Date currentDate;
    private DateFormat format;

    public Horloge() {
        format = new SimpleDateFormat("HH:mm:ss");
        currentDate= new Date();
    }

    public String GetDateFormatString(){
       return format.format(currentDate);
    }

    public void SetDateFormatString(String newTime){
        try {
            currentDate = format.parse(newTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
