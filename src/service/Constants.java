package service;

public class Constants {

    private Constants() {
    }

    // configuration
    public final static String HOST_NAME = "localhost";
    public final static int PORT_NUM = 4999;
    public final static int MASTER_PORT_NUM = 5000;
    public final static long HEARTBEAT_MILLISECOND = 5000;
    public final static String DIR_PATH = "./data/";
    public final static String SEPARATOR = "/";
    public final static String SPLIT = " ";

    // special values
    public final static String UNKNOWN = " ";
    public final static String SUCCESS = "SUCCESS";
    public final static String FAIL = "FAIL";

    // service menu
    public final static String REGISTER = "reg";
    public final static String SEARCH = "find";
    public final static String DELETE = "rm";
    public final static String LOOKUP = "ls";
    public final static String DOWNLOAD = "get";
    public final static String USER_EXIT = "exit";

    // metricNames
    public final static String REQUEST_NUM = "sentRequestNum";
    public final static String REGISTER_LATENCY = "RegisterLatency";
    public final static String DELETE_LATENCY = "DeleteLatency";
    public final static String SEARCH_LATENCY = "SearchLatency";
    public final static String OBTAIN_LATENCY = "ObtainLatency";
    public final static String DATA_TRANSFER = "DataTransferBytes";

}
