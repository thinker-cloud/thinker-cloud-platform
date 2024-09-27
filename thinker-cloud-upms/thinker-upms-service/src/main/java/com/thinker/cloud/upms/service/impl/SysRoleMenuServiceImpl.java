package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysRoleMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysRoleMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleMenuVO;
import com.thinker.cloud.upms.converter.SysRoleMenuConverter;
import com.thinker.cloud.upms.mapper.SysRoleMenuMapper;
import com.thinker.cloud.upms.model.entity.SysRoleMenu;
import com.thinker.cloud.upms.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 角色菜单 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public List<SysRoleMenuVO> page(IPage<SysRoleMenuVO> page, SysRoleMenuQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysRoleMenuVO> list(SysRoleMenuQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysRoleMenuQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysRoleMenuQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysRoleMenuVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysRoleMenuDTO dto) {
        // 转换数据并保存
        SysRoleMenu entity = SysRoleMenuConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysRoleMenuDTO dto) {
        // 检查数据是否存在
        SysRoleMenu oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysRoleMenu entity = SysRoleMenuConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysRoleMenu oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
