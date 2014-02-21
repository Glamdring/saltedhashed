package com.saltedhashed;

public class PasswordResponse {

    private Algorithm algorithm;
    private String hash;
    private String salt;
    private int iterations;

    public Algorithm getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String base64Hash) {
        this.hash = base64Hash;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public int getIterations() {
        return iterations;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
