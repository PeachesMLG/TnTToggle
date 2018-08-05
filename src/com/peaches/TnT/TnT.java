package com.peaches.TnT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TnT implements Listener, CommandExecutor {
    private static Main plugin;

    public TnT(Main pl) {
        plugin = pl;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            p.openInventory(GUI(p));
        }
        return true;
    }

    private static Inventory GUI(Player p) {
        ArrayList<String> Entity_Lore = new ArrayList<>();
        ArrayList<String> Sound_Lore = new ArrayList<>();
        ArrayList<String> Particle_Lore = new ArrayList<>();

        if (!plugin.Entities.contains(p.getName())) {
            Entity_Lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled");
        } else {
            Entity_Lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Disabled");
        }

        if (!plugin.Particles.contains(p.getName())) {
            Particle_Lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled");
        } else {
            Particle_Lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Disabled");
        }

        if (!plugin.Sound.contains(p.getName())) {
            Sound_Lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled");
        } else {
            Sound_Lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Disabled");
        }

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "" + ChatColor.BOLD + "Toggle TnT");
        inv.setItem(0, Main.makeItem(Material.STAINED_GLASS_PANE, 1, 14, " "));

        ItemStack Entities = Main.makeItem(Material.TNT, 1, 0, ChatColor.RED + "" + ChatColor.BOLD + "Entities", Entity_Lore);
        ItemStack Particles = Main.makeItem(Material.FEATHER, 1, 0, ChatColor.RED + "" + ChatColor.BOLD + "Particles", Particle_Lore);
        ItemStack Sound = Main.makeItem(Material.GOLD_RECORD, 1, 0, ChatColor.RED + "" + ChatColor.BOLD + "Sound", Sound_Lore);
        inv.setItem(2, Entities);
        inv.setItem(4, Particles);
        inv.setItem(6, Sound);
        inv.setItem(8, Main.makeItem(Material.STAINED_GLASS_PANE, 1, 14, " "));
        return inv;
    }


    @EventHandler
    public void onclick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        //TODO SLAPI
        //TODO LORE
        if (e.getInventory().getTitle().equalsIgnoreCase(GUI(p).getTitle())) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(GUI(p).getItem(2).getType())) {
                if (plugin.Entities.contains(e.getWhoClicked().getName())) {
                    plugin.Entities.remove(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Entities have been enabled"));
                } else {
                    plugin.Entities.add(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Entities have been disabled"));
                }
            }
            if (e.getCurrentItem().getType().equals(GUI(p).getItem(4).getType())) {
                if (plugin.Particles.contains(e.getWhoClicked().getName())) {
                    plugin.Particles.remove(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Particles have been enabled"));
                } else {
                    plugin.Particles.add(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Particles have been disabled"));
                }
            }
            if (e.getCurrentItem().getType().equals(GUI(p).getItem(6).getType())) {
                if (plugin.Sound.contains(e.getWhoClicked().getName())) {
                    plugin.Sound.remove(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Sounds have been disabled"));
                } else {
                    plugin.Sound.add(e.getWhoClicked().getName());
                    p.openInventory(GUI(p));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix") + "&fTnT Sounds have been disabled"));
                }
            }
        }
    }
}
