package fr.wave.remotedemo.document;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Impact {
    private float distance;
    private int score;

    private float angle;

    private int amount;

    private Zone zone;

}
