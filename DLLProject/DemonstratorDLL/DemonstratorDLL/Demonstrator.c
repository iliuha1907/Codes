#pragma warning(disable : 4996)

#include<Windows.h>
#include <tlhelp32.h>
#include <stdio.h> 
#include <Winnt.h> 
#include "StaticLibExample.h"

void  addControls(HWND hWnd);
void  addMenus(HWND hWnd);
void getNameModules();

void openDll1();
void executeDll1();
void getAuthorDll1();
void getDescriptionDll1();
void freeDll1();

void openDll2();
void executeDll2();
void getAuthorDll2();
void getDescriptionDll2();
void freeDll2();

void executeStat();
void getAuthorStat();
void getDescriptionStat();

LRESULT CALLBACK funcWinProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam);

MSG msg;

HWND hWnd;
HMENU hMenuBar;
HWND listbox;
HWND listTXT;

WNDCLASS wc;

HINSTANCE hLib1;
HINSTANCE hLib2;

HDC hDC, hCompatibleDC;
PAINTSTRUCT PaintStruct;
HANDLE hBitmap, hOldBitmap;
RECT Rect;
BITMAP Bitmap;

int WINAPI wWinMain(HINSTANCE hInstance, HINSTANCE h, LPSTR sxCmdLine, int nCmdShow)
{
	memset(&wc, 0, sizeof(WNDCLASS));
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wc.hInstance = hInstance;
	wc.lpfnWndProc = &funcWinProc;
	wc.lpszClassName = "MY_APP";//уникальное
	if (!RegisterClass(&wc))
	{
		return -1;
	}
	if (hWnd = CreateWindow(wc.lpszClassName, L"Demonstrator", WS_OVERLAPPEDWINDOW | WS_VISIBLE, 0, 0, 1600, 900, NULL, NULL, NULL, NULL))
	{
		while (GetMessage(&msg, NULL, NULL, NULL))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}
	return 0;
}

LRESULT CALLBACK funcWinProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	switch (uMsg)//в зависимости от полученного сообщения
	{
	case WM_COMMAND:
		switch (wParam)//здесь в зависимости от пункта меню
		{
		case 0:
			SendMessage(listbox, LB_RESETCONTENT, 0, 0);
			break;
		case 1:
			openDll1();
			break;
		case 2:
			executeDll1();
			break;
		case 3:
			getAuthorDll1();
			break;
		case 4:
			getDescriptionDll1();
			break;
		case 5:
			freeDll1();
			break;
		case 6:
			openDll2();
			break;
		case 7:
			executeDll2();
			break;
		case 8:
			getAuthorDll2();
			break;
		case 9:
			getDescriptionDll2();
			break;
		case 10:
			freeDll2();
			break;
		case 11:
			getNameModules();
			break;
		case 12:
			executeStat();
			break;
		case 13:
			getAuthorStat();
			break;
		case 14:
			getDescriptionStat();
			break;
		}
		break;
	
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
	case WM_CREATE:
		addControls(hWnd);
		addMenus(hWnd);
		break;
	default:
		return DefWindowProcW(hWnd, uMsg, wParam, lParam);
		break;
	}
}

void  addControls(HWND hWnd)
{
	listTXT=CreateWindow("static", "List of processes: ", WS_VISIBLE | WS_CHILD | WS_BORDER | SS_CENTER, 0, 100, 100, 50, hWnd, NULL, NULL, NULL);
	listbox = CreateWindow("listbox", "List_Box", WS_CHILD | WS_VISIBLE | WS_BORDER | WS_VSCROLL, 0, 150, 1500, 600, hWnd, NULL, NULL, NULL);
	ShowWindow(listbox, SW_HIDE);
	ShowWindow(listTXT, SW_HIDE);
}

void  addMenus(HWND hWnd)
{
	hMenuBar = CreateMenu();
	HMENU hM0 = CreateMenu();
	HMENU hM1 = CreateMenu();
	HMENU hM2 = CreateMenu();
	HMENU hMSt = CreateMenu();
	HMENU hMFile = CreateMenu();
	AppendMenu(hM0, MF_STRING, 0, "Clear listBox");
	AppendMenu(hM1, MF_STRING, 1, "Open Procdll");
	AppendMenu(hM1, MF_STRING, 2, "Execute Procdll");
	AppendMenu(hM1, MF_STRING, 3, "Get author");
	AppendMenu(hM1, MF_STRING, 4, "Get Description");
	AppendMenu(hM1, MF_STRING, 5, "Free dll");
	AppendMenu(hM2, MF_STRING, 6, "Open ImageDll");
	AppendMenu(hM2, MF_STRING, 7, "Execute ImageDll");
	AppendMenu(hM2, MF_STRING, 8, "Get author");
	AppendMenu(hM2, MF_STRING, 9, "Get Description");
	AppendMenu(hM2, MF_STRING, 10, "Free dll");
	AppendMenu(hMFile, MF_STRING, 11, "Get names of the modules");
	AppendMenu(hMSt, MF_STRING, 12, "Execute Static lib");
	AppendMenu(hMSt, MF_STRING, 13, "Get author");
	AppendMenu(hMSt, MF_STRING, 14, "Get Description");

	AppendMenu(hMenuBar, MF_POPUP, (UINT_PTR)hM0, "Edit");
	AppendMenu(hMenuBar, MF_POPUP, (UINT_PTR)hMFile, "File");
	AppendMenu(hMenuBar, MF_POPUP, (UINT_PTR)hM1, "Menu for 1 Dll");
	AppendMenu(hMenuBar, MF_POPUP, (UINT_PTR)hM2, "Menu for 2 Dll");
	AppendMenu(hMenuBar, MF_POPUP, (UINT_PTR)hMSt, "Menu for static library");
	SetMenu(hWnd, hMenuBar);
}

