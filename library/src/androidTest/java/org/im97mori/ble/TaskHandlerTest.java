package org.im97mori.ble;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateUtils;

import org.im97mori.ble.task.AbstractBLETask;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TaskHandlerTest {

    @Test
    public void addTaskTest001() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };

        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        TaskHandler taskHandler = new TaskHandler(thread.getLooper());

        taskHandler.addTask(task, new Message());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, countDownLatch.getCount());

        taskHandler.quit();
    }

    @Test
    public void addTaskTest002() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        AbstractBLETask task = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };

        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        TaskHandler taskHandler = new TaskHandler(thread.getLooper());

        taskHandler.addTask(task, new Message());
        taskHandler.addTask(task, new Message());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, countDownLatch.getCount());

        taskHandler.quit();
    }

    @Test
    public void cancelTaskTest001() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task1 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                try {
                    Thread.sleep(DateUtils.SECOND_IN_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        AbstractBLETask task2 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger.addAndGet(1);
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };

        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        TaskHandler taskHandler = new TaskHandler(thread.getLooper());

        taskHandler.addTask(task1, new Message());
        taskHandler.addTask(task2, new Message());
        taskHandler.cancelTask(task2.getTaskId());

        try {
            countDownLatch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, atomicInteger.get());

        taskHandler.quit();
    }

    @Test
    public void cancelTaskTest002() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task1 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                try {
                    Thread.sleep(DateUtils.SECOND_IN_MILLIS * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                  return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        final AtomicInteger atomicInteger1 = new AtomicInteger(0);
        AbstractBLETask task2 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger1.addAndGet(1);
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        final AtomicInteger atomicInteger2 = new AtomicInteger(0);
        AbstractBLETask task3 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger2.addAndGet(1);
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };

        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        TaskHandler taskHandler = new TaskHandler(thread.getLooper());

        taskHandler.addTask(task1, new Message());
        taskHandler.addTask(task2, new Message());
        taskHandler.addTask(task3, new Message());
        taskHandler.cancelTask(task2.getTaskId());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, atomicInteger1.get());
        assertEquals(1, atomicInteger2.get());

        taskHandler.quit();
    }

    @Test
    public void clearTaskTest001() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task1 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                try {
                    Thread.sleep(DateUtils.SECOND_IN_MILLIS * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {
            }
        };
        final AtomicInteger atomicInteger1 = new AtomicInteger(0);
        AbstractBLETask task2 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger1.addAndGet(1);
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {
            }
        };
        final AtomicInteger atomicInteger2 = new AtomicInteger(0);
        AbstractBLETask task3 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger2.addAndGet(1);
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {
            }
        };
        final AtomicInteger atomicInteger3 = new AtomicInteger(0);
        AbstractBLETask task4 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                atomicInteger3.addAndGet(1);
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {
                BLELogUtils.stackLog();
            }
        };

        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        TaskHandler taskHandler = new TaskHandler(thread.getLooper());

        taskHandler.addTask(task1, new Message());
        taskHandler.addTask(task2, new Message());
        taskHandler.addTask(task3, new Message());
        taskHandler.clearTask();
        taskHandler.addTask(task4, new Message());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, atomicInteger1.get());
        assertEquals(0, atomicInteger2.get());
        assertEquals(1, atomicInteger3.get());

        taskHandler.quit();
    }

    @Test
    public void clearBusyTest001() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task1 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                return true;
            }

            @Override
            public boolean isBusy() {
                return true;
            }

            @Override
            public void cancel() {

            }
        };
        AbstractBLETask task2 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        AbstractBLETask task3 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        Looper looper = thread.getLooper();
        TaskHandler taskHandler = new TaskHandler(looper);

        taskHandler.addTask(task1, new Message());
        taskHandler.addTask(task2, new Message());

        assertEquals(1, countDownLatch.getCount());

        try {
            Thread.sleep(DateUtils.SECOND_IN_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        taskHandler.addTask(task3, new Message());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, countDownLatch.getCount());

        taskHandler.quit();
    }

    @Test
    public void clearBusyTest002() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AbstractBLETask task1 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                return true;
            }

            @Override
            public boolean isBusy() {
                return true;
            }

            @Override
            public void cancel() {

            }
        };
        AbstractBLETask task2 = new AbstractBLETask() {
            @Override
            public boolean doProcess(Message message) {
                countDownLatch.countDown();
                return true;
            }

            @Override
            public boolean isBusy() {
                return false;
            }

            @Override
            public void cancel() {

            }
        };
        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        Looper looper = thread.getLooper();
        TaskHandler taskHandler = new TaskHandler(looper);

        taskHandler.addTask(task1, new Message());
        taskHandler.addTask(task2, new Message());

        assertEquals(1, countDownLatch.getCount());

        taskHandler.clearBusy();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, countDownLatch.getCount());

        taskHandler.quit();
    }

    @Test
    public void quitTest001() {
        HandlerThread thread = new HandlerThread(this.getClass().getName());
        thread.start();
        Looper looper = thread.getLooper();
        TaskHandler taskHandler = new TaskHandler(looper);
        taskHandler.quit();

        try {
            Thread.sleep(DateUtils.SECOND_IN_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(looper.getThread().isAlive());
    }
}
