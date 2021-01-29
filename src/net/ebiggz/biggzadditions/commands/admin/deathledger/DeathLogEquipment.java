package net.ebiggz.biggzadditions.commands.admin.deathledger;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DeathLogEquipment {

    private ItemStack head;
    private ItemStack chest;
    private ItemStack legs;
    private ItemStack feet;
    private ItemStack offHand;

    public DeathLogEquipment(ConfigurationSection deathLogEquipmentData) {
        if(deathLogEquipmentData == null) return;
        this.head = deathLogEquipmentData.getItemStack("head");
        this.chest = deathLogEquipmentData.getItemStack("chest");
        this.legs = deathLogEquipmentData.getItemStack("legs");
        this.feet = deathLogEquipmentData.getItemStack("feet");
        this.offHand = deathLogEquipmentData.getItemStack("offHand");
    }

    public DeathLogEquipment(ItemStack head, ItemStack chest, ItemStack legs, ItemStack feet, ItemStack offHand) {
        if(head != null && head.getType() != Material.AIR) {
            this.head = head;
        }
        if(chest != null && chest.getType() != Material.AIR) {
            this.chest = chest;
        }
        if(legs != null && legs.getType() != Material.AIR) {
            this.head = legs;
        }
        if(feet != null && feet.getType() != Material.AIR) {
            this.feet = feet;
        }
        if(offHand != null && offHand.getType() != Material.AIR) {
            this.offHand = offHand;
        }
    }

    public ItemStack getEquipmentSlot(EquipmentSlot slot) {
        if(slot == EquipmentSlot.HEAD) {
            return head;
        }
        if(slot == EquipmentSlot.CHEST) {
            return chest;
        }
        if(slot == EquipmentSlot.LEGS) {
            return legs;
        }
        if(slot == EquipmentSlot.FEET) {
            return feet;
        }
        if(slot == EquipmentSlot.OFF_HAND) {
            return offHand;
        }
        return null;
    }

    public List<ItemStack> getAll() {
        List<ItemStack> items = new ArrayList<ItemStack>();
        if(head != null) {
            items.add(head);
        }
        if(chest != null) {
            items.add(chest);
        }
        if(legs != null) {
            items.add(legs);
        }
        if(feet != null) {
            items.add(feet);
        }
        if(offHand != null) {
            items.add(offHand);
        }
        return items;
    }

    public boolean itemIsEquipment(ItemStack item) {
        return item == head ||
                item == chest ||
                item == legs ||
                item == feet ||
                item == offHand;
    }

    public boolean hasEquipment() {
        return head != null ||
                chest != null ||
                legs != null ||
                feet != null ||
                offHand != null;
    }
}
