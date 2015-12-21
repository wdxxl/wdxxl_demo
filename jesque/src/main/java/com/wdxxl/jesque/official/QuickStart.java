package com.wdxxl.jesque.official;

import java.util.Arrays;
import java.util.Date;

import com.wdxxl.jesque.job.TestAction;
import com.wdxxl.jesque.job.TestJob;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientImpl;
import net.greghaines.jesque.utils.JesqueUtils;
import net.greghaines.jesque.worker.MapBasedJobFactory;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerImpl;
import net.greghaines.jesque.worker.WorkerListener;

public class QuickStart {
    public static void main(String[] args) {
        // Configuration
        final Config config = new ConfigBuilder().build();
        // ClientImpl
        final Client client = new ClientImpl(config);

        // Add a job to the Queue
        final Job job = new Job(TestAction.class.getSimpleName(),
                new Object[] {1, 2.3d, true, "test", Arrays.asList("inner", 4.5)});
        final Job job2 = new Job(TestJob.class.getSimpleName(), new Object[] {"Hello", "World"});

        client.enqueue("foo", job);
        client.enqueue("tar", job2);
        client.end();

        // Start a worker to run jobs from the queue
        final Worker worker =
                new WorkerImpl(config, Arrays.asList("foo","tar"), new MapBasedJobFactory(JesqueUtils.map(
                        JesqueUtils.entry(TestAction.class.getSimpleName(),TestAction.class),
                        JesqueUtils.entry(TestJob.class.getSimpleName(), TestJob.class))));
        // Listeners
        worker.getWorkerEventEmitter().addListener(new WorkerListener() {
            @Override
            public void onEvent(WorkerEvent event, Worker worker, String queue, Job job,
                    Object runner, Object result, Throwable t) {
                if (runner instanceof TestAction) {
                    ((TestAction) runner).setRunTime(new Date());
                }
            }
        }, WorkerEvent.JOB_EXECUTE);

        final Thread workerThread = new Thread(worker);
        workerThread.start();

        try {Thread.sleep(500);} catch (Exception e) {}

        worker.end(true);
        try{workerThread.join();}catch(Exception e){e.printStackTrace();}
    }
}
