package com.job_portal.jobportal.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * @author Titus Murithi Bundi
 */
@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long? = null,

    @JsonIgnore
    @Column(name = "username")
    var userName: String = "",

    @Column(name = "email")
    var email: String = "",

    @Column(name = "phonenumber")
    var phoneNumber: String = "",

    @JsonIgnore
    @Column(name = "password")
    var userPassword: String = "",

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = setOf()
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }

    override fun getPassword(): String = userPassword
    override fun getUsername(): String = userName
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
