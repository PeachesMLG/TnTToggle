package com.peaches.TnT;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends JavaPlugin implements Listener {

    public Main(Main pl) {
    }

    public Main() {
        PluginDescriptionFile pdf = getDescription();
    }

    public ArrayList<String>Sound = new ArrayList<>();
    public ArrayList<String>Particles = new ArrayList<>();
    public ArrayList<String>Entities = new ArrayList<>();
    private ProtocolManager protocolManager;
    private final static int CENTER_PX = 154;
    private final Map<String, Entry<Method, Object>> commandMap = new HashMap<>();
    private CommandMap map = new SimpleCommandMap(null);

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this);
        getCommand("tnttoggle").setExecutor(new TnT(this));
        // Regersting an outgoing plugin requst for BungeeCord
        registerEvents();
        //Refrence ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager();
        //Disabling Sound EFFECTS
        protocolManager.addPacketListener(
                new PacketAdapter(this, ListenerPriority.NORMAL,
                        PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        // Item packets (id: 0x29)
                        String name = event.getPacket().getStrings().read(0);
                        if (!name.equals("game.tnt.primed") && !name.equals("random.explode") && !name.equals("tile.piston.out") && !name.equals("tile.piston.in")) {
                            return;
                        }
                        if(Sound.contains(event.getPlayer().getName())){
                            event.setCancelled(true);
                        }

                    }
                });

        //Disabling Entities
        protocolManager.addPacketListener(
                new PacketAdapter(this, ListenerPriority.NORMAL,
                        PacketType.Play.Server.SPAWN_ENTITY) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        //Needs to make sure its tnt then cancel
                        if(event.getPacket().getIntegers().read(9)==50 || event.getPacket().getIntegers().read(9)==70){
                            if(Entities.contains(event.getPlayer().getName())){
                                event.setCancelled(true);
                            }
                        }

                    }
                });

        //Disabling Particles
        protocolManager.addPacketListener(
                new PacketAdapter(this, ListenerPriority.NORMAL,
                        PacketType.Play.Server.EXPLOSION) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if(Particles.contains(event.getPlayer().getName())) {
                            event.setCancelled(true);
                        }
                    }
                });



        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print(this.getDescription().getName()+" Enabled!");
        System.out.print("");
        System.out.print("-------------------------------");
    }



    public void onDisable() {
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print(this.getDescription().getName()+" Disabled!");
        System.out.print("");
        System.out.print("-------------------------------");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new TnT(this), this);
    }

    public static ItemStack makeItem(Material material, int amount, int type, String name) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }
}
