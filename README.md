
#x7g0p Project

##An application track user's body information and exercise.

&nbsp;&nbsp;&nbsp;&nbsp;The Project will be used to help people **lose weight and track their body information**.

&nbsp;&nbsp;&nbsp;&nbsp;The target audience will be people who want to lose weight or who want to track their workouts.

&nbsp;&nbsp;&nbsp;&nbsp;The reason why I want to do this project is that I am now keeping fit, and this program can help me.



##User Story
- As a user, I want to add my information in ListOfUser
- As a user, I want to add my name
- As a user, I want to add my sex
- As a user, I want to add my weight
- As a user, I want to add my height
- As a user, I want to add my exercised time per week
- As a user, I want to have my own id


- As a user, I want to remove me from ListOfUser
- As a user, I want to find back my id when I forget that
- As a user, I want to know my BMI
- As a user, I want to know my BMR


- As a user, I want to save my information to system
- As a user, I want to load information from system


##Admin Story
- As an admin, I want to log in admin panel with password
- As an admin, I want to know all user's information
- As an admin, I want to change user's information


##Phase 4: Task 2

###Event Log

- Tue Mar 29 16:45:33 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:33 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:39 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:39 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:39 PDT 2022
- Id 1 exist in System
- Tue Mar 29 16:45:41 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:41 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:41 PDT 2022
- Id 21 Not exist in System
- Tue Mar 29 16:45:41 PDT 2022
- Add user with ID: 21 Success
- Tue Mar 29 16:45:45 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:45 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:45 PDT 2022
- Add user with ID: 21 Success
- Tue Mar 29 16:45:45 PDT 2022
- Id 22 Not exist in System
- Tue Mar 29 16:45:48 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:48 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:48 PDT 2022
- Add user with ID: 21 Success
- Tue Mar 29 16:45:48 PDT 2022
- Id 21 exist in System
- Tue Mar 29 16:45:49 PDT 2022
- Remove user with ID: 21 Success
- Tue Mar 29 16:45:55 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:45:55 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:45:55 PDT 2022
- Find user with name: tester Success
- Tue Mar 29 16:45:55 PDT 2022
- Find user with name: tester Success
- Tue Mar 29 16:46:00 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:46:00 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:46:00 PDT 2022
- Calculate User with ID: 1 BMI is 21
- Tue Mar 29 16:46:07 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:46:07 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:46:07 PDT 2022
- Calculate User with ID: 2 BMI Fail
- Tue Mar 29 16:46:09 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:46:09 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:46:11 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:46:11 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:46:11 PDT 2022
- Calculate User with ID: 12 BMR Fail
- Tue Mar 29 16:46:14 PDT 2022
- Add user with ID: 0 Success
- Tue Mar 29 16:46:14 PDT 2022
- Add user with ID: 1 Success
- Tue Mar 29 16:46:14 PDT 2022
- Calculate User with ID: 1 BMR is 1148

###Description
The reason there is a lot of duplication in the log is that every time the user opens a new panel, the TempFIle are loaded back into ListFfUser


##Phase 4: Task 3
- &nbsp;&nbsp;&nbsp;&nbsp; I will construct more interface, in that case I can delete some duplication
- I will change the all user panel read and write file. Now I use a temp file for each panel, which will
generate many duplications.