package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.common.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysParamsDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysParamsQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsVO;
import com.thinker.cloud.upms.sys.converter.SysParamsConverter;
import com.thinker.cloud.upms.sys.mapper.SysParamsMapper;
import com.thinker.cloud.upms.sys.model.entity.SysParams;
import com.thinker.cloud.upms.sys.service.ISysParamsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 公共参数 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysParamsServiceImpl extends ServiceImpl<SysParamsMapper, SysParams> implements ISysParamsService {

    @Override
    public List<SysParamsVO> page(IPage<SysParamsVO> page, SysParamsQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysParamsVO> list(SysParamsQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysParamsQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysParamsQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysParamsVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysParamsDTO dto) {
        // 转换数据并保存
        SysParams entity = SysParamsConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysParamsDTO dto) {
        // 检查数据是否存在
        SysParams oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysParams entity = SysParamsConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysParams oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
