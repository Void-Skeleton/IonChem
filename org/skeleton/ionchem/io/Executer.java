package org.skeleton.ionchem.io;

import org.skeleton.ionchem.states.ChemComponent;
import org.skeleton.ionchem.states.ChemContainer;
import org.skeleton.ionchem.states.generator.ReactionList;

public class Executer {
    private ChemContainer container;
    private ReactionList reactionList;

    public Executer(ChemContainer container, ReactionList reactionList) {
        this.container = container;
        this.reactionList = reactionList;
    }

    public void view(String[] args) {
        for (ChemComponent component : container.getComponents()) {
            System.out.printf("%s : %f\n", component.getName(), component.getAmount());
        }
    }

    public void modify(String[] args) {
        String name = args[0];
        double amount = Double.parseDouble(args[1]);
        container.modifyComponent(name, amount);
    }

    public void react(String[] args) {
        reactionList.activate(container);
    }

    public void stop(String[] args) {
        System.exit(0);
    }
}
