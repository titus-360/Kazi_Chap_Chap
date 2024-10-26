package com.job_portal.jobportal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("com.job_portal.jobportal.models")
@EnableJpaRepositories("com.job_portal.jobportal.repositories")
class JobPortalApplication

fun main(args: Array<String>) {
    runApplication<JobPortalApplication>(*args)
}
