package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.enums.Category;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.CATEGORIES)
@RequiredArgsConstructor
public class CategoriesResource {

    @GetMapping()
    public List<Category> getCategories() {
        return List.of(Category.values());
    }
}
