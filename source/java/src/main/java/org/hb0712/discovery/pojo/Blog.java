package org.hb0712.discovery.pojo;

import java.util.Date;

/*
 * 微博、朋友圈等
 */
public class Blog {
    private Integer id;
    private String note;
    private Date time;
    private String jsonobject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getJsonobject() {
        return jsonobject;
    }

    public void setJsonobject(String jsonobject) {
        this.jsonobject = jsonobject;
    }
}
