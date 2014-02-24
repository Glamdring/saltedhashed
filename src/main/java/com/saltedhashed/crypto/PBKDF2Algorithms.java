package com.saltedhashed.crypto;

import java.lang.reflect.Constructor;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKeyFactorySpi;

/**
 * Additional PBKDF2 algorithms (With SHA-X)
 *
 * @author bozho
 *
 */
public class PBKDF2Algorithms {
    private static final Provider provider = Security.getProvider("SunJCE");
    public static void initialize() {
        provider.put("SecretKeyFactory.PBKDF2WithHmacSHA224", "com.saltedhashed.crypto.PBKDF2Core$HmacSHA224");
        provider.put("SecretKeyFactory.PBKDF2WithHmacSHA256", "com.saltedhashed.crypto.PBKDF2Core$HmacSHA256");
        provider.put("SecretKeyFactory.PBKDF2WithHmacSHA384", "com.saltedhashed.crypto.PBKDF2Core$HmacSHA384");
        provider.put("SecretKeyFactory.PBKDF2WithHmacSHA512", "com.saltedhashed.crypto.PBKDF2Core$HmacSHA512");
    }

    /**
     * Factory method to get the proper factory class. The SunJCE provider
     * appears to have its own classloader so simply adding the values (as
     * above) does not work and the classes are not found. Hence the reflection
     * work below.
     *
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    @SuppressWarnings("unchecked")
    public static SecretKeyFactory getSecretKeyFactory(String algorithm) throws NoSuchAlgorithmException {
        try {
            Class<SecretKeyFactorySpi> clazz = (Class<SecretKeyFactorySpi>) Class.forName((String) provider.get("SecretKeyFactory." + algorithm));
            SecretKeyFactorySpi spi = clazz.newInstance();
            Constructor<SecretKeyFactory> c = SecretKeyFactory.class.getDeclaredConstructor(SecretKeyFactorySpi.class, Provider.class, String.class);
            c.setAccessible(true);
            return c.newInstance(spi, provider, algorithm);
        } catch (Exception e) {
            throw new NoSuchAlgorithmException(e);
        }
    }

}
