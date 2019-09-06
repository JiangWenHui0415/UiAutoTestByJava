package com.purang.camp.uiautotest.tools.wingui;

import java.io.*;
import java.text.MessageFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import com.purang.camp.uiautotest.utils.LoggerUtil;
import com.purang.camp.uiautotest.utils.StringBufferUtil;
import com.purang.camp.uiautotest.utils.ThreadExecutor;

/**
 * 说明：
 * 1、通过动态生成vbs，由vbs进行一系列的win32操作，可自定义返回否不返回；
 * 2、执行cmd命令，截取返回流信息；
 * 3、可读取指定的注册表信息、杀进程、读取指定的系统环境变量等。
 *
 * @author 测试仔刘毅
 **/

public class Win32GuiByVbs {
    private final StringBufferUtil SBF = new StringBufferUtil();
    private final String VBSRES = "vbsResult";
    private final String vbs_1 = "Set fObject = CreateObject(\"Scripting.FileSystemObject\") \n"
            + "Set fileStream = fObject.CreateTextFile(\"{0}\", True) \n";
    private final String vbs_2 = "fileStream.Write({0}) \n" + "fileStream.Close \n" + "Set fileStream = nothing \n"
            + "Set fObject = Nothing";
    private final ThreadExecutor execute = new ThreadExecutor();

    /**
     * execute a specified vbs string and get its return value by temp file.
     *
     * @param vbs
     *            vbs string to be executed
     * @return the vbs returned values: string
     * @throws RuntimeException
     **/
    public String getVbsResult(String vbs) {
        String vbsfileName = System.getenv("TEMP") + "\\temp_" + SBF.getMilSecNow() + ".vbs";
        String tmpfileName = System.getenv("TEMP") + "\\temp_" + SBF.getMilSecNow() + ".txt";
        String vbsContent = MessageFormat.format(vbs_1, new Object[] { tmpfileName }) + vbs + "\n"
                + MessageFormat.format(vbs_2, new Object[] { VBSRES });
        String resText = null;
        File file = null;
        BufferedReader fileReader = null;

        try {
            createVbsFile(vbsContent, new File(vbsfileName));
            executeVbsFile(vbsfileName);

            file = new File(tmpfileName);
            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            resText = fileReader.readLine().toString();
            fileReader.close();
            new File(vbsfileName).delete();
            file.delete();
        } catch (Exception ex) {
            LoggerUtil.error(ex);
            throw new RuntimeException(ex.getMessage());
        }
        return resText;
    }

