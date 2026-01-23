package com.capstone.stayahead.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RedemptionId implements Serializable {


    private  Integer usersId;

    private  Integer voucherId;

    public RedemptionId() {
    }

    public RedemptionId(Integer usersId, Integer voucherId) {
        this.usersId = usersId;
        this.voucherId = voucherId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(usersId, voucherId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj )
            return true;
        if(obj == null || getClass()!= obj.getClass())
            return false;
        RedemptionId that = (RedemptionId) obj;
        if(!usersId.equals(that.usersId))
            return false;

        return voucherId.equals(that.voucherId);
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }
}
