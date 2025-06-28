package net.ceoofgoogle.createvehiclemechanics;

import com.mojang.brigadier.CommandDispatcher;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.ceoofgoogle.createvehiclemechanics.utils.Network;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

import static net.ceoofgoogle.createvehiclemechanics.CreateVehicleMechanicsMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(
                Commands.literal("transmitter_debug")
                        .requires(source -> source.hasPermission(2))
                        .executes(ctx -> {
                            Map<String, List<KineticBlockEntity>> freqMap = Network.ROTATE.Frequency.get(0);

                            System.out.println("--- Frequency Debug ---");
                            for (Map.Entry<String, List<KineticBlockEntity>> entry : freqMap.entrySet()) {
                                String frequency = entry.getKey();
                                List<KineticBlockEntity> transmitters = entry.getValue();

                                // Clean list (just in case)
                                transmitters.removeIf(BlockEntity::isRemoved);
                                ctx.getSource().sendSuccess(() -> Component.literal("Frequency: " + frequency), false);
                                for (KineticBlockEntity be : transmitters) {
                                    ctx.getSource().sendSuccess(() -> Component.literal(" - Block at " + be.getBlockPos()), false);
                                }

                                System.out.println("Frequency: " + frequency);
                                for (KineticBlockEntity be : transmitters) {
                                    System.out.println(" - Block at " + be.getBlockPos());
                                }
                            }

                            return 1;
                        })
        );
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(
                event.getDispatcher(),
                event.getBuildContext(),
                event.getCommandSelection()
        );
    }
}

