//package com.job_portal.jobportal.services.impl
//
//import com.job_portal.jobportal.dtos.SignUpRequestDto
//import com.job_portal.jobportal.models.User
//import com.job_portal.jobportal.repositories.UserRepository
//import com.job_portal.jobportal.services.UserService
//import org.slf4j.LoggerFactory
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//
///**
// *
// * @author Titus Murithi Bundi
// */
//@Service
//class CustomUserDetailsService(private val userRepository: UserRepository) : UserService {
//
//    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
//
//    override fun existsByUsername(userName: String): User {
//        logger.info("Checking if user exists by username: $userName")
//        val user = userRepository.findByUserName(userName)
//            ?: throw UsernameNotFoundException("User not found with username: $userName")
//        return user
//    }
//
//    override fun registerUser(signUpRequestDto: SignUpRequestDto): User {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun existsByEmail(email: String): Boolean {
//        TODO("Not yet implemented")
//    }
//}
