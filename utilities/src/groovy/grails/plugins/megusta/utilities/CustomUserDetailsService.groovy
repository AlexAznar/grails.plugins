package grails.plugins.megusta.utilities

/**
 * Created by IntelliJ IDEA.
 * User: gaurav
 * Date: 12/5/11
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CustomUserDetailsService implements GrailsUserDetailsService {

    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
        return loadUserByUsername(username)
    }

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def userClassName = ConfigurationHolder.config.grails.plugins.springsecurity.userLookup.userDomainClassName
        def userClass = ApplicationHolder.application.getClassForName(userClassName)

        userClass.withTransaction { status ->
            def user = userClass.findByUsernameOrEmail(username, username)
            if (!user)
                throw new UsernameNotFoundException('User not found', username)

            def authorities = user.authorities.collect {new GrantedAuthorityImpl(it.authority)}

            return new GrailsUser(user.username, user.password, user.enabled, !user.accountExpired,
                    !user.passwordExpired, !user.accountLocked,
                    authorities ?: NO_ROLES, user.id)
        }
    }
}