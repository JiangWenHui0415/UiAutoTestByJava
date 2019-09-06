package com.purang.yyt_uiautotest.tools.ieau3;

import com.purang.yyt_uiautotest.utils.ThreadExecutor;

public class BrowserGuiAuto {

	private static final String ASSIST = System.getProperty("user.dir") + "/exec/";
	private final ThreadExecutor execute = new ThreadExecutor();

	private int maxWait = 5;

	/**
	 * set timeout for window waits
	 * 
	 * @param timeout
	 *            time in sencods to wait for window initialize
	 **/
	public void setWindowWait(int timeout) {
		this.maxWait = timeout;
	}

	/**
	 * click on browser object, found by objectId, using au3 libs</BR> you can
	 * use it like this: ieAU3ClickById("窗口标题", 1, "idvalues");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param ieIndex
	 *            the window index shared the same title, begins at 1
	 * @param objectId
	 *            object id value of ie object
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickById(String ieTitle, int ieIndex, String objectId) {
		String fileExec = ASSIST + "ieClickById.exe";
		String cmd = "\"" + fileExec + "\" \"" + ieTitle + "\" \"" + ieIndex + "\" \"" + objectId + "\" \"" + maxWait
				+ "\"";
		execute.executeCommands(cmd);
	}

	/**
	 * click on browser object, found by objectId, using au3 libs</BR> you can
	 * use it like this: ieAU3ClickById("窗口标题", "idvalues");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectId
	 *            object id value of ie object
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickById(String ieTitle, String objectId) {
		ieAU3ClickById(ieTitle, 1, objectId);
	}

	/**
	 * click on browser object, found by object name and index, using au3
	 * libs</BR> you can use it like this: ieAU3ClickByName("窗口标题", 1,
	 * "namevalues", 0);
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param ieIndex
	 *            the window index shared the same title, begins at 1
	 * @param objectName
	 *            objectName value of ie object
	 * @param ObjectIndex
	 *            index of the object shared the same objectName, begins at 0
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickByName(String ieTitle, int ieIndex, String objectName, int ObjectIndex) {
		String fileExec = ASSIST + "ieClickByName.exe";
		String cmd = "\"" + fileExec + "\" \"" + ieTitle + "\" \"" + ieIndex + "\" \"" + objectName + "\" \""
				+ ObjectIndex + "\" \"" + maxWait + "\"";
		execute.executeCommands(cmd);
	}

	/**
	 * click on browser object, found by object name and index, using au3
	 * libs</BR> you can use it like this: ieAU3ClickByName("窗口标题",
	 * "namevalues", 0);
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectName
	 *            objectName value of ie object
	 * @param ObjectIndex
	 *            index of the object shared the same objectName, begins at 0
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickByName(String ieTitle, String objectName, int ObjectIndex) {
		ieAU3ClickByName(ieTitle, 1, objectName, ObjectIndex);
	}

	/**
	 * click on browser object, found by object name and index, using au3
	 * libs</BR> you can use it like this: ieAU3ClickByName("窗口标题",
	 * "namevalues");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectName
	 *            objectName value of ie object
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickByName(String ieTitle, String objectName) {
		ieAU3ClickByName(ieTitle, objectName, 0);
	}

	/**
	 * click on browser object, found by linktext, default index 0, using au3
	 * libs</BR> you can use it like this: ieAU3ClickByLinkText("窗口标题", 1,
	 * "link texts");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param ieIndex
	 *            the window index shared the same title, begins at 1
	 * @param linkText
	 *            link text of ie links
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickByLinkText(String ieTitle, int ieIndex, String linkText) {
		String fileExec = ASSIST + "ieClickByLinkText.exe";
		String cmd = "\"" + fileExec + "\" \"" + ieTitle + "\" \"" + ieIndex + "\" \"" + linkText + "\" \"" + maxWait
				+ "\"";
		execute.executeCommands(cmd);
	}

	/**
	 * click on browser object, found by linktext, default index 0, using au3
	 * libs</BR> you can use it like this: ieAU3ClickByLinkText("窗口标题",
	 * "link texts");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param linkText
	 *            link text of ie links
	 * @throws RuntimeException
	 **/
	public void ieAU3ClickByLinkText(String ieTitle, String linkText) {
		ieAU3ClickByLinkText(ieTitle, 1, linkText);
	}

