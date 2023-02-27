package com.thecodebarista;
import com.thecodebarista.model.Appointment;
import java.time.*;
import static com.thecodebarista.controller.LoginFormCtrl.static_ZoneId;
import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;

/**
 * Functional Interface - Contains one abstract method utilized by lambda expression.
 * Addition default methods used to perform various time conversions for scheduler.
 */
@FunctionalInterface
public interface TimeMachine {

    /**
     * Business Open in Est hours.
     * Used in method to calculate the first hour in the 'hours' dropdown.
     */
    int busHrsOpen = 8;
    /**
     * Total amount of business hour.
     * Used in method to determine the last hour populated in the 'hours' dropdown.
     */
    int totalBusHrs = 14;

    /**
     * The one Abstract Method in Functional Interface for calculating the duration minutes; it will be used in lambda.
     * @param appointment
     * @return
     */
    Long getDurationMins(Appointment appointment); //Abstract method used in lambda

    /**
     * Get the Local Open hr. for the Appointments Business Hours.
     * @param ldt LocalDateTime passed in from calling method.
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
     * @param ldt LocalDateTime passed in from calling method.
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
