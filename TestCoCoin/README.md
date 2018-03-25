# Automated tests for CoCoin 

### Setup

- copy the CoCoin.apk file in the project root
- set your device name value in the "deviceName" capability in the CocoinTests.java file, instead of "emulator-5554"
- use gradle to run the tests
    - on linux: `./gradlew clean test`
    - on windows: `./gradlew.bat clean test`
- you can check out the resulting report in build\reports\tests\test


Note: I will only be automating some example test cases on the UI level. I made this choice because I only got the .apk of the app, not the link to the github repo, so I'm inferring that lower level tests are out of scope.

### Prioritization criteria in terms of business impact:
- Flows type: main/secondary flows
    - if the flow is one of the main flows of the app(part of what the app is for), a failure will have a higher business impact. So for example, flows related to inserting and tracking expenses have a higher priority than ones related to customizing the profile
- Impact on performing core actions in the app
    - if there are no workarounds, the impact is higher
- Data handling
    - if data is lost and cannot be restored the impact is higher. For example if a crash can result in loosing all data inserted for a month it's much worse than if the data can be restored after crash. So if there are mechanisms to handle user data safely, issues like crashes can have a lower business impact.

It's difficult to come up with more relevant and specific ones without more context on the project environment.

Priority assignment:  
1️⃣ - high priority  
2️⃣ - medium priority  
3️⃣ - low priority  


### UI level:
---

### Smoke tests 
- :one: does the app open? - if it doesn't open at all, or crashes on open, that's bad
- :one: important flows:

*set password:*  
swipe 4 times   
insert password  
insert it again  
wait for app screen to load  
-> check that elements from the expenses insert screen are displayed
-> check that the correct activity is displayed

*insert expenses:*    
insert amount    
choose category    
press on checkmark  
-> check that money field was reset to 0

*check that expenses are inserted:*  
insert data  
click on hamburger menu item  
insert code  
-> correct activity displayed
-> total amount displayed
-> each amount displayed with correct corresponding category?

#### Pros for automating on the UI level:
We can do these on the UI level, to simulate closely the user's context. 

#### Cons for automating on the UI level:
Can take longer to run than on other levels.
Could fail for multiple reasons: device-related, appium-related, setup-related issues. That's because of the large number of layers/components we use: Devices(real/emulator), Appium (on top of Selenium)

#### Other levels:
It would be more difficult on the unit level to test everything together and understand if the whole system works.

### UI elements tests
- :one: are the graphs screens populated when they should be and not populated when they shouldn't?
- :one: do the menu items take users to the correct activities? - like Settings, Expense report, different views, help, about, feedback
- :two: do UI gestures work? - e.g. get the password screen through both swipe down and pressing on the menu item
- :three: are pop-ups/alerts displayed? For example:
    - when I want to sync the data I get a message that I need to sign up
    - when I press on a slice of the chart I get a pop-up with details on the category

#### Pros for automating on the UI level:
We can implement these on the UI level as well, to get information about user-like interaction with the app. 
If we can simulate user behavior as close as possible, we can get information on how usable it is. For example, is it difficult to swipe? Does the swipe need to be too long to trigger the action?

#### Cons for automating on the UI level:
As for the smoke tests

#### Other levels:
We can probably also create unit tests for some of the elements.
For example we can create unit tests for the gestures that are set to trigger certain actions, to make sure we set the gesture type correctly. I'm not sure how easy it is to get usability info on this level.
To test the actual display of a pop-up, or for how long it is displayed, I imagine is more difficult without using the UI.


### Unit tests
---

- :one: field validations
- :one: data-driven unit tests for the fields with money input

#### Pros for automating on the unit test level:
Unit tests run fast and we get fast feedback. This also means we can easily run all the tests we want to run.
It's easy to pinpoint failure cause, because we're testing components separately.

#### Cons for automating on the unit test level:
A risk I imagine(which depends on the project context), is that it might be more difficult to evaluate if the relevant cases have been tested.
For example, I can imagine a context where a developer doesn't think of some validation cases, because she did not take into account the context in which the user will use a field. It can be harder to imagine real use at this level, to have a black-box oriented mindset.

#### Other levels:
It can be slow to run these tests on the UI level, and more work to implement the setup in order to check what we want (in order to test a field I first have to open the app, and get to the screen that contains the field). 
It can be easier to think of interesting real-case use if starting from the user's perspective on the fields.


### Integration tests - not on the UI level
---
- :one: cloud sync - probably on the DB level
- :one: domain testing for consequences of input in charts - unit tests style

#### Pros for automating on the unit test level:
Again, unit tests run fast and we get fast feedback and the setup is easy.

#### Cons for automating on the unit test level:
As for unit tests, we can miss some relevant real-use cases because of the lack of black-box perspective, especially in the case of testing for consequences.

#### Other levels:
Testing components together can mean a lot of setup on the UI level, especially if we use third party services.


### Post-production testing - monitoring 
---
- :two: measure performance on large-scale use
- :two: get crash reports from users, parse and group them by error/exception

#### Pros for automating monitoring output analysis:
We can get insight into how the app behaves in real-life.

#### Cons for automating monitoring output analysis:
It could be difficult to make sens of a lot of data, so a risk is that we don't interpret it correctly. Or we miss collecting relevant outputs.

#### Other levels:
Data can be modeled to include randomness while simulating a realistic use to a certain extent also on the unit test level and for UI level tests. But it requires a lot of resources and effort to create a context very close to real use when testing on test environments.
And there will be unanticipated situations/conditions.
