package osp.Threads;

import java.util.Vector;

import javax.xml.ws.Dispatch;

import java.util.Enumeration;
import osp.Utilities.*;
import osp.IFLModules.*;
import osp.Tasks.*;
import osp.EventEngine.*;
import osp.Hardware.*;
import osp.Devices.*;
import osp.Memory.*;
import osp.Resources.*;

/**
 * Name: Bryan Valarezo
 * StudentID: 110362410
 * 
 * I pledge my honor that all parts of this project were done by me individually, without 
 * collaboration with anyone, and without consulting any external sources that provide 
 * full or partial solutions to a similar project. 
 * I understand that breaking this pledge will result in an “F” for the entire course.
 */

/**
 * This class is responsible for actions related to threads, including creating,
 * killing, dispatching, resuming, and suspending threads.
 * 
 * @OSPProject Threads
 */
public class ThreadCB extends IflThreadCB {
    /**
     * The thread constructor. Must call
     * 
     * super();
     * 
     * as its first statement.
     * 
     * @OSPProject Threads
     */

    private static ReadyQueue sharedReadyQueue;
    private int dispatchCount;
    private int queueID;

    public ThreadCB() {
        // your code goes here
        super();
        dispatchCount = 0;
    }

    /**
     * This method will be called once at the beginning of the simulation. The
     * student can set up static variables here.
     * 
     * @OSPProject Threads
     */
    public static void init() {
        // your code goes here
        MyOut.print("osp.Threads.ThreadCB", "Entering Student Method..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        // set up the queue
        sharedReadyQueue = new ReadyQueue();
    }

    /**
     * Sets up a new thread and adds it to the given task. The method must set the
     * ready status and attempt to add thread to task. If the latter fails because
     * there are already too many threads in this task, so does this method,
     * otherwise, the thread is appended to the ready queue and dispatch() is
     * called.
     * 
     * The priority of the thread can be set using the getPriority/setPriority
     * methods. However, OSP itself doesn't care what the actual value of the
     * priority is. These methods are just provided in case priority scheduling is
     * required.
     * 
     * @return thread or null
     * 
     * @OSPProject Threads
     */
    static public ThreadCB do_create(TaskCB task) {
        // your code goes here
        ThreadCB thread;
        if (MaxThreadsPerTask < task.getThreadCount()) {
            thread = null;
            dispatch();
            return thread;
        }
        thread = new ThreadCB(); // CREATE A THREAD
        if (task.addThread(thread) == FAILURE) { // TRY TO ADD TO TASK
            thread = null;
            dispatch();
            return thread;
        }
        thread.setTask(task); // SET THREAD TO TASK
        thread.setStatus(ThreadReady);
        thread.setQueueID(1);
        sharedReadyQueue.pushObjToQueue(1, thread); // ADD TO QUEUE
        dispatch();
        return thread;
    }

    /**
     * Kills the specified thread.
     * 
     * The status must be set to ThreadKill, the thread must be removed from the
     * task's list of threads and its pending IORBs must be purged from all device
     * queues.
     * 
     * If some thread was on the ready queue, it must removed, if the thread was
     * running, the processor becomes idle, and dispatch() must be called to resume
     * a waiting thread.
     * 
     * @OSPProject Threads
     */
    public void do_kill() {
        // your code goes here
        MyOut.print(this, "Entering Student Method..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    /**
     * Suspends the thread that is currenly on the processor on the specified event.
     * 
     * Note that the thread being suspended doesn't need to be running. It can also
     * be waiting for completion of a pagefault and be suspended on the IORB that is
     * bringing the page in.
     * 
     * Thread's status must be changed to ThreadWaiting or higher, the processor set
     * to idle, the thread must be in the right waiting queue, and dispatch() must
     * be called to give CPU control to some other thread.
     * 
     * @param event - event on which to suspend this thread.
     * 
     * @OSPProject Threads
     */
    public void do_suspend(Event event) {
        // your code goes here
        MyOut.print(this, "Entering Student Method..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    /**
     * Resumes the thread.
     * 
     * Only a thread with the status ThreadWaiting or higher can be resumed. The
     * status must be set to ThreadReady or decremented, respectively. A ready
     * thread should be placed on the ready queue.
     * 
     * @OSPProject Threads
     */
    public void do_resume() {
        // your code goes here
        MyOut.print(this, "Entering Student Method..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    /**
     * Selects a thread from the ready queue and dispatches it.
     * 
     * If there is just one theread ready to run, reschedule the thread currently on
     * the processor.
     * 
     * In addition to setting the correct thread status it must update the PTBR.
     * 
     * @return SUCCESS or FAILURE
     * 
     * @OSPProject Threads
     */
    public static int do_dispatch() {
        // your code goes here
        MyOut.print("osp.Threads.ThreadCB", "Entering Student Method..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return FAILURE;
    }

    /**
     * Called by OSP after printing an error message. The student can insert code
     * here to print various tables and data structures in their state just after
     * the error happened. The body can be left empty, if this feature is not used.
     * 
     * @OSPProject Threads
     */
    public static void atError() {
        // your code goes here

    }

    /**
     * Called by OSP after printing a warning message. The student can insert code
     * here to print various tables and data structures in their state just after
     * the warning happened. The body can be left empty, if this feature is not
     * used.
     * 
     * @OSPProject Threads
     */
    public static void atWarning() {
        // your code goes here

    }

    public static ReadyQueue getSharedReadyQueue() {
        return sharedReadyQueue;
    }

    public static void setSharedReadyQueue(ReadyQueue sharedReadyQueue) {
        ThreadCB.sharedReadyQueue = sharedReadyQueue;
    }

    public int getDispatchCount() {
        return dispatchCount;
    }

    public void setDispatchCount(int dispatchCount) {
        this.dispatchCount = dispatchCount;
    }

    public int getQueueID() {
        return queueID;
    }

    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }

    /*
     * Feel free to add methods/fields to improve the readability of your code
     */

}

/*
 * Feel free to add local classes to improve the readability of your code
 */
class ReadyQueue {

    private QueueList queue1;
    private QueueList queue2;
    private QueueList queue3;

    public ReadyQueue() {
        this.queue1 = new QueueList();
        this.queue2 = new QueueList();
        this.queue3 = new QueueList();
    }

    public QueueList getQueue1() {
        return queue1;
    }

    public void setQueue1(QueueList queue1) {
        this.queue1 = queue1;
    }

    public QueueList getQueue2() {
        return queue2;
    }

    public void setQueue2(QueueList queue2) {
        this.queue2 = queue2;
    }

    public QueueList getQueue3() {
        return queue3;
    }

    public void setQueue3(QueueList queue3) {
        this.queue3 = queue3;
    }

    public boolean isQueue1Empty() {
        return queue1.isEmpty();
    }

    public boolean isQueue2Empty() {
        return queue2.isEmpty();
    }

    public boolean isQueue3Empty() {
        return queue3.isEmpty();
    }

    public final synchronized void pushObjToQueue(int queue, Object Obj) {
        if (queue == 1) {
            queue1.push(obj);
        } else if (queue == 2) {
            queue2.push(obj);
        } else if (queue == 3) {
            queue3.push(obj);
        }
    }

    public final synchronized Object popObjectFromQueue(int queue, Object Obj) {
        if (queue == 1) {
            return queue1.pop(obj);
        } else if (queue == 2) {
            return queue2.pop(obj);
        } else if (queue == 3) {
            return queue3.pop(obj);
        }
    }
}

class QueueList extends GenericList {

    public QueueList() {
        super();
    }

    public final synchronized void push(Object obj) {
        super.insert(obj);
    }

    public final synchronized Object pop() {
        return super.removeTail();
    }
}