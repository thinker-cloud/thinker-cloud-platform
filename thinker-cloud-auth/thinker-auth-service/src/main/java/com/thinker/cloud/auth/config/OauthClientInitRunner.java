package com.thinker.cloud.auth.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.thinker.cloud.auth.converter.OauthClientConverter;
import com.thinker.cloud.auth.mapper.OauthClientMapper;
import com.thinker.cloud.auth.model.entity.OauthClient;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.security.repository.RedisOauthClientRepository;
import com.thinker.cloud.security.repository.entity.RedisRegisteredClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * oauth 客户端认证参数初始化
 *
 * @author admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OauthClientInitRunner {

    private final OauthClientMapper oauthClientMapper;
    private final RedisOauthClientRepository redisOauthClientRepository;

    @Async
    @Slave
    @EventListener(WebServerInitializedEvent.class)
    public void initOauthClient() {
        log.debug("初始化客户端缓存信息开始");
        redisOauthClientRepository.deleteAll();
        List<OauthClient> list = oauthClientMapper.selectAllList();
        List<RedisRegisteredClient> clients = OauthClientConverter.INSTANTS.listToRepository(list);
        redisOauthClientRepository.saveAll(clients);
        log.debug("初始化客户端缓存信息结束，size: {}", clients.size());
    }

    @Async
    @Slave
    @EventListener(OauthClientRefreshEvent.class)
    public void refreshOauthClient(OauthClientRefreshEvent event) {
        log.debug("刷新客户端缓存信息开始");
        if (!event.isDeleted) {
            OauthClient entity = oauthClientMapper.selectOne(Wrappers.<OauthClient>lambdaQuery()
                    .eq(OauthClient::getId, event.getSource()));
            RedisRegisteredClient client = OauthClientConverter.INSTANTS.toRepository(entity);
            redisOauthClientRepository.save(client);
            log.debug("刷新客户端缓存信息结束，client: {}", entity.getClientId());
        } else {
            redisOauthClientRepository.deleteById(String.valueOf(event.getSource()));
        }
    }

    /**
     * 客户端刷新事件
     */
    @Getter
    public static class OauthClientRefreshEvent extends ApplicationEvent {

        private final boolean isDeleted;

        public OauthClientRefreshEvent(Object source) {
            super(source);
            this.isDeleted = false;
        }

        public OauthClientRefreshEvent(Object source, boolean isDeleted) {
            super(source);
            this.isDeleted = isDeleted;
        }
    }
}
