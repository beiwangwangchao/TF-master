/*
 *
 */

package com.lkkhpg.dsis.platform.job.examples;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * <p>
 * This is just a simple job that says "Hello" to the world.
 * </p>
 * 
 * @author shiliyan
 *
 */
public class HelloJob extends AbstractJob {

    private static Logger log = LoggerFactory.getLogger(HelloJob.class);

    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     * <p>
     * Quartz requires a public empty constructor so that the scheduler can
     * instantiate the class whenever it needs.
     * </p>
     */
    public HelloJob() {
    }

    @Override
    public void safeExecute(JobExecutionContext context) {

        //Say Hello to the World and display the date/time
         log.info("Hello World! - " + new Date());
        this.setExecutionSummary("Hello World! Finish at " + new Date());
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}
