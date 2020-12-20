package org.skeleton.ionchem.states.ratefunction;

import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.Reaction;

@Deprecated(forRemoval = true)
public interface RateFunction {
    public Reaction getReaction();

    public void setReaction(Reaction reaction);

    public double forContainer(ChemContainer container);
}
