#pragma once
#include<Windows.h>
#include <tlhelp32.h>
#include <stdio.h> 
#include <Winnt.h> 

 BOOLEAN GetAuthor(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

 BOOLEAN GetDescription(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

 VOID Execute(void* ptrHWND);
	

