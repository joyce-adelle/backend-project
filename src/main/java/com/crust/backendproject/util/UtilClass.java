package com.crust.backendproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class UtilClass {

	private UtilClass() {

	}

	/*
	 * Generates a six digit random number
	 * 
	 */
	public static String generateOtp() {

		SecureRandom random = new SecureRandom();

		return String.valueOf(100000 + random.nextInt(900000));
	}

	public static String buildEmail(String name, String otp) {
		try {
			Resource resource = new ClassPathResource("templates/template.html");
			String template = Files.readString(resource.getFile().toPath());
			return template.replace("{{name}}", name).replace("{{otp}}", otp);
		} catch (IOException e) {
			log.error("Error reading email template: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNull(Collection<?> collection) {
		return isNull((Object) collection) || collection.isEmpty();
	}

	public static boolean isNull(Map<?, ?> map) {
		return isNull((Object) map) || map.isEmpty();
	}

	public static boolean isNull(String key) {
		return key == null || key.isEmpty();
	}

}
