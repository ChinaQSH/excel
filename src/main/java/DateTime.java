import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *
 * @author: qushihao
 * @Date: 2022-02-14 14:33
 * @Description:
 */

public class DateTime {
    public static void main(String[] args) {
        Date dateTime = new Date();
        long time = dateTime.getTime();
        System.out.println(time);
        long times=1644818630638L;
        Date date = new Date(times);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:hh");
        String format = simpleDateFormat.format(date);
        System.out.println(format);


    }
}
