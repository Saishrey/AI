#include <bits/stdc++.h>
using namespace std;

int fn;

struct cnode
{
    int cn;
    int pn;
    int gn;
    int hn;
};
typedef pair<cnode, int> state;
struct cmp
{
    bool operator()(state p1, state p2)
    {
        return p1.second > p2.second;
    }
};
priority_queue<state, vector<state>, cmp> open;
vector<cnode> node;
vector<state> open1;
vector<state> close;
vector<cnode> neighbor;

bool Belongs(int m, vector<state> n)
{
    for (int i = 0; i < n.size(); i++)
    {
        if (n[i].first.cn == m)
        {
            return true;
        }
    }
    return false;
}
void print(int sn,int en)
{
    int pn=-1000;
    for (int i = 0; i < close.size(); i++)
    {
        if (close[i].first.cn == sn)
        {
            pn = close[i].first.cn;
            cout << "The path is :" << endl;
            cout << close[i].first.cn;
            cout << " -->";
        }
        if (close[i].first.pn == pn)
        {
            if (close[i].first.cn == en)
            {
                cout << close[i].first.cn;
            }
            else
            {
                cout << close[i].first.cn;
                cout << " -->";
                pn = close[i].first.cn;
            }
        }
    }
}
void moveGen(int n)
{

    for (int i = 0; i < node.size(); i++)
    {
        if (node[i].pn == n)
        {
            neighbor.push_back(node[i]);
        }
    }
}
int k(int n, int m)
{
    for (int i = 0; i < node.size(); i++)
    {
        if (node[i].cn == m)
        {

            if (node[i].pn == n)
            {
                return node[i].gn;
            }
        }
    }
    return 0;
}
void propagateImprovement(cnode m)
{
    int newG;
    moveGen(m.cn);

    for (int s = 0; s < neighbor.size(); s++)
    {
        if (neighbor[s].pn == m.cn)
        {
            newG = m.gn + k(m.cn, neighbor[s].cn);
            if (newG < neighbor[s].gn)
            {

                if (Belongs(neighbor[s].cn, close))
                {
                    for (int i = 0; i < close.size(); i++)
                        {
                            if (close[i].first.cn == neighbor[s].cn)
                            {
                                close[i].first.pn = m.cn;
                                close[i].first.gn = newG;
                                close[i].second = close[i].first.gn + neighbor[s].hn;
                            }
                        }
                    propagateImprovement(neighbor[s]);
                }
            }
        }
    }
}

int Astar(vector<cnode> node, int sn, int en)
{
    state n;
    int topN;
    for (int i = 0; i < node.size(); i++)
    {
        if (node[i].cn == sn)
        {

            fn = node[i].hn;
            node[i].pn = -1;
            node[i].gn = 0;
            open.push({node[i], fn});
            open1.push_back({node[i], fn});
        }
    }
    close.clear();

    while (!open.empty())
    {

        n = open.top();

        open.pop();
        for (int i = 0; i < open1.size(); i++)
        {
            if (open1[i].first.cn == n.first.cn)
            {
                topN = i;
            }
        }
        open1.erase(open1.begin() + topN);

        close.push_back(n);
        if (n.first.cn == en)
        {

            print(sn,en);
            return 0;
        }

        moveGen(n.first.cn);
        for (int m = 0; m < neighbor.size(); m++)
        {
            if (neighbor[m].pn == n.first.cn)
            {
                if (!(Belongs(neighbor[m].cn, open1) || Belongs(neighbor[m].cn, close)))
                {
                    neighbor[m].pn = n.first.cn;
                    neighbor[m].gn = n.first.gn + k(n.first.cn, neighbor[m].cn);
                    fn = neighbor[m].gn + neighbor[m].hn;
                    open.push({neighbor[m], fn});
                    open1.push_back({neighbor[m], fn});
                }
                if (Belongs(neighbor[m].cn, open1))
                {
                    if ((n.first.gn + k(n.first.cn, neighbor[m].cn)) < neighbor[m].gn)
                    {
                        neighbor[m].pn = n.first.cn;
                        neighbor[m].gn = n.first.gn + k(n.first.cn, neighbor[m].cn);
                        fn = neighbor[m].gn + neighbor[m].hn;
                        open.push({neighbor[m], fn});
                        open1.push_back({neighbor[m], fn});
                    }
                }
                if (Belongs(neighbor[m].cn, close))
                {
                    if ((n.first.gn + k(n.first.cn, neighbor[m].cn)) < neighbor[m].gn)
                    {

                        for (int i = 0; i < close.size(); i++)
                        {
                            if (close[i].first.cn == neighbor[m].cn)
                            {
                                close[i].first.pn = n.first.cn;
                                close[i].first.gn = n.first.gn + k(n.first.cn, neighbor[m].cn);
                                close[i].second = close[i].first.gn + neighbor[m].hn;
                            }
                        }

                        propagateImprovement(neighbor[m]);
                    }
                }
            }
        }
    }
    return -1;
}

int main()
{
    int cn, pn, hn, gn, en, sn, fn;
    bool in;

    int j = 111;
    int k = 111;

    do
    {
        cout << "Enter the following properties of node" << endl;
        cout << "Enter the current node: ";
        cin >> cn;
        cout << endl
             << "Enter the parent node: ";
        cin >> pn;
        cout << endl
             << "Enter the heuristic value of node:";
        cin >> hn;
        cout << endl
             << "Enter the cost of path: ";
        cin >> gn;
        cout << endl;
        node.push_back({cn, pn, gn, hn});

        cout << "Do you want to add more nodes(1 -> yes or 0 -> no):" << endl;
        cin >> in;
    } while (in);

start:
    cout << "Enter the start node:" << endl;
    cin >> sn;

    for (int i = 0; i < node.size(); i++)
    {
        if (node[i].cn == sn)
        {

            j = i;
        }
    }
    if (j == 111)
    {
        cout << "Entered start node is not valid" << endl;
        goto start;
    }

end:
    cout << "Enter the goal node:" << endl;
    cin >> en;
    for (int i = 0; i < node.size(); i++)
    {
        if (node[i].cn == en)
        {
            k = i;
        }
    }
    if (k == 111)
    {
        cout << "Entered goal node is not valid" << endl;
        goto end;
    }

    return Astar(node, sn, en);
}
