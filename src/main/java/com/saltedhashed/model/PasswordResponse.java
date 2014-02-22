package com.saltedhashed.model;

public class PasswordResponse {

    private Algorithm algorithm;
    private AlgorithmDetails algorithmDetails;
    private String hash;
    private String salt;

    public Algorithm getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public AlgorithmDetails getAlgorithmDetails() {
        return algorithmDetails;
    }
    public void setAlgorithmDetails(AlgorithmDetails algorithmDetails) {
        this.algorithmDetails = algorithmDetails;
    }
}
