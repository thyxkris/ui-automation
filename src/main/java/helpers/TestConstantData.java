package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by macked on 19/04/2017.
 */
public class TestConstantData {
    public final static int pageLoadWaitTime = ConfigHelper.getInt("pageload.wait.time");//20;
    public final static int elementWaitTime = ConfigHelper.getInt("element.wait.time");//15;
    public final static int NORMAL_WAIT_INTERNAL = ConfigHelper.getInt("normal.wait.interval");//50;
}


