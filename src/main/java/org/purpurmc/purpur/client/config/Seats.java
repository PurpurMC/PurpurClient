package org.purpurmc.purpur.client.config;

import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Seats {
    private Seat bat = new Seat(-0.25D, 0.5D, 0.0D);
    private Seat bee = new Seat(-0.1D, 0.5D, 0.0D);
    private Seat cat = new Seat(0.0D, 0.4D, 0.0D);
    private Seat cod = new Seat(-0.25D, 0.1D, 0.0D);
    private Seat elderGuardian = new Seat(0.0D, 0.75D, 0.0D);
    private Seat enderDragon = new Seat(0.0D, 0.4D, 0.0D);
    private Seat fox = new Seat(-0.25D, 0.6D, 0.0D);
    private Seat hoglin = new Seat(0.0D, 0.75D, 0.0D);
    private Seat ironGolem = new Seat(-0.25D, 0.7D, 0.0D);
    private Seat ocelot = new Seat(0.0D, 0.5D, 0.0D);
    private Seat parrot = new Seat(-0.15D, 0.3D, 0.0D);
    private Seat piglin = new Seat(0.0D, 0.75D, 0.0D);
    private Seat piglinBrute = new Seat(0.0D, 0.75D, 0.0D);
    private Seat polarBear = new Seat(0.0D, 0.75D, 0.0D);
    private Seat polarBearStanding = new Seat(-1.0D, 0.5D, 0.0D);
    private Seat pufferfish = new Seat(-0.1D, 0.25D, 0.0D);
    private Seat rabbit = new Seat(-0.25D, 0.4D, 0.0D);
    private Seat salmon = new Seat(-0.1D, 0.5D, 0.0D);
    private Seat snowGolem = new Seat(-0.3D, 0.6D, 0.0D);
    private Seat tropicalFish = new Seat(-0.1D, 0.7D, 0.0D);

    public Seat getBat() {
        return this.bat;
    }

    public void setBat(Seat bat) {
        this.bat = bat;
    }

    public Seat getBee() {
        return this.bee;
    }

    public void setBee(Seat bee) {
        this.bee = bee;
    }

    public Seat getCat() {
        return this.cat;
    }

    public void setCat(Seat cat) {
        this.cat = cat;
    }

    public Seat getCod() {
        return this.cod;
    }

    public void setCod(Seat cod) {
        this.cod = cod;
    }

    public Seat getElderGuardian() {
        return this.elderGuardian;
    }

    public void setElderGuardian(Seat elderGuardian) {
        this.elderGuardian = elderGuardian;
    }

    public Seat getEnderDragon() {
        return this.enderDragon;
    }

    public void setEnderDragon(Seat enderDragon) {
        this.enderDragon = enderDragon;
    }

    public Seat getFox() {
        return this.fox;
    }

    public void setFox(Seat fox) {
        this.fox = fox;
    }

    public Seat getHoglin() {
        return this.hoglin;
    }

    public void setHoglin(Seat hoglin) {
        this.hoglin = hoglin;
    }

    public Seat getIronGolem() {
        return this.ironGolem;
    }

    public void setIronGolem(Seat ironGolem) {
        this.ironGolem = ironGolem;
    }

    public Seat getOcelot() {
        return this.ocelot;
    }

    public void setOcelot(Seat ocelot) {
        this.ocelot = ocelot;
    }

    public Seat getParrot() {
        return this.parrot;
    }

    public void setParrot(Seat parrot) {
        this.parrot = parrot;
    }

    public Seat getPiglin() {
        return this.piglin;
    }

    public void setPiglin(Seat piglin) {
        this.piglin = piglin;
    }

    public Seat getPiglinBrute() {
        return this.piglinBrute;
    }

    public void setPiglinBrute(Seat piglinBrute) {
        this.piglinBrute = piglinBrute;
    }

    public Seat getPolarBear() {
        return this.polarBear;
    }

    public void setPolarBear(Seat polarBear) {
        this.polarBear = polarBear;
    }

    public Seat getPolarBearStanding() {
        return this.polarBearStanding;
    }

    public void setPolarBearStanding(Seat polarBearStanding) {
        this.polarBearStanding = polarBearStanding;
    }

    public Seat getPufferfish() {
        return this.pufferfish;
    }

    public void setPufferfish(Seat pufferfish) {
        this.pufferfish = pufferfish;
    }

    public Seat getRabbit() {
        return this.rabbit;
    }

    public void setRabbit(Seat rabbit) {
        this.rabbit = rabbit;
    }

    public Seat getSalmon() {
        return this.salmon;
    }

    public void setSalmon(Seat salmon) {
        this.salmon = salmon;
    }

    public Seat getSnowGolem() {
        return this.snowGolem;
    }

    public void setSnowGolem(Seat snowGolem) {
        this.snowGolem = snowGolem;
    }

    public Seat getTropicalFish() {
        return this.tropicalFish;
    }

    public void setTropicalFish(Seat tropicalFish) {
        this.tropicalFish = tropicalFish;
    }
}
