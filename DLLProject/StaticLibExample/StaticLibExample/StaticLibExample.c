#pragma warning(disable : 4996)
#include "StaticLibExample.h"


const char* author = "Ilya Huzei";
const char* description = "This is a static library that makes beeb and shows something";


BOOLEAN GetAuthor(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten)
{
	DWORD length = strlen(author);
	if (dwBufferSize < length)
	{
		return FALSE;
	}
	else
	{
		strcpy(buffer, author);
		DWORD value = length * sizeof(char);
		pdwBytesWritten = &value;
		return TRUE;
	}
}


BOOLEAN GetDescription(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten)
{
	DWORD length = strlen(description);
	if (dwBufferSize < length)
	{
		return FALSE;
	}
	else
	{
		strcpy(buffer, description);
		DWORD value = length * sizeof(char);
		pdwBytesWritten = &value;
		return TRUE;
	}
}

VOID Execute(void* ptrHWND)
{
	HWND hWnd = (HWND)ptrHWND;
	MessageBeep(MB_OK);
	MessageBox(hWnd, L"Hello, This is static library!", L"Static library", MB_OK);
}
