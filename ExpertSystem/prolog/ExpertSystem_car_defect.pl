
go:-
defect_may_be(Defect),
write('It is suggested that the car has '),
write(Defect),write(' problem.'),
nl,
clear_stored_answers;
write('Sorry, the system is unable to identify the defect'),nl,clear_stored_answers.



defect_may_be(drained_battery) :-
   user_says(starter_was_ok,no),
   user_says(starter_is_ok,yes),
   user_says(fuel_is_ok,yes),
   nl,
   write('Your attempts to start the car have run down the battery.'),nl,
   write('Recharging or jump-starting will be necessary.'),nl,
   write('But there is probably nothing wrong with the battery itself.'),nl,!.

defect_may_be(wrong_gear) :-
   user_says(starter_was_ok,yes),
   user_says(starter_is_ok,yes),
   user_says(fuel_is_ok,yes),
   nl,
   write('Check that the gearshift is set to Park or Neutral.'),nl,
   write('Try jiggling the gearshift lever.'),nl,!.

defect_may_be(starting_system) :-
   user_says(starter_was_ok,no),
   user_says(starter_is_ok,no),
   user_says(fuel_is_ok,yes),
   nl,
   write('Check for a defective battery, voltage'),nl,
   write('regulator, or alternator; if any of these is'),nl,
   write('the problem, charging the battery or jump-'),nl,
   write('starting may get the car going temporarily.'),nl,
   write('Or the starter itself may be defective.'),nl,!.


defect_may_be(fuel_system) :-
   user_says(starter_was_ok,yes),
   user_says(starter_is_ok,no),
   user_says(fuel_is_ok,no),
   nl,
   write('Check whether there is fuel in the tank.'),nl,
   write('If so, check for a clogged fuel line or filter'),nl,
   write('or a defective fuel pump.'),nl,!.

defect_may_be(ignition_system) :-
   user_says(starter_was_ok,yes),
   user_says(starter_is_ok,no),
   user_says(fuel_is_ok,yes),
   nl,
   write('Check the spark plugs, cables, distributor,'),nl,
   write('coil, and other parts of the ignition system.'),nl,
   write('If any of these are visibly defective or long'),nl,
   write('overdue for replacement, replace them; if this'),nl,
   write('does not solve the problem, consult a mechanic.'),nl,!.



ask_question(starter_was_ok) :-
   write('When you first started trying to start the car,'),nl,
   write('did the starter crank the engine normally? '),nl.

ask_question(starter_is_ok) :-
   write('Does the starter crank the engine normally now? '),nl.
ask_question(fuel_is_ok) :-
   write('Look in the carburetor.  Can you see or smell gasoline?'),nl.
   
%
% Case knowledge base
%   (information supplied by the user during the consultation)
%

:- dynamic(stored_answer/2).

   % (Clauses get added as user answers questions.)


%
% Procedure to get rid of the stored answers
% without abolishing the dynamic declaration


clear_stored_answers :- retract(stored_answer(_,_)),fail.
clear_stored_answers.


%
% Procedure to retrieve the users answer to each question when needed,
% or ask the question if it has not already been asked
%

user_says(Q,A) :- stored_answer(Q,A).

user_says(Q,A) :- \+ stored_answer(Q,_),
                  nl,nl,
                  ask_question(Q),
                  read(Response),
                  asserta(stored_answer(Q,Response)),
                  Response = A.



   
