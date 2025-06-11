package pl.jezyk_2137.cobwebassist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class Util {
    public static int findSlotWithWaterOrLava(MinecraftClient client) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            Item item = stack.getItem();
            if (item == Items.WATER_BUCKET || item == Items.LAVA_BUCKET) {
                return i;
            }
        }
        return -1;

    }
    public static int findSlotWithCobweb(MinecraftClient client) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.getItem() == Items.COBWEB) {
                return i;
            }
        }
        return -1;
    }
}