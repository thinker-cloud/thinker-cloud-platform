package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysOrganizationDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysOrganizationQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysOrganizationVO;
import com.thinker.cloud.upms.converter.SysOrganizationConverter;
import com.thinker.cloud.upms.mapper.SysOrganizationMapper;
import com.thinker.cloud.upms.model.entity.SysOrganization;
import com.thinker.cloud.upms.service.ISysOrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 组织架构 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements ISysOrganizationService {

    @Override
    public List<SysOrganizationVO> page(IPage<SysOrganizationVO> page, SysOrganizationQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysOrganizationVO> list(SysOrganizationQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysOrganizationQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysOrganizationQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysOrganizationVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysOrganizationDTO dto) {
        // 转换数据并保存
        SysOrganization entity = SysOrganizationConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysOrganizationDTO dto) {
        // 检查数据是否存在
        SysOrganization oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysOrganization entity = SysOrganizationConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysOrganization oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}