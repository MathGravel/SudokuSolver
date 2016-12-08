%define name      	jsudokucreator
%define version   	1.1
%define installdir	%{_datadir}/%{name}

Name:				%{name}
Version:			%{version}
Release:			0
Source:				%{name}-%{version}.tar.gz
BuildArch: 			noarch
Packager:   		Reiner Pr�ls <jsudokucreator@googlemail.com>
BuildRequires: 		desktop-file-utils
Group:				Amusements/Games/Board/Puzzle
License:			GPL
URL:				http://www.mitglied.lycos.de/jsudokucreator
BuildRoot:			%{_tmppath}/%{name}-%{version}-root

Summary:			Program for creating, solving, storing and printing Sudokus
Summary(de):		Programm zum Erzeugen, L�sen, Speichern und Drucken von Sudokus

%description
JSudokuCreator is a Java application for creating, solving and printing Sudokus. JSudokuCreator is an Open Source project released under the GPL.
JSudokuCreator can create, solve and printout Sudokus. It can also save Sudokus in files an load them from files.
At the moment JSudokuCreator is localized in English and German.
A short online help is available.

%description -l de
JSudokuCreator ist ein Java Programm zum Erzeugen, L�sen und Drucken von Sudokus. JSudokuCreator ist ein Open Source Projekt das unter der GPL ver�ffentlicht wird.
JSudokuCreator kann Sudokus erzeugen, l�sen, ausdrucken und in Dateien speichern und von Dateine laden.
Zur Zeit ist JSudokuCreator in Deutsch und Englisch �bersetzt.
Eine kurze Onlinehilfe ist verf�gbar

%prep

%setup

%build
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

%install
%{__mkdir_p} %{buildroot}%{installdir}
%{__mkdir_p} %{buildroot}%{_datadir}/pixmaps
%{__mkdir_p} %{buildroot}%{_datadir}/applications

%{__cp} -a . %{buildroot}%{installdir}

%{__install} -m 644 %{name}.png %{buildroot}%{_datadir}/pixmaps/%{name}.png

cat > %{name}.desktop <<EOF
[Desktop Entry]
Encoding=UTF-8
Name=JSudokuCreator
Type=Application
Comment=Launches JSudokuCreator
GenericName=Java Sudoku Creator
Comment[de]=Java Sudoku Creator
GenericName[de]=Java Sudoku Creator
Exec=%{installdir}/jstart
Icon=%{name}
Terminal=false
EOF

desktop-file-install --vendor= \
                     --dir=%{buildroot}%{_datadir}/applications \
                     --add-category=Game \
                     --add-category=LogicGame \
                     %{name}.desktop

rm -f %{name}.desktop

%post 
%{__ln_s} -f %{installdir}/jstart %{_bindir}/jsudokucreator
 
%preun
rm -f %{_bindir}/jsudokucreator

%postun

%files
%defattr(-,root,root)
%{installdir}
%{_datadir}/applications/%{name}.desktop
%{_datadir}/pixmaps/%{name}.png

%clean
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

%changelog
* Sun Nov 23 2008 Reiner Pr�ls
- small changes

* Fri Dec 28 2007 Reiner Pr�ls
- first version
