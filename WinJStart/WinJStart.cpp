/*
 *  WinJStart.cpp
 *
 *  Created on 8. May 2006, 18:35
 *
 *  Copyright (C) 8. May 2006  <Reiner>

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

// WinJStart.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <winerror.h>

int APIENTRY WinMain(HINSTANCE hInstance, HINSTANCE  hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
	STARTUPINFO si;
	PROCESS_INFORMATION pi;

	ZeroMemory(&si, sizeof(STARTUPINFO));
	ZeroMemory(&si, sizeof(PROCESS_INFORMATION));
	si.cb = sizeof(si);
	si.wShowWindow = SW_HIDE;
	si.dwFlags = STARTF_USESHOWWINDOW;

	TCHAR cmd[8192], buf[_MAX_PATH];
	TCHAR drive[_MAX_DRIVE], dir[_MAX_DIR], name[_MAX_FNAME];

	GetModuleFileName(NULL, buf, _MAX_PATH);
	_splitpath(buf, drive, dir, name, NULL);
	lstrcpy(buf, "\"");
	lstrcat(buf, drive);
	lstrcat(buf, dir);
	lstrcat(buf, name);
	lstrcat(buf, _T(".jar"));
	lstrcat(buf, "\"");

	lstrcpy(cmd, "\"java.exe\" -jar");
	lstrcat(cmd, _T(" "));
	lstrcat(cmd, buf);

	if (CreateProcess(NULL, cmd, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi))
	{
		CloseHandle(pi.hProcess);
		CloseHandle(pi.hThread);
	}
	else
	{
		if (GetLastError() == ERROR_FILE_NOT_FOUND)
		{
			MessageBox(NULL, _T("You must install the Java Runtime from Sum Microsystems Inc.!\nYou can find it under http://www.java.com"), _T("Java Runtime missing"), MB_ICONSTOP);
		}
	}
	
	return 0;
}

