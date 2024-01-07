package fr.wave.remotedemo.document;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@Getter
public class Competition {

    @Id
    private String id;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;


    private String description;
    @Setter
    @NotNull(message = "Name cannot be null")
    private String name;
    @DBRef
    private List<User> users;
}
