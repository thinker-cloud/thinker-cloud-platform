package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.converter.SysMenuConverter;
import com.thinker.cloud.upms.mapper.SysMenuMapper;
import com.thinker.cloud.upms.model.entity.SysMenu;
import com.thinker.cloud.upms.service.ISysMenuService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

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
}
