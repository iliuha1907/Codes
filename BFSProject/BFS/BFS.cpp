// BFS.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include "pch.h"
#include <iostream>
#include<fstream>
#include<list>
#include<vector>

using namespace std;
int k = 1;
int* visited;
int* results;
vector<vector<int>> graf;
list<int> tempDots;

void go(int v)
{
	for (int i = 0; i < graf[v].size(); i++)
	{
		int dest = graf[v][i];
		if (visited[dest] == 0)
		{
			tempDots.push_back(dest);
			visited[dest] = 1;
		}
	}
}

int main()
{
	ifstream fin("input.txt");
	ofstream fout("output.txt");
	int n;
	fin >> n;
	graf.resize(n);
	visited = new int[n];
	results = new int[n];

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			int buf;
			fin >> buf;
			if (buf == 1)
			{
				graf[i].push_back(j);
			}
		}
		visited[i] = 0;
	}
	
	for (int i = 0; i < n; i++)
	{
		if (visited[i] == 0)
		{
			visited[i] = 1;
			tempDots.push_back(i);
			while (tempDots.size())
			{
				int v = *(tempDots.begin());
				tempDots.erase(tempDots.begin());
				results[v] = k++;
				go(v);
			}
		}
	}

	for (int i = 0; i < n; i++)
	{
		fout << results[i] << " ";
	}

	fin.close();
	fout.close();
	system("pause");
	return 0;
}

// Запуск программы: CTRL+F5 или меню "Отладка" > "Запуск без отладки"
// Отладка программы: F5 или меню "Отладка" > "Запустить отладку"

// Советы по началу работы 
//   1. В окне обозревателя решений можно добавлять файлы и управлять ими.
//   2. В окне Team Explorer можно подключиться к системе управления версиями.
//   3. В окне "Выходные данные" можно просматривать выходные данные сборки и другие сообщения.
//   4. В окне "Список ошибок" можно просматривать ошибки.
//   5. Последовательно выберите пункты меню "Проект" > "Добавить новый элемент", чтобы создать файлы кода, или "Проект" > "Добавить существующий элемент", чтобы добавить в проект существующие файлы кода.
//   6. Чтобы снова открыть этот проект позже, выберите пункты меню "Файл" > "Открыть" > "Проект" и выберите SLN-файл.
