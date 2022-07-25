/*package com.elysian.client.module.modules.client;

import com.elysian.client.module.ModuleExample;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.ui.hud.components.Watermark;
import com.elysian.client.util.Timer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

public class HUDPhobos extends ToggleableModule {

    private final Property<Boolean> renderingUp  = new Property<Boolean>(true, "Example", "Rendering Up");
    private final Property<Boolean> potionIcons  = new Property<Boolean>(true, "Example", "Potion Icons");
    private final Property<Boolean> shadow  = new Property<Boolean>(true, "Example", "Shadow");
    private final EnumProperty<Bichota> watermark  = new EnumProperty<Bichota>(Bichota.HI, "Example", "Watermark");
    private final Property<Boolean> modeVer  = new Property<Boolean>(true, "Example", "ModeVer");
    private final Property<Boolean> arrayList  = new Property<Boolean>(true, "Example", "Array list");
    private final Property<Boolean> serverBrand  = new Property<Boolean>(true, "Example", "Server Brand");
    private final Property<Boolean> ping  = new Property<Boolean>(true, "Example", "Ping");
    private final Property<Boolean> tps  = new Property<Boolean>(true, "Example", "Tps");
    private final Property<Boolean> fps  = new Property<Boolean>(true, "Example", "Fps");
    private final Property<Boolean> coords  = new Property<Boolean>(true, "Example", "Coords");
    private final Property<Boolean> direction  = new Property<Boolean>(true, "Example", "Directions");
    private final Property<Boolean> speed  = new Property<Boolean>(true, "Example", "Speed");
    private final Property<Boolean> potions  = new Property<Boolean>(true, "Example", "Potions");
    private final Property<Boolean> textRadar  = new Property<Boolean>(true, "Example", "Text Radar");
    private final Property<Boolean> armor  = new Property<Boolean>(true, "Example", "Armor");
    private final Property<Boolean> percent  = new Property<Boolean>(true, "Example", "Percent");
    private final Property<Boolean> totems  = new Property<Boolean>(true, "Example", "Totems");
    private final EnumProperty<Greeter> greeter  = new EnumProperty<Greeter>(Greeter.NONE, "Greeter", "G");
    private final Property<Boolean> time  = new Property<Boolean>(true, "Time", "Time");
    private final Property<Boolean> lag  = new Property<Boolean>(true, "Lag", "Lag");
    private final NumberProperty<Integer> hudRed   = new NumberProperty<Integer>(5, 0, 50, "Red");
    private final NumberProperty<Integer> hudGreen  = new NumberProperty<Integer>(5, 0, 50, "Green");
    private final NumberProperty<Integer> hudBlue  = new NumberProperty<Integer>(5, 0, 50, "Blue");
    private final Property<Boolean> grayNess  = new Property<Boolean>(true, "Future Colors", "FC");
    private Map<String, Integer> players = new HashMap<>();

    private static final ResourceLocation box = new ResourceLocation("textures/gui/container/shulker_box.png");
    private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
    private int color;
    private boolean shouldIncrement;
    private int hitMarkerTimer;
    private final Timer timer = new Timer();

    public enum Bichota {
        NONE,
        CLIENT,
        HI
    }

    public enum Greeter {
        NONE,
        NAME,
        TIME,
        LONG
    }

    public HUDPhobos() {
        super("HUDPhobos", new String[] {"HUDPhobos"}, "HUDPhobos", ModuleType.RENDER);
        this.offerProperties(renderingUp, potionIcons, shadow, watermark, arrayList, serverBrand, ping, tps, fps, coords, direction, speed, potions, textRadar, armor, perecent, totems,  this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (shouldIncrement) {
            hitMarkerTimer++;
        }
        if (hitMarkerTimer == 10) {
            hitMarkerTimer = 0;
            shouldIncrement = false;
        }

        @Override
        public void onRender2D(Render2DEvent event) {
            if (fullNullCheck()) {
                return;
            }

            int width = renderer.scaledWidth;
            int height = renderer.scaledHeight;
            color = ColorUtil.toRGBA(hudRed.getValue(), hudGreen.getValue(), hudBlue.getValue());
            String grayString = (grayNess.getValue() ? TextUtil.GRAY : "");

            switch (watermark.getValue()) {
                case PHOBOS:
                    renderer.drawString("Phobos" + (modeVer.getValue() ? " v" + Phobos.MODVER : ""), 2, 2, color, true);
                    break;
                case EARTH:
                    renderer.drawString("3arthh4ck" + (modeVer.getValue() ? " v" + Phobos.MODVER : ""), 2, 2, color, true);
                default:
            }
    }
}
*/