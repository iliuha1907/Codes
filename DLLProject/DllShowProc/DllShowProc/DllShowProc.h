#pragma once
#ifdef DllShowProc_EXPORTS 
#define DllShowProc __declspec(dllexport)  
#else 
#define DllShowProc_API __declspec(dllimport)  
#endif 

#include"stdio.h"
 

DllShowProc_API BOOLEAN GetAuthor(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

DllShowProc_API BOOLEAN GetDescription(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

DllShowProc_API VOID Execute(void* ptrListbox);