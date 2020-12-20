package org.skeleton.ionchem.states;

import java.util.List;

public class Reaction {
    private List<ChemComponent> reactants;

    public Reaction(List<ChemComponent> reactants) {
        this.reactants = reactants;
    }

    public List<ChemComponent> getReactants() {
        return reactants;
    }

    private void perform0(double degree, ChemContainer target) {
        for (ChemComponent c : reactants) {
            target.modifyComponent(c.getName(), degree * c.getAmount());
        }
    }

    public double perform(double degree, ChemContainer target) {
        double actualDegree = degree;
        for (ChemComponent c : reactants) {
            double amountAfter = actualDegree * c.getAmount() + target.getAmount(c.getName());
            if (amountAfter < 0) actualDegree = - target.getAmount(c.getName()) / c.getAmount();
        }
        perform0(actualDegree, target);
        target.clean();
        return actualDegree;
    }
}
