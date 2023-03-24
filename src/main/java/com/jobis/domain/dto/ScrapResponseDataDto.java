package com.jobis.domain.dto;

import lombok.Data;

@Data
public class ScrapResponseDataDto {
    private ScrapResponseJsonListDto jsonList;
    private String appVer;
    private String errMsg;
    private String company;
    private String svcCd;
    private String hostNm;
    private String workerResDt;
    private String workerReqDt;
}
