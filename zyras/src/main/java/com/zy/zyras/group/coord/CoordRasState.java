package com.zy.zyras.group.coord;

/**
 *
 * @author wuhailong
 */
public class CoordRasState {
    
    private static boolean master;

    public static boolean isMaster() {
        return master;
    }

    public static void setMaster(boolean master) {
        CoordRasState.master = master;
    }
    
    
}
