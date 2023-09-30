package com.js.mealkitecommerce.app.global.initData;

import com.js.mealkitecommerce.app.model.VO.Customer.JoinForm;
import com.js.mealkitecommerce.app.service.CustomerService;

public interface InitDataBefore {
    default void before(CustomerService customerService) {
        JoinForm joinForm1 = new JoinForm("이름1", "user1", "1234", "1234", "user1@test.com", "청주시", "01000000000");
        JoinForm joinForm2 = new JoinForm("이름2", "user2", "1234", "1234", "user2@test.com", "청원구", "01011111111");
        customerService.join(joinForm1);
        customerService.join(joinForm2);
    }
}
