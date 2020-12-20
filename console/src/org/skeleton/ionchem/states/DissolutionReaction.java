package org.skeleton.ionchem.states;

import java.util.List;

public class DissolutionReaction extends Reaction {
    private double solubility;

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

    public DissolutionReaction(List<ChemComponent> reactants, Double
            solubility) {
        super(reactants);
        checkReaction(this);
        this.solubility = solubility;
    }

    public double getSolubility() {
        return solubility;
    }

    @Override
    public double getRate(ChemContainer container) {
        double amount = Integer.MAX_VALUE;
        for (ChemComponent component : this.getReactants()) {
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
