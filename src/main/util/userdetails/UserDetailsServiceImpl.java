// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.util.userdetails;

import java.util.ArrayList;
import java.util.List;

import main.model.user.userrole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.model.user.User;
import main.service.crud.user.user.UserCrudService;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserCrudService userCrudService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        User user = userCrudService.findUserByUsername(ssoId);
		if( user == null ) {
			throw new UsernameNotFoundException("Username not found");
		}
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, getGrantedAuthorities(user));
	}

    public List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole userRole : user.getUserRoles()){
			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userRole.getRoleName()));
		}
		return authorities;
	}
	
}
