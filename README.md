# Banking System

This is Banking system program.

Program backend consist of 5 package (accounts, accounts.service, dao.implementations, dao.interfaces. transactions. users) and several txt files.
Teller controll overall program through DAO.
User can open and close several kinds of account, deposit and withdraw.

1. Teller login(currently I have not set id and pw. so just press Sign in button).

2. After teller login, put the user's account number(1 or 2 or 3) and Submit button.
   If you want to add new user press Add New Customer button.

3. If Add New Customer button was pressed several blank for information will show up.
   all of them should be filled. If is not error will happen when Submit button is pressed.

4. If you put correct account number and press Submit button, you can move to account page which show you current blance.
   In this page you can do 5 things.
  	
 	1. Deposit  (put the money you want to deposit into blank and press Deposit button)
	   new balance will show up immediately.
	2. Withdraw (put the money you want to deposit into blank and press Withdraw button)
                new balance will show up immediately.
	3. Close account
	4. Show account list of the user.
	5. Add new account

5. If Cancle button is pressed, it brings you to first page.
