package com.thinker.cloud.upms.uac.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.security.enums.LoginTypeEnum;
import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import com.thinker.cloud.upms.sys.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信验证码登录处理器
 *
 * @author admin
 **/
@Slf4j
@Component
@AllArgsConstructor
public class SmsAuthLoginHandler extends AbstractAuthLoginHandler {

    @Override
    protected LoginTypeEnum getAuthType() {
        return LoginTypeEnum.SMS;
    }

    @Slave
    @Override
    public AuthUserDetail getAuthUser(AuthParams authParam) {
        SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPhone, authParam.getSubject()));
        return super.getAuthUser(sysUser);
    }

    @Slave
    @Override
    public AuthUserDetail getMemberUser(AuthParams authParam) {
        return null;
    }
}
