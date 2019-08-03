package com.zy.admin.apimonitor.quartz;


import com.zy.admin.apimonitor.common.MonitorFrequency;
import com.zy.admin.apimonitor.model.GroupPlan;
import com.zy.admin.apimonitor.job.HttpMonitorJob;
import org.apache.commons.lang3.StringUtils;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DynamicJobManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicJobManager.class);

    private static final String MONITORING_INSTANCE_JOB_NAME_PREFIX = "monitoring-groupPlan-";


    public static String generateMonitoringInstanceJobName(String key) {
        return MONITORING_INSTANCE_JOB_NAME_PREFIX + key;
    }


    private GroupPlan groupPlan;

    public DynamicJobManager(GroupPlan groupPlan) {
        this.groupPlan = groupPlan;
    }

    public boolean enable() {
        //final ApplicationInstance groupPlan = groupPlanRepository.findByGuid(guid, ApplicationInstance.class);
        if (groupPlan.getEnabled()) {
            LOGGER.debug("<{}> Instance[guid={}] already enabled, ignore it", username(), groupPlan.getId());
            return false;
        }

        final boolean addSuccessful = startupMonitoringJob(groupPlan);
        if (!addSuccessful) {
            LOGGER.debug("<{}> NOTE: Add MonitoringJob[jobName={}] failed", username(), groupPlan.getJobName());
            return false;
        }

        //update
        groupPlan.setEnabled(true);
        LOGGER.debug("<{}> Update ApplicationInstance[guid={}] enabled=true,jobName={}", username(), groupPlan.getId(), groupPlan.getJobName());

        return true;
    }

    private boolean startupMonitoringJob(GroupPlan groupPlan) {
        final String jobName = getAndSetJobName(groupPlan);
        String cron = MonitorFrequency.valueOf(groupPlan.getFrequency()).getCronExpression();
        DynamicJob job = new DynamicJob(jobName)
                .cronExpression(cron)
                .target(HttpMonitorJob.class)
                .addJobData(HttpMonitorJob.APPLICATION_INSTANCE_ID, groupPlan.getId());
        job.triggerKey();
        return executeStartup(groupPlan, job);
    }

    private boolean executeStartup(GroupPlan groupPlan, DynamicJob job) {
        boolean result = false;
        try {
            if (DynamicSchedulerFactory.existJob(job)) {
                result = DynamicSchedulerFactory.resumeJob(job);
                LOGGER.debug("<{}> Resume  [{}] by ApplicationInstance[guid={},groupPlanName={}] result: {}", username(), job, groupPlan.getId(), groupPlan.getName(), result);
            } else {
                result = DynamicSchedulerFactory.registerJob(job);
                LOGGER.debug("<{}> Register  [{}] by ApplicationInstance[guid={},groupPlanName={}] result: {}", username(), job, groupPlan.getId(), groupPlan.getName(), result);
            }
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Register [" + job + "] failed", username(), e);
        }
        return result;
    }

    private String getAndSetJobName(GroupPlan groupPlan) {
        String jobName = groupPlan.getJobName();
        if (StringUtils.isEmpty(jobName)) {
            jobName = generateMonitoringInstanceJobName(groupPlan.getId().toString());
            groupPlan.setJobName(jobName);
        }
        return jobName;
    }

    private String username() {
    	return null;
        //return SecurityUtils.currentUsername();
    }


//    public boolean delete() {
//        if (groupPlan.isEnabled()) {
//            LOGGER.debug("<{}> Forbid delete enabled ApplicationInstance[guid={}]", username(), groupPlan.getGuid());
//            return false;
//        }
//
//        httpRequestService.deleteHttpLog(groupPlan.getGuid());
//
//        checkAndRemoveJob(groupPlan);
//
//        //logic delete
//        groupPlan.setArchived(true);
//        httpRequestService.archivedHttpData(groupPlan.getGuid());
//        LOGGER.debug("<{}> Archive ApplicationInstance[guid={}] and FrequencyMonitorLogs,MonitoringReminderLogs", username(), groupPlan.getGuid());
//        return true;
//    }
//
//    private void checkAndRemoveJob(GroupPlan groupPlan) {
//        DynamicJob job = new DynamicJob(getAndSetJobName(groupPlan));
//        try {
//            if (DynamicSchedulerFactory.existJob(job)) {
//                final boolean result = DynamicSchedulerFactory.removeJob(job);
//                LOGGER.debug("<{}> Remove DynamicJob[{}] result [{}]", username(), job, result);
//            }
//        } catch (SchedulerException e) {
//            LOGGER.error("<{}> Remove [" + job + "] failed", username(), e);
//        }
//    }
//
//
//    /* * 1. Remove the job
//     * 2. update groupPlan to enabled=false
//     **/
//
//    public boolean kill() {
//        if (!groupPlan.isEnabled()) {
//            LOGGER.debug("<{}> Expect ApplicationInstance[guid={}] enabled=true,but it is false, illegal status",
//            		username(), groupPlan.getGroupId());
//            return false;
//        }
//
//        if (!pauseJob(groupPlan)) {
//            LOGGER.debug("<{}> Pause Job[name={}] failed", username(), groupPlan.getJobName());
//            return false;
//        }
//
//        //update
//        groupPlan.setEnabled(false);
////        httpRequestService.updateEnabled(groupPlan);
//        LOGGER.debug("<{}> Update ApplicationInstance[guid={}] enabled=false", username(), groupPlan.getGuid());
//        return true;
//    }
//
//    private boolean pauseJob(GroupPlan groupPlan) {
//        DynamicJob job = new DynamicJob(getAndSetJobName(groupPlan));
//        try {
//            return DynamicSchedulerFactory.pauseJob(job);
//        } catch (SchedulerException e) {
//            LOGGER.error("<{}> Pause [" + job + "] failed", username(), e);
//            return false;
//        }
//    }
}