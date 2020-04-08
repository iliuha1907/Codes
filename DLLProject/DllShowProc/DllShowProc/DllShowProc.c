#pragma warning(disable : 4996)
#include "stdafx.h"
#include "DllShowProc.h"
#include<Windows.h>
#include <tlhelp32.h>
#include <stdio.h> 
#include <Winnt.h> 

const char* author = "Ilya Huzei";
const char* description = "This is a DLL that sends list of processes to the given listbox";
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
VOID Execute(void* ptrListbox)
{
	HWND listbox = (HWND)ptrListbox;
	HANDLE hProcessSnap;
	PROCESSENTRY32 pe32;
	wchar_t buf[1000];
	int i = 0;//������� ��� ������ ��������

	//�������� ������ ���� ���������
	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (hProcessSnap == INVALID_HANDLE_VALUE)
	{
		SendMessageW(listbox, LB_ADDSTRING, 0, (LPARAM)"Can`t get processes");
		CloseHandle(hProcessSnap);
		return;
	}

	//����� ������� �� ����������� ����� ���������� ������ ����
	pe32.dwSize = sizeof(PROCESSENTRY32);


	//�������� � ������ ���������
	if (!Process32First(hProcessSnap, &pe32))//������,���� ������� ��������. ����� � �������� � �������
	{
		SendMessageW(listbox, LB_ADDSTRING, 0, (LPARAM)L"Can`t get first process");
		CloseHandle(hProcessSnap);
		return;
	}
	int n = pe32.cntThreads;
	wsprintf(buf, L"%d  %s id:%d, ParentID:%d, Priority:%d, Number of Threads:%d \n", ++i, (wchar_t*)pe32.szExeFile, (wchar_t*)pe32.th32ProcessID,
		(wchar_t*)pe32.th32ParentProcessID, (wchar_t*)pe32.pcPriClassBase, (wchar_t*)pe32.cntThreads);

	SendMessageW(listbox, LB_ADDSTRING, 0, (LPARAM)buf);
	//�������� ������ �������
	

	
	//���� �� ��������� ������
	while (Process32Next(hProcessSnap, &pe32))
	{
		wsprintf(buf, L"%d  %s id:%d, ParentID:%d, Priority:%d, Number of Threads:%d \n", ++i, (wchar_t*)pe32.szExeFile, (wchar_t*)pe32.th32ProcessID,
			(wchar_t*)pe32.th32ParentProcessID, (wchar_t*)pe32.pcPriClassBase, (wchar_t*)pe32.cntThreads);
	    SendMessageW(listbox, LB_ADDSTRING, 0, (LPARAM)buf);
	}
	//��������� ������ � Handle
	CloseHandle(hProcessSnap);
	
	SendMessageW(listbox, LB_ADDSTRING, 0, (LPARAM)L"********************************");
	//printf("Hello");
}