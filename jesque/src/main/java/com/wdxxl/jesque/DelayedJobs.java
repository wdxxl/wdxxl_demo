package com.wdxxl.jesque;

import java.util.Arrays;

import com.wdxxl.jesque.job.TestJob;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientPoolImpl;
import net.greghaines.jesque.utils.JesqueUtils;
import net.greghaines.jesque.worker.MapBasedJobFactory;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerImpl;
import redis.clients.jedis.JedisPool;

public class DelayedJobs {
    public static void main(String[] args) {
        // Queue Name
        final String QUEUE = "fooDelayed";
        // Configuration
        final Config config =
                new ConfigBuilder().withHost("localhost").withPort(6379).withDatabase(0).build();
        // Client
        final Client client = new ClientPoolImpl(config, new JedisPool("localhost"));
        long delay = 10;// seconds
        long future = System.currentTimeMillis() + (delay * 1000);// future timestamp
        // Enqueue job
        Job job = new Job(TestJob.class.getSimpleName(), new Object[] {"Hello", "World"});
        client.delayedEnqueue(QUEUE,
                job, // arguments
                future);
        // End
        client.end();
        // Start a worker to run jobs from the queues
        final Worker worker = new WorkerImpl(config, Arrays.asList(QUEUE), new MapBasedJobFactory(
                JesqueUtils.map(JesqueUtils.entry(TestJob.class.getSimpleName(), TestJob.class))));
        final Thread workerThread = new Thread(worker);
        workerThread.start();
        // Wait a few secs then shutdown
        try {Thread.sleep(5000);} catch (Exception e) {}
        client.removeDelayedEnqueue(QUEUE, job);
        try {Thread.sleep(7000);} catch (Exception e) {}
        // Give ourselves time to process
        worker.end(true);
        try {workerThread.join();} catch (Exception e) {e.printStackTrace();}
    }
}
