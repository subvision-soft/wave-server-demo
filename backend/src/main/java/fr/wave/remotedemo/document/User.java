package fr.wave.remotedemo.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Category category;
    @DBRef
    private List<Target> targets;






}
