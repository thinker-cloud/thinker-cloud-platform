package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.common.exception.FailException;
import com.thinker.cloud.common.utils.ListUtil;
import com.thinker.cloud.upms.api.sys.model.dto.SysRoleDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysRoleQuery;
import com.thinker.cloud.upms.api.sys.model.query.SysUserRoleQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleVO;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserRoleVO;
import com.thinker.cloud.upms.sys.converter.SysRoleConverter;
import com.thinker.cloud.upms.sys.mapper.SysRoleMapper;
import com.thinker.cloud.upms.sys.model.entity.SysRole;
import com.thinker.cloud.upms.sys.service.ISysRoleService;
import com.thinker.cloud.upms.sys.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * 角色管理 服务实现类
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final ISysUserRoleService sysUserRoleService;

    @Override
    public List<SysRoleVO> page(IPage<SysRoleVO> page, SysRoleQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysRoleVO> list(SysRoleQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysRoleQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysRoleQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysRoleVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysRoleDTO dto) {
        // 转换数据并保存
        SysRole entity = SysRoleConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysRoleDTO dto) {
        // 检查数据是否存在
        SysRole oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysRole entity = SysRoleConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysRole oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }

    @Override
    public List<SysRoleVO> listByUserId(Long userId) {
        // 根据系统用户id获取关联角色id
        SysUserRoleQuery query = new SysUserRoleQuery().setUserId(userId);
        List<SysUserRoleVO> userRoles = sysUserRoleService.list(query);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Lists.newArrayList();
        }

        // 根据关联角色id查询角色信息
        List<Long> roleIds = ListUtil.toList(userRoles, SysUserRoleVO::getRoleId);
        return baseMapper.list(new SysRoleQuery().setIds(roleIds).setEnabled(true));
    }
}
