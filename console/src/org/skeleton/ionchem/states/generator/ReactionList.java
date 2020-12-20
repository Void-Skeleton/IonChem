package org.skeleton.ionchem.states.generator;

import org.skeleton.ionchem.states.ChemComponent;
import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.Reaction;
//import org.skeleton.ionchem.states.ratefunction.RateFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReactionList {
    private List<String> availableReactants;
    private List<ChemComponent> buildBuffer;
    private List<Reaction> availableReactions;
//    private List<RateFunction> rateFunctions;

    public ReactionList(List<String> availableReactants) {
        this.availableReactants = availableReactants;
        this.availableReactions = new ArrayList<>();
        this.buildBuffer = new ArrayList<>();
//        this.rateFunctions = new ArrayList<>();
    }

    public List<String> getAvailableReactants() {
        return availableReactants;
    }

    public void addComponent(ChemComponent component) {
        buildBuffer.add(component);
    }

    public void addComponents(ChemComponent... components) {
        for (ChemComponent component : components) addComponent(component);
    }

    /**
     * requires the constructor needed to be the first!!!
     * @param clazz the desired class
     * @param params the additional parameters
     * @return the obtained reaction
     */
    public Reaction build0(Class clazz, int index, Object... params) {
        Reaction reaction = null;
        try {
            Class[] paramTypes = new Class[params.length + 1];
            paramTypes[0] = List.class;
            for (int i = 0; i < params.length; i ++) {
                paramTypes[i + 1] = params[i].getClass();
//                System.out.println(paramTypes[i + 1]);
            }
            Object[] params1 = new Object[params.length + 1];
            params1[0] = new ArrayList<>(buildBuffer);
            System.arraycopy(params, 0, params1, 1, params.length);
            reaction = (Reaction) clazz.getDeclaredConstructor(paramTypes).newInstance(params1);
            // will empty the OG buffer, so if i don't new, the reactant list will be empty
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        buildBuffer.clear();
        availableReactions.add(reaction);
        return reaction;
    }

    public Reaction build(Class clazz, Object...  params) {
        return build0(clazz, 0, params);
    }

    public void activate(ChemContainer container) {
        activate(container, new boolean[availableReactions.size()]);
    }

    public void activate(ChemContainer container, boolean[] activationStates) {
        boolean hasReacted = false;
        for (int i = 0; i < availableReactions.size(); i ++) {
            Reaction reaction = availableReactions.get(i);
            double res = reaction.perform(container);
            if (res != 0) {
                activationStates[i] = true;
                hasReacted = true;
            }
        }
        if (!hasReacted) return;
        activate(container, activationStates);
    }
}
