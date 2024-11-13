package com.job_portal.jobportal.models

import jakarta.persistence.*
import jakarta.persistence.Table

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Table(name = "tenants")
@Entity
data class Tenant(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_id_seq")
    @SequenceGenerator(name = "tenant_id_seq", sequenceName = "tenant_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "first_name")
    val firstName: String = "",

    @Column(name = "last_name")
    val lastName: String = "",

    @Column(name = "email")
    val email: String = "",

    @Column(name = "outstanding_balance")
    val outstandingBalance: Double = 0.0,

    @ManyToOne
    val house: House? = null


)
