package me.xjcyan1de.cyanfood.Items;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.xjcyan1de.cyanfood.Main;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

public class Crook {
    final private Main main;
    final private Configuration config;

    public Crook(Main main) {
        this.main = main;
        this.config = main.config;
        if (config.getBoolean("items.crook.enable")) item();
    }

    private void item() {
        final SlimefunItem crook = new SlimefunItem(Categories.TOOLS, new CustomItem(new MaterialData(Material.WOOD_HOE), config.getString("items.crook.itemname"), "", config.getString("items.crook.itemlore")), "CROOK", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {new ItemStack(Material.STICK), new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null});
        crook.register(false, new BlockBreakHandler() {
            @Override
            public boolean onBlockBreak(BlockBreakEvent arg0, ItemStack arg1, int arg2, List<ItemStack> arg3) {
                if (SlimefunManager.isItemSimiliar(arg1, crook.getItem(), true)) {
                    PlayerInventory.damageItemInHand(arg0.getPlayer());
                    if ((arg0.getBlock().getType() == Material.LEAVES || arg0.getBlock().getType() == Material.LEAVES_2) && CSCoreLib.randomizer().nextInt(100) < config.getInt("chances.crook")) {
                        ItemStack sapling = new MaterialData(Material.SAPLING, (byte) ((arg0.getBlock().getData() % 4) + (arg0.getBlock().getType() == Material.LEAVES_2 ? 4: 0))).toItemStack(1);
                        arg3.add(sapling);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}