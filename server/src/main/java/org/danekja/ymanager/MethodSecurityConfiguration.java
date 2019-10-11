package org.danekja.ymanager;

import org.danekja.ymanager.business.auth.permissions.DelegatingPermissionEvaluator;
import org.danekja.ymanager.business.auth.permissions.TypedPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity (jsr250Enabled = true, prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final List<TypedPermissionEvaluator> permissionEvaluators;

    @Autowired
    public MethodSecurityConfiguration(List<TypedPermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        methodSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator());

        return methodSecurityExpressionHandler;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        Map<String, PermissionEvaluator> map = new HashMap<>();

        // Build lookup table of PermissionEvaluator by supported target type
        for (TypedPermissionEvaluator permissionEvaluator : permissionEvaluators) {
            map.put(permissionEvaluator.getTargetType(), permissionEvaluator);
        }

        return new DelegatingPermissionEvaluator(map);
    }
}
