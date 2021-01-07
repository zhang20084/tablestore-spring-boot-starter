package site.dunhanson.tablestore.spring.boot.entity;

import lombok.Data;

@Data
public class SubDocument {
    private String subProjectName;
    private String subProjectCode;
    private String biddingBudget;
    private String winTenderer;
    private String winBidPrice;
    private String winTendererManager;
    private String winTendererPhone;
    private String secondTenderer;
    private String secondBidPrice;
    private String secondTendererManager;
    private String secondTendererPhone;
    private String thirdTenderer;
    private String thirdBidPrice;
    private String thirdTendererManager;
    private String thirdTendererPhone;
}
