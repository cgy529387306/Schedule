package com.android.mb.schedule.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cgy on 18/10/28.
 */
@Entity
public class Office {
    private static final long serialVersionUID = 295261812;

    @Id(autoincrement = true)
    private Long id;
    private String old_id;
    private long parent_id;
    private String old_parent_id;
    private String parent_ids;
    private String name;
    private String sort;
    private long area_id;
    private String old_area_id;
    private String code;
    private String type;
    private String grade;
    private String address;
    private String zip_code;
    private String master;
    private String phone;
    private String thumbnail;
    private String city;
    private String fax;
    private String email;
    private String USEABLE;
    private String PRIMARY_PERSON;
    private String DEPUTY_PERSON;
    private long create_by;
    private String create_date;
    private long update_by;
    private String update_date;
    private String remarks;
    private int st_del;

    @Generated(hash = 295261812)
    public Office(Long id, String old_id, long parent_id, String old_parent_id,
            String parent_ids, String name, String sort, long area_id,
            String old_area_id, String code, String type, String grade,
            String address, String zip_code, String master, String phone,
            String thumbnail, String city, String fax, String email, String USEABLE,
            String PRIMARY_PERSON, String DEPUTY_PERSON, long create_by,
            String create_date, long update_by, String update_date, String remarks,
            int st_del) {
        this.id = id;
        this.old_id = old_id;
        this.parent_id = parent_id;
        this.old_parent_id = old_parent_id;
        this.parent_ids = parent_ids;
        this.name = name;
        this.sort = sort;
        this.area_id = area_id;
        this.old_area_id = old_area_id;
        this.code = code;
        this.type = type;
        this.grade = grade;
        this.address = address;
        this.zip_code = zip_code;
        this.master = master;
        this.phone = phone;
        this.thumbnail = thumbnail;
        this.city = city;
        this.fax = fax;
        this.email = email;
        this.USEABLE = USEABLE;
        this.PRIMARY_PERSON = PRIMARY_PERSON;
        this.DEPUTY_PERSON = DEPUTY_PERSON;
        this.create_by = create_by;
        this.create_date = create_date;
        this.update_by = update_by;
        this.update_date = update_date;
        this.remarks = remarks;
        this.st_del = st_del;
    }

    @Generated(hash = 1674092051)
    public Office() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOld_id() {
        return old_id;
    }

    public void setOld_id(String old_id) {
        this.old_id = old_id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getOld_parent_id() {
        return old_parent_id;
    }

    public void setOld_parent_id(String old_parent_id) {
        this.old_parent_id = old_parent_id;
    }

    public String getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(String parent_ids) {
        this.parent_ids = parent_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }

    public String getOld_area_id() {
        return old_area_id;
    }

    public void setOld_area_id(String old_area_id) {
        this.old_area_id = old_area_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUSEABLE() {
        return USEABLE;
    }

    public void setUSEABLE(String USEABLE) {
        this.USEABLE = USEABLE;
    }

    public String getPRIMARY_PERSON() {
        return PRIMARY_PERSON;
    }

    public void setPRIMARY_PERSON(String PRIMARY_PERSON) {
        this.PRIMARY_PERSON = PRIMARY_PERSON;
    }

    public String getDEPUTY_PERSON() {
        return DEPUTY_PERSON;
    }

    public void setDEPUTY_PERSON(String DEPUTY_PERSON) {
        this.DEPUTY_PERSON = DEPUTY_PERSON;
    }

    public long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public long getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(long update_by) {
        this.update_by = update_by;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSt_del() {
        return st_del;
    }

    public void setSt_del(int st_del) {
        this.st_del = st_del;
    }
}
