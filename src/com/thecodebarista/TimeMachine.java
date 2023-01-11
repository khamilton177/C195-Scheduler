package com.thecodebarista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Generic Functional Interface - Utiled by labda expressions to calculate time conversion for scheduler.
 *
 */
@FunctionalInterface
//public interface TimeMachine<T> {
public interface TimeMachine {

//    public T timeConvert();
    // LocalDateTime getLDT(LocalDate ldt, LocalTime lt);
    LocalDateTime makeLDT();

}
