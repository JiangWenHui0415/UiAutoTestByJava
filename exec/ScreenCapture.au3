#include <WinAPI.au3>
#include <ScreenCapture.au3>
#include <WindowsConstants.au3>

If $CmdLine[0] < 1 Then 
	Exit 
EndIf

ScreenCapture($CmdLine[1])

Func ScreenCapture($fileName)
	IF FileExists($fileName) Then
		FileDelete($fileName)
	EndIf
	_ScreenCapture_Capture($fileName)
EndFunc 
