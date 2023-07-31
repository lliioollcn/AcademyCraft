package com.mohistmc.academy.client.block.entity;

import com.mohistmc.academy.capability.IFCapabilityImpl;
import com.mohistmc.academy.capability.IIFCapability;
import com.mohistmc.academy.world.AcademyCapability;
import com.mohistmc.academy.world.AcademyItems;
import com.mohistmc.academy.world.menu.AcademyMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BaseNodeBlockEntity extends AcademyContainerBlockEntity {
    public BaseNodeBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    public abstract int getRange();

    public boolean isConnected() {
        //TODO: 连接/断开矩阵
        return false;
    }

    public void tick() {


        Optional<?> optional = getCapability(AcademyCapability.IF_CAPABILITY).resolve();
        if (optional.isPresent()) {
            Object cap = optional.get();
            if (cap instanceof IIFCapability ifPower) {
                updatePower(0, ifPower.getIF());
                updatePower(1, ifPower.getIF() * -1);

            }
        }


    }

    private void updatePower(int i, int i1) {
        if (getMenu() != null) {
            ItemStack item = getMenu().container.getItem(i);
            if (item.is(AcademyItems.ENERGY_UNIT.get()) || item.is(AcademyItems.DEVELOPER_PORTABLE.get())) {
                Optional<?> optional = getCapability(AcademyCapability.IF_CAPABILITY).resolve();
                if (optional.isPresent()) {
                    Object cap = optional.get();
                    if (cap instanceof IIFCapability ifPower) {
                        item.setDamageValue(item.getDamageValue() + i1);
                        item.setTag(ifPower.serializeNBT(item.getTag()));
                    }
                }
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == AcademyCapability.IF_CAPABILITY) {
            return LazyOptional.of(() ->
                    new IFCapabilityImpl(1)
            ).cast();
        }
        return LazyOptional.empty();
    }
}
