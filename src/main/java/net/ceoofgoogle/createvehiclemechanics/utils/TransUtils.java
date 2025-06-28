package net.ceoofgoogle.createvehiclemechanics.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;

public class TransUtils {
    public static boolean isInCenter2x2(BlockHitResult hit) {
        double u = hit.getLocation().x - hit.getBlockPos().getX();
        double v = hit.getLocation().y - hit.getBlockPos().getY();
        double w = hit.getLocation().z - hit.getBlockPos().getZ();

        double hitU = 0, hitV = 0;
        switch (hit.getDirection()) {
            case UP, DOWN -> {
                hitU = u; hitV = w;
            }
            case NORTH, SOUTH -> {
                hitU = u; hitV = 1 - v;
            }
            case WEST, EAST -> {
                hitU = w; hitV = 1 - v;
            }
        }

        // Convert to pixel scale
        double pixelU = hitU * 16;
        double pixelV = hitV * 16;

        return (pixelU >= 6 && pixelU <= 10) && (pixelV >= 6 && pixelV <= 10);
    }

    public static Direction[] getAllowedFacesByAxis(Direction.Axis axis) {
        if (axis == Direction.Axis.X) {
            return new Direction[]{
                    Direction.NORTH, Direction.SOUTH, Direction.UP, Direction.DOWN
            };
        } else if (axis == Direction.Axis.Y) {
            return new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
        } else {
            return new Direction[]{Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};
        }
    }
    public static void displayFreq(){

    }

}