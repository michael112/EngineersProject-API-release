// (C) websystique

package main.service.userdetails;

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
import main.service.model.user.user.UserService;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(ssoId);
		System.out.println("User : "+user);
		if(user==null){
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, getGrantedAuthorities(user));
	}


    private List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(UserRole userRole : (java.util.Set<UserRole>) user.getUserRoles()){
			System.out.println("UserRole : "+ userRole);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userRole.getRoleName()));
		}
		System.out.print("authorities :"+authorities);
		return authorities;
	}
	
}
