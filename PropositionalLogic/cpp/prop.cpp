#include<iostream>
#include<string>
#include<unordered_map>
#include<stack>
#include<vector>
using namespace std;


int priority(char symbol) {
    switch(symbol) {
        case '(':
            return 0;
        case '+':
            return 1;
        case '*':
            return 2;
        default:
            return 0;
    }
    return 0;
}

int getval(int a, int b, char op){
    switch(op){
        case '+': return a + b;
        case '*': return a * b;
    }
    return 0;
}

bool evalpost(string infix, string s) {
    unordered_map<char, int> mp;
    int j = 0;
    for(int i = 0; i < infix.length(); i++) {
        if(isalpha(infix[i])) {
            if(mp.find(infix[i]) == mp.end()) {
                mp[infix[i]]=s[j++]-'0';
            } 
        }
    }

    stack<int> val;
    stack<char> ops;

    for(int i=0; i<infix.length(); i++) {
        if(infix[i] == ' ' || infix[i] == '~') {
            continue;
        }
        else if(infix[i] == '(') {
            ops.push(infix[i]);
        }
        else if(isalpha(infix[i])) {
            //cout<<infix[i]<<" ";
            if(i > 0 && infix[i-1] == '~') {
                val.push((1-mp[infix[i]]));
            }
            else {
                val.push(mp[infix[i]]);
            }
        }
        else if(infix[i]==')') {
            while(!ops.empty() && ops.top() != '(') {
                int a=val.top();
                val.pop();

                int b=val.top();
                val.pop();

                char op=ops.top();
                ops.pop();

                val.push(getval(b, a, op));
            }

            if(!ops.empty()) {
                ops.pop();
            }
        }
        else {
            while(!ops.empty() && priority(ops.top()) >= priority(infix[i])) {
                int a=val.top();
                val.pop();

                int b=val.top();
                val.pop();

                char op=ops.top();
                ops.pop();

                val.push(getval(b, a, op));
            }
            ops.push(infix[i]);
        }
    }

    while(!ops.empty()) {
        int a=val.top();
        val.pop();

        int b=val.top();
        val.pop();

        char op=ops.top();
        ops.pop();

        val.push(getval(b, a, op));
    }

    return val.top();
}

int printTable(string s, vector<string>& posb) {
    int c = 0;
    for(int i = 0; i < posb.size(); i++) {
        for(int j=0; j < posb[i].size(); j++) {
            if(posb[i][j]=='0') {
                cout<<"False\t";
            }
            else {
                cout<<"True\t";
            }
        }

        if(evalpost(s, posb[i])) {
            c++;
            cout<<"True\n";
        }
        else {
            cout<<"False\n";
        }
    }
    return c;
}

void gen(string t, int i, int n, vector<string>& posb) {
    if(i == n) {
        posb.push_back(t);
        return;
    }
    t.push_back('0');
    gen(t, i+1, n, posb);
    t.pop_back();

    t.push_back('1');
    gen(t, i+1, n, posb);
    t.pop_back();
}

int main() {
    int n = 0, result = 0;
    string infix;
    cout << "Enter number of variables: ";
    cin >> n;
    cout << endl;
    
    string t;
    
    vector<string> posb;
    
    gen(t, 0, n, posb);
    cout << "(Use + for OR, * for AND, ~for NOT" << endl;
    cout << "Enter the wff: ";
    //getline(cin,infix);
    
    cin >> infix;
    result = printTable(infix,posb);
    
    if(result) {
        cout << "\n=>The wff is valid.";
    }
    else {
        cout << "\n=>The wff is satisfiable.";
    }

    return 0;
}