package site.dunhanson.tablestore.spring.boot.entity;

import lombok.Data;

import java.util.List;

@Data
public class Document {
    private Long partitionkey;
    private Long docid;
    private Long status = 1L;
    private String pageTime;
    private Long docchannel;
    private String doctitle;
    private String dochtmlcon;
    private String area;
    private String province;
    private String city;
    private String district;
    private Long docstatus;
    private String doccontent;
    private String dockeywords;
    private String cruser;
    private String crtime;
    private Long hitscount;
    private String opertime;
    private String operuser;
    private String publishtime;
    private Long downscount;
    private Integer auditstatus;
    private String auditdesc;
    private String audittime;
    private String uuid;
    private String webSourceNo;
    private String webSourceName;
    private String infoSource;
    private String infoType;
    private String industry;
    private String pageAttachments;
    private String qstatus;
    private String qcodes;
    private String qtype;
    private String qrange;
    private String doctextcon;
    private String projectName;
    private String projectCode;
    private String projectAddr;
    private String tenderee;
    private String tendereeAddr;
    private String tendereePhone;
    private String tendereeContact;
    private String agency;
    private String agencyPhone;
    private String agencyContact;
    private String abandonedTender;
    private List<SubDocument> subDocsJson;
}
