package com.mohistmc.academy.client.block.entity;

import com.mohistmc.academy.capability.IFCapabilityImpl;
import com.mohistmc.academy.world.AcademyBlockEntities;
import com.mohistmc.academy.world.AcademyCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NodeBasicBlockEntity extends BaseNodeBlockEntity {
    public NodeBasicBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(AcademyBlockEntities.NODE_BASIC.get(), p_155229_, p_155230_);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public int getRange() {
        return 1;
    }



}
