package love.korni.studydiscordbot.service;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;

/**
 * Service for work with category.
 */
public interface CategoryService {

    /**
     * Create a category.
     *
     * @param guild guild where need to create a category
     * @param name name of category
     * @return created category
     */
    Category createCategory(Guild guild, String name);

    /**
     * Delete the category.
     *
     * @param guild guild where need to delete a category
     * @param id category id
     */
    void deleteCategory(Guild guild, Long id);

}
