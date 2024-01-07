package fr.wave.remotedemo.document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
public class Target {
    private List<Impact> impacts;
    private int time;
    private LocalDate date;

    private String userId;

}
