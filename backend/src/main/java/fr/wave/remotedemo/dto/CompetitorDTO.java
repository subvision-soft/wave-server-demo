package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.enums.Category;
import fr.wave.remotedemo.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorDTO {

    private String firstName;
    private String lastName;
    private Category category;
    private Long id;
    private Sex sex;

}
