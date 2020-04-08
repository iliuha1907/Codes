#pragma warning(disable : 4996)
#include"stdafx.h"
#include"ImageDll.h"

const char* author = "Ilya Huzei";
const char* description = "This is a DLL that sends draws an image, getting Window, HDC and positions as parameters";

HDC hDC, hCompatibleDC;
PAINTSTRUCT PaintStruct;
HANDLE hBitmap, hOldBitmap;
RECT Rect;
BITMAP Bitmap;

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

VOID Execute(void* ptrHWND,void* ptrHDC,int startX,int startY,int width,int heigth)
{
	HWND hWnd = (HWND)ptrHWND;
	HDC hDC = (HDC)ptrHDC;
	hBitmap = (HBITMAP)LoadImage(NULL, TEXT("Image.bmp"), IMAGE_BITMAP,
		0, 0, LR_LOADFROMFILE | LR_CREATEDIBSECTION); 
	if (hBitmap == NULL)
	{
		return;
	}
	GetObject(hBitmap, sizeof(BITMAP), &Bitmap);
	hCompatibleDC = CreateCompatibleDC(hDC);
	hOldBitmap = SelectObject(hCompatibleDC, hBitmap);
	GetClientRect(hWnd, &Rect);
	StretchBlt(hDC, startX, startY, width, heigth, hCompatibleDC, 0, 0, Bitmap.bmWidth,
		Bitmap.bmHeight, SRCCOPY);
	SelectObject(hCompatibleDC, hOldBitmap);
	DeleteObject(hBitmap);
	DeleteDC(hCompatibleDC);
	ReleaseDC(hWnd, hDC);
}