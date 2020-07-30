package love.korni.studydiscordbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.service.CategoryService;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public Category createCategory(Guild guild, String name) {
        ChannelAction<Category> category = guild.createCategory(name);
        return category.complete();
    }

    @Override
    public void deleteCategory(Guild guild, Long id) {
        Category categoryById = guild.getCategoryById(id);
        if (Objects.nonNull(categoryById)) {
            categoryById.delete().complete();
        }
    }
}
