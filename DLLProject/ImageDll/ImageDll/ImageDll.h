#pragma once
#ifdef ImageDll_EXPORTS 
#define ImageDll __declspec(dllexport)  
#else 
#define ImageDll_API __declspec(dllimport)  
#endif 

#include<Windows.h>
#include <tlhelp32.h>
#include <stdio.h> 
#include <Winnt.h> 


ImageDll_API BOOLEAN GetAuthor(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

ImageDll_API BOOLEAN GetDescription(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);

ImageDll_API VOID Execute(void* ptrHWND, void* ptrHDC, int startX, int startY, int width, int heigth);