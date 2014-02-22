package com.saltedhashed;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;

import com.lambdaworks.crypto.SCryptUtil;

public class Verifier {

    public boolean verify(String password, PasswordResponse response) {
        if (response.getAlgorithm() == Algorithm.BCRYPT) {
            return BCrypt.checkpw(password, response.getHash());
        }

        if (response.getAlgorithm() == Algorithm.SCRYPT) {
            return SCryptUtil.check(password, response.getHash());
        }

        if (response.getAlgorithm() == Algorithm.PBKDF2) {
            byte[] hash = Base64.decodeBase64(response.getHash());
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), response.getSalt().getBytes(), response
                    .getAlgorithmDetails().getIterations(), response.getAlgorithmDetails().getKeySize());
            try {
                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmac" + response.getAlgorithmDetails().getHashFunction().replace("-", ""));
                byte[] expectedHash = skf.generateSecret(spec).getEncoded();
                return Arrays.equals(hash, expectedHash);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        if (response.getAlgorithm() == Algorithm.SHA512) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] expectedHash = digest.digest((password + response.getSalt()).getBytes("UTF-8"));
                return Arrays.equals(Base64.decodeBase64(response.getHash()), expectedHash);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        return true;
    }
}
