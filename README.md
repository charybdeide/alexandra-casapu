# alexandra-casapu

# Monefy Test Plan

## *Context*: 
I'm assuming I've joined the team and was asked to test the app to find possible problems with big business impact.  
Product website: http://www.monefy.me/


## *My mission*: Test Monefy as a QA Engineer

## *Strategy*:

- Start with a tour of the app to discover features
- Start with realistic usage - basic functions and flows around the features we market most:
> see your spendings distribution on the chart or get detailed information from the transactions list  
> manage categories, if defaults do not work for you  
> be safely synchronized using your own Dropbox account  
> choose the report period  
> select the currency  
> use budget mode  
> backup and export data in one click  
> 
> 2 ways to add records:  
> Within a few seconds  
> press “-” or “+” button at the bottom of the screen  
> enter amount  
> tap on the category icon  
>
> Or even faster!  
> tap on any category icon around the chart  
> enter the amount and hit “Add”  

- Define risks and decide which have big business impact 
- Use Domain testing for cases with big business impact - around features that we think will encourage users to buy the app if they work very well and are easy to use, features included in the paid version, and others
- Create tests for the claims the app makes - on their website, in the appstore
- Define personas and test more complex scenarios
- Find out if/what we want to automate in our context - for what do we need constant, fast feedback?
- Since it's already released, I can contact a variety of users and organize usability testing sessions/feedback sessions
- Consider scope of platforms/OSes - in my case I chose iOS(the challenge asked to focus on one platform) - test on different OS versions, different resolutions
- Test both the free and paid version - understand how the focus is different for each case
- Consider other possibly relevant quality criteria, like security, performance, compatibility, scalability

## *Some risks with possible high business impact*:

- Basic flows not working
- App is not easy to use
- Users can't buy the app
- Data gets aggregated incorrectly/corrupted
- We loose users data
- It does not store data safely(?)


## *Charters and Test Ideas/Cases with assigned priorities*

Priority assignment based on the risks defined above:  
:one:   - high priority  
:two:   - medium priority  
:three: - low priority  

### Charter: Explore the basic functionalities to better understand how/if they work 
---
- :one: download,install and open the app 

#### Main screen:
- :one: is it easy to figure out what categories are readily available?
- :one: is it easy to click on the icons and trigger an action?
- :one: is it intuitive to interact with the pie-chart?
- :one: add an expense - from the icons on the main screen
    - with note 
- :one: CRUD on expenses 
    - with both adding modes: clicking on categories icon or on - sign - are they consistent?
- :two: test the beginner guideline - after installing the app, see if the messages are helpful for figuring out how to do things - when I do what the tutorial says, does what they say happens happen?
- pie chart - is it accurate? 
    - :one: when adding expense records in only one category/a few/many/all 
    - :two: when having big differences - categories with very little expense, others with huge expense
    - :two: when having equal values for all categories
    - :one: in response to CRUD operations - after deleting, adding again, longer sequences
- how do you add income from the main screen? - no category for that?
    - :one: seems like only from the + sign - CRUD on income records

### Charter: Explore and perform CRUD on the Balance screen to find out if main functions work 
---
#### Balance screen
 - expand the lower side of the page  
    - :one: is information there accurate? - is it consistent with the one in the chart?
    - :one: explore the domain of the Balance variable
        - get to 0
        - value with decimals
        - large/small values
    - :one: how are many expenses/categories listed there?
    - :one: Is content updated here after each change?

### Charter: Do more in-depth testing for data input in expenses 
---
- see notes on calculator below
- :two: use the "delete character" button inside the sum input field
- :one: change account from the icon on the left in the sum input field - is it set correctly after saving?
- :two: change the date after filling in some data - is the input data persistent?
- :one: add realistic note - a sentence about what the money was spent on
- :two: add a longer note
- :two: a note with all kinds of available input chars - uppercase/lowercase, other alphabets, emojis, special chars
- :one: number expenses in a category - shown on the Balance screen - is the counter accurate? - when there are 0/some/many records

#### Calculator

There is a calculator embedded when inputting numbers - does it work correctly?
- :one: usability aspects - are the keys easy to press? Is the size of keys convenient for our target users?
- :one: how are approximations made? - do we store the data in floating point or fixed point data types? 
  - based on this info define tests with decimal values and number of decimals variations
  - seems like max decimals numbers is 2
    - to investigate if it is consistent throughout
    - see if I can get an output with more than 2, using operations
- :one: do every operation and check with an oracle(different calculator, inverse functions - e.g. multiply then divide, add/subtract)
- :one: test order of operations - do divisions/multiplications have priority?

### Charter: Explore menu items to find more capabilities and if they work
---
#### Menu
- Filter icon 
    - :two: accounts   
        - filter by certain accounts/all 
        - add more accounts to filter by
    - :one: choose time interval and see how it is reflected in the chart - I assigned high priority because "choose the report period" is listed as a main feature
        - Day, Week, Month, Year, 
        - specific day - past(recent/long time ago), today, future(near/distant), ok/cancel, special days - leap year end of February, days for which there is no data/many data
- Transfer icon 
    - :one: is it easy/intuitive how to make a transfer? 
        - does the order of fields required to fill in data make sense?
        - are the from/to accounts intuitively marked?                                                                    
        - is it possible/easy to correct mistakes - e.g. edit the amount/accounts selected?
        - can I undo? - does it work?
    - :one: choose a different date - ok/cancel
    - :one: validations on the sum 
        - negative/0/numbers with decimals?/very small/very large
        - see if there are validations in regards to a total sum available - can I define how much I have in an account? can I transfer more then I have in it?
        
