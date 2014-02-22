package com.saltedhashed;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

public class VerifierTest {

    @Test
    public void bcryptTest() {
        String password = "";
    }

    @Test
    public void pbkdf2Test() throws Exception {
        String password = PasswordUtils.getRandomPassword();
        PasswordResponse response = new PasswordResponse();
        String salt = PasswordUtils.getRandomSalt();
        response.setAlgorithm(Algorithm.PBKDF2);
        response.setSalt(salt);
        response.setAlgorithmDetails(new AlgorithmDetails());
        response.getAlgorithmDetails().setIterations(100);
        response.getAlgorithmDetails().setHashFunction("SHA-1");
        response.getAlgorithmDetails().setKeySize(password.getBytes().length * 8);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 100, response.getAlgorithmDetails().getKeySize());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmac" + response.getAlgorithmDetails().getHashFunction().replace("-", ""));
        byte[] hash = skf.generateSecret(spec).getEncoded();
        response.setHash(Base64.encodeBase64String(hash));

        Verifier verifier = new Verifier();
        System.out.println(password);
        System.out.println(salt);
        System.out.println(response.getAlgorithmDetails().getKeySize());
        System.out.println(response.getHash());
        Assert.assertTrue(verifier.verify(password, response));
        Assert.assertFalse(verifier.verify(password + "1", response));
    }
}
