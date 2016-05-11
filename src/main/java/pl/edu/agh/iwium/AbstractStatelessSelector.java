package pl.edu.agh.iwium;

import algorithms.ISelector;
import dataset.Dataset;
import environment.IAction;
import environment.IState;

public abstract class AbstractStatelessSelector implements ISelector {
	private static final long serialVersionUID = 1L;

	@Override
	public void learn(IState s1, IState s2, IAction a, double reward) {}

	@Override
	public void newEpisode() {}

	@Override
	public Dataset extractDataset() {
		return null;
	} 
	
}
