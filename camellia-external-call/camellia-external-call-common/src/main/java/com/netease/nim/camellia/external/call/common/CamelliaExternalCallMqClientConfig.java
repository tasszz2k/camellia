package com.netease.nim.camellia.external.call.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by caojiajun on 2023/2/27
 */
public class CamelliaExternalCallMqClientConfig<R> {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private String namespace = "default";
    private ScheduledExecutorService scheduledExecutor;
    private int reportIntervalSeconds = 5;
    private MqSender mqSender;
    private CamelliaExternalCallRequestSerializer<R> serializer;
    private String controllerUrl;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    public void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = scheduledExecutor;
    }

    public int getReportIntervalSeconds() {
        return reportIntervalSeconds;
    }

    public void setReportIntervalSeconds(int reportIntervalSeconds) {
        this.reportIntervalSeconds = reportIntervalSeconds;
    }

    public MqSender getMqSender() {
        return mqSender;
    }

    public void setMqSender(MqSender mqSender) {
        this.mqSender = mqSender;
    }

    public CamelliaExternalCallRequestSerializer<R> getSerializer() {
        return serializer;
    }

    public void setSerializer(CamelliaExternalCallRequestSerializer<R> serializer) {
        this.serializer = serializer;
    }

    public String getControllerUrl() {
        return controllerUrl;
    }

    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
