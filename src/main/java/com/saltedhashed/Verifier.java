package com.saltedhashed;

import gnu.crypto.Registry;
import gnu.crypto.jce.GnuCrypto;

import java.security.Security;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;

import com.lambdaworks.crypto.SCryptUtil;
import com.saltedhashed.crypto.PBKDF2Algorithms;
import com.saltedhashed.model.Algorithm;
import com.saltedhashed.model.PasswordResponse;

public class Verifier {

    static {
        PBKDF2Algorithms.initialize();
    }

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
                SecretKeyFactory skf = PBKDF2Algorithms.getSecretKeyFactory("PBKDF2WithHmac" + response.getAlgorithmDetails().getHashFunction().replace("-", ""));
                byte[] expectedHash = skf.generateSecret(spec).getEncoded();
                return Arrays.equals(hash, expectedHash);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        return true;
    }
}
