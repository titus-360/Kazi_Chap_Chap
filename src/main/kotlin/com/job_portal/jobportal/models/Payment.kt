package com.job_portal.jobportal.models

import jakarta.persistence.*

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "amount")
    val amount: Double = 0.0,

    @Column(name = "payment_date")
    val paymentDate: String = "",

    @ManyToOne
    val tenant: Tenant? = null
)
