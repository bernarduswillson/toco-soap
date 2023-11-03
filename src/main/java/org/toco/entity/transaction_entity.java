package org.toco.entity;
import lombok.Data;

@Data
public class transaction_entity {
    private Integer user_id;
    private Integer amount;
    private String image;
    private String status;

    public transaction_entity(Integer user_id, Integer amount, String image, String status) {
        this.user_id = user_id;
        this.amount = amount;
        this.image = image;
        this.status = status;
    }
}