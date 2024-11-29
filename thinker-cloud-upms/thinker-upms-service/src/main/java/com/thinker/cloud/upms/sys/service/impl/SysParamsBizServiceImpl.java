package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.common.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysParamsBizDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysParamsBizQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsBizVO;
import com.thinker.cloud.upms.sys.converter.SysParamsBizConverter;
import com.thinker.cloud.upms.sys.mapper.SysParamsBizMapper;
import com.thinker.cloud.upms.sys.model.entity.SysParamsBiz;
import com.thinker.cloud.upms.sys.service.ISysParamsBizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 业务参数 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysParamsBizServiceImpl extends ServiceImpl<SysParamsBizMapper, SysParamsBiz> implements ISysParamsBizService {

    @Override
    public List<SysParamsBizVO> page(IPage<SysParamsBizVO> page, SysParamsBizQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysParamsBizVO> list(SysParamsBizQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysParamsBizQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysParamsBizQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysParamsBizVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysParamsBizDTO dto) {
        // 转换数据并保存
        SysParamsBiz entity = SysParamsBizConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysParamsBizDTO dto) {
        // 检查数据是否存在
        SysParamsBiz oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysParamsBiz entity = SysParamsBizConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysParamsBiz oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
