knowledge_base = {
    'Flu': ['muscle pain', 'cough', 'fever', 'runny nose', 'headaches', 'sore throat', 'fatigue'],
    'Tuberculosis': ['chest pain', 'cough', 'cough for three or more weeks', 'fever', 'fatigue', 'chills',
                     'loss of appetite', 'cough with blood or mucus'],
    'Coronavirus': ['loss of taste', 'loss of smell', 'sore throat', 'cough', 'fever', 'shortness of breath',
                    'chest pain']
}


def inference(symptoms):
    probability = {}
    for disease in knowledge_base.keys():
        count = 0
        for symptom in knowledge_base[disease]:
            if symptom in symptoms:
                count += 1
        probability[disease] = count / len(knowledge_base[disease])

    max_probability = 0
    for disease in probability.keys():
        max_probability = max(max_probability, probability[disease])

    diseases = []
    for disease in probability.keys():
        if probability[disease] == max_probability:
            diseases.append(disease)

    diagnosis = ', '.join(diseases)
    diagnosis += '.'

    if max_probability == 1:
        print('You are having ' + diagnosis)
    elif max_probability > 0:
        print('You may have ' + diagnosis)
    else:
        print('You are not having any disease.')


def ask_questions():
    symptoms_list = []
    questions = []
    for disease in knowledge_base.keys():
        questions += knowledge_base[disease]

    questions = list(set(questions))
    questions.sort()
    print('Please answer the following questions: ')
    for question in questions:
        answer = input(f'Do you have {question} ? [y/n] : ')
        if answer == 'y':
            symptoms_list.append(question)
    print('')
    return symptoms_list


symptoms = ask_questions()
inference(symptoms)