- Hamburger menu item - opens a menu:
  - :one: is it easy to navigate between the menu items?
  - :one: is it easy to navigate between this menu and other app screens?
  - Categories
    - Expense
      - add new 
        - :two: seems like it should be available for the paid version only - search if it is available for free from another place in the app
        - for testing the paid version
          - :one: see if I can create a new category
          - :two: can I create a category with no name?
          - :two: weird category names? - special chars, other alphabets, emojis - how are those category names displayed in other places of the app?
          - :three: long category names
          - :three: can I use all the icons they've got, as it says in the feature description?
          - :three: create 2 categories with the same icon
          - :two: add many many categories - how do they show up on the main screen?
      - edit existing 
        - :two: edit the name - same ideas of input as for creating a new category
        - change the associated icon - it seems like it's a paid-version only feature               
            - :two: can I somehow change the icon with the free version?
            - :two: is it easy to choose a new icon?
            - :three: is there a relevant selection of symbols?
            - :two: does the change propagate throughout the app? on all synced devices?
            - :three: change the icon multiple times for the same category
      - delete existing
        - :two: delete - see if the change propagates in other places, like the front screen, the Balance screen, and on other synced devices
        - :two: can I undo?
        - :three: can I delete all?
    - Income
        - :one: add new - the same as for expenses
        - :two: edit existing - the same as for expenses
  - Accounts
    - Transfer 
      - :one: is it the same transfer screen as from the menu? is the functionality consistent with that one?
    - Add account 
      - :one: try some previously mentioned test ideas for the account name input field
      - :one: set currency - paid feature
        - set a different currency than the existing accounts - how are the calculations combined? what does the chart show?
        - exchange rate - find out what it does - do you set an exchange rate you want to be made, and the app shows you the data in only one currency?
      - :one: initial account balance 
        - set 0/a moderate amount/ a very big one/ with decimals/ can it be negative?
      - initial balance date - how does this value reflect in the application?
        - :two: set in the future 
          - how do transactions from this account for today look like? 
          - set initial account balance an amount larger than 0, set a starting date from next month - creating a transfer from this account to another - what will the balance look like?
      - :one: included in the balance flag - see its impact in the Balance screen - check if it's included, and if the calculations reflect the set amount
    - :one: Edit and delete - look at consequences
  - Currencies - paid only
    - is this the same curencies feature as in the Accounts? 
  - Settings
    - :two: passcode protection feature 
        - paid only
        - need to provide passcode when using app? when exactly is the passcode required? 
        - get app to background without closing it - see if passcode is required when accessing app
        - does the passcode propagate on other synced devices?
    - :three: review app - is it easy to leave a review - can help with marketing
    - :three: Export to file
        - see if I can export to csv with default delimiter and decimal separator
        - vary chars for delimiter and separator
        - does the .csv contain accurate data?
    - :one: Dropbox sync 
        - set up a Dropbox account
        - sync changes on multiple devices 
        - make changes in a sequence or in parallel - it seems realistic to consider 2 people using the same account, from the claim in the Synchronization feature description
            - simultaneous create, update, delete
            - create from one device - update from the other - check consequences on the first device
            - changes at the expense/income level, at the Categories level, at the Accounts level 
        - change the Dropbox account set initially
    - :one: Create backup 
        - for a small amount of data
        - for quite some data - how much would a user add in a month? what about a year?
        - is the backup created?
    - :one: Restore data
        - can backups created for the previous feature be restored?
        - what if I have more backups? 
            - is the one I choose restored?
    - :two: Clear data
        - does it get deleted on all synced devices?
        - is this compliant to the EU GDPR norms
        
### Charter: Explore flows through the app to test realistic use
---

- :one: test the download and install flow in different conditions, with interrupts: internet connection, software update, device shutdown(empty battery/restart), calls, SMSes, other apps opened
- :one: adding data - e.g. expense/transfer - are the fields consistent with other apps on the same platform(iOS), and can they be used in the same way? 
    - keyboard keys like return, delete, special characters, letters from non-english alphabets, emojis 
    - select/copy/paste
    - Back/Cancel buttons placement
- :one: buying the app 
    - is it easy/straightforward to purchase the app?
    - are the promised features present right away?
    - do the free features still work as before or consistently?
    - am I charged the claimed amount? - identify which are features of the App Store, and which are on our side
    - Restore the app - see if this works as for other apps on Apple devices
- :three: going from the paid version to the free one again - is it possible? - it says "unlimited use"
- :two: long term use - in relation to device resource usage 


### Other aspects to explore further
---

- :one:/:three: ads - I didn't see any in the app - find out when/how they are shown, if it blocks users from doing anything (These can have high or low priority, depending on the business model we have - do we rely on ads more for revenue, or on app purchases?)

- :two: focus on features which were changed recently, and around them - higher probability of bugs
    - current version:  Version 1.1.4
    - version history 
        - latest release 19 March 2018 - Synchronization improvements
        - release 13 March 2018:
            - 1. Pie chart became even more interactive - now you can see all your records for a category in one tap!
            - 2. Improved onboarding experience

- :two: How scalable do we want the app to be? - can we handle a big users number increase?


