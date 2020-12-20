package org.skeleton.ionchem.states.generator;

import org.skeleton.ionchem.states.ChemComponent;
import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.Reaction;
import org.skeleton.ionchem.states.ratefunction.RateFunction;

import java.util.ArrayList;
import java.util.List;

public class ReactionList {
    private List<String> availableReactants;
    private List<ChemComponent> buildBuffer;
    private List<Reaction> availableReactions;
    private List<RateFunction> rateFunctions;

    public ReactionList(List<String> availableReactants) {
        this.availableReactants = availableReactants;
        this.availableReactions = new ArrayList<>();
        this.buildBuffer = new ArrayList<>();
        this.rateFunctions = new ArrayList<>();
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

    public Reaction build(RateFunction rateFunction) {
        Reaction reaction = new Reaction(new ArrayList<>(buildBuffer));
        // will empty the OG buffer, so if i don't new, the reactant list will be empty
        buildBuffer.clear();
        rateFunction.setReaction(reaction);
        availableReactions.add(reaction);
        rateFunctions.add(rateFunction);
        return reaction;
    }

    public void activate(ChemContainer container) {
        activate(container, new boolean[availableReactions.size()]);
    }

    public void activate(ChemContainer container, boolean[] activationStates) {
        boolean hasReacted = false;
        for (int i = 0; i < availableReactions.size(); i ++) {
            Reaction reaction = availableReactions.get(i);
            RateFunction rateFunction = rateFunctions.get(i);
            double rate = rateFunction.forContainer(container);
            double res = reaction.perform(rate, container);
            activationStates[i] = true;
            if (res != 0) hasReacted = true;
        }
        if (!hasReacted) return;
        activate(container, activationStates);
    }
}
