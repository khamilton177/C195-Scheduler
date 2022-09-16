package com.thecodebarista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeMachine {

    LocalDateTime getLDT(LocalDate ldt, LocalTime lt);

}
