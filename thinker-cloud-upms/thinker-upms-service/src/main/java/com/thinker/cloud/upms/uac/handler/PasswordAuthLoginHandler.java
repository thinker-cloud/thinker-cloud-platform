package com.thinker.cloud.upms.uac.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.api.uac.enums.AuthTypeEnum;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUser;
import com.thinker.cloud.upms.sys.model.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 密码登录处理器
 *
 * @author admin
 **/
@Slf4j
@Component
public class PasswordAuthLoginHandler extends AbstractAuthLoginHandler {

    @Override
    protected AuthTypeEnum getAuthType() {
        return AuthTypeEnum.PASSWORD;
    }

    @Slave
    @Override
    public AuthUser getAuthUser(AuthParams authParam) {
        SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, authParam.getSubject()));
        return super.getAuthUser(sysUser);
    }

}
