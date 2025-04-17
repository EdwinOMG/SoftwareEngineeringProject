package main.java;

import java.util.Collection;
import java.util.Iterator;
// wrapper class for 2d array, this is the return object
public class DigitChains implements Iterable<Iterable<Integer>> {
    private final Iterable<Iterable<Integer>> chains;

    public DigitChains(Iterable<Iterable<Integer>> chains) {
        this.chains = chains;
    }

    @Override
    public Iterator<Iterable<Integer>> iterator() {
        return chains.iterator();
    }

	public Collection<Integer> getChains() {
		// TODO Auto-generated method stub
		return null;
	}
}