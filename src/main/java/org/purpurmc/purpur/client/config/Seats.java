package org.purpurmc.purpur.client.config;

import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Seats {
    private Seat bat = new Seat(-0.25D, 0.5D, 0.0D);
    private Seat bee = new Seat(-0.1D, 0.5D, 0.0D);
    private Seat cat = new Seat(0.0D, 0.4D, 0.0D);
    private Seat cod = new Seat(-0.25D, 0.1D, 0.0D);
    private Seat elderGuardian = new Seat(0.0D, 0.0D, 0.0D);
    private Seat enderDragon = new Seat(0.0D, 0.4D, 0.0D);
    private Seat fox = new Seat(-0.25D, 0.6D, 0.0D);
    private Seat ironGolem = new Seat(-0.25D, 0.7D, 0.0D);
    private Seat ocelot = new Seat(0.0D, 0.5D, 0.0D);
    private Seat parrot = new Seat(-0.15D, 0.3D, 0.0D);
    private Seat polarBear = new Seat(0.0D, 0.75D, 0.0D);
    private Seat polarBearStanding = new Seat(-1.0D, 0.5D, 0.0D);
    private Seat pufferfish = new Seat(-0.1D, 0.25D, 0.0D);
    private Seat rabbit = new Seat(-0.25D, 0.4D, 0.0D);
    private Seat salmon = new Seat(-0.1D, 0.5D, 0.0D);
    private Seat snowGolem = new Seat(-0.3D, 0.6D, 0.0D);
    private Seat tropicalFish = new Seat(-0.1D, 0.7D, 0.0D);

    public Seat getBat() {
        return bat;
    }

    public void setBat(Seat bat) {
        this.bat = bat;
    }

    public Seat getBee() {
        return bee;
    }

    public void setBee(Seat bee) {
        this.bee = bee;
    }

    public Seat getCat() {
        return cat;
    }

    public void setCat(Seat cat) {
        this.cat = cat;
    }

    public Seat getCod() {
        return cod;
    }

    public void setCod(Seat cod) {
        this.cod = cod;
    }

    public Seat getElderGuardian() {
        return elderGuardian;
    }

    public void setElderGuardian(Seat elderGuardian) {
        this.elderGuardian = elderGuardian;
    }

    public Seat getEnderDragon() {
        return enderDragon;
    }

    public void setEnderDragon(Seat enderDragon) {
        this.enderDragon = enderDragon;
    }

    public Seat getFox() {
        return fox;
    }

    public void setFox(Seat fox) {
        this.fox = fox;
    }

    public Seat getIronGolem() {
        return ironGolem;
    }

    public void setIronGolem(Seat ironGolem) {
        this.ironGolem = ironGolem;
    }

    public Seat getOcelot() {
        return ocelot;
    }

    public void setOcelot(Seat ocelot) {
        this.ocelot = ocelot;
    }

    public Seat getParrot() {
        return parrot;
    }

    public void setParrot(Seat parrot) {
        this.parrot = parrot;
    }

    public Seat getPolarBear() {
        return polarBear;
    }

    public void setPolarBear(Seat polarBear) {
        this.polarBear = polarBear;
    }

    public Seat getPolarBearStanding() {
        return polarBearStanding;
    }

    public void setPolarBearStanding(Seat polarBearStanding) {
        this.polarBearStanding = polarBearStanding;
    }

    public Seat getPufferfish() {
        return pufferfish;
    }

    public void setPufferfish(Seat pufferfish) {
        this.pufferfish = pufferfish;
    }

    public Seat getRabbit() {
        return rabbit;
    }

    public void setRabbit(Seat rabbit) {
        this.rabbit = rabbit;
    }

    public Seat getSalmon() {
        return salmon;
    }

    public void setSalmon(Seat salmon) {
        this.salmon = salmon;
    }

    public Seat getSnowGolem() {
        return snowGolem;
    }

    public void setSnowGolem(Seat snowGolem) {
        this.snowGolem = snowGolem;
    }

    public Seat getTropicalFish() {
        return tropicalFish;
    }

    public void setTropicalFish(Seat tropicalFish) {
        this.tropicalFish = tropicalFish;
    }
}
