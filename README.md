# Final Project

## Overview
In teams of 3-4 members, enhance the hw2/react fullstack template to add 1 new feature per team member. Each team member to add 1 new additional feature. Coordinate so that the new features fit around a theme, and 2 people don't add the same feature. Large features can be split up between members. For example a contacts list feature may be composed of 2 endpoints, 1 to add a contact, and 1 to list contacts.

Please avoid force push (git push -f)! It can delete other student's commits.

Sample ideas:
- Contacts list
- Credit feature
- Send direct message
- public feed

### Requirements
- Add 1 new endpoint
- Modify front end to use new endpoint
- Merge all code to main branch
- Each member must submit commits for their own feature from their own account.

Each team member will have their own score.

### Scoring
- Does Feature work
- Are edge cases handled? (Think of what happens in common edge cases)
- Is work merged to main branch

## Team [Final Project Group]
- Grishma Thumar
     Feature: Fixed Deposit
      Backend:
        Implemented a handler file (FixedDepositHandler) to calculate and return the maturity amount using compound interest logic.
        Added logic to validate inputs like the amount and duration (in years).
        Stored fixed deposit details in the database using DAO and DTO classes.
      Frontend:
        Developed a user interface for the Fixed Deposit feature.
        Created a form to take inputs: Amount and Duration (Years).
        Integrated the backend API to display the maturity amount dynamically on the screen.

- Devarsh Hirpara
      Feature: Currency Conversion   
        Backend:
          Created the CurrencyConversionHandler to perform currency conversion based on predefined exchange rates.
          Ensured clean API response containing converted values for various currencies (INR, GBP, EUR, CNY, etc.).
        
      Frontend:
         Designed a user-friendly form to input the USD amount for conversion.
         Integrated the backend API to fetch and display the converted values for each currency.
         Ensured dynamic updates based on user input and handled errors gracefully.


  - Meet Dobariya:
  
        Features: Delete User, Signup Page, and Transaction History
  
        Delete User:
        Backend: Implemented DeleteUserHandler to allow authenticated users to delete their profiles from the database.
        Frontend: Integrated a button for users to delete their account under the User Details page.
        Redirected users to the Login page after successful deletion.
  
        Signup Page:
        Developed a clean Signup form for new users to register by entering Username, Password, Email, Balance, and Mobile Number.
        Connected the form with the backend API to store user data securely.
  
        Transaction History:
        Backend: Created a handler file to retrieve a user’s past transaction records.
        Frontend: Displayed transaction details dynamically in a table format.

