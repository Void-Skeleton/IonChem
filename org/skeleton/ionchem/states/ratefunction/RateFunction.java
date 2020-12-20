package org.skeleton.ionchem.states.ratefunction;

import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.Reaction;

public interface RateFunction {
    public Reaction getReaction();

    public void setReaction(Reaction reaction);

    public double forContainer(ChemContainer container);
}
