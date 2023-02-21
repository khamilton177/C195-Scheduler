package com.thecodebarista;

import java.time.*;

import static com.thecodebarista.controller.LoginFormCtrl.static_ZoneId;
import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;

/**
 * Generic Functional Interface - Utilized by lambda expressions to calculate time conversion for scheduler.
 *
 */
@FunctionalInterface
public interface TimeMachine {

    int busHrsOpen = 8; // Business Open in Est hours.
    int totalBusHrs = 14;

    <T> void timeConverter();
    // LocalDateTime makeLDT();

    default void convertBusinessHrs() {
        LocalDateTime busNowLdt = LocalDateTime.now(ZoneId.of("US/Eastern")).withSecond(0).withNano(0);
        System.out.println("Headquarters Time Now: " + busNowLdt);

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("Local Date Time: " + ldt);

        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.of(static_ZoneId));
        System.out.println("businessHrs  Sys Static: " + locZdt);

        ZonedDateTime businessZdt = locZdt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        System.out.println("businessHrs  Sys Business (EST): " + businessZdt);

        LocalDateTime UtcNowLdt = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println("UTC Date NOW: " + UtcNowLdt);
        LocalDate UtcCurDt = UtcNowLdt.toLocalDate();
        LocalTime UtcCurTime = UtcNowLdt.toLocalTime();
    }

    default LocalDateTime getLDT(LocalDate ldt, LocalTime lt) {
        return LocalDateTime.of(ldt, lt);
    }

    default LocalTime ltInput(int hrs, int mins) {
        return LocalTime.of(hrs, mins);
    }

    /**
     * Get the Local Open hr. for the Appointments Business Hours.
     * @param ldt
     * @return the int representing the local start hr for Appointments.
     */
    default int getLocOpenHr(LocalDateTime ldt) {
        int openHr;

        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.of(static_ZoneId));
        System.out.println("businessHrs  Sys Static: " + locZdt);

        ZonedDateTime businessZdt = locZdt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        System.out.println("businessHrs  Sys Business (EST): " + businessZdt);

        int localOffset = businessZdt.getOffset().compareTo(locZdt.getOffset())/60/60;
        System.out.println("businessHrs Sys Compare business to local: " + localOffset);

        if (localOffset < 0) {
            openHr = addExact(busHrsOpen, localOffset);
        }
        else if (localOffset > 0){
            openHr = subtractExact(busHrsOpen, localOffset);
        }
        else {
            openHr = busHrsOpen;
        }
        return openHr;
    }

    /**
     * Get the Local Close hr. for the Appointments Business Hours.
     * @param ldt
     * @return the int representing the local end hr for Appointments.
     */
    default LocalTime getLocCloseTime(LocalDateTime ldt) {
        int locHrsOpen = getLocOpenHr(ldt);
        int closeHr = locHrsOpen + totalBusHrs;
        LocalTime busCloseTime = ldt.withHour(closeHr).withMinute(0).withSecond(0).withNano(0).toLocalTime();
        System.out.println(busCloseTime);
        return busCloseTime;
    }

}
