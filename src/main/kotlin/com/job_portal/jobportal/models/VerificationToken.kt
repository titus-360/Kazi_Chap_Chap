package com.job_portal.jobportal.models

import jakarta.persistence.*

/**
 *
 * @author Titus Murithi Bundi
 */
@Table(name = "verification_tokens")
@Entity
data class VerificationToken(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_token_id_seq")
    @SequenceGenerator(
        name = "verification_token_id_seq",
        sequenceName = "verification_token_id_seq",
        allocationSize = 1
    )
    val id: Long? = null,

    @Column(name = "token")
    val token: String = "",

    @Column(name = "expiry_date")
    val expiryDate: Long = 0,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)
