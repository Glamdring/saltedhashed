package com.saltedhashed;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.lambdaworks.crypto.SCryptUtil;
import com.saltedhashed.model.Algorithm;
import com.saltedhashed.model.AlgorithmDetails;
import com.saltedhashed.model.PasswordResponse;

public class VerifierTest {

    Verifier verifier = new Verifier();

    @Test
    public void bcryptTest() {
        String password = PasswordUtils.getRandomPassword();
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(5));
        PasswordResponse response = new PasswordResponse();
        response.setAlgorithm(Algorithm.BCRYPT);
        response.setHash(hash);

        Assert.assertTrue(verifier.verify(password, response));
        Assert.assertFalse(verifier.verify(password + " ", response));
    }

    @Test
    public void scryptTest() {
        String password = PasswordUtils.getRandomPassword();
        String hash = SCryptUtil.scrypt(password, 2, 2, 2);
        PasswordResponse response = new PasswordResponse();
        response.setAlgorithm(Algorithm.SCRYPT);
        response.setHash(hash);

        Assert.assertTrue(verifier.verify(password, response));
        Assert.assertFalse(verifier.verify(password + " ", response));
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
        response.getAlgorithmDetails().setHashFunction("SHA1");
        response.getAlgorithmDetails().setKeySize(password.getBytes().length * 8);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 100, response.getAlgorithmDetails().getKeySize());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmac" + response.getAlgorithmDetails().getHashFunction().replace("-", ""));
        byte[] hash = skf.generateSecret(spec).getEncoded();
        response.setHash(Base64.encodeBase64String(hash));

        Assert.assertTrue(verifier.verify(password, response));
        Assert.assertFalse(verifier.verify(password + "1", response));
    }
}
