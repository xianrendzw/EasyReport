package com.easytoolsoft.easyreport.membership.shiro.security;

import com.easytoolsoft.easyreport.support.security.PasswordService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

/**
 * 用户口令服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("ShiroPasswordService")
public class ShiroPasswordService implements PasswordService {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void setRandomNumberGenerator(final RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(final String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(final int hashIterations) {
        this.hashIterations = hashIterations;
    }

    @Override
    public String genreateSalt() {
        return this.randomNumberGenerator.nextBytes().toHex();
    }

    @Override
    public String encode(final CharSequence rawPassword, final String credentialsSalt) {
        return new SimpleHash(
            this.algorithmName,
            rawPassword,
            ByteSource.Util.bytes(credentialsSalt),
            this.hashIterations).toHex();
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return this.encode(rawPassword, "shiro");
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return this.matches(rawPassword, encodedPassword, "shiro");
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword, final String credentialsSalt) {
        final String encode = this.encode(rawPassword, credentialsSalt);
        return encode.equals(encodedPassword);
    }

}
