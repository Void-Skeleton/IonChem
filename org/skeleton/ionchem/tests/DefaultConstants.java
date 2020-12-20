package org.skeleton.ionchem.tests;

import org.skeleton.ionchem.states.ChemComponent;
import org.skeleton.ionchem.states.generator.ReactionList;
import org.skeleton.ionchem.states.ratefunction.SolubilityDissociationRateFunction;

import java.util.ArrayList;
import java.util.List;

public class DefaultConstants {
    // some double replacement reaction env idk
    public static final ReactionList reactionList;

    static {
        List<String> availableReactants = new ArrayList<>();
        availableReactants.add("sodium_ion");
        availableReactants.add("chloride_ion");
        availableReactants.add("silver_ion");
        availableReactants.add("nitrate_ion");
        availableReactants.add("sodium_chloride");
        availableReactants.add("sodium_nitrate");
        availableReactants.add("silver_chloride");
        availableReactants.add("silver_nitrate");
        reactionList = new ReactionList(availableReactants);
        ChemComponent c1 = new ChemComponent("sodium_ion", 1);
        ChemComponent c2 = new ChemComponent("chloride_ion", 1);
        ChemComponent c3 = new ChemComponent("silver_ion", 1);
        ChemComponent c4 = new ChemComponent("nitrate_ion", 1);
        ChemComponent c5 = new ChemComponent("sodium_chloride", -1);
        ChemComponent c6 = new ChemComponent("sodium_nitrate", -1);
        ChemComponent c7 = new ChemComponent("silver_chloride", -1);
        ChemComponent c8 = new ChemComponent("silver_nitrate", -1);

        reactionList.addComponents(c5, c1, c2);
        reactionList.build(new SolubilityDissociationRateFunction(5.41));
        reactionList.addComponents(c6, c1, c4);
        reactionList.build(new SolubilityDissociationRateFunction(8.7));
        reactionList.addComponents(c7, c3, c2);
        reactionList.build(new SolubilityDissociationRateFunction(0));
        reactionList.addComponents(c8, c3, c4);
        reactionList.build(new SolubilityDissociationRateFunction(7.17));
    }
}
