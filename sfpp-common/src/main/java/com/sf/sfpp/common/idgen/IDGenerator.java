package com.sf.sfpp.common.idgen;

import com.sf.sfpp.common.Constants;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/10
 */
public class IDGenerator {
    private final static Map<String,Integer> system = new HashMap<String, Integer>();
    private final static Map<String,Integer> systemShard = new HashMap<String, Integer>();
    private final static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public final static int SYSTEM_SHARD_LENGTH = 3;
    public final static int SYSTEM_LENGTH = 2;

    private final static String SYSTEM_SHARD_FORMAT = "%0"+SYSTEM_SHARD_LENGTH+"d";
    private final static String SYSTEM_FORMAT = "%0"+SYSTEM_LENGTH+"d";

    static{
        system.put(Constants.PUBLIC_COMPONENT_SYSTEM,1);
        systemShard.put(Constants.PUBLIC_COMPONENT_SYSTEM,1);
    }

    public static String getID(String sys){
        UUID uuid = UUID.randomUUID();
        return new StringBuilder().append(String.format(SYSTEM_SHARD_FORMAT,threadLocalRandom.nextInt(systemShard.get(sys)))).append(String.format(SYSTEM_FORMAT,system.get(sys))).append(uuid).toString();
    }

    public static void main(String[] args) {
        System.out.println(IDGenerator.getID("pcomp"));
        System.out.println(IDGenerator.getID("pcomp"));
        System.out.println(IDGenerator.getID("pcomp"));
        System.out.println(IDGenerator.getID("pcomp"));
    }
}
