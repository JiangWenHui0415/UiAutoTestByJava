#Region ;**** 参数创建于 ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=WriteErrorMessage.exe
#AutoIt3Wrapper_Res_Fileversion=0.0.0.0
#EndRegion ;**** 参数创建于 ACNWrapper_GUI ****

#include <IE.au3>
If $CmdLine[0] < 5 Then 
	Exit
EndIf

writemessageinfo($CmdLine[1], $CmdLine[2], $CmdLine[3], $CmdLine[4], $CmdLine[5])

Func writemessageinfo($title, $div, $lib, $filename, $timeout)
	WinWait($title,"",$timeout)
	If  WinExists($title) Then
		WinActive($title)
		Sleep (500)
		$oIE = _IEAttach ($title, "Title")
		Sleep (500)
		
		$divobj = _IEGetObjById($oIE,$div)
		If @error  Then
			Return False
		EndIf	
		
		$olis = _IETagNameGetCollection ($divobj, $lib)
		If @error  Then
			Return False
		EndIf
		
		$file=FileOpen($filename,1)
        If $file = -1 Then
			Return False
        EndIf
		
		For $oli In $olis
			$text=_IEPropertyGet($oli,"outertext")
			FileWrite($file,$text)
		Next
		
		FileClose($file)
		WinClose($title)
	Else
		Return False
	EndIf
EndFunc