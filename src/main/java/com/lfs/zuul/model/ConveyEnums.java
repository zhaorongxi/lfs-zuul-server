package com.lfs.zuul.model;

public enum ConveyEnums {

    NO_CHECK_TOKEN(1),
    CHECK_TOKEN_ANYWAY(2);

    private Integer meaning;

    public Integer getMeaning() {
        return meaning;
    }

    ConveyEnums(Integer meaning) {
        this.meaning = meaning;
    }
}
