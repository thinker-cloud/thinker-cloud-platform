package com.thinker.cloud.upms.uac.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.security.credential.CredentialTemplate;
import com.thinker.cloud.security.credential.model.CredentialValid;
import com.thinker.cloud.upms.api.uac.enums.LoginTypeEnum;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
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

    private final CredentialTemplate credentialTemplate;

    @Override
    protected LoginTypeEnum getAuthType() {
        return LoginTypeEnum.SMS;
    }

    @Override
    public AuthUserDetail getAuthUser(AuthParams authParam) {
        // 检查验证码是否正确
        CredentialValid validation = new CredentialValid()
                .setSubject(authParam.getSubject())
                .setCredential(authParam.getCredential());
        if (!credentialTemplate.validate(validation)) {
            throw FailException.of("验证码错误");
        }

        // 根据登录手机号获取用户
        SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPhone, authParam.getSubject()));
        return super.getAuthUser(sysUser);
    }
}
