package com.thinker.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.auth.api.model.dto.OauthClientSocialDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientSocialQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientSocialVO;
import com.thinker.cloud.auth.converter.OauthClientSocialConverter;
import com.thinker.cloud.auth.mapper.OauthClientSocialMapper;
import com.thinker.cloud.auth.model.entity.OauthClientSocial;
import com.thinker.cloud.auth.service.IOauthClientSocialService;
import com.thinker.cloud.common.exception.FailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * oauth2客户端社交账号配置 服务实现类
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Service
public class OauthClientSocialServiceImpl extends ServiceImpl<OauthClientSocialMapper, OauthClientSocial> implements IOauthClientSocialService {

    @Override
    public List<OauthClientSocialVO> page(IPage<OauthClientSocialVO> page, OauthClientSocialQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<OauthClientSocialVO> list(OauthClientSocialQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(OauthClientSocialQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(OauthClientSocialQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public OauthClientSocialVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public Boolean saveData(OauthClientSocialDTO dto) {
        // 转换数据并保存
        OauthClientSocial entity = OauthClientSocialConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(OauthClientSocialDTO dto) {
        // 检查数据是否存在
        OauthClientSocial oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        OauthClientSocial entity = OauthClientSocialConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        OauthClientSocial oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
