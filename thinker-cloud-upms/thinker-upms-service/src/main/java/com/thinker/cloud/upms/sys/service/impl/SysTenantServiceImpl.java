package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysTenantDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysTenantQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysTenantVO;
import com.thinker.cloud.upms.sys.converter.SysTenantConverter;
import com.thinker.cloud.upms.sys.mapper.SysTenantMapper;
import com.thinker.cloud.upms.sys.model.entity.SysTenant;
import com.thinker.cloud.upms.sys.service.ISysTenantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 租户管理 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Override
    public List<SysTenantVO> page(IPage<SysTenantVO> page, SysTenantQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysTenantVO> list(SysTenantQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysTenantQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysTenantQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysTenantVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysTenantDTO dto) {
        // 转换数据并保存
        SysTenant entity = SysTenantConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysTenantDTO dto) {
        // 检查数据是否存在
        SysTenant oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysTenant entity = SysTenantConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysTenant oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
