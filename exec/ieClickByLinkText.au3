#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_UseUpx=n
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

#include <IE.au3>

If $CmdLine[0] < 4 Then 
	Exit
EndIf

ieClickByLinkText($CmdLine[1], $CmdLine[2], $CmdLine[3], $CmdLine[4])

Func ieClickByLinkText($windowTitle, $winIndex, $linkText, $timeout)
	WinWait($windowTitle,"",$timeout)
	If  WinExists($windowTitle) Then
		WinWaitActive($windowTitle, "", 5)
		Sleep (500)
		
		$Browser = _IEAttach ($windowTitle, "Title", $winIndex)
		If @error  Then
			Return False
		EndIf
		
		_IELinkClickByText($Browser, $linkText, 0)
		If @error  Then
			Return False
		EndIf
	Else
		Return False
	EndIf
EndFunc