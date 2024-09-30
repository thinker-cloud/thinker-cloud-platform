package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysDictBizDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysDictBizQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictBizVO;
import com.thinker.cloud.upms.sys.converter.SysDictBizConverter;
import com.thinker.cloud.upms.sys.mapper.SysDictBizMapper;
import com.thinker.cloud.upms.sys.model.entity.SysDictBiz;
import com.thinker.cloud.upms.sys.service.ISysDictBizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 业务字典 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysDictBizServiceImpl extends ServiceImpl<SysDictBizMapper, SysDictBiz> implements ISysDictBizService {

    @Override
    public List<SysDictBizVO> page(IPage<SysDictBizVO> page, SysDictBizQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysDictBizVO> list(SysDictBizQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysDictBizQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysDictBizQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysDictBizVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysDictBizDTO dto) {
        // 转换数据并保存
        SysDictBiz entity = SysDictBizConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysDictBizDTO dto) {
        // 检查数据是否存在
        SysDictBiz oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysDictBiz entity = SysDictBizConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysDictBiz oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
