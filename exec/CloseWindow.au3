#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=CloseWindow.exe
#AutoIt3Wrapper_Res_Fileversion=0.0.0.0
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

If $CmdLine[0] < 2 Then 
	Exit 
EndIf

closeWindow($CmdLine[1], $CmdLine[2])

Func closeWindow($windowtitle, $timeout)
	WinWait($windowtitle,"",$timeout)
	If  WinExists($windowtitle) Then
		WinActive($windowtitle)
		Sleep (500)
		WinClose($windowtitle)
	Else
		Return False
	EndIf
EndFunc