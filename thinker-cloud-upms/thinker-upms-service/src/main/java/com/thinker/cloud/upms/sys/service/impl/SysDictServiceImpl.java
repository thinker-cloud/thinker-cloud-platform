package com.thinker.cloud.upms.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.common.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysDictDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysDictQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictVO;
import com.thinker.cloud.upms.sys.converter.SysDictConverter;
import com.thinker.cloud.upms.sys.mapper.SysDictMapper;
import com.thinker.cloud.upms.sys.model.entity.SysDict;
import com.thinker.cloud.upms.sys.service.ISysDictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 公共字典 服务实现类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public List<SysDictVO> page(IPage<SysDictVO> page, SysDictQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysDictVO> list(SysDictQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysDictQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysDictQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysDictVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysDictDTO dto) {
        // 转换数据并保存
        SysDict entity = SysDictConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysDictDTO dto) {
        // 检查数据是否存在
        SysDict oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysDict entity = SysDictConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysDict oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
