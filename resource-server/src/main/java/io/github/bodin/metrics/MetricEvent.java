package io.github.bodin.metrics;

public class MetricEvent {
    private final String clientId;
    private final boolean success;
    private final String statusCode;
    private final String statusMessage;

    public MetricEvent(String clientId, boolean success, String statusCode, String statusMessage) {
        this.clientId = clientId;
        this.success = success;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getClientId() {
        return clientId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "MetricEvent{" +
                "clientId='" + clientId + '\'' +
                ", success=" + success +
                ", statusCode='" + statusCode + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
