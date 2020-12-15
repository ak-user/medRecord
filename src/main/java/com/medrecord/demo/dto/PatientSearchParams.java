package com.medrecord.demo.dto;

import java.time.LocalDate;

public class PatientSearchParams {
    private String searchPhrase;
    private LocalDate dobFrom;
    private LocalDate dobTo;

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public LocalDate getDobFrom() {
        return dobFrom;
    }

    public LocalDate getDobTo() {
        return dobTo;
    }
}
