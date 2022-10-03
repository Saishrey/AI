#include <bits/stdc++.h>
using namespace std;

int N = 8;
vector<int> v(8);
int steps = 0;

typedef pair<int, vector<int>> state;
struct cmp
{
    bool operator()(state p1, state p2)
    {
        return p1.first > p2.first;
    }
};

priority_queue<state, vector<state>, cmp> q;

bool sameDiagonal(int, int, int, int);
bool canAttackQueen(int, int, int, int);
int canAttackExistingQueens(int index, int col, int attacks, vector<int> a);

void print();

void print(vector<int> v4)
{

    cout << "   The positions of queens are :" << endl;
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
        {
            if (v4[i] == j)
            {
                cout << "Q ";
            }
            else
            {
                cout << "* ";
            }
        }
        cout << endl;
    }
}

/*bool sameCol(int col1, int col2)
{
    return col1 == col2;
}*/

bool sameDiagonal(int row1, int col1, int row2, int col2)
{
    return col1 - col2 == row1 - row2 || col1 - col2 == row2 - row1;
}

bool canAttackQueen(int row1, int col1, int row2, int col2)
{
    sameDiagonal(row1, col1, row2, col2);
}

int canAttackExistingQueens(int index, int col, int attacks, vector<int> a)
{
    for (int i = 0; i < index; i++)
    {
        attacks += canAttackQueen(index, col, i, a[i]);
    }
    for (int i = index + 1; i < N; i++)
    {
        attacks += canAttackQueen(index, col, i, a[i]);
    }
    return attacks;
}
int score(vector<int> a)
{
    int attacks = 0;

    for (int k = 0; k < N; k++)
    {
        int col = a[k];
        attacks += canAttackExistingQueens(k, col, attacks, a);
    }

    return attacks;
}
vector<state> path;
bool isDuplicate(state elem)
{
    bool result = false;
    if (find(path.begin(), path.end(), elem) != path.end())
    {
        result = true;
        return result;
    }

    return result;
}

int main()
{
    cout << "Enter the column positions of 8 queens:" << endl;

    for (int j = 0; j < N; j++)
    {
        cin >> v[j];
    }

    vector<int> vp = v;
    q.push(state(score(vp), {vp}));
    while (true)
    {
        int cost;

        while (isDuplicate(q.top()))
        {
            q.pop();
        }

        path.push_back(q.top());
        cost = q.top().first;
        vp = q.top().second;
        q.pop();

        steps++;
        cout << "# " << steps;
        print(vp);
        if (cost == 0)
        {
            cout << "    Solved!!!!!";
            break;
        }
        for (int i = 1; i < N; i++)
        {
            vector<int> v1 = vp;
            int temp = v1[0];
            v1[0] = v1[i];
            v1[i] = temp;

            q.push(state(score(v1), {v1}));
            v1.clear();
        }
    }
}
