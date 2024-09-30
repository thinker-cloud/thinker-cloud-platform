package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.core.utils.ListUtil;
import com.thinker.cloud.db.enums.DataScopeTypeEnum;
import com.thinker.cloud.upms.api.sys.model.dto.SysUserDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysUserQuery;
import com.thinker.cloud.upms.api.sys.model.vo.AuthorizeVO;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleVO;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserVO;
import com.thinker.cloud.upms.api.uac.enums.UserTypeEnum;
import com.thinker.cloud.upms.sys.converter.SysUserConverter;
import com.thinker.cloud.upms.sys.mapper.SysUserMapper;
import com.thinker.cloud.upms.sys.model.entity.SysUser;
import com.thinker.cloud.upms.sys.service.ISysMenuService;
import com.thinker.cloud.upms.sys.service.ISysOrganizationService;
import com.thinker.cloud.upms.sys.service.ISysRoleService;
import com.thinker.cloud.upms.sys.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息 服务实现类
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysRoleService sysRoleService;
    private final ISysMenuService sysMenuService;
    private final ISysOrganizationService sysOrganizationService;

    @Override
    public List<SysUserVO> page(IPage<SysUserVO> page, SysUserQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysUserVO> list(SysUserQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysUserQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysUserQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysUserVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysUserDTO dto) {
        // 转换数据并保存
        SysUser entity = SysUserConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysUserDTO dto) {
        // 检查数据是否存在
        SysUser oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysUser entity = SysUserConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysUser oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }

    @Override
    public AuthorizeVO getAuthorize(SysUser sysUser) {
        Optional.ofNullable(sysUser).orElseThrow(() -> FailException.of("用户不存在"));

        Long userId = sysUser.getId();
        AuthorizeVO authorize = new AuthorizeVO();

        // 超级管理员拥有所有权限
        if (UserTypeEnum.ADMIN.getType().equals(sysUser.getType())) {
            authorize.setDataScopeType(DataScopeTypeEnum.ALL.getType());
            return authorize;
        }

        // 获取关联角色
        List<SysRoleVO> roles = sysRoleService.listByUserId(userId);
        authorize.setRoles(ListUtil.toList(roles, SysRoleVO::getCode));

        // 获取关联菜单权限
        List<Long> roleIds = ListUtil.toList(roles, SysRoleVO::getId);
        List<SysMenuVO> menus = sysMenuService.listByRoleIds(roleIds);
        authorize.setMenus(ListUtil.toList(menus, SysMenuVO::getCode));
        authorize.setPermissions(ListUtil.toList(menus, SysMenuVO::getPermission));

        // 获取用户最高数据权限类型
        Optional<SysRoleVO> maxRoleOpt = roles.stream()
                .min(Comparator.comparingInt(SysRoleVO::getDataScopeType));
        if (maxRoleOpt.isEmpty()) {
            return authorize;
        }

        // 根据数据权限类型获取数据权限ids
        SysRoleVO sysRoleVO = maxRoleOpt.get();
        DataScopeTypeEnum dataScope = DataScopeTypeEnum.resolver(sysRoleVO.getDataScopeType());
        authorize.setDataScopeType(dataScope.getType());

        // 本级及子级
        Long organizationId = sysUser.getOrganizationId();
        if (DataScopeTypeEnum.OWN_CHILD_LEVEL.equals(dataScope)) {
            List<Long> ids = sysOrganizationService.listAndSubIds(organizationId);
            authorize.setDataScopeIds(ids.stream().map(String::valueOf).toList());
            return authorize;
        }

        // 本级
        if (DataScopeTypeEnum.OWN_LEVEL.equals(dataScope)) {
            authorize.setDataScopeIds(Collections.singleton(String.valueOf(organizationId)));
        }
        return authorize;
    }
}
