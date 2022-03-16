package microserviceAttraction.utilities;

import java.util.concurrent.TimeUnit;

public class SleepUtilities {

    public void sleepLighter() {
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException var2) {
        }
    }
}
