package main.security.coursemembership;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import org.springframework.security.access.ConfigAttribute;

import main.security.coursemembership.annotations.CourseMembershipRequired;

public class CourseMembershipSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return processAnnotations(clazz.getAnnotations());
    }

    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        return processAnnotations(AnnotationUtils.getAnnotations(method));
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private List<ConfigAttribute> processAnnotations(Annotation[] annotations) {
        if( ( annotations == null ) || ( annotations.length == 0 ) ) {
            return null;
        }
        List<ConfigAttribute> attributes = new ArrayList<>();
        for( Annotation a : annotations ) {
            if( a instanceof CourseMembershipRequired ) {
                CourseMembershipRequired cmr = (CourseMembershipRequired) a;
                attributes.add(new CourseMembershipSecurityConfig(cmr.type().name()));
                return attributes;
            }
        }
        return null;
    }

}