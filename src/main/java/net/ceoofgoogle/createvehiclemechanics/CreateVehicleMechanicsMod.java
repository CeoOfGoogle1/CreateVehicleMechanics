package net.ceoofgoogle.createvehiclemechanics;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.ceoofgoogle.createvehiclemechanics.registry.ModBlocks;
import net.ceoofgoogle.createvehiclemechanics.registry.ModCreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateVehicleMechanicsMod.MODID)
public class CreateVehicleMechanicsMod {
    public static final String MODID = "createvehiclemechanics";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);



    public CreateVehicleMechanicsMod() {
        getLogger().info("Initializing Create ArmorBlocks!");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        ModBlocks.register();
        ModCreativeModeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

    }


    public static Logger getLogger() {
        return LOGGER;
    }



}
