package com.saltedhashed.model;

public class AlgorithmDetails {

    private int iterations;
    private String hashFunction;
    private int keySize;
    public int getIterations() {
        return iterations;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    public String getHashFunction() {
        return hashFunction;
    }
    public void setHashFunction(String hashFunction) {
        this.hashFunction = hashFunction;
    }
    public int getKeySize() {
        return keySize;
    }
    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
}
