import java.lang.management.ManagementFactory;
import com.sun.management.*;

public class TimerTest {

    public static void main(String[] args) {

        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        long t = bean.getProcessCpuTime();
        int c = 0;

        while (c < 50) {
            long u = bean.getProcessCpuTime(); //System.nanoTime() ;//bean.getCurrentThreadCpuTime();

            if (t != u) {
                System.out.println(t + " " + (t / 1000000.0));
                t = u;
                c++;
            }

        }
    }
}
