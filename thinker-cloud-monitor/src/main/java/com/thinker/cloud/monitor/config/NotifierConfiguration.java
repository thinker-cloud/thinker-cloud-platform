package com.thinker.cloud.monitor.config;

import com.thinker.cloud.monitor.support.StatusChangeNotifier;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * 监控消息通知配置
 *
 * @author admin
 */
@Configuration
@AllArgsConstructor
public class NotifierConfiguration {

    private final StatusChangeNotifier notifier;
    private final InstanceRepository repository;

    @Primary
    @Bean(initMethod = "start", destroyMethod = "stop")
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(notifier, repository);
        remindingNotifier.setReminderPeriod(Duration.ofHours(3));
        remindingNotifier.setCheckReminderInverval(Duration.ofMinutes(3));
        return remindingNotifier;
    }
}
