package com.purang.camp.uiautotest.core.assertion;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import com.purang.camp.uiautotest.tools.wingui.Win32GuiByAu3;
import com.purang.camp.uiautotest.utils.ReadConfiguration;
import com.purang.camp.uiautotest.utils.StackTraceUtil;
import com.purang.camp.uiautotest.logging.webdriver.HtmlLogger;
import com.purang.camp.uiautotest.logging.webdriver.LogMapKey;

public class Assertions {
    private final ReadConfiguration config = new ReadConfiguration("/com/purang/yyt_uiautotest/core/webdriver/webdriver.properties");

    private final String CAPTURE_MESSAGE = config.get("CAPTURE_MESSAGE");
    private String captureTo = null;
    private static String className = null;
    private HtmlLogger logger = null;
    private boolean exitRun = true;
    private boolean needLog = false;

    private StackTraceUtil stack = new StackTraceUtil();
    private StackTraceElement[] traces;
    private StackTraceElement trace;

    /**
     * Description: set log result path.
     *
     * @param result
     */
    public void setResultPath(String result) {
        this.captureTo = result;
    }

    /**
     * Description: cunstruction with parameter initialize.
     *
     * @param mdriver
     *            the webdriver object.
     * @param captureTo
     *            the log path to put the screen shot files.
     * @param clsName
     *            class name which calling this method.
     * @param logger
     *            the LoggerModeChoice object that used this class.
     */
    public Assertions(WebDriver mdriver, String captureTo, String clsName, HtmlLogger logger) {
        this.logger = logger;
        this.captureTo = captureTo;
        className = clsName;
    }

    /**
     * Description: cunstruction with parameter initialize.
     *
     * @param driver
     *            the webdriver object.
     * @param captureToPath
     *            the log path to put the screen shot files.
     * @param className
     *            class name which calling this method.
     */
    public Assertions(WebDriver driver, String captureToPath, String className) {
        this(driver, captureToPath, className, null);
    }

    /**
     * Description: set if exit on condition assert failed.
     *
     * @param exitOnError
     *            the bool value to decide if exit when error occured.
     */
    public void setExitOnAssertFailure(boolean exitOnError) {
        this.exitRun = exitOnError;
    }

    /**
     * Description: set if your want to record log after assert passed.
     *
     * @param needRecord
     *            if set true, it will record log after assert passed.
     */
    public void setRecordOnSucceed(boolean needRecord) {
        this.needLog = needRecord;
    }

    /**
     * Description:record log when assert passed.
     */
    private void recordAfterAssertion(String status, String message) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int first = 3, last = 3;
        String mtdName = trace[first].getMethodName();

