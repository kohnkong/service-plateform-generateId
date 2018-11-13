package com.service.platform.generateId.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "entity_id_flow")
public class EntityIdFlow {
    @Id
    private String head;

    @Column(name = "length")
    private Integer length;

    @Column(name = "batch_id")
    private Integer batchId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    public EntityIdFlow() {

    }

    public EntityIdFlow(String head, Integer length, Integer batchId, Date createDate, Date updateDate) {
        this.head = head;
        this.length = length;
        this.batchId = batchId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    /**
     * @return head
     */
    public String getHead() {
        return head;
    }

    /**
     * @param head
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * @return length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @return batch_id
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * @param batchId
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}