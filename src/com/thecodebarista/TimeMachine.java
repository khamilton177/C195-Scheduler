package com.thecodebarista;

import java.time.*;

import static com.thecodebarista.controller.LoginFormCtrl.static_ZoneId;

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

        default void convertBusinessHrs() {
        int locHrsOpen;
        int locHrsClose;

        LocalDateTime busNowLdt = LocalDateTime.now(ZoneId.of("US/Eastern")).withSecond(0).withNano(0);
        System.out.println("Headquarters Time Now: " + busNowLdt);

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("Local Date Time: " + ldt);
        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.of(static_ZoneId));

        System.out.println("businessHrs  Sys Static: " + locZdt);
        ZonedDateTime businessZdt = locZdt.withZoneSameInstant(ZoneId.of("US/Eastern"));

        System.out.println("businessHrs  Sys Business (EST): " + businessZdt);

        //System.out.println("Local business Hrs: " + locHrsOpen + "am - " + locHrsClose + "pm");
    /*
            LocalDateTime UtcNowLdt = LocalDateTime.now(ZoneId.of("UTC"));
            System.out.println("UTC Date NOW: " + UtcNowLdt);
            LocalDate UtcCurDt = UtcNowLdt.toLocalDate();
            LocalTime UtcCurTime = UtcNowLdt.toLocalTime();
    */


    }
}
