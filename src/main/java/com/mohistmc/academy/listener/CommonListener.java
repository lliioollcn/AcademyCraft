package com.mohistmc.academy.listener;

import com.mohistmc.academy.AcademyCraft;
import com.mohistmc.academy.world.AcademyBlocks;
import com.mohistmc.academy.world.AcademyItems;
import com.mohistmc.academy.world.block.*;
import com.mohistmc.academy.world.item.AppSettings;
import com.mohistmc.academy.world.item.Logo;
import com.mohistmc.academy.world.provider.AcademyBlockTagsProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.common.MinecraftForge;

import java.util.concurrent.CompletableFuture;

/**
 * 通用事件监听器
 *
 * @author lliiooll
 */
public class CommonListener {

    private static CommonListener INSTANCE = null;
    private static final Logger LOGGER = LogUtils.getLogger();
    private IEventBus modEventBus;

    public static CommonListener getInstance() {
        if (INSTANCE == null) INSTANCE = new CommonListener();
        return INSTANCE;
    }

    /**
     * 初始化事件
     */
    public void init() {
        if (this.modEventBus == null) {
            this.modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        }
        this.modEventBus.addListener(this::addCreative);
        this.modEventBus.addListener(this::commonSetup);
        this.modEventBus.addListener(this::gatherData);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(event.includeServer(), new AcademyBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));

    }

    /**
     * 初始化事件
     *
     * @param event
     */
    private void commonSetup(final FMLCommonSetupEvent event) {

        LOGGER.info("HELLO FROM COMMON SETUP");
    }


    private void addCreative(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(AcademyCraft.MODID, "academy_group"), builder ->
                builder.title(Component.translatable("itemGroup.academy"))
                        .icon(() -> new ItemStack(AcademyItems.LOGO.get()))
                        .displayItems((params, output) -> {
                            AcademyItems.ITEMS.getEntries().stream().filter(item ->
                                    !((item.get() instanceof Logo)
                                            || (item.get() instanceof AppSettings)
                                            || item.get().getDescriptionId().contains("dev_normal_sub")
                                            || item.get().getDescriptionId().contains("dev_advanced_sub")
                                            || item.get().getDescriptionId().contains("windgen_fan_block")
                                            || item.get().getDescriptionId().contains("wingen_base_sub")
                                            || item.get().getDescriptionId().contains("matrix_sub"))
                            ).forEach(item -> output.accept(item.get()));
                            AcademyBlocks.BLOCKS.getEntries().stream().filter(block ->
                                    !(block.get() instanceof DevNormalSubBlock)
                                            && !(block.get() instanceof DevAdvancedSubBlock)
                                            && !(block.get() instanceof MatrixSubBlock)
                                            && !(block.get() instanceof WindGenBaseSubBlock)
                                            && !(block.get() instanceof WindGenFan)
                                            && !(block.get() instanceof LiquidBlock)
                            ).forEach(block -> output.accept(block.get()));
                        }));
    }

    public IEventBus getModEventBus() {
        return modEventBus;
    }

}
