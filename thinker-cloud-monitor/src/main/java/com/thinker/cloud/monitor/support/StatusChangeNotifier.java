package com.thinker.cloud.monitor.support;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static de.codecentric.boot.admin.server.domain.values.StatusInfo.*;

/**
 * 服务状态变化通知
 *
 * @author admin
 */
@Slf4j
@Component
public class StatusChangeNotifier extends AbstractEventNotifier {

    protected StatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @NonNull
    @Override
    protected Mono<Void> doNotify(@NonNull InstanceEvent event, @NonNull Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status) {
                    // 健康检查没通过
                    case STATUS_DOWN:
                        log.info("应用服务:{},状态:{},ServiceUrl:{},健康检查没通过通知！", instance.getRegistration().getName()
                                , status, instance.getRegistration().getServiceUrl());
                        break;
                    // 服务离线
                    case STATUS_OFFLINE:
                        log.info("应用服务:{},状态:{},ServiceUrl:{},服务离线！", instance.getRegistration().getName()
                                , status, instance.getRegistration().getServiceUrl());
                        break;
                    //服务上线
                    case STATUS_UP:
                        log.info("应用服务:{},状态:{},ServiceUrl:{},服务上线！", instance.getRegistration().getName()
                                , status, instance.getRegistration().getServiceUrl());
                        break;
                    // 服务未知异常
                    case STATUS_UNKNOWN:
                        log.info("应用服务:{},状态:{},ServiceUrl:{},服务未知异常！", instance.getRegistration().getName()
                                , status, instance.getRegistration().getServiceUrl());
                        break;
                    default:
                        break;
                }
            } else {
                log.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(), event.getType());
            }
        });
    }
}
