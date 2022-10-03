#include<bits/stdc++.h>
using namespace std;
/*
1 2 3
5 6 -1
7 8 4
1 2 3
5 8 6
-1 7 4
*/

struct path{
	vector<vector<int>> a;
};
typedef pair<int,pair<vector<vector<int>>,vector<path>>> state;
struct cmp{
	bool operator() (state p1,state p2)
	{
		return p1.first>p2.first;
	}
};
int score(vector<vector<int> > a ,vector<vector<int>> goal)
{
	int dist=0;
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			if(a[i][j]!=-1)
			{
				for(int k=0;k<3;k++)
				{
					for(int l=0;l<3;l++)
					{
						if(a[i][j]==goal[k][l])
							dist+=abs(i-k)+abs(j-l);
					}
				}
			}
		}
	}
	
	return dist;
}
// int score(vector<vector<int>> v1,vector<vector<int>> v2)
// {
// 	int x1,x2,y1,y2;
// 	for(int i=0;i<v1.size();i++)
// 	{
// 		for(int j=0;j<v1[i].size();j++)
// 		{
// 			if(v1[i][j]==-1)
// 			{
// 				x1 = i;
// 				y1 = j;
// 			}
// 			if(v2[i][j]==-1)
// 			{
// 				x2 = i;
// 				y2 = j;
// 			}
// 		}
// 	}
// 	return abs(x1-x2)+abs(y1-y2);
// }
int main(){
	int n,m;
	//cin>>n>>m;
	n = 3,m=3;
	cout<<"Enter Initial matrix"<<endl;
	vector<vector<int>> mat(n,vector<int>(m));
    vector<vector<int>> ans(n,vector<int>(m));
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			cin>>mat[i][j];
		}
	}
	cout<<"Enter Final matrix"<<endl;
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			cin>>ans[i][j];
		}
	}
	int dx[5] = {0,0,1,-1};
	int dy[5] = {-1,1,0,0};
	priority_queue<state,vector<state>,cmp> q;
	vector<path> p1;
	path p11;
	p11.a = mat;
	p1.push_back(p11);
	q.push(state(score(mat,ans),{mat,p1}));
	int i;
//	map<vector<path>,int> mp;
	while((i=q.size()))
	{
		//cout<<i<<endl;
		while(i--)
		{
			vector<vector<int>> v = q.top().second.first;
			vector<path> p = q.top().second.second;
			//mp[p] = 1;
			q.pop();
			int x,y;
			bool f= true;
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<m;j++)
				{
					if(v[i][j]==-1)
					{
					   x = i;
					   y = j;
					   break;	
					}
				}
			}
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<m;j++)
				{
					if(v[i][j]!=ans[i][j])
					{
					   f = false;	
					}
				}
			}
			if(f)
			{
				cout<<endl<<"Solution "<<endl;
				for(int i=0;i<p.size();i++)
				{
					cout<<"------------------------------"<<endl;
					for(auto j:p[i].a)
					{
						for(auto k:j)
						{
							cout<<k<<" ";
						}
						cout<<endl;
					}
					cout<<"----------------------------"<<endl;
				}
				exit(1);
			}
			for(int i=0;i<4;i++)
			{
				int x1 = x + dx[i];
				int y1 = y + dy[i];
				if(x1>=n||y1>=m||x1<0||y1<0)
				 continue;
				bool fv = false;
				
				swap(v[x][y],v[x1][y1]);
				path vp;
				
				vp.a = v;
				for(auto k:p)
				{
					bool it = false;
					for(int i=0;i<n;i++)
					{
						for(int j=0;j<m;j++)
						{
							if(v[i][j]!=k.a[i][j])
							 it = true;
						}
					}
					if(!it)
					{
						fv = true;
						break;
					}
				}
				p.push_back(vp);
				if(!fv)
				{
				  q.push(state(score(v,ans),{v,p}));
				}
				swap(v[x][y],v[x1][y1]);
				p.pop_back();
			}
		}
	}
}
