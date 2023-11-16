package org.toco.entity;

import lombok.Data;

@Data
public class userGems_Entity {
    private Integer user_id;
    private Integer gem;

    public userGems_Entity(Integer user_id, Integer gem) {
        this.user_id = user_id;
        this.gem = gem;
    }
}
