package net.ceoofgoogle.createvehiclemechanics.utils;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Network {
        ROTATE;

        public final List<Map<String, List<KineticBlockEntity>>> Frequency;

        Network(){
            this.Frequency = new ArrayList<>();
            Frequency.add(new HashMap<>());
        }

    }

