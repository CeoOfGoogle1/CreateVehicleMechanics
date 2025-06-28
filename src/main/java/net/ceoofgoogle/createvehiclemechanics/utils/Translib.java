package net.ceoofgoogle.createvehiclemechanics.utils;
import com.simibubi.create.content.kinetics.base.IRotate;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
public interface Translib extends IForgeBlockEntity {
    default void reloadSettings(){}

    default void afterReload(){}

    default int getChannel(){
        return getPersistentData().getInt("channel");
    }

    default String getPassword(){
        return getPersistentData().getString("password");
    }

}
