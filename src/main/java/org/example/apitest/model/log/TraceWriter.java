/*
 * Copyright (c) 2023 . All rights reserved.
 * This software is the confidential and proprietary information of Minigate Co., Ltd.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement.
 */
package org.example.apitest.model.log;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.apitest.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.apitest.utils.StringUtil.CRLF;
import static org.example.apitest.utils.StringUtil.NULL;



/**
 * 모듈 로그기록 처리 공통 클래스. of <code>TraceWriter</code>
 *
 * @version : 2.0.0
 * @since : 2022
 * @author : Brian,ohk (brian.ohk@minigate.net)
 */
@Slf4j
@Getter
@Setter
public class TraceWriter {

    // ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // ┃            logging
    // ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * (기본값) logger format (HEAD)
     */
    public static final String HEAD
            = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    /**
     * (기본값) logger format (BODY)
     */
    public static final String BODY
            = "┃";
    /**
     * (기본값) logger format (FOOT)
     */
    public static final String FOOT
            = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    /**
     * (기본값) logger format (HEAD_BLOCK)
     */
    public static final String HEAD_BLOCK
            = "┡━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    public static final String PARAMS_HEAD = BODY + "    ";
    public static final String NEW_LINE = "\n";

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.sss";

    /* 로그 Head */
    String logHead;
    /* apiUrl */
    String apiUrl;
    /* POST, GET, .... */
    String method;
    /* 로그 입력을 포맷에 맞게 구성한 StringBuilder */
    StringBuilder logBuilder = null;
    /* 로그 입력 본문 메시지 (기본 포맷을 제외한)  */
    String logMessage;
    /* 로직 프로세스 시작 시간 */
    long startTime;
    /* 로직 프로세스 종료 시간  */
    long endTime;
    String requestTime;

    public TraceWriter() {
        this.startTime = System.currentTimeMillis();
        this.requestTime = getRequestTime();
    }

    public TraceWriter(String logHead, String method, String apiUrl) {
        this.startTime = System.currentTimeMillis();
        this.requestTime = getRequestTime();
        this.apiUrl = (StringUtil.isNotEmpty(apiUrl)) ? apiUrl : "";
        this.method = (StringUtil.isNotEmpty(method)) ? method : "";
        this.logHead = (StringUtil.isNotEmpty(logHead)) ? logHead : NULL;
    }

    public TraceWriter(long startMillis, String logHead, String method, String apiUrl) {
        this.startTime = startMillis;
        this.requestTime = getRequestTime();
        this.apiUrl = (StringUtil.isNotEmpty(apiUrl)) ? apiUrl : "";
        this.method = (StringUtil.isNotEmpty(method)) ? method : "";
        this.logHead = (StringUtil.isNotEmpty(logHead)) ? logHead : NULL;
    }

    /* LogObject 로 부터 수신받은 데이터를 처리 */
    public String getTrace() {
        String trace;
        logBuilder = new StringBuilder();

        try {
            logBuilder.append(CRLF);
            logBuilder.append(HEAD + CRLF);
            logBuilder.append(BODY)
                    .append(" (>) [")
                    .append(getMethod())
                    .append("] '")
                    .append(getApiUrl())
                    .append("' [ ")
                    .append(requestTime)
                    .append(" ]" + CRLF + HEAD_BLOCK + CRLF);

            logBuilder.append(StringUtil.isNotEmpty(getLogMessage()) ? getLogMessage() : "")
                    .append(BODY)
                    .append(CRLF);

            logBuilder.append(BODY)
                    .append(" (>) ")
                    .append(StringUtil.getProcessTime(this.startTime, System.currentTimeMillis()))
                    .append(CRLF);
            logBuilder.append(FOOT + CRLF);
        }
        catch (Exception e) {
            clearBuilder();
            throw e;
        }
        finally {
            trace = (logBuilder != null) ? logBuilder.toString() : "";
            clearBuilder();
        }
        return trace;
    }

    String getRequestTime() {
        SimpleDateFormat dayTime = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dayTime.format(new Date(getStartTime()));
    }

    void clearBuilder() {
        if (this.logBuilder != null) {
            this.logBuilder.setLength(0);
            this.logBuilder = null;
        }
    }

    public void add(String msg) {
        if (this.logMessage != null) {
            this.logMessage += BODY + "  ".concat(msg.concat(CRLF));
        }
        else {
            this.logMessage = BODY + "  ".concat(msg.concat(CRLF));
        }
    }

    //┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //┃  0    : INFO
    //┃  1    : DEBUG
    //┃  2    : WARN
    //┃  3    : ERROR
    //┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void log(int level) {
        if (getTrace() != null) {
            switch (level) {
                case 0:
                    log.info(getTrace());
                    break;
                case 1:
                    log.debug(getTrace());
                    break;
                case 2:
                    log.warn(getTrace());
                    break;
                case 3:
                    log.error(getTrace());
                    break;
            }
        }
    }

    public void log(long endMillis, int level) {
        this.endTime = endMillis;
        log(level);
    }

    public void log() {
        log(0);
    }
}