import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
public class ManageDate {
    private String start;
    private String end;
    List<String> startEndDates;

    public ManageDate(String start, String end) {
        this.start = start;
        this.end = end;
        this.startEndDates = new ArrayList<>();
    }

    // start에서 end까지 날짜 List 반환
    public List getStartAndEndDate(){
        Calendar startDate = Calendar.getInstance();
        String[] tmpStart = this.start.split("\\.");
        startDate.set(
                Integer.parseInt(tmpStart[0]),
                Integer.parseInt(tmpStart[1])-1,
                Integer.parseInt(tmpStart[2]));

        Calendar endDate = Calendar.getInstance();
        String[] tmpEnd = this.end.split("\\.");
        endDate.set(
                Integer.parseInt(tmpEnd[0]),
                Integer.parseInt(tmpEnd[1])-1,
                Integer.parseInt(tmpEnd[2])-1);
//
        Calendar currentDate = (Calendar) startDate.clone();
        while (!currentDate.after(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String formattedDate = sdf.format(currentDate.getTime());

            // 예약 희망 날짜
            System.out.println(formattedDate);
            this.startEndDates.add(formattedDate);
            currentDate.add(Calendar.DATE, 1);
        }
        return this.startEndDates;
    }

    // 예약 가능 여부 검사
    public boolean isPossibleReservation(Map<String, Boolean> reservationDates){
        for (String date : this.startEndDates){
            if(reservationDates.containsKey(date)){
                return false;
            }
        }
        return true;
    }

}
