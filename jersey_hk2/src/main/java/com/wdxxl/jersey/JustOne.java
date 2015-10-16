package com.wdxxl.jersey;
public class JustOne {
    private int secretNumber = 0;

    public int getSecretNumber() {
        return secretNumber;
    }

    public void bumpSecretNumber() {
        secretNumber += 1;
    }
}