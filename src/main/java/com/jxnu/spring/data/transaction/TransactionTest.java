package com.jxnu.spring.data.transaction;

/**
 * @author shoumiao_yao
 * @date 2016-08-09
 */
public class TransactionTest {

    @DataTx
    public void save() {
    }

    @OrderTx
    public void update() {
    }

}
