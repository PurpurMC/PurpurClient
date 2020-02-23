package net.pl3x.fabric.purpurclient.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "purpurclient")
public class ModConfig implements ConfigData {
    public boolean rainFix = true;
    public boolean scoreboardExtendedRender = false;

    @ConfigEntry.Gui.CollapsibleObject
    public SeatOffsets seatOffsets = new SeatOffsets();

    public static class SeatOffsets {
        @ConfigEntry.Gui.CollapsibleObject
        public XYZ bat = new XYZ(-0.25D, 0.5D, 0.0D);

        @ConfigEntry.Gui.CollapsibleObject
        public XYZ bee = new XYZ(-0.1D, 0.5D, 0.0D);

        @ConfigEntry.Gui.CollapsibleObject
        public XYZ cat = new XYZ(0.0D, 0.4D, 0.0D);

        @ConfigEntry.Gui.CollapsibleObject
        public XYZ cod = new XYZ(-0.25D, 0.1D, 0.0D);
    }

    public static class XYZ {
        public double x;
        public double y;
        public double z;

        XYZ(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