        for (int i = first; i < trace.length; i++) {
            if (trace[i].getClassName().contains(".reflect.")) {
                last = ((i - 1) <= first) ? first : (i - 1);
                break;
            }
        }
        String cName = trace[last].getClassName() + " # " + trace[last].getLineNumber();
        Map<String, String> map = new HashMap<String, String>();
        map.put(LogMapKey.METHOD_NAME_KEY.getValue(), mtdName);
        map.put(LogMapKey.RUN_STATUS_KEY.getValue(), status);
        map.put(LogMapKey.MESSAGE_KEY.getValue(), message);
        map.put(LogMapKey.CLASS_NAME_KEY.getValue(), cName);
        logger.recorder(map);
    }

    /**
     * Description:record log when assert passed.
     */
    private void recordSuccessAfterAssertion() {
        if (needLog && null != logger) {
            recordAfterAssertion("passed", "assert passed!");
        }
    }

    /**
     * Description:throw RuntimeException has been caught that stops the test
     * run.
     *
     * @param exception
     *            the Exception object been caught.
     */
    private void exitOnAssertionError(AssertionError exception) {
        if (exitRun) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Description: throw user defined RuntimeException that stops the test run.
     *
     * @param message
     *            the user defined message to be recorded.
     */
    private void exitOnAssertionError(String message) {
        if (exitRun) {
            throw new RuntimeException(message);
        }
    }

    /**
     * Description: take screen shot by remotewebdriver.
     *
     * @throws Exception
     */
    private void screenShotOnAssertionError(Throwable e) {
        String time = String.valueOf(System.currentTimeMillis());
        String fileName = captureTo + className + "_assertion_" + time + ".png";
        // 未为RemoteWebDriver重新实现TakeScreenShot，故无法使用默认的截图
        new Win32GuiByAu3().screenCapture(fileName);
        if (null != logger) {
            recordAfterAssertion("failed", "assert " + CAPTURE_MESSAGE + " [" + fileName + "]");
        }
    }

    /**
     * Description: print error message on console.
     *
     * @param exception
     *            the Throwables.
     */
    private void consolePrintOnError(Throwable exception) {
        traces = exception.getStackTrace();
        trace = traces[stack.getTraceClassLevel(traces)];
        String info = trace.getClassName() + ", method: " + trace.getMethodName() + ", line: " + trace.getLineNumber();
        System.err.println("Assert failed: \n" + info);
    }

    /**
     * Description: to do something when assert failed.
     *
     * @param ae
     *            the AssertionError.
     * @param message
     *            user defined messages.
     */
    private void handleOnAssertError(AssertionError ae, String message) {
        screenShotOnAssertionError(ae);
        consolePrintOnError(ae);
        if (null == message) {
            exitOnAssertionError(ae);
        } else {
            System.err.println("Assert failed: " + message);
            exitOnAssertionError(message);
        }
    }

    /**
     * Description: JUnit/TestNG assertTrue method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param condition
     *            the condition to be judged.
     */
    public void assertTrue(String message, Boolean condition) {
        try {
            org.testng.AssertJUnit.assertTrue(message, condition);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertTrue method.
     *
     * @param condition
     *            the condition to be judged.
     */
    public void assertTrue(Boolean condition) {
        assertTrue(null, condition);
    }

    /**
     * Description: JUnit/TestNG assertFalse method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param condition
     *            the condition to be judged.
     */
    public void assertFalse(String message, Boolean condition) {
        assertTrue(message, !condition);
    }

    /**
     * Description: JUnit/TestNG assertFalse method.
     *
     * @param condition
     *            the condition to be judged.
     */
    public void assertFalse(Boolean condition) {
        assertTrue(null, !condition);
    }

    /**
     * Description: JUnit/TestNG assertNull method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param object
     *            the object to be judged if is null.
     */
    public void assertNull(String message, Object object) {
        assertTrue(message, null == object);
    }

    /**
     * Description: JUnit/TestNG assertNull method.
     *
     * @param object
     *            the object to be judged if is null.
     */
    public void assertNull(Object object) {
        assertTrue(null, null != object);
    }

    /**
     * Description: JUnit/TestNG assertNotNull method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param object
     *            the object to be judged if is null.
     */
    public void assertNotNull(String message, Object object) {
        assertTrue(message, null == object);
    }

    /**
     * Description: JUnit/TestNG assertNotNull method.
     *
     * @param object
     *            the object to be judged if is null.
     */
    public void assertNotNull(Object object) {
        assertTrue(null, null != object);
    }

    /**
     * Description: JUnit/TestNG assertSame method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertSame(String message, Object expected, Object actual) {
        try {
            org.testng.AssertJUnit.assertSame(message, expected, actual);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertSame method.
     *
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * Description: JUnit/TestNG assertNotSame method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertNotSame(String message, Object expected, Object actual) {
        try {
            org.testng.AssertJUnit.assertNotSame(message, expected, actual);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertNotSame method.
     *
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertNotSame(Object expected, Object actual) {
        assertNotSame(null, expected, actual);
    }

    /**
     * Description: JUnit/TestNG assertEquals method.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertEquals(String message, Object expected, Object actual) {
        try {
            org.testng.AssertJUnit.assertEquals(message, expected, actual);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertEquals method.
     *
     * @param expected
     *            the expected object.
     * @param actual
     *            the actual object.
     */
    public void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Description: JUnit/TestNG assertEquals method for string type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, String expected, String actual) {
        try {
            org.testng.AssertJUnit.assertEquals(message, expected, actual);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertEquals method for string type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String expected, String actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Description: JUnit/TestNG assertEquals method for double type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     * @param delta
     *            the delta value for compare.
     */
    public void assertEquals(String message, double expected, double actual, double delta) {
        try {
            org.testng.AssertJUnit.assertEquals(message, expected, actual, delta);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertEquals method for double type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     * @param delta
     *            the delta value for compare.
     */
    public void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Description: JUnit/TestNG assertEquals method for float type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     * @param delta
     *            the delta value for compare.
     */
    public void assertEquals(String message, float expected, float actual, float delta) {
        try {
            org.testng.AssertJUnit.assertEquals(message, expected, actual, delta);
            recordSuccessAfterAssertion();
        } catch (AssertionError ae) {
            handleOnAssertError(ae, message);
        }
    }

    /**
     * Description: JUnit/TestNG assertEquals method for float type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     * @param delta
     *            the delta value for compare.
     */
    public void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Description: JUnit/TestNG assertEquals method for long type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, long expected, long actual) {
        assertEquals(message, Long.valueOf(expected), Long.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for long type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(long expected, long actual) {
        assertEquals(null, Long.valueOf(expected), Long.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for bool type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, boolean expected, boolean actual) {
        assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for bool type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(boolean expected, boolean actual) {
        assertEquals(null, Boolean.valueOf(expected), Boolean.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for byte type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, byte expected, byte actual) {
        assertEquals(message, Byte.valueOf(expected), Byte.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for byte type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(byte expected, byte actual) {
        assertEquals(null, Byte.valueOf(expected), Byte.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for char type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, char expected, char actual) {
        assertEquals(message, Character.valueOf(expected), Character.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for char type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(char expected, char actual) {
        assertEquals(null, Character.valueOf(expected), Character.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for short type value
     * compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, short expected, short actual) {
        assertEquals(message, Short.valueOf(expected), Short.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for short type value
     * compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(short expected, short actual) {
        assertEquals(null, Short.valueOf(expected), Short.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for int type value compare.
     *
     * @param message
     *            user defined error message to throw in Exception.
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(String message, int expected, int actual) {
        assertEquals(message, Integer.valueOf(expected), Integer.valueOf(actual));
    }

    /**
     * Description: JUnit/TestNG assertEquals method for int type value compare.
     *
     * @param expected
     *            the expected value.
     * @param actual
     *            the actual value.
     */
    public void assertEquals(int expected, int actual) {
        assertEquals(null, Integer.valueOf(expected), Integer.valueOf(actual));
    }
}
