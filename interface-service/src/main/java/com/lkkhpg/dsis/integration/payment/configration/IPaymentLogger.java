/*
 *
 */
package com.lkkhpg.dsis.integration.payment.configration;

import org.slf4j.Logger;

/**
 * @author shiliyan
 *
 */
public interface IPaymentLogger {

    default void info(String info, Logger logger, Object... para) {
        if (logger.isInfoEnabled()) {
            logger.info(info, para);
        }
    }

    default void info(String info, Logger logger) {
        if (logger.isInfoEnabled()) {
            logger.info(info);
        }
    }

    default void error(String info, Logger logger, Object... para) {
        if (logger.isErrorEnabled()) {
            logger.error(info, para);
        }
    }

    default void error(String info, Logger logger) {
        if (logger.isErrorEnabled()) {
            logger.error(info);
        }
    }
}