    /**
     * execute a specified vbs string and get its return value by temp file.
     *
     * @param vbs
     *            vbs string to be executed
     * @throws RuntimeException
     **/
    public void executeVbsText(String vbs) {
        String vbsfileName = System.getenv("TEMP") + "\\temp_" + SBF.getMilSecNow() + ".vbs";
        File file = new File(vbsfileName);
        try {
            createVbsFile(vbs, file);
            executeVbsFile(vbsfileName);
            file.delete();
        } catch (Exception ex) {
            LoggerUtil.error(ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * execute a vbs file.
     *
     * @param vbsfileName
     *            whole name whitch vbs file to be executed
     * @throws RuntimeException
     **/
    public void executeVbsFile(String vbsfileName) {
        String[] vbsCmd = new String[] { "wscript", vbsfileName };
        execute.executeCommands(vbsCmd);
    }

    /**
     * create a temp vbs file to be executed.
     *
     * @param vbs
     *            string content to be written into file
     * @param vbsfileName
     *            whole name whitch vbs file to be saved
     * @throws RuntimeException
     **/
    public void createVbsFile(String vbs, File vbsfileName) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vbsfileName)));
            writer.write(vbs);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new RuntimeException("execute extern file failed:" + e.getMessage());
        }
    }

    /**
     * read regedit info by specified key.
     *
     * @param regKey
     *            whole path and keyname of regedit key
     * @return the regedit key value
     * @throws RuntimeException
     **/
    public String regRead(String regKey) {
        String vbsStr = "Set WshObject = CreateObject(\"WScript.Shell\") \n" + VBSRES
                + " = WshObject.RegRead(\"" + regKey + "\") \n" + "Set WshObject = Nothing";
        return getVbsResult(vbsStr);
    }

    /**
     * read regedit info by specified key.
     *
     * @param regKey
     *            whole path and keyname of regedit key
     * @param value
     *            regedit value
     * @param valueType
     *            regedit value type
     * @throws RuntimeException
     **/
    public void regWrite(String regKey, String value, String valueType) {
        String vbsStr = "Set WshObject = CreateObject(\"WScript.Shell\") \n" + "WshObject.RegWrite \""
                + regKey + "\", " + value + ", \"" + valueType + "\"\n" + "Set WshObject = Nothing";
        executeVbsText(vbsStr);
    }

    /**
     * get the ie version under current system.
     *
     * @return the internet explorer version string
     * @throws RuntimeException
     **/
    public String ieVersion() {
        String keyName = "HKLM\\Software\\Microsoft\\Internet Explorer\\version";
        String version = regRead(keyName);
        return version;
    }

    /**
     * get the ie version under current system.
     *
     * @param seconds
     *            windows TIME_WAIT settings, default 30s.
     * @throws RuntimeException
     **/
    public void windowsTimeWaitSet(int seconds) {
        regWrite("HKLM\\SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\TcpTimedWaitDelay",
                String.valueOf(seconds), "REG_DWORD");
    }

    /**
     * get the ie version under current system.
     *
     * @throws RuntimeException
     **/
    public void windowsTimeWaitSet() {
        windowsTimeWaitSet(30);
    }

    /**
     * get system environment values. 最好使用System.getenv()代替。
     *
     * @param virName
     *            viriable name to get, such as "classpath", "JAVA_HOME"
     * @return the viriable value
     * @throws RuntimeException
     **/
    public String getEnvironment(String virName) {
        byte[] env = new byte[2048];
        try {
            Process process = Runtime.getRuntime().exec("cmd /c echo %" + virName + "% ");
            InputStream iStream = process.getInputStream();
            iStream.read(env);
            process.waitFor();
            return new String(env).trim();
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new RuntimeException("execute extern file failed:" + e.getMessage());
        }
    }

    /**
     * judge if the specified process is exist under windows.
     *
     * @param processName
     *            process name like "iexplore.exe" or "iexplore".
     * @param userFilter
     *            if system process excluded.
     * @return when process exists, return true, else false.
     *
     * @throws RuntimeException
     **/
    public boolean processExistUnderWindows(String processName, boolean userFilter) {
        processName = processName.toLowerCase().replace(".exe", "") + ".exe";
        String command = userFilter ? "cmd /c tasklist /FI \"USERNAME eq %USERDOMAIN%\\%USERNAME%\""
                : "cmd /c tasklist";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String pName;
            while ((pName = reader.readLine()) != null) {
                if (pName.toLowerCase().contains(processName)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            process.waitFor();
            return false;
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new RuntimeException("execute extern file failed:" + e.getMessage());
        }
    }

    /**
     * judge if the specified process is exist under windows.
     *
     * @param processName
     *            process name like "iexplore.exe" or "iexplore".
     * @return when process exists, return true, else false.
     *
     * @throws RuntimeException
     **/
    public boolean processExistUnderWindows(String processName) {
        return processExistUnderWindows(processName, true);
    }

    /**
     * kill win32 process using cmd.exe.
     *
     * @param process
     *            process name like 'iexplore' or 'iexplore.exe'
     * @throws RuntimeException
     **/
    public void killWin32Process(String process) {
        String cmd = "cmd /c taskkill /FI \"USERNAME eq %USERDOMAIN%\\%USERNAME%\" /f /im "
                + process.toLowerCase().replace(".exe", "") + ".exe";
        execute.executeCommands(cmd);
    }

    /**
     * excel file saved again by vbs after java modify.
     *
     * @param vbsName
     *            vbs file path and name.
     * @param fileName
     *            file to be resaved.
     * @param sheetName
     *            the sheet to be modified.
     * @throws RuntimeException
     **/
    public void excelReSave(String vbsName, String fileName, String sheetName) {
        String command = "cmd /c " + new File(vbsName).getAbsolutePath() + " " + fileName + " " + sheetName;
        execute.executeCommands(command);
    }

    /**
     * excel file saved again by vbs after java modify.
     *
     * @param fileName
     *            file to be resaved.
     * @param sheetName
     *            the sheet to be modified.
     * @throws RuntimeException
     **/
    public void excelReSave(String fileName, String sheetName) {
        excelReSave("./exec/ExcelReSave.vbs", fileName, sheetName);
    }
}
