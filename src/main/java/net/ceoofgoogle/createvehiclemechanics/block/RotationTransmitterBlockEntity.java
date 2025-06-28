package net.ceoofgoogle.createvehiclemechanics.block;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.ceoofgoogle.createvehiclemechanics.utils.Network;
import net.ceoofgoogle.createvehiclemechanics.utils.Translib;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class RotationTransmitterBlockEntity extends KineticBlockEntity implements Translib {
    public RotationTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
  /*  @Override
    public float getGeneratedSpeed() {
        float base = super.getGeneratedSpeed();
        float clamped = Mth.clamp(base, -16, 16);
        if (base != clamped) {
            System.out.println("Clamped speed from " + base + " to " + clamped);
        }
        return clamped;
    }

   */
    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);

        // Clamp speed to max 16 RPM
        if (Math.abs(getSpeed()) > 16) {
            setSpeed(Mth.clamp(getSpeed(), -16, 16));
            notifyUpdate(); // sync with clients
        }
    }


    @Override
    public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
        if (other instanceof RotationTransmitterBlockEntity otherTransmitter) {
            return otherTransmitter.getChannel() == getChannel() &&
                    otherTransmitter.getLevel() == getLevel() &&
                    otherTransmitter.getPassword().equals(getPassword());
        }
        return false;
    }

    @Override
    public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
        var checks = new ArrayList<>(getConnectedTransmitters());
        for (KineticBlockEntity tile : checks) {
            if (tile.getBlockPos() != this.getBlockPos() && !neighbours.contains(tile.getBlockPos())) {
                neighbours.add(tile.getBlockPos());
            }
        }
        return super.addPropagationLocations(block, state, neighbours);
    }

    public List<KineticBlockEntity> getConnectedTransmitters() {
        Map<String, List<KineticBlockEntity>> passwordMap = Network.ROTATE.Frequency.get(0); // Assume using index 0 or a default one

        // Clean up removed or empty entries
        passwordMap.values().removeIf(List::isEmpty);
        passwordMap.values().forEach(list -> list.removeIf(BlockEntity::isRemoved));

        String password = getPassword();
        List<KineticBlockEntity> list = passwordMap.computeIfAbsent(password, k -> new ArrayList<>());

        // Remove mismatched or invalid transmitters
        list.removeIf(blockEntity ->
                !(blockEntity instanceof RotationTransmitterBlockEntity transmitter)
                        || !transmitter.getPassword().equals(password)
        );

        // Add self if not present
        if (!list.contains(this)) {
            list.add(this);
        }

        return list;
    }

    @Override
    public void reloadSettings() {
        getConnectedTransmitters().remove(this);
        if (level == null) return;
        if (hasNetwork())
            getOrCreateNetwork().remove(this);
        detachKinetics();
        removeSource();
    }

    @Override
    public void afterReload() {
        if (level == null) return;

        attachKinetics();
    }

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
        return (target.getPersistentData().getString("password").equals(getPassword())
                && target.getBlockState().getBlock() instanceof RotationalTransmitterBlock) ? 1f : 0f;
    }

}
