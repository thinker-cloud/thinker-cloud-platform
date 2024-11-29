package com.thinker.cloud.upms.api.uac.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.thinker.cloud.common.enums.IEnumDict;
import com.thinker.cloud.common.utils.enums.EnumCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author admin
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum implements IEnumDict<Integer> {

    //
    ADMIN(-1, "超级管理员"),
    TENANT(1, "租户管理员"),
    ORDINARY(2, "普通用户"),
    ;

    @EnumValue
    private final Integer type;
    private final String desc;

    @Override
    public Integer getValue() {
        return this.type;
    }

    /**
     * 根据类型获取枚举
     *
     * @param type type
     * @return AuthTypeEnum
     */
    public static UserTypeEnum resolver(Integer type) {
        return EnumCacheUtil.loadEnumValue(UserTypeEnum.class, type);
    }

}
