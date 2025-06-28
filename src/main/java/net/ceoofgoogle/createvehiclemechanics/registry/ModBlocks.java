package net.ceoofgoogle.createvehiclemechanics.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.ceoofgoogle.createvehiclemechanics.CreateVehicleMechanicsMod;

import net.ceoofgoogle.createvehiclemechanics.block.RotationTransmitterBlockEntity;
import net.ceoofgoogle.createvehiclemechanics.block.RotationalTransmitterBlock;

public class ModBlocks {
    public static final CreateRegistrate REGISTRATE = CreateVehicleMechanicsMod.REGISTRATE;

    public static final BlockEntry<RotationalTransmitterBlock> ROTATION_TRANSMITTER =
            REGISTRATE.block("rotation_transmitter", RotationalTransmitterBlock::new)
                    //.transform(BlockStateGen.axisBlockProvider(false))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .simpleItem()// Allows saving ItemStacks
                    .register();
    public static final BlockEntityEntry<RotationTransmitterBlockEntity> RTBE = REGISTRATE.blockEntity("rt_block_entity", RotationTransmitterBlockEntity::new)
            .register();
    public static void register(){
        CreateVehicleMechanicsMod.getLogger().info("Registering Blocks");
    }
}



