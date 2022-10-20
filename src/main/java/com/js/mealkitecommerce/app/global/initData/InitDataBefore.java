package com.js.mealkitecommerce.app.global.initData;

import com.js.mealkitecommerce.app.service.CustomerService;

public interface InitDataBefore {
    default void before(CustomerService customerService) {

    }
}
