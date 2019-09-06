#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=TyepAlert.exe
#AutoIt3Wrapper_Res_Fileversion=0.0.0.0
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

If $CmdLine[0] < 4 Then 
	Exit 
EndIf

typeAlert($CmdLine[1], $CmdLine[2], $CmdLine[3], $CmdLine[4])

Func typeAlert($alerttitle, $editindex, $text, $timeout)
	WinWait($alerttitle,"",$timeout)
	If  WinExists($alerttitle) Then
		WinActive($alerttitle)
		Sleep (500)
		ControlSetText($alerttitle,"",$editindex, $text)
	Else
		Return False
	EndIf
EndFunc