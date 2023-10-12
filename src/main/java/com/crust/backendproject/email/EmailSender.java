package com.crust.backendproject.email;

public interface EmailSender {

	void send(String to, String email) throws RuntimeException;

}
