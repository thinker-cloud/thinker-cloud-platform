package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysUserRoleDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysUserRoleQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserRoleVO;
import com.thinker.cloud.upms.converter.SysUserRoleConverter;
import com.thinker.cloud.upms.mapper.SysUserRoleMapper;
import com.thinker.cloud.upms.model.entity.SysUserRole;
import com.thinker.cloud.upms.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户角色 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public List<SysUserRoleVO> page(IPage<SysUserRoleVO> page, SysUserRoleQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysUserRoleVO> list(SysUserRoleQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysUserRoleQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysUserRoleQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysUserRoleVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysUserRoleDTO dto) {
        // 转换数据并保存
        SysUserRole entity = SysUserRoleConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysUserRoleDTO dto) {
        // 检查数据是否存在
        SysUserRole oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysUserRole entity = SysUserRoleConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysUserRole oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
