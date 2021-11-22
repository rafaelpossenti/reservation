package com.possenti.reservation.dto;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityDto {

    private List<LocalDate> dates;

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }
}
