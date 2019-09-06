#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=ClickAlert.exe
#AutoIt3Wrapper_Res_Fileversion=0.0.0.0
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

If $CmdLine[0] < 3 Then 
	Exit 
EndIf

clickAlert($CmdLine[1], $CmdLine[2], $CmdLine[3])

Func clickAlert($alerttitle, $buttontext, $timeout)
	 
	WinWait($alerttitle,"",$timeout)
	If  WinExists($alerttitle) Then
		WinActive($alerttitle)
		Sleep (500)
		ControlClick($alerttitle,"",$buttontext)
	Else
		Return False
	EndIf
EndFunc 
   