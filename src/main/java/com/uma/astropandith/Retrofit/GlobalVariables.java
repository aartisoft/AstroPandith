package com.uma.astropandith.Retrofit;

public class GlobalVariables {

    public static final String BASE_URL = "https://meraastro.com/API_new/Android/";
   // public static final String BASE_URL = "https://meraastro.in/API_new/Android/";
   // public static final String BASE_URL = "https://meraastro.com/API_new_test/Android/";
    //public static final String BASE_URL = "https://www.apptest.in/meraastro/API_new/Android/";

    public static final String REGISTRATION = "pandit/RegistrationModel.php";
    public static final String LOGIN = "pandit/login.php";
    public static final String STATUS = "pandit/updateRStatusSetDT.php";
    public static final String CALLSTATUS = "pandit/updateCallStatusSetDT.php";
    public static final String CHATSTATUS = "pandit/updateChatStatusSetDT.php";
    public static final String REPORT_PENDING_LIST = "pandit/reportPendingList.php";
    public static final String STATUS_WAITLIST_ONLINE = "users/updateStatusWaitListToOnline.php";

    public static final String CHAT_INFO_DATA = "users/chatDataIncert.php";

    public static final String REPORT_COMPLETED_LIST = "pandit/reportCompletedList.php";
    public static final String UPLOAD_CERTIFICATE = "pandit/uploadCertificate1.php";
    public static final String UPLOAD_CERTIFICATE2 = "pandit/uploadCertificate2.php";
    public static final String UPLOAD_CERTIFICATE3 = "pandit/uploadCertificate3.php";
    public static final String PANDITH_REPLY = "pandit/panditReplayPendingReports.php";
    public static final String TUTORIAL_LIST = "pandit/panditAppTutorialList.php";
    public static final String TUTORIAL_STEPS = "pandit/panditAppTutorialTopic.php";
    public static final String CHAT_REQUEST = "pandit/chatRequestList.php";
    public static final String CHAT_REQUEST1 = "pandit/chatRequestList.php";
    public static final String CHAT_SATUS_UPDATE = "pandit/updateChatStatus.php";
    public static final String NO_RESPONCE = "pandit/updateChatStatus.php";
    public static final String CHATSATUS_NORESPONCE_FROM_USER = "pandit/updateChatStatus.php";
    public static final String CHAT_SATUS_CANCEL = "pandit/updateChatStatus.php";
    public static final String USER_CHAT_STATE = "pandit/chatRequestResponseList.php";

    public static final String CHAT_HISTORY = "pandit/chatHistory.php";
    public static final String CALL_HISTORY = "pandit/callHistory.php";

    public static final String WALLET_HISTORY = "pandit/panditWalletHistory.php";
    public static final String PANDITH_REPLAY = "pandit/panditReplayPendingReports.php";
    public static final String UPDATE_REPORT = "pandit/updateReport.php";
    public static final String CHAT_ATACHMENT = "pandit/uploadChatImg.php";
    public static final String CHAT_SATE_VALUES = "pandit/userEndChatStatusDetails.php";
    public static final String CHAT_SATE_VALUES_BAL = "pandit/userEndChatStatusDetails.php";
    public static final String CALL_CLARIFICATION = "pandit/panditReplay2UserClarificationReq.php";


    public static String MESSAGE = " ";

    public static String PID ;


    public enum SERVICE_MODE {

        REGISTRATION,
        LOGIN,
        STATUS,
        PSTATUS,
        CALLSTATUS,
        CHATSTATUS,
        REPORT_PENDING_LIST,
        REPORT_COMPLETED_LIST,
        UPLOAD_CERTIFICATE,
        UPLOAD_CERTIFICATE2,
        UPLOAD_CERTIFICATE3,
        PANDITH_REPLY,
        TUTORIAL_LIST,
        TUTORIAL_STEPS,
        CHAT_HISTORY,
        CALL_HISTORY,
        WALLET_HISTORY,
        PANDITH_REPLAY,
        CHAT_REQUEST,
        CHAT_SATUS_CANCEL,
        CHAT_SATUS_UPDATE,
        USER_CHAT_STATE,
        CHATSATUS_NORESPONCE_FROM_USER,
        CHAT_ATACHMENT,
        CHAT_SATE_VALUES,
        UPDATE_REPORT,
        CALL_CLARIFICATION,
        NO_RESPONCE,
        STATUS_WAITLIST_ONLINE,
        CHAT_REQUEST1,
        CHAT_INFO_DATA,
        CHAT_SATE_VALUES_BAL,


    }
}
