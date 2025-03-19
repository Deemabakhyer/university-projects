/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sallatk;

/**
 *
 * @author deema
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE")
public class Issue implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueID;
    @Column(name = "subject")
    private String subject;
    @Column(name = "attachment")
    private String attachment;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @Column(name = "creationDate")
    private String creationDate;
    @Column(name = "updateDate")
    private String updateDate;

    public Issue() {
    }

    public Issue(String subject, String attachment, String description, String status, String creationDate, String updateDate) {
        this.subject = subject;
        this.attachment = attachment;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;

    }

    public int getIssueID() {
        return this.issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setupdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
