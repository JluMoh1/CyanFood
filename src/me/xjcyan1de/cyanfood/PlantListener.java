package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlantListener implements Listener {
    private final CyanFood cyanfood;
    private final Configuration config;

    public PlantListener(CyanFood cyanfood) {
        this.cyanfood = cyanfood;
        this.config = cyanfood.cfgcyanfood;
        cyanfood.server.getPluginManager().registerEvents(this, cyanfood);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent e) {
        SlimefunItem item = BlockStorage.check(e.getLocation().getBlock());
        if (item != null) {
            e.setCancelled(true);
            cyanfood.berries.forEach(berry -> {
                if (item.getName().equalsIgnoreCase(berry.toBush())) {
                    BlockStorage.store(e.getLocation().getBlock(), berry.getItem());
                    switch (berry.getType()) {
                        case BUSH: {
                            e.getLocation().getBlock().setType(Material.LEAVES);
                            e.getLocation().getBlock().setData(berry.getData().toByte());
                            break;
                        }
                        default: {
                            e.getLocation().getBlock().setType(Material.SKULL);
                            Skull s = (Skull) e.getLocation().getBlock().getState();
                            s.setSkullType(SkullType.PLAYER);
                            s.setRotation(cyanfood.bf[new Random().nextInt(cyanfood.bf.length)]);
                            s.setRawData((byte) 1);
                            s.update();

                            try {
                                CustomSkull.setSkull(e.getLocation().getBlock(), berry.getData().getTexture());
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            break;
                        }
                    }
                    e.getWorld().playEffect(e.getLocation(), Effect.STEP_SOUND, Material.LEAVES);
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onHarvest(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.LEAVES && block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ()).getType() == Material.GRASS) {
            if (CSCoreLib.randomizer().nextInt(100) < config.getInt("chances.hand"))
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), cyanfood.saplings.get(CSCoreLib.randomizer().nextInt(cyanfood.saplings.size())));
        } else {
            ItemStack item = harvestPlant(e.getBlock());
            if (item != null) {
                e.setCancelled(true);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = harvestPlant(e.getClickedBlock());
        if (item != null) {
            e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, Material.LEAVES);
            e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), item);
        }
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) {
        ItemStack item = BlockStorage.retrieve(e.getBlock());
        if (item != null) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
        }
    }

    public ItemStack harvestPlant(Block block) {
        final ItemStack[] itemstack = new ItemStack[1];
        SlimefunItem item = BlockStorage.check(block);
        if (item != null) {
            cyanfood.berries.forEach(berry -> {
                if (item.getName().equalsIgnoreCase(berry.getName())) {
                    switch (berry.getType()) {
                        default: {
                            block.setType(Material.SAPLING);
                            block.setData((byte) 0);
                            itemstack[0] = berry.getItem();
                            BlockStorage.store(block, berry.toBush());
                            break;
                        }
                    }
                }
            });
        }
        return itemstack[0];
    }
}
