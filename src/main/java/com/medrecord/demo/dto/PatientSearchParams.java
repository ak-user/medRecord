package com.medrecord.demo.dto;

import java.time.LocalDate;

public class PatientSearchParams {
    private String searchPhrase;
    private LocalDate dobFrom;
    private LocalDate dobTo;

    public PatientSearchParams() {
    }

    public PatientSearchParams(String searchPhrase, LocalDate dobFrom, LocalDate dobTo) {
        this.searchPhrase = searchPhrase;
        this.dobFrom = dobFrom;
        this.dobTo = dobTo;
    }

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
