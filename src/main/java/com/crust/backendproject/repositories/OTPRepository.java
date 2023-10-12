package com.crust.backendproject.repositories;

import com.crust.backendproject.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Transactional
public interface OTPRepository extends JpaRepository<OTP, Long> {

	Optional<OTP> findByOtpAndEmail(String otp, String email);

	void deleteOtpByExpiredAtBefore(Instant now);

	@Modifying
	@Query("UPDATE OTP confirmOtp SET confirmOtp.confirmedAt = ?1 WHERE confirmOtp.email = ?2")
	void confirmedAt(Instant now, String email);

}
