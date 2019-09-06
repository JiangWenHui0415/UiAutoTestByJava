Public Function WriteExcel(fileName, sheetName)
	On Error Resume Next
	
	Set fObject = CreateObject("Scripting.FileSystemObject")
	Set ExcelApp = CreateObject("Excel.Application")
	ExcelApp.Visible = False
	ExcelApp.DisplayAlerts = False
	
	Set workBook = ExcelApp.Workbooks.Open(fileName)
	workBook.Worksheets(sheetName).Activate
	workBook.Save
	
	Set workBook = Nothing
	ExcelApp.Quit
	Set ExcelApp = Nothing
	Set fObject = Nothing
End Function

WriteExcel wscript.arguments(0), wscript.arguments(1)