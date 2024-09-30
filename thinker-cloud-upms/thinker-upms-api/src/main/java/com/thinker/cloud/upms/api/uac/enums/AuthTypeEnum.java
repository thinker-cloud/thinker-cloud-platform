package com.thinker.cloud.upms.api.uac.enums;

import com.thinker.cloud.core.enums.IEnumDict;
import com.thinker.cloud.core.utils.enums.EnumCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录认证类型
 *
 * @author admin
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnum implements IEnumDict<String> {

    //
    PASSWORD("账号密码登录"),
    SMS("验证码登录"),
    WX("微信登录"),
    WX_MINI("微信小程序登录");

    private final String desc;

    @Override
    public String getValue() {
        return this.name();
    }

    /**
     * 根据类型获取枚举
     *
     * @param type type
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum resolver(String type) {
        return EnumCacheUtil.loadEnumValue(AuthTypeEnum.class, type);
    }
}
