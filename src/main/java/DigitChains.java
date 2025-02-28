package main.java;

import java.util.Iterator;

public class DigitChains implements Iterable<Iterable<Integer>> {
    private final Iterable<Iterable<Integer>> chains;

    public DigitChains(Iterable<Iterable<Integer>> chains) {
        this.chains = chains;
    }

    @Override
    public Iterator<Iterable<Integer>> iterator() {
        return chains.iterator();
    }
}