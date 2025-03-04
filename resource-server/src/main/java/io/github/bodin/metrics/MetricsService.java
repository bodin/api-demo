package io.github.bodin.metrics;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    public void blockedSubscription(String clientId, String message){
        this.emit(new MetricEvent(clientId, false, "block-subscription", message));
    }
    public void blockedScope(String clientId, String message){
        this.emit(new MetricEvent(clientId, false, "block-scope", message));
    }
    public void blockedLimit(String clientId, String message){
        this.emit(new MetricEvent(clientId, false, "block-limit", message));
    }
    public void success(String clientId, String message){
        this.emit(new MetricEvent(clientId, true, "success", message));
    }
    public void failure(String clientId, String message){
        this.emit(new MetricEvent(clientId, false, "failure", message));
    }
    private void emit(MetricEvent e) {
        if(e == null) return;

        LoggerFactory.getLogger(this.getClass()).info(e.toString());
    }
}
