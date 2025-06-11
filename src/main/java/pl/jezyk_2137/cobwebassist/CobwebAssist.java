package pl.jezyk_2137.cobwebassist;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class CobwebAssist implements ClientModInitializer {
    private static boolean sequenceRunning = false;
    private static boolean modEnabled = true;
    private static int startSlot = 0;
    private static int waterSlot = -1;
    private static int cobwebSlot = -1;
    private static int overlayTicks = 0;
    private static final int OVERLAY_DURATION = 10;

    @Override
    public void onInitializeClient() {
        KeyBinds.register();

        HudRenderCallback.EVENT.register((DrawContext context, float tickDelta) -> {
            if (overlayTicks > 0) {
                String status = modEnabled ? "Assist ON" : "Assist OFF";
                int color = modEnabled ? 0x22AA22 : 0xAA2222;
                context.drawCenteredTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        status,
                        context.getScaledWindowWidth() / 2,
                        context.getScaledWindowHeight() / 2 + 20,
                        color
                );
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBinds.toggleKey.wasPressed()) {
                modEnabled = !modEnabled;
                overlayTicks = OVERLAY_DURATION;
            }
            if (KeyBinds.triggerKey.wasPressed() && !sequenceRunning && modEnabled) {
                if (client.player != null) {
                    startSlot = client.player.getInventory().selectedSlot;
                    waterSlot = Util.findSlotWithWaterOrLava(client);
                    cobwebSlot = Util.findSlotWithCobweb(client);
                    if (waterSlot != -1 && cobwebSlot != -1) {
                        sequenceRunning = true;
                        runSequence(client);
                        sequenceRunning = false;
                    }
                }
            }
            if (overlayTicks > 0) {
                overlayTicks--;
            }
        });
    }

    private void runSequence(MinecraftClient client) {
        setSlot(client, waterSlot);
        rightClickItem(client);
        rightClickItem(client);

        setSlot(client, cobwebSlot);
        rightClickBlock(client);

        setSlot(client, startSlot);
    }

    private void setSlot(MinecraftClient client, int slot) {
        client.player.getInventory().selectedSlot = slot;
    }

    private void rightClickItem(MinecraftClient client) {
        if (client.player != null) {
            client.interactionManager.interactItem(client.player, client.player.getActiveHand());
        }
    }

    private void rightClickBlock(MinecraftClient client) {
        if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) client.crosshairTarget;
            client.interactionManager.interactBlock(
                    client.player,
                    client.player.getActiveHand(),
                    blockHit
            );
        } else {
            rightClickItem(client);
        }
    }
}