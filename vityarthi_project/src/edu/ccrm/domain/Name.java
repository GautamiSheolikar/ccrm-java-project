package edu.ccrm.domain;

public final class Name {
    private final String first;
    private final String last;

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() { return first; }
    public String getLast() { return last; }

    public String full() {
        String f = first == null ? "" : first;
        String l = last == null ? "" : last;
        String full = (f + " " + l).trim();
        return full;
    }

    @Override
    public String toString() { return full(); }
}


