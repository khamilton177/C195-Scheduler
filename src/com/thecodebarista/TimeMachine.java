package com.thecodebarista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Generic Functional Interface - Utilized by lambda expressions to calculate time conversion for scheduler.
 *
 */
@FunctionalInterface
//public interface TimeMachine<T> {
public interface TimeMachine {

    //T timeConverter();
    // LocalDateTime getLDT(LocalDate ldt, LocalTime lt);
    LocalDateTime makeLDT();
            /*        LocalTime lt = ltInput(StartTimeHrs.getValue(), StartTimeMins.getValue());
                    System.out.println("In doCalc #3.1 " + lt);
                    // StartTime = getLDT(lt.getHour(), lt.getMinute());
                    StartTime = lt.atDate(ApptStart_DatePick.getValue());
                    Timestamp tsStart = Timestamp.valueOf(StartTime);
                    String tsStartFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tsStart);
                    start_TxtFld.setText(tsStartFormatted);
                    System.out.println("In doCalc #3.2 " + StartTime);
                    ZonedDateTime locZdt = ZonedDateTime.of(StartTime, ZoneId.of(static_ZoneId));
                    ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
                    System.out.println("In doCalc #3.3 -Local time: " + locZdt);
                    System.out.println("In doCalc #3.3 -UTC time: " + utcZdt);
                    System.out.println("In doCalc #3.4 ");*/

/*                    StartTime = calculateStartDt();
                    EndTime = StartTime.plusMinutes(DurationCB.getValue());
                    Timestamp tsEnd = Timestamp.valueOf(EndTime);
                    String tsEndFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tsEnd);
                    end_TxtFld.setText(tsEndFormatted);*/
}
