package com.thinker.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.dto.SysUserDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysUserQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserVO;
import com.thinker.cloud.upms.converter.SysUserConverter;
import com.thinker.cloud.upms.mapper.SysUserMapper;
import com.thinker.cloud.upms.model.entity.SysUser;
import com.thinker.cloud.upms.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户信息 服务实现类
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public List<SysUserVO> page(IPage<SysUserVO> page, SysUserQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<SysUserVO> list(SysUserQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(SysUserQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(SysUserQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public SysUserVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(SysUserDTO dto) {
        // 转换数据并保存
        SysUser entity = SysUserConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(SysUserDTO dto) {
        // 检查数据是否存在
        SysUser oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        SysUser entity = SysUserConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        SysUser oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
