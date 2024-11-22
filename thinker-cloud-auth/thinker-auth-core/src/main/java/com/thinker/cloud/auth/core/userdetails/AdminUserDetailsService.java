package com.thinker.cloud.auth.core.userdetails;

import com.thinker.cloud.core.constants.CommonConstants;
import com.thinker.cloud.security.constants.SecurityConstants;
import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.security.model.AuthUser;
import com.thinker.cloud.upms.api.sys.client.IUserClient;
import com.thinker.cloud.upms.api.uac.enums.UserTypeEnum;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * PC用户统一授权接口实现
 *
 * @author admin
 **/
@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("AlibabaServiceOrDaoClassShouldEndWithImpl")
public class AdminUserDetailsService implements BaseUserDetailsService {

    @DubboReference
    private final IUserClient userClient;

    @Override
    public UserDetails loadUserByAuthParams(AuthParams authParams) {
        AuthUserDetail authUser = userClient.getAuthUser(authParams);
        return this.getUserDetails(authUser);
    }

    /**
     * 构建UserDetails
     *
     * @param userDetail 用户信息
     * @return UserDetails
     */
    private UserDetails getUserDetails(AuthUserDetail userDetail) {
        Optional.ofNullable(userDetail).orElseThrow(() -> new UsernameNotFoundException("账号不存在"));

        Set<String> authsSet = new HashSet<>();

        // 关联角色
        userDetail.getRoles().stream()
                .filter(Objects::nonNull)
                .map(roleId -> SecurityConstants.AUTH_ROLE + roleId)
                .forEach(authsSet::add);

        // 菜单权限
        userDetail.getMenus().stream()
                .filter(Objects::nonNull)
                .map(menuId -> SecurityConstants.AUTH_MENU + menuId)
                .forEach(authsSet::add);

        // 数据权限
        userDetail.getDataScopeIds().stream()
                .filter(Objects::nonNull)
                .map(dataScopeId -> SecurityConstants.AUTH_DATA_SCOPE + dataScopeId)
                .forEach(authsSet::add);

        // 接口权限
        authsSet.addAll(userDetail.getPermissions());

        // 权限集合
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(authsSet.toArray(new String[0]));

        // 构造Security用户
        return new AuthUser(userDetail.getId(), userDetail.getType(), userDetail.getPhone(),
                UserTypeEnum.ADMIN.getType().equals(userDetail.getType()),
                userDetail.getOrganizationId(), userDetail.getTenantId(), userDetail.getDataScopeType(),
                userDetail.getUsername(), SecurityConstants.SECURITY_BCRYPT + userDetail.getPassword(),
                userDetail.getStatus().equals(CommonConstants.STATUS_NORMAL),
                !CommonConstants.STATUS_EXPIRED.equals(userDetail.getStatus()), true,
                !CommonConstants.STATUS_LOCK.equals(userDetail.getStatus()), authorities);
    }
}
