package net.ceoofgoogle.createvehiclemechanics.registry;

import com.simibubi.create.AllCreativeModeTabs;
import net.ceoofgoogle.createvehiclemechanics.CreateVehicleMechanicsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


import static net.ceoofgoogle.createvehiclemechanics.CreateVehicleMechanicsMod.REGISTRATE;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateVehicleMechanicsMod.MODID);

    public static final RegistryObject<CreativeModeTab> MECHANICS = addTab("vehicle_mechanics", "Create Vehicle Mechanics",
            ModBlocks.ROTATION_TRANSMITTER::asStack);


    public static RegistryObject<CreativeModeTab> addTab(String id, String name, Supplier<ItemStack> icon) {
        String itemGroupId = "itemGroup." + CreateVehicleMechanicsMod.MODID + "." + id;
        REGISTRATE.addRawLang(itemGroupId, name);
        CreativeModeTab.Builder tabBuilder = CreativeModeTab.builder()
                .icon(icon)
                .displayItems(ModCreativeModeTabs::displayItems)
                .title(Component.translatable(itemGroupId))
                .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey());
        return CREATIVE_MODE_TABS.register(id, tabBuilder::build);
    }

    private static void displayItems(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output pOutput) {
        pOutput.accept(ModBlocks.ROTATION_TRANSMITTER);

    }


    public static void register(IEventBus eventBus) {
        CreateVehicleMechanicsMod.getLogger().info("Registering CreativeTabs!");
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
