package net.ceoofgoogle.createvehiclemechanics.block;


import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.ceoofgoogle.createvehiclemechanics.registry.ModBlocks;
import net.ceoofgoogle.createvehiclemechanics.utils.TransUtils;
import net.ceoofgoogle.createvehiclemechanics.utils.Translib;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Arrays;



public class RotationalTransmitterBlock extends DirectionalKineticBlock  implements  IBE<RotationTransmitterBlockEntity>,IWrenchable {

    public RotationalTransmitterBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        return defaultBlockState()
                .setValue(FACING, face)
                .setValue(BlockStateProperties.AXIS, face.getAxis());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(BlockStateProperties.AXIS);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(BlockStateProperties.AXIS);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            RotationTransmitterBlockEntity be = getBlockEntity(level, pos);
            if (be != null)
                be.getConnectedTransmitters().remove(be);
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public Class<RotationTransmitterBlockEntity> getBlockEntityClass() {
        return RotationTransmitterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RotationTransmitterBlockEntity> getBlockEntityType() {
        return ModBlocks.RTBE.get();
    }

    @Override
    public  InteractionResult use(BlockState state, Level level, BlockPos pos,
                                  Player player, InteractionHand hand, BlockHitResult hit) {
        Direction.Axis axis = state.getValue(BlockStateProperties.AXIS); // or your axis property
        Direction face = hit.getDirection();

        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.isEmpty())
            return InteractionResult.PASS;

        Direction[] allowedFaces = TransUtils.getAllowedFacesByAxis(axis);
        if (!Arrays.asList(allowedFaces).contains(face))
            return InteractionResult.FAIL;

        if (!TransUtils.isInCenter2x2(hit))
            return InteractionResult.PASS;

        if (!(level.getBlockEntity(pos) instanceof Translib translib))
            return InteractionResult.PASS;

        if (player.isShiftKeyDown()) {
            translib.getPersistentData().remove("password");
            player.displayClientMessage(Component.literal("Frequency cleared."), true);
        } else {
            String itemName = heldItem.getItem().getDescriptionId(); // or getRegistryName().toString()
            translib.getPersistentData().putString("password", itemName);
            player.displayClientMessage(Component.literal("Frequency set to: " + itemName), true);
        }

        translib.reloadSettings();
        translib.afterReload();

        return InteractionResult.SUCCESS;
    }
}
