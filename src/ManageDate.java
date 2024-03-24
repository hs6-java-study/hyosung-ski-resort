import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
public class ManageDate {
    private String start;
    private String end;
    List<Calendar> startEndDates;


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
                Integer.parseInt(tmpStart[1]),
                Integer.parseInt(tmpStart[2]));

        Calendar endDate = Calendar.getInstance();
        String[] tmpEnd = this.end.split("\\.");
        endDate.set(
                Integer.parseInt(tmpEnd[0]),
                Integer.parseInt(tmpEnd[1]),
                Integer.parseInt(tmpEnd[2])-1);
//
        Calendar currentDate = (Calendar) startDate.clone();
        while (!currentDate.after(endDate)) {
            this.startEndDates.add((Calendar) currentDate.clone());
            currentDate.add(Calendar.DATE, 1);
        }
        return this.startEndDates;
    }

    // 유효성 검사
    // start에서 end까지 날짜 for으로 하나하나 빼면서
    // room객체의 dates 필드의 key값 여부 확인
    // 날짜 모두 없으면은 key_value 쌍으로 삽입
    // return true/false

    public boolean isPossibleReservation(Map<Calendar, Boolean> reservationDates){
        for (Calendar date : this.startEndDates){
            System.out.println(date.getTime() + " == " + reservationDates.get(date));
            if(reservationDates.containsKey(date)){
                return false;
            }
        }
        return true;
    }

}
