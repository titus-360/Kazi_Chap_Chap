package com.job_portal.jobportal.models

import jakarta.persistence.*

/**
 * Author: Titus Murithi Bundi
 * Date:11/11/24
 */
@Entity
@Table(name = "house")
data class House(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_id_seq")
    @SequenceGenerator(name = "house_id_seq", sequenceName = "house_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "house_type_id")
    val houseType: HouseType? = null,

    @Column(name = "price")
    val price: Double = 0.0,
)
