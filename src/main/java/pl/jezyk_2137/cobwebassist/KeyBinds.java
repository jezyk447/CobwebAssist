package pl.jezyk_2137.cobwebassist;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBinding toggleKey;
    public static KeyBinding triggerKey;

    public static void register() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cobwebassist.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_0,
                "Cobweb Assist"
        ));
        triggerKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cobwebassist.trigger",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "Cobweb Assist"
        ));
    }
}