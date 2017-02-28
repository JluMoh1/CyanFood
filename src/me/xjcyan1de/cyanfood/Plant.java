package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Plant extends SlimefunItem {

    private final int food = 2;
    boolean edible;

    public Plant(Category category, ItemStack item, String name, RecipeType recipeType, boolean edible, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);
        this.edible = edible;
    }

    public boolean isEdible() {
        return this.edible;
    }

    public void restoreHunger(Player p) {
        int level = p.getFoodLevel() + food;
        p.setFoodLevel(level > 20 ? 20 : level);
        p.setSaturation(food);
    }
}
