#include<iostream>
#include<queue>
using namespace std;
int m,c,k;//m missionaries, n cannibals, there can be k people on board
int xx[]={0,0,1,1,2};//The number of people who can board each time
int yy[]={1,2,0,1,0};
struct zt{
  int lm;
  int lc;
  int rm;
  int rc;
  int ship;
  int top;
};
queue<zt>open;
struct zt temp[1000];
int index=0;
int Isdangerous(zt temp)
{
    if((temp.lm>=temp.lc&&temp.rm>=temp.rc&&temp.lm>=0&&temp.lc>=0&&temp.rm>=0&&temp.rc>=0)||
       (temp.lm>=0&&temp.lc>=0&&temp.rm>=0&&temp.rc>=0&&temp.lm==0&&temp.rm>=temp.rc)||
       (temp.lm>=0&&temp.lc>=0&&temp.rm>=0&&temp.rc>=0&&temp.lm>=temp.lc&&temp.rm==0))
    {
        return 1;
    }
    return 0;
}
int pd(zt p,int top)
{
    for(int i=0;i<top;i++)
    {
        if(temp[i].lm==p.lm&&temp[i].lc==p.lc&&temp[i].rm==p.rm&&temp[i].rc==p.rc&&temp[i].ship==p.ship)
         {
             return 0;
         }
    }

    return 1;
}
void bfS()
{

    while(!open.empty())
    {
         zt temp1=open.front();
         int x1=temp1.lm;
         int y1=temp1.lc;
         int x2=temp1.rm;
         int y2=temp1.rc;

         for(int i=0;i<5;i++)
         {

             if(temp1.ship==1){
                    zt temp2;
                    temp2.lm=x1-xx[i];
                    temp2.lc=y1-yy[i];
                    temp2.rm=x2+xx[i];
                    temp2.rc=y2+yy[i];
                    temp2.ship=-temp1.ship;
                    temp2.top=temp1.top+1;

                      if(temp2.lm==0&&temp2.lc==0&&temp2.rm==3&&temp2.rc==3)
                     {

                         goto loop;
                     }
                    if(Isdangerous(temp2)==1&&pd(temp2,index)==1)
                    {

                        temp[index++]=temp2;
                        open.push(temp2);

                    }
             }
             if(temp1.ship==-1){
                 zt temp2;
                    temp2.lm=x1+xx[i];
                    temp2.lc=y1+yy[i];
                    temp2.rm=x2-xx[i];
                    temp2.rc=y2-yy[i];
                    temp2.ship=-temp1.ship;
                    temp2.top=temp1.top+1;
                    if(Isdangerous(temp2)==1&&pd(temp2,index)==1)
                    {
                        temp[index++]=temp2;
                        open.push(temp2);
                    }
             }
         }
        open.pop();
    }
loop:;
}
int main()
{
    zt p;
    p.lm=3;
    p.lc=3;
    p.rm=0;
    p.rc=0;
    p.ship=1;
    p.top=0;
    open.push(p);
    temp[index++]=p;
    bfS();
    cout<<"Initial state:"<<3<<' '<<3<<' '<<0<<' '<<0<<endl;
    for(int i=3;i<index-1;i++)
    {
        if(temp[i].ship==1)
        {
            cout<<"B----->A:";
            cout<<temp[i].lm<<' '<<temp[i].lc<<' '<<temp[i].rm<<' '<<temp[i].rc<<endl;
        }
         if(temp[i].ship==-1)
        {
            cout<<"A----->B:";
            cout<<temp[i].lm<<' '<<temp[i].lc<<' '<<temp[i].rm<<' '<<temp[i].rc<<endl;
        }

    }
    cout<<"Target Status:"<<0<<' '<<0<<' '<<3<<' '<<3<<endl;
    return 0;
}

