If $CmdLine[0] < 2 Then 
	Exit 
EndIf

ActivateWindow($CmdLine[1], $CmdLine[2])
Func ActivateWindow($winTitle, $winWait)
	WinWait($winTitle,"",$winWait)
	
	If  WinExists($winTitle) Then
		WinActivate($winTitle)
	Else
		Return False
	EndIf
EndFunc