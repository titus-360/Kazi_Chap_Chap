package com.job_portal.jobportal.models

import jakarta.persistence.*

/**
 * Author: Titus Murithi Bundi
 * Date:11/11/24
 */
@Entity
@Table(name = "house_types")
data class HouseType(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_type_id_seq")
    @SequenceGenerator(name = "house_type_id_seq", sequenceName = "house_type_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "category")
    val category: String = ""
)