	/**
	 * input text to editros, found by objectId, using au3 libs</BR> you can use
	 * it like this: ieAU3SendKeysById("窗口标题", 1, "idvalues", "text content");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param ieIndex
	 *            the window index shared the same title, begins at 1
	 * @param objectId
	 *            object id value of ie object
	 * @param textContent
	 *            text content to be input to editors
	 * @throws RuntimeException
	 **/
	public void ieAU3SendKeysById(String ieTitle, int ieIndex, String objectId, String textContent) {
		String fileExec = ASSIST + "ieSendKeysById.exe";
		String cmd = "\"" + fileExec + "\" \"" + ieTitle + "\" \"" + ieIndex + "\" \"" + objectId + "\" \""
				+ textContent + "\" \"" + maxWait + "\"";
		execute.executeCommands(cmd);
	}

	/**
	 * input text to editros, found by objectId, using au3 libs</BR> you can use
	 * it like this: ieAU3SendKeysById("窗口标题", 1, "idvalues", "text content");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectId
	 *            object id value of ie object
	 * @param textContent
	 *            text content to be input to editors
	 * @throws RuntimeException
	 **/
	public void ieAU3SendKeysById(String ieTitle, String objectId, String textContent) {
		ieAU3SendKeysById(ieTitle, 1, objectId, textContent);
	}

	/**
	 * input text to editros, found by object name and its index, using au3
	 * libs</BR> you can use it like this: ieAU3SendKeysByName("窗口标题", 1,
	 * "objectName values", 0, "text content");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param ieIndex
	 *            the window index shared the same title, begins at 1
	 * @param objectName
	 *            objectName value of ie object
	 * @param ObjectIndex
	 *            index of the object shared the same objectName, begins at 0
	 * @param textContent
	 *            text content to be input to editors
	 * @throws RuntimeException
	 **/
	public void ieAU3SendKeysByName(String ieTitle, int ieIndex, String objectName, int ObjectIndex, String textContent) {
		String fileExec = ASSIST + "ieSendKeysByName.exe";
		String cmd = "\"" + fileExec + "\" \"" + ieTitle + "\" \"" + ieIndex + "\" \"" + objectName + "\" \""
				+ ObjectIndex + "\" \"" + textContent + "\" \"" + maxWait + "\"";
		execute.executeCommands(cmd);
	}

	/**
	 * input text to editros, found by object objectName and its index, using
	 * au3 libs</BR> you can use it like this: ieAU3SendKeysByName("窗口标题",
	 * "objectName values", 0, "text content");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectName
	 *            objectName value of ie object
	 * @param ObjectIndex
	 *            index of the object shared the same objectName, begins at 0
	 * @param textContent
	 *            text content to be input to editors
	 * @throws RuntimeException
	 **/
	public void ieAU3SendKeysByName(String ieTitle, String objectName, int ObjectIndex, String textContent) {
		ieAU3SendKeysByName(ieTitle, 1, objectName, ObjectIndex, textContent);
	}

	/**
	 * input text to editros, found by object name and its index, using au3
	 * libs</BR> you can use it like this: ieAU3SendKeysByName("窗口标题",
	 * "objectName values", "text content");
	 * 
	 * @param ieTitle
	 *            ie window title
	 * @param objectName
	 *            objectName value of ie object
	 * @param textContent
	 *            text content to be input to editors
	 * @throws RuntimeException
	 **/
	public void ieAU3SendKeysByName(String ieTitle, String objectName, String textContent) {
		ieAU3SendKeysByName(ieTitle, objectName, 0, textContent);
	}
}