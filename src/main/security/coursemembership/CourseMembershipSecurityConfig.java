package main.security.coursemembership;

import org.springframework.security.access.SecurityConfig;

public class CourseMembershipSecurityConfig extends SecurityConfig {

    public CourseMembershipSecurityConfig(String type) {
        super(type);
    }

}
