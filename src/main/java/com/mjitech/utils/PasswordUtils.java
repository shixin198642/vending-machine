package com.mjitech.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.mjitech.constant.PasswordConstant;

@Component
public class PasswordUtils {

	public String hashPassword(String password) {
		String currentTimestamp = Long.toString(System.currentTimeMillis());

		String md5 = DigestUtils.md5Hex(password.getBytes());

		String beforeBase64 = currentTimestamp + md5;

		String base64 = Base64.encodeBase64String(beforeBase64.getBytes());

		return PasswordConstant.PASSWORD_PREFIX + base64;
	}

	private String getMD5FromHash(String hash) {
		String base64 = hash.substring(PasswordConstant.PASSWORD_PREFIX
				.length());
		String base64decoded = new String(Base64.decodeBase64(base64));
		String md5 = base64decoded.substring(13);

		return md5;
	}

	public boolean validatePassword(String password, String hash) {
		if (hash.startsWith(PasswordConstant.PASSWORD_PREFIX)) {
			String md5 = this.getMD5FromHash(hash);
			String passwordMd5 = DigestUtils.md5Hex(password.getBytes());
			return md5.equals(passwordMd5);
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		PasswordUtils u = new PasswordUtils();
		System.out
				.println(u.validatePassword("12345666", u.hashPassword("12345666")));
	}

}
