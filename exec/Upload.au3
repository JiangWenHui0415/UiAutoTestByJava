#Region ;**** ���������� ACNWrapper_GUI ****
#AutoIt3Wrapper_outfile=Upload.exe
#AutoIt3Wrapper_Res_Fileversion=0.0.0.0
#EndRegion ;**** ���������� ACNWrapper_GUI ****

If $CmdLine[0] < 3 Then 
	Exit 
EndIf

fileUpload($CmdLine[1], $CmdLine[2], $CmdLine[3])

Func fileUpload($uploadtitle, $uploadfile, $timeout)
	WinWait($uploadtitle,"",$timeout)
	If  WinExists($uploadtitle) Then
		WinActive($uploadtitle)
		Sleep (500)
		ControlSetText($uploadtitle,"","Edit1",$uploadfile)
		ControlClick($uploadtitle, "", "��(&O)")
	Else
		Return False
	EndIf
EndFunc