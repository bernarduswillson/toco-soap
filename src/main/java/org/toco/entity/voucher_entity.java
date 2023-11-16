package org.toco.entity;

import lombok.Data;

@Data
public class voucher_entity {
    private String code;
    private Integer user_id;
    private Integer amount;
    private String created_at;

    public voucher_entity(String code, Integer user_id, Integer amount, String created_at) {
        this.code = code;
        this.user_id = user_id;
        this.amount = amount;
        this.created_at = created_at;
    }
}