void getNameModules()
{
	if (hLib1 == NULL && hLib2 == NULL)
	{
		MessageBox(hWnd, "No connected dll modules and 1 connected static library: StaticLibExample", "Modules", MB_ICONERROR);
		return;
	}

	char buffer[256], buffer2[256];
	if (hLib2 == NULL)
	{
		GetModuleFileName(hLib1, buffer, 255);
		strcat(buffer,"\n static library: StaticLibExample");
		MessageBox(hWnd, buffer, "Modules", MB_OK);
		return;
	}
	
	if (hLib1 == NULL)
	{
		GetModuleFileName(hLib2, buffer, 255);
		strcat(buffer, "\n static library: StaticLibExample");
		MessageBox(hWnd, buffer, "Modules", MB_OK);
		return;
	}

	GetModuleFileName(hLib1, buffer, 255);
	GetModuleFileName(hLib2, buffer2, 255);

	strcat(buffer, "\n");
	strcat(buffer, buffer2);
	strcat(buffer, "\n static library: StaticLibExample");
	MessageBox(hWnd, buffer, "Modules", MB_OK);
}
void openDll1()
{
	hLib1 = LoadLibrary("Plugins/DllShowProc.dll");
	if (hLib1==NULL)
	{
		return;
	}
}

void executeDll1()
{
	if (hLib1 == NULL)
	{
		return;
	}
	InvalidateRect(hWnd, &Rect,TRUE);
	ShowWindow(listbox, SW_SHOW);
	ShowWindow(listTXT, SW_SHOW);
	VOID(*pFunction)(void* listbox);
	(FARPROC)pFunction = GetProcAddress(hLib1, "Execute");
	pFunction(listbox);
}

void getAuthorDll1()
{
	if (hLib1 == NULL)
	{
		return;
	}

	DWORD bufferSize = 12, pdwBytesWritten = 0;
	char buffer[12];

	BOOLEAN(*pFunction)(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);
	(FARPROC)pFunction = GetProcAddress(hLib1, "GetAuthor");
	pFunction(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Author", MB_OK);
}

void getDescriptionDll1()
{
	if (hLib1 == NULL)
	{
		return;
	}

	DWORD bufferSize = 65, pdwBytesWritten = 0;
	char buffer[65];
	
	BOOLEAN(*pFunction)(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);
	(FARPROC)pFunction = GetProcAddress(hLib1, "GetDescription");
	pFunction(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Description", MB_OK);
}

void freeDll1()
{
	FreeLibrary(hLib1);
	hLib1 = NULL;
	SendMessage(listbox, LB_RESETCONTENT, 0, 0);
	ShowWindow(listbox, SW_HIDE);
	ShowWindow(listTXT, SW_HIDE);
}


void openDll2()
{
	hLib2 = LoadLibrary("Plugins/ImageDll.dll");
	if (hLib2 == NULL)
	{
		return;
	}
}

void getAuthorDll2()
{
	if (hLib2 == NULL)
	{
		return;
	}

	DWORD bufferSize = 12, pdwBytesWritten = 0;
	char buffer[12];

	BOOLEAN(*pFunction)(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);
	(FARPROC)pFunction = GetProcAddress(hLib2, "GetAuthor");
	pFunction(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Author", MB_OK);
}

void getDescriptionDll2()
{
	if (hLib2 == NULL)
	{
		return;
	}

	DWORD bufferSize = 90, pdwBytesWritten = 0;
	char buffer[90];

	BOOLEAN(*pFunction)(LPSTR buffer, DWORD dwBufferSize, DWORD* pdwBytesWritten);
	(FARPROC)pFunction = GetProcAddress(hLib2, "GetDescription");
	pFunction(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Description", MB_OK);
}
void executeDll2()
{
	if (hLib2 == NULL)
	{
		return;
	}
	VOID(*pFunction)(void* hWnd,void* hDc, int startX, int startY, int width, int heigth);
	(FARPROC)pFunction = GetProcAddress(hLib2, "Execute");
	ShowWindow(listbox, SW_HIDE);
	ShowWindow(listTXT, SW_HIDE);
	hDC = GetDC(hWnd);
	GetClientRect(hWnd, &Rect);
	pFunction(hWnd,hDC,0,0,Rect.right,Rect.bottom);
}

void freeDll2()
{
	FreeLibrary(hLib2);
	hLib2 = NULL;
	InvalidateRect(hWnd, &Rect, TRUE);
}

void executeStat()
{
	Execute(hWnd);
}

void getAuthorStat()
{
	DWORD bufferSize = 12, pdwBytesWritten = 0;
	char buffer[12];

	GetAuthor(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Author", MB_OK);
}

void getDescriptionStat()
{
	DWORD bufferSize = 90, pdwBytesWritten = 0;
	char buffer[70];

	GetDescription(buffer, bufferSize, &pdwBytesWritten);
	MessageBox(hWnd, buffer, "Description", MB_OK);
}

