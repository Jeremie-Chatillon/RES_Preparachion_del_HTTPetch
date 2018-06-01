import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Horloge implements Runnable{
    static private Date currentDate;
    static private DateFormat format;
    private int secondes;
    private int minutes;
    private int heures;

    public Horloge() {
        currentDate = new Date();
        format = new SimpleDateFormat("HH:mm:ss");
        setVariables(GetDateFormatString());
    }

    public String GetDateFormatString(){
       return format.format(currentDate);
    }

    public void SetDateFormatString(String newTime){
        setVariables(newTime);
        try {
            currentDate = format.parse(newTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private  void setVariables(String newTime){
        String time[] = newTime.split(":");
        heures = Integer.parseInt(time[0]);
        minutes = Integer.parseInt(time[1]);
        secondes = Integer.parseInt(time[2]);
    }

    public  void run(){
        while (true) {
            if (secondes == 59) {
                secondes = 0;

                if (minutes == 59) {
                    minutes = 0;
                    if (heures == 11) {
                        heures = 0;
                    } else {
                        heures++;
                    }
                } else {
                    minutes++;
                }
            } else {
                secondes++;
            }

            String newDate = String.valueOf(heures) + ":" + String.valueOf(minutes) + ":" + String.valueOf(secondes);
            SetDateFormatString(newDate);
            System.out.println(currentDate.toString());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
