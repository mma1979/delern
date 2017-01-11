package org.dasfoo.delern.models;

/**
 * Created by katarina on 11/1/16.
 */

public enum Level {

    /**
     * Level of card. Card is new.
     */
    L0,

    /**
     * Level of card for repetition intervals.
     */
    L1,

    /**
     * Level of card for repetition intervals.
     */
    L2,

    /**
     * Level of card for repetition intervals.
     */
    L3,

    /**
     * Level of card for repetition intervals.
     */
    L4,

    /**
     * Level of card for repetition intervals.
     */
    L5,

    /**
     * Level of card for repetition intervals.
     */
    L6,

    /**
     * Level of card for repetition intervals.
     */
    L7;

    private static Level[] sValues = values();

    /**
     * Returns next level by current in enum. If current level is L3, it returns L4.
     *
     * @return next level of card.
     */
    public Level next() {
        return sValues[(this.ordinal() + 1) % sValues.length];
    }
}