package com.thinker.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.cloud.auth.api.model.dto.OauthClientDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.converter.OauthClientConverter;
import com.thinker.cloud.auth.mapper.OauthClientMapper;
import com.thinker.cloud.auth.model.entity.OauthClient;
import com.thinker.cloud.auth.service.IOauthClientService;
import com.thinker.cloud.core.exception.FailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * oauth2客户端配置 服务实现类
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Service
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements IOauthClientService {

    @Override
    public List<OauthClientVO> page(IPage<OauthClientVO> page, OauthClientQuery query) {
        return baseMapper.page(page, query);
    }

    @Override
    public List<OauthClientVO> list(OauthClientQuery query) {
        return baseMapper.list(query);
    }

    @Override
    public List<Long> idsByQuery(OauthClientQuery query) {
        return baseMapper.idsByQuery(query);
    }

    @Override
    public Integer countByQuery(OauthClientQuery query) {
        return baseMapper.countByQuery(query);
    }

    @Override
    public OauthClientVO findDetail(Long id) {
        return baseMapper.findDetail(id);
    }

    @Override
    public OauthClientVO loadClientByClientId(String clientId) {
        return baseMapper.selectByClientId(clientId);
    }

    @Override
    public Boolean saveData(OauthClientDTO dto) {
        // 转换数据并保存
        OauthClient entity = OauthClientConverter.INSTANTS.toEntity(dto);
        return super.save(entity);
    }

    @Override
    public Boolean modifyById(OauthClientDTO dto) {
        // 检查数据是否存在
        OauthClient oldEntity = baseMapper.selectById(dto.getId());
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 转换数据并更新
        OauthClient entity = OauthClientConverter.INSTANTS.toEntity(dto);
        return super.updateById(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        // 检查数据是否存在
        OauthClient oldEntity = baseMapper.selectById(id);
        Optional.ofNullable(oldEntity).orElseThrow(() -> new FailException("操作失败，数据不存在"));

        // 删除数据
        return super.removeById(id);
    }
}
