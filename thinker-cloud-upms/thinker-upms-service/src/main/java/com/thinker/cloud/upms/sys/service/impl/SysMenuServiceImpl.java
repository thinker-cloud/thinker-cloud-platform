package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.common.exception.FailException;
import com.thinker.cloud.common.utils.ListUtil;
import com.thinker.cloud.upms.api.sys.model.dto.SysMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysMenuQuery;
import com.thinker.cloud.upms.api.sys.model.query.SysRoleMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleMenuVO;
import com.thinker.cloud.upms.sys.converter.SysMenuConverter;
import com.thinker.cloud.upms.sys.mapper.SysMenuMapper;
import com.thinker.cloud.upms.sys.model.entity.SysMenu;
import com.thinker.cloud.upms.sys.service.ISysMenuService;
import com.thinker.cloud.upms.sys.service.ISysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 菜单管理 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final ISysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuVO> page(IPage<SysMenuVO> page, SysMenuQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysMenuVO> list(SysMenuQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysMenuQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysMenuQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysMenuVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysMenuDTO dto) {
        // 转换数据并保存
        SysMenu entity = SysMenuConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysMenuDTO dto) {
        // 检查数据是否存在
        SysMenu oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysMenu entity = SysMenuConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysMenu oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }

    @Override
    public List<SysMenuVO> listByRoleIds(List<Long> roleIds) {
        // 查询角色ids关联菜单ids
        List<SysRoleMenuVO> list = sysRoleMenuService.list(new SysRoleMenuQuery().setRoleIds(roleIds));

        // 根据角色关联菜单ids查询
        List<Long> menuIds = ListUtil.toList(list, SysRoleMenuVO::getMenuId);
        return this.list(new SysMenuQuery().setIds(menuIds));
    }
}
