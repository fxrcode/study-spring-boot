package io.tony.springstudy.jobqueuefuture.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notebook {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;

    @Column(columnDefinition = "varchar(100)")
    private String name;
    private Date createTime;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
