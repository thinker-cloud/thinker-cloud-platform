package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.thinker.cloud.upms.model.vo.SysRoleVO;
import com.thinker.cloud.upms.model.dto.SysRoleDTO;
import com.thinker.cloud.upms.model.entity.SysRole;
import com.thinker.cloud.upms.mapper.SysRoleMapper;
import com.thinker.cloud.upms.service.ISysRoleService;
import com.thinker.cloud.upms.model.query.SysRoleQuery;
import com.thinker.cloud.upms.converter.SysRoleConverter;
import com.thinker.cloud.core.exception.FailException;

import java.util.List;
import java.util.Optional;

/**
 * 角色管理 服务实现类
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

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
}
