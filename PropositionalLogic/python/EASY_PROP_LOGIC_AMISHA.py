# (a+!b)*c

import itertools

def FindInputs(ex):
    inputs = []
    for c in ex:
        if c!='*' and c!='+' and c!='!' and c!='(' and c!=')' and c!=' ' and c not in inputs:
            inputs.append(c)
    return inputs

def evaluate(ex, row):
    input_no = 0
    replaced = []
    for c in ex:
        if c!='*' and c!='+' and c!='!' and c!='(' and c!=')' and c!=' ' and c not in replaced :
            # print(f'ex = ex.replace({c}, {str(row[input_no])})')
            ex = ex.replace(c, str(row[input_no]))
            input_no += 1
            replaced.append(c)
    ex = ex.replace('*','&')
    ex = ex.replace('+','|')
    ex = ex.replace('!','~')
    print(ex) 
    result = eval(ex)
    return result

def compute_results(truthtable, ex):
    outputs=[]
    for row in truth_table:
        outputs.append(evaluate(ex, row))
    return outputs

print('Rules: ')
print('1) To enter "AND" use "*"')
print('2) To enter "OR" use "+"')
print('3) To enter "NOT" use "!"')

n = int(input('Enter number of inputs: '))
expr = input('Enter the logical expression: ')

# all possible combinations of 0 1 for size n
# generates tuples of size n
truth_table = list(itertools.product([0,1],repeat=n)) 

table_outputs = compute_results(truth_table, expr)

print('')
print('Truth Table:')
for c in FindInputs(expr):
    print(c,end='  ')
print('Output')

for i in range(len(truth_table)):
    for val in truth_table[i]:
        print(val,end='  ')
    print('  ',table_outputs[i])
print('')

if table_outputs.count(1)>=1:
    print('The entered propositional logic expression is Satisfiable')
else:
    print('The entered propositional logic expression is Not Satisfiable')
