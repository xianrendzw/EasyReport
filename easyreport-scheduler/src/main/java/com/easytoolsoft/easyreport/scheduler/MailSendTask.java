package com.easytoolsoft.easyreport.scheduler;

import com.easytoolsoft.easyreport.po.ReportingTaskPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class MailSendTask extends TimerTask {
    private final static Logger logger = LoggerFactory.getLogger(MailSendTask.class);

    @Override
    public void run() {
        List<ReportingTaskPo> tasks = TaskUtils.getTasks();
        for (ReportingTaskPo task : tasks) {
            try {
                if (isRunnable(task)) {
                    TaskUtils.execute(task);
                    logger.info("执行完成!");
                }
            } catch (Exception ex) {
                logger.error(task.getComment(), ex);
            }
        }
    }

    public boolean isRunnable(ReportingTaskPo rt) throws ParseException {
        int interval = rt.getInterval();
        Calendar c = Calendar.getInstance();
        long curmill = c.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date time = sdf.parse(rt.getTime());
        Calendar ctime = Calendar.getInstance();
        ctime.setTime(time);

        int hour = ctime.get(Calendar.HOUR_OF_DAY);
        int minutes = ctime.get(Calendar.MINUTE);
        int seconds = ctime.get(Calendar.SECOND);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String curtime = dateFormat.format(c.getTime());
        String timeStr = dateFormat.format(time);

        if (rt.getPeroid().equals("y")) {
            int curyear = c.get(Calendar.YEAR);
            c.set(curyear, 1 - 1, 1, hour, minutes, seconds);
            c.add(Calendar.DATE, interval);
            long mill = c.getTimeInMillis();
            return mill == curmill;
        }

        if (rt.getPeroid().equals("M")) {
            int curmounth = c.get(Calendar.MONTH);
            c.set(curmounth, interval - 1, 1, hour, minutes, seconds);
            long mill = c.getTimeInMillis();
            return mill == curmill;
        }

        if (rt.getPeroid().equals("w")) {
            int curweek = c.get(Calendar.DAY_OF_WEEK);
            return curweek - 1 == interval && c.get(Calendar.HOUR_OF_DAY) == hour && c.get(Calendar.MINUTE) == minutes
                    && c.get(Calendar.SECOND) == seconds;
        }

        if (rt.getPeroid().equals("d")) {
            return c.get(Calendar.DATE) % interval == 0 && timeStr.equals(curtime);
        }

        if (rt.getPeroid().equals("h")) {
            return c.get(Calendar.HOUR) % interval == 0 && c.get(Calendar.MINUTE) == minutes
                    && c.get(Calendar.SECOND) == seconds;
        }

        if (rt.getPeroid().equals("m")) {
            return c.get(Calendar.MINUTE) % interval == 0 && c.get(Calendar.SECOND) == 0;
        }

        if (rt.getPeroid().equals("s")) {
            return c.get(Calendar.SECOND) % interval == 0;
        }
        return false;

    }

}
