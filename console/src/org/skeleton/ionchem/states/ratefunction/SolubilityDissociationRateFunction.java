package org.skeleton.ionchem.states.ratefunction;

import org.skeleton.ionchem.states.ChemComponent;
import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.Reaction;

import java.util.List;

@Deprecated(forRemoval = true)
public class SolubilityDissociationRateFunction implements RateFunction {
    private double solubility;
    private Reaction reaction;

    private ChemComponent reactant;

    /**
     * @throws IllegalArgumentException when the reaction is not dissociation lmao
     * @param reaction reaction lmao
     */
    private void checkReaction(Reaction reaction) {
        /**
         * @see org.skeleton.ionchem.states.generator.ReactionList#build(RateFunction)
         */
        if (reaction != null) {
            // pass a null reaction
            List<ChemComponent> reactants = reaction.getReactants();
            int negative = 0;
            for (ChemComponent component : reactants) {
                if (component.getAmount() < 0) {
                    negative ++;
                    reactant = component;
                }
                if (negative > 1) throw new IllegalArgumentException("The reaction given is not a dissociation reaction! ");
            }
        }
    }

    public SolubilityDissociationRateFunction(double solubility) {
        this(solubility, null);
    }

    public SolubilityDissociationRateFunction(double solubility, Reaction reaction) {
        checkReaction(reaction);
        this.solubility = solubility;
        this.reaction = reaction;
    }

    public double getSolubility() {
        return solubility;
    }

    @Override
    public Reaction getReaction() {
        return reaction;
    }

    @Override
    public void setReaction(Reaction reaction) {
        checkReaction(reaction);
        this.reaction = reaction;
    }

    @Override
    public double forContainer(ChemContainer container) {
        double amount = Integer.MAX_VALUE;
        for (ChemComponent component : reaction.getReactants()) {
            if (component.equals(reactant)) continue;
            String name = component.getName();
            double currentAmount = container.getAmount(name) / component.getAmount();
            if (currentAmount < amount) amount = currentAmount;
        }
        double capacity = solubility - amount;
        double reactantAmount = container.getAmount(reactant.getName());
        if (capacity > 0) {
            return Math.min(reactantAmount, capacity);
        } else {
//            System.out.println(capacity);
            return capacity;
        }
    }
}
