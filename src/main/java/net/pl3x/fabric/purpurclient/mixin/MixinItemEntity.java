package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    @Shadow
    private int age;
    @Shadow
    private int pickupDelay;
    @Shadow
    private int health;

    public MixinItemEntity(EntityType<? extends ItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public ItemStack getStack() {
        return null;
    }

    @Shadow
    private void applyBuoyancy() {
    }

    @Shadow
    private boolean canMerge() {
        return false;
    }

    @Shadow
    private void tryMerge() {
    }

    @Override
    public boolean damage(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!this.getStack().isEmpty() && this.getStack().getItem() == Items.NETHER_STAR && damageSource.isExplosive()) {
            return false;
        } else {
            // Purpur start
            if (world.isClient) {
                if (damageSource == DamageSource.LAVA) return false;
                if (damageSource.isFire() || damageSource == DamageSource.IN_FIRE) return false;
                if (damageSource.isExplosive()) return false;
            }
            // Purpur end
            this.scheduleVelocityUpdate();
            this.health = (int) ((float) this.health - f);
            if (this.health <= 0) {
                this.remove();
            }

            return false;
        }
    }

    @Override
    public void tick() {
        if (this.getStack().isEmpty()) {
            this.remove();
        } else {
            super.tick();
            if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
                --this.pickupDelay;
            }

            this.prevX = this.getX();
            this.prevY = this.getY();
            this.prevZ = this.getZ();
            Vec3d vec3d = this.getVelocity();
            if (this.isInFluid(FluidTags.WATER) || (world.isClient && this.isInFluid(FluidTags.LAVA))) { // Purpur
                this.applyBuoyancy();
            } else if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
            }

            if (this.world.isClient) {
                this.noClip = false;
            } else {
                this.noClip = !this.world.doesNotCollide(this);
                if (this.noClip) {
                    this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().y1 + this.getBoundingBox().y2) / 2.0D, this.getZ());
                }
            }

            if (!this.onGround || squaredHorizontalLength(this.getVelocity()) > 9.999999747378752E-6D || (this.age + this.getEntityId()) % 4 == 0) {
                this.move(MovementType.SELF, this.getVelocity());
                float f = 0.98F;
                if (this.onGround) {
                    f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.98F;
                }

                this.setVelocity(this.getVelocity().multiply((double) f, 0.98D, (double) f));
                if (this.onGround) {
                    this.setVelocity(this.getVelocity().multiply(1.0D, -0.5D, 1.0D));
                }
            }

            boolean bl = MathHelper.floor(this.prevX) != MathHelper.floor(this.getX()) || MathHelper.floor(this.prevY) != MathHelper.floor(this.getY()) || MathHelper.floor(this.prevZ) != MathHelper.floor(this.getZ());
            int i = bl ? 2 : 40;
            if (this.age % i == 0) {
                if (this.world.getFluidState(new BlockPos(this)).matches(FluidTags.LAVA)) {
                    if (!world.isClient) // Purpur
                    this.setVelocity((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F), 0.20000000298023224D, (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F));
                    this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }

                if (!this.world.isClient && this.canMerge()) {
                    this.tryMerge();
                }
            }

            if (this.age != -32768) {
                ++this.age;
            }

            this.velocityDirty |= this.checkWaterState();
            if (!this.world.isClient) {
                double d = this.getVelocity().subtract(vec3d).lengthSquared();
                if (d > 0.01D) {
                    this.velocityDirty = true;
                }
            }

            if (!this.world.isClient && this.age >= 6000) {
                this.remove();
            }

        }
    }
}
