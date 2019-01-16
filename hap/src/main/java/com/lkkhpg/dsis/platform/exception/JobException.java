/*
 *
 */
package com.lkkhpg.dsis.platform.exception;

/**
 * @author shiliyan
 *
 */
public class JobException extends BaseException {
    
    private static final String MSG_ERROR_JOB_EXCEPTION = "msg.error.job.exception.";

    public static final String JOB_EXCEPTION = "job_exception";

    public static final String NOT_SUB_CLASS = "not.sub.class";

    public JobException(String code, String descriptionKey, Object[] parameters) {
        super(code, MSG_ERROR_JOB_EXCEPTION + descriptionKey, parameters);
    }

    public JobException(String code, String descriptionKey) {
        this(code, descriptionKey, null);
    }

    private static final long serialVersionUID = 2809497996266500743L;

}
