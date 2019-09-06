#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=Download.exe
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

If $CmdLine[0] < 4 Then 
	Exit 
EndIf

fileDownload($CmdLine[1],$CmdLine[2],$CmdLine[3],$CmdLine[4])

Func fileDownload($download_title, $save_title, $SaveAsFileName, $timeout)
	If 	FileExists ($SaveAsFileName) Then
		FileDelete($SaveAsFileName)
	EndIf

	 WinWait($download_title,"",$timeout)
	 If  WinExists($download_title) Then
		WinActivate($download_title)
		Sleep (500)
		ControlClick($download_title,"","保存(&S)","")
		WinWaitActive($save_title,"",$timeout)
		WinActivate($save_title)

		If  @OSVersion == "WIN_2008" Then
			ControlSetText($save_title,"","Edit1", "")
			ControlClick($save_title, "","Edit1")
			SendKeys($saveAsFileName)
		Else
			ControlSetText($save_title,"","Edit1", $saveAsFileName)
		EndIf
		WinActivate($save_title)
		Sleep(500)
		ControlClick($save_title, "","保存(&S)")
	 Else
	   If	WinExists($save_title) Then
			WinActivate($save_title)
						
			If  @OSVersion == "WIN_2008" Then
				 ControlSetText($save_title,"","Edit1", "")
				 ControlClick($save_title, "","Edit1")
				 SendKeys($saveAsFileName)
			Else
				ControlSetText($save_title,"","Edit1", $saveAsFileName)
			EndIf
			WinActivate($save_title)
			Sleep(500)
			ControlClick($save_title, "","保存(&S)")
		Else
			Return False
		EndIf
	EndIf
	
	For $i = 1 to 10
		If WinWait("REGEXPTITLE:已下载.*","",1) Or WinWait($save_title,"",1) Then
			Sleep(500)
		Else
			ExitLoop
		EndIf
	Next
	Return FileExists($SaveAsFileName)
EndFunc

Func SendKeys($Str)
	For $i = 1 To StringLen($Str)
		Send('{ASC ' & StringToBinary(StringMid($Str, $i, 1) & ' ') & '}')
	Next
EndFunc